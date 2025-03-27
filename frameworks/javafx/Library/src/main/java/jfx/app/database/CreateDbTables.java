package jfx.app.database;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDbTables {

    public static void main(String[] args) {
        String DbName = "libraryDb";
        String password = "ocS-+o{$W0";
        long start = System.currentTimeMillis();
        createDb("" + DbName, "" + password);
        createTables("" + DbName, "" + password);
        System.out.println("The program takes " + (System.currentTimeMillis() - start) + "ms");

        try {
            UserDb.AddUser("javafx", "javafx", "javafx", "javafx@java.oracle", "password", "M");

            UserDb.AddUser("java_A", "java_A", "java_A", "java_A@java.oracle", "password", "A");
            UserDb.AddUser("java_B", "java_B", "java_B", "java_B@java.oracle", "password", "B");
            UserDb.AddUser("java_C", "java_C", "java_C", "java_C@java.oracle", "password", "C");
            UserDb.AddUser("java_banned", "java_banned", "java_banned", "java_banned@java.oracle", "password", "S");
            System.out.println("Users javafx, java_A, java_B, java_C, java_banned have been created successfully");

            fillBookTable("Things Fall Apart", "1958",
                    "A story of traditional Igbo society in the face of colonialism.", "Chinua Achebe", "1930", 1,
                    "978-0385474542", "Heinemann");

            fillBookTable("The Beautiful Ones Are Not Yet Born", "1968",
                    "A tale of post-colonial Ghana, grappling with corruption.", "Ayi Kwei Armah", "1939", 2,
                    "978-0877287553", "Heinemann");

            fillBookTable("So Long a Letter", "1979",
                    "A woman's reflections on marriage, friendship, and the changing roles of women.", "Mariama Bâ",
                    "1929", 3, "978-0852553536", "Heinemann");

            fillBookTable("Season of Migration to the North", "1966",
                    "A powerful tale about identity, colonialism, and post-colonial conflict.", "Tayeb Salih", "1929",
                    4, "978-0889224670", "Heinemann");

            fillBookTable("The Joys of Motherhood", "1979",
                    "A novel about the pressures of motherhood and tradition in Nigeria.", "Buchi Emecheta", "1944", 5,
                    "978-0141181844", "Heinemann");

            fillBookTable("No Sweetness Here", "1970",
                    "A collection of short stories about the complexities of African life.", "Ama Ata Aidoo", "1940", 6,
                    "978-0435904722", "Heinemann");

            fillBookTable("The Palm-Wine Drinkard", "1952",
                    "A surreal narrative about the quest for a palm-wine tapper.", "Amos Tutuola", "1920", 7,
                    "978-0435974722", "Heinemann");

            fillBookTable("God's Bits of Wood", "1960", "A powerful story of a workers' strike in colonial Senegal.",
                    "Sembène Ousmane", "1923", 8, "978-0852554236", "Heinemann");

            fillBookTable("Petals of Blood", "1977",
                    "A powerful novel about post-independence Kenya and its political struggles.", "Ngũgĩ wa Thiong'o",
                    "1938", 9, "978-0852554809", "Heinemann");

            fillBookTable("The Fishermen", "2015",
                    "A gripping novel about four brothers and the consequences of a prophecy.", "Chigozie Obioma",
                    "1986", 10, "978-1328519356", "Little, Brown and Company");

            System.out.println("The 10 books where added successfully.");
        } catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillBookTable(String title, String firstEditionYear, String description, String author,
            String authorBirthYear, Integer itemId, String isbn, String editor)
            throws ClassNotFoundException, SQLException {
        BookDb.AddBook(title, firstEditionYear, description);
        BookDb.AddAuthor(author, authorBirthYear);
        BookDb.AddEditor(editor, isbn);
        BookDb.AddBookCopy(itemId, title, firstEditionYear, isbn);
        int id = BookDb.getAuthorId(author, authorBirthYear);
        BookDb.updateHasWritten(title, firstEditionYear, id);
    }

    public static void createTables(String DbName, String password) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DbName, "root",
                    "" + password)) {
                System.out.println("You are connected to the database " + DbName + "!");

                Statement stmt0 = con.createStatement();
                String sql0 = "CREATE TABLE IF NOT EXISTS  Author (Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, AuthorName VARCHAR(50), Birthdate INT);";
                stmt0.executeUpdate(sql0);

                System.out.println("Table Author created successfully!");

                Statement stmt1 = con.createStatement();
                String sql1 = "CREATE TABLE IF NOT EXISTS  Book (Title VARCHAR(255), FirstYearEdition VARCHAR(10), Description VARCHAR(255), PRIMARY KEY(Title, FirstYearEdition));";
                stmt1.executeUpdate(sql1);

                System.out.println("Table Book created successfully!");

                Statement stmt2 = con.createStatement();
                String sql2 = "CREATE TABLE IF NOT EXISTS  Editor (ISBN VARCHAR(255) PRIMARY KEY, EditorName VARCHAR(30));";
                stmt2.executeUpdate(sql2);
                System.out.println("Table Editor created successfully!");

                Statement stmt3 = con.createStatement();
                String sql3 = "CREATE TABLE IF NOT EXISTS  User (Login VARCHAR(30) PRIMARY KEY, LastName VARCHAR(30), FirstName VARCHAR(30), EmailAddress VARCHAR(255), HashedPassword VARCHAR(255), Category CHAR(1), maxBooks INT, maxDays INT);";
                stmt3.executeUpdate(sql3);
                System.out.println("Table User successfully!");

                Statement stmt4 = con.createStatement();
                String sql4 = "CREATE TABLE IF NOT EXISTS  HasWritten (AuthorId INT , BookTitle VARCHAR(255), BookFirstYearEdition VARCHAR(10), FOREIGN KEY (BookTitle, BookFirstYearEdition) REFERENCES Book (Title, FirstYearEdition), FOREIGN KEY (AuthorId) REFERENCES Author (Id), PRIMARY KEY (AuthorId, BookTitle, BookFirstYearEdition));";
                stmt4.executeUpdate(sql4);
                System.out.println("Table HasWritten created successfully!");

                Statement stmt5 = con.createStatement();
                String sql5 = "CREATE TABLE IF NOT EXISTS  UserCategory (UserLogin VARCHAR(30), ManagerLogin VARCHAR(30), Date DATE, NewCategory CHAR(1), FOREIGN KEY (UserLogin) REFERENCES User (Login), FOREIGN KEY (ManagerLogin) REFERENCES User (Login));";
                stmt5.executeUpdate(sql5);

                System.out.println("Table UserCategory successfully!");

                Statement stmt6 = con.createStatement();
                String sql6 = "CREATE TABLE IF NOT EXISTS  Item (CopyId INT PRIMARY KEY, BookTitle VARCHAR(255), BookFirstYearEdition VARCHAR(10), EditorISBN VARCHAR(255), FOREIGN KEY (EditorISBN) REFERENCES Editor(ISBN), FOREIGN KEY (BookTitle, BookFirstYearEdition) REFERENCES Book (Title, FirstYearEdition));";
                stmt6.executeUpdate(sql6);

                System.out.println("Table Item successfully!");

                Statement stmt7 = con.createStatement();
                String sql7 = "CREATE TABLE IF NOT EXISTS  HasBorrowed (BookCopyId INT, BorrowerLogin VARCHAR(30), BorrowingDate Date, LimitDate Date, GiveBackDate Date, FOREIGN KEY (BookCopyId) REFERENCES Item (CopyId), FOREIGN KEY (BorrowerLogin) REFERENCES User (Login))";
                stmt7.executeUpdate(sql7);
                // PRIMARY KEY (BookCopyId, BorrowerLogin)
                System.out.println("Table Item created successfully!");
            }
            System.out.println("All the Tables have been successfully created...");

        } catch (Exception e) {
            System.exit(1);
        }
    }

    public static void createDb(String DbName, String password) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "" + password);
            System.out.println("You are connected to server!");

            Statement stmt = con.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS " + DbName;
            stmt.executeUpdate(sql);

            System.out.println("Database " + DbName + " created successfully!");

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
