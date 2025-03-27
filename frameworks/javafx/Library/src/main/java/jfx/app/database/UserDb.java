package jfx.app.database;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static jfx.app.database.DbUtils.getConn;

import jfx.app.model.UserCategory;
import jfx.app.model.Password;
import jfx.app.model.User;

public class UserDb {
    public static void AddUser(String login, String lastName, String firstName, String emailAddress, String password,
            String category) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        String query = """
                INSERT INTO User (Login, LastName, FirstName, EmailAddress, HashedPassword, Category, maxBooks, maxDays)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        try (Connection conn = getConn();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Compute maxBooks and maxDays based on category
            Integer maxBooks = new UserCategory().getMaxBooks(category);
            Integer maxDays = new UserCategory().getMaxDays(category);

            // Set values in the prepared statement
            pstmt.setString(1, login);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, emailAddress);
            pstmt.setString(5, Password.sha256(password)); // Secure password hashing
            pstmt.setString(6, category);
            pstmt.setInt(7, maxBooks);
            pstmt.setInt(8, maxDays);

            // Execute the update
            pstmt.executeUpdate();
        }
    }

    public static void updateCategory(String userLogin, String managerLogin, String category)
            throws SQLException, ClassNotFoundException {

        String updateQuery = """
                UPDATE User
                SET Category = ?, maxBooks = ?, maxDays = ?
                WHERE Login = ?""";

        String insertQuery = """
                INSERT INTO changeCategory (UserLogin, ManagerLogin, Date, NewCategory)
                VALUES (?, ?, ?, ?)""";

        try (Connection conn = getConn(); // Use local function instead of Database.getConnection()
                PreparedStatement pstmtUpdate = conn.prepareStatement(updateQuery);
                PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery)) {

            // Compute maxBooks and maxDays based on the new category
            Integer maxBooks = new UserCategory().getMaxBooks(category);
            Integer maxDays = new UserCategory().getMaxDays(category);

            // Update the User category
            pstmtUpdate.setString(1, category);
            pstmtUpdate.setInt(2, maxBooks);
            pstmtUpdate.setInt(3, maxDays);
            pstmtUpdate.setString(4, userLogin);
            pstmtUpdate.executeUpdate();

            // Insert the changeCategory record
            LocalDate date = LocalDate.now();
            pstmtInsert.setString(1, userLogin);
            pstmtInsert.setString(2, managerLogin);
            pstmtInsert.setDate(3, Date.valueOf(date));
            pstmtInsert.setString(4, category);
            pstmtInsert.executeUpdate();
        }
    }

    // // Example of the local function for getting the connection
    // private static Connection getConn() throws SQLException,
    // ClassNotFoundException {
    // String url = "jdbc:mysql://localhost:3306/your_database";
    // String user = "your_username";
    // String password = "your_password";
    // Class.forName("com.mysql.cj.jdbc.Driver");
    // return DriverManager.getConnection(url, user, password);
    // }

    public static User searchUser(String userLogin, String userName, String userSurname, String userEmail)
            throws SQLException, ClassNotFoundException {

        String query = "";
        PreparedStatement pstmt = null;

        if (!Objects.equals(userLogin, "")) {
            query = "SELECT * FROM User WHERE Login = ?";
            pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, userLogin);
        } else if (!Objects.equals(userName, "")) {
            query = "SELECT * FROM User WHERE FirstName = ?";
            pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, userName);
        } else if (!Objects.equals(userSurname, "")) {
            query = "SELECT * FROM User WHERE LastName = ?";
            pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, userSurname);
        } else if (!Objects.equals(userEmail, "")) {
            query = "SELECT * FROM User WHERE EmailAddress = ?";
            pstmt = getConn().prepareStatement(query);
            pstmt.setString(1, userEmail);
        }

        if (pstmt != null) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildUser(rs);
                }
            }
        }
        return null;
    }

    public static User searchUser(String userLogin) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM User WHERE Login = ?";

        try (Connection conn = getConn();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userLogin);

            try (ResultSet rs = pstmt.executeQuery()) {
                return buildUser(rs);
            }
        }

    }

    private static User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        if (rs.next()) {
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setLogin(rs.getString("Login"));
            user.setEmailAddress(rs.getString("EmailAddress"));
            user.setHashedPassword(rs.getString("HashedPassword"));
            user.setCategory(rs.getString("Category"));
            user.setMaxBooks(rs.getInt("maxBooks"));
            user.setMaxDays(rs.getInt("maxDays"));
        }
        return user;
    }

    public static ObservableList<User> searchUsers() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM User";

        try (Connection conn = getConn();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            return getUsers(rs);
        }
    }

    private static ObservableList<User> getUsers(ResultSet rs) throws SQLException {
        ObservableList<User> users;
        users = FXCollections.observableArrayList();

        while (rs.next()) {
            User user = new User();
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setLogin(rs.getString("Login"));
            user.setEmailAddress(rs.getString("EmailAddress"));
            user.setHashedPassword(rs.getString("HashedPassword"));
            user.setCategory(rs.getString("Category"));
            user.setMaxBooks(rs.getInt("maxBooks"));
            user.setMaxDays(rs.getInt("maxDays"));

            users.add(user);
        }
        return users;
    }

    public static void deleteUser(String login) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM User WHERE Login = ?";

        try (Connection conn = getConn();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, login);
            pstmt.executeUpdate();
        }
    }

    public static boolean checkUserExistence(String login, String name, String surname, String email)
            throws SQLException, ClassNotFoundException {

        String query1 = "SELECT COUNT(1) FROM User WHERE Login = ?";
        String query2 = "SELECT COUNT(1) FROM User WHERE FirstName = ?";
        String query3 = "SELECT COUNT(1) FROM User WHERE LastName = ?";
        String query4 = "SELECT COUNT(1) FROM User WHERE EmailAddress = ?";

        try (Connection conn = getConn();
                PreparedStatement pstmt1 = conn.prepareStatement(query1);
                PreparedStatement pstmt2 = conn.prepareStatement(query2);
                PreparedStatement pstmt3 = conn.prepareStatement(query3);
                PreparedStatement pstmt4 = conn.prepareStatement(query4)) {

            pstmt1.setString(1, login);
            pstmt2.setString(1, name);
            pstmt3.setString(1, surname);
            pstmt4.setString(1, email);

            try (ResultSet rs1 = pstmt1.executeQuery();
                    ResultSet rs2 = pstmt2.executeQuery();
                    ResultSet rs3 = pstmt3.executeQuery();
                    ResultSet rs4 = pstmt4.executeQuery()) {

                if (rs1.next() && !Objects.equals(login, "")) {
                    return rs1.getInt(1) == 1;
                }

                if (rs2.next() && !Objects.equals(name, "")) {
                    return rs2.getInt(1) == 1;
                }

                if (rs3.next() && !Objects.equals(surname, "")) {
                    return rs3.getInt(1) == 1;
                }

                if (rs4.next() && !Objects.equals(email, "")) {
                    return rs4.getInt(1) == 1;
                }
            }
        }

        return false;
    }

    public static boolean checkUserExistence(String login) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM User WHERE Login = ?";
    
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, login);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            }
        }
        return false;
    }
    

    public static ObservableList<User> getBlackList() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM User WHERE Category = ?";

        try (Connection conn = getConn();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "S");

            try (ResultSet rs = pstmt.executeQuery()) {
                return getUsers(rs);
            }
        }
    }

}