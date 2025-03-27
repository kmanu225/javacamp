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
