package jfx.app.database;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDbTables {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
        String DbName = "libraryDb";
        String password = "ocS-+o{$W0";
        long start = System.currentTimeMillis();
        createDb("" + DbName, "" + password);
        createTables("" + DbName, "" + password);
        System.out.println("The program takes " + (System.currentTimeMillis() - start) + "ms");

        UserDb.AddUser("javafx", "javafx", "javafx", "javafx@java.oracle", "password", "M");
        UserDb.AddUser("java_A", "java_A", "java_A", "java_A@java.oracle", "password", "A");
        UserDb.AddUser("java_B", "java_B", "java_B", "java_B@java.oracle", "password", "B");
        UserDb.AddUser("java_C", "java_C", "java_C", "java_C@java.oracle", "password", "C");
        UserDb.AddUser("java_banned", "java_banned", "java_banned", "java_banned@java.oracle", "password", "S");
        System.out.println("Users javafx, java_A, java_B, java_C, java_banned have been created successfully");


        fillBookTable("Efuru", "1966", "A novel about a strong Igbo woman defying societal expectations.",
                "Flora Nwapa", "1931", "978-0435909727", "Heinemann");
        fillBookTable("The Joys of Motherhood", "1979",
                "A poignant novel about the struggles of a Nigerian woman in a changing society.",
                "Buchi Emecheta", "1944", "978-0807616230", "George Braziller");
        fillBookTable("The Old Man and the Medal", "1956", "A satirical novel about colonial injustice in Cameroon.",
                "Ferdinand Oyono", "1929", "978-0435905408", "Heinemann");
        fillBookTable("The Radiance of the King", "1954",
                "A philosophical novel exploring race and identity in colonial Africa.",
                "Camara Laye", "1928", "978-1590171349", "NYRB Classics");
        fillBookTable("The Famished Road", "1991", "A magical realism novel about a spirit child in Nigeria.",
                "Ben Okri", "1959", "978-0385425131", "Jonathan Cape");
        fillBookTable("Woman at Point Zero", "1975", "A feminist novel exploring oppression and resilience.",
                "Nawal El Saadawi", "1931", "978-1783605941", "Zed Books");
        fillBookTable("Ancestral Sacrifice", "1971", "A Ghanaian play about tradition and modernity.",
                "Joe de Graft", "1924", "978-9964971698", "Longman");
        fillBookTable("The Healers", "1979", "A historical novel about resistance to colonialism in Ghana.",
                "Ayi Kwei Armah", "1939", "978-0435902810", "Heinemann");
        fillBookTable("Bound to Violence", "1968", "A novel critiquing power structures in postcolonial Africa.",
                "Yambo Ouologuem", "1940", "978-0435901318", "Heinemann");
        fillBookTable("Waiting for an Angel", "2002",
                "A novel about political oppression in Nigeria under military rule.",
                "Helon Habila", "1967", "978-0393325111", "W.W. Norton & Company");

        System.out.println("The 10 books where added successfully.");

    }

    public static void fillBookTable(String title, String firstEditionYear, String description, String author,
            String authorBirthYear, String isbn, String editor) throws ClassNotFoundException, SQLException {
        BookDb.AddBook(title, firstEditionYear, description);
        BookDb.AddAuthor(author, authorBirthYear);
        BookDb.AddBookCopy(isbn, title, firstEditionYear, editor);
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
                String sql1 = "CREATE TABLE IF NOT EXISTS  Book (Title VARCHAR(255), FirstYearEdition Int, Description VARCHAR(255), PRIMARY KEY(Title, FirstYearEdition));";
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
                String sql4 = "CREATE TABLE IF NOT EXISTS  HasWritten (AuthorId INT , BookTitle VARCHAR(255), BookFirstYearEdition Int, FOREIGN KEY (BookTitle, BookFirstYearEdition) REFERENCES Book (Title, FirstYearEdition), FOREIGN KEY (AuthorId) REFERENCES Author (Id), PRIMARY KEY (AuthorId, BookTitle, BookFirstYearEdition));";
                stmt4.executeUpdate(sql4);

                System.out.println("Table HasWritten created successfully!");

                Statement stmt5 = con.createStatement();
                String sql5 = "CREATE TABLE IF NOT EXISTS  changeCategory (UserLogin VARCHAR(30), ManagerLogin VARCHAR(30), Date DATE, NewCategory CHAR(1), FOREIGN KEY (UserLogin) REFERENCES User (Login), FOREIGN KEY (ManagerLogin) REFERENCES User (Login));";
                stmt5.executeUpdate(sql5);

                System.out.println("Table changeCategory successfully!");

                Statement stmt6 = con.createStatement();
                String sql6 = "CREATE TABLE IF NOT EXISTS  Item (CopyId INT PRIMARY KEY, BookTitle VARCHAR(255), BookFirstYearEdition INT, EditorISBN VARCHAR(255), FOREIGN KEY (EditorISBN) REFERENCES Editor(ISBN), FOREIGN KEY (BookTitle, BookFirstYearEdition) REFERENCES Book (Title, FirstYearEdition));";
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
