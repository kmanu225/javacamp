package jfx.app.database;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static jfx.app.database.DbUtils.dbExecuteQuery;
import static jfx.app.database.DbUtils.dbExecuteUpdate;
import jfx.app.model.UserCategory;
import jfx.app.model.Password;
import jfx.app.model.User;



public class UserDb {
    public static void AddUser(String login, String lastName, String firstName, String emailAddress, String password,
            String category) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Integer maxBooks = new UserCategory().getMaxBooks(category);
        Integer maxDays = new UserCategory().getMaxDays(category);
        dbExecuteUpdate("""
                INSERT INTO User (Login, LastName, FirstName, EmailAddress, HashedPassword, Category, maxBooks, maxDays)
                VALUES ('""" + login + "','" + lastName + "','" + firstName + "','" + emailAddress + "','"
                + Password.sha256(password) + "','" + category + "'," + maxBooks + "," + maxDays + ")");
    }

    public static void updateCategory(String userLogin, String managerLogin, String category)
            throws SQLException, ClassNotFoundException {
        Integer maxBooks = new UserCategory().getMaxBooks(category);
        Integer maxDays = new UserCategory().getMaxDays(category);
        dbExecuteUpdate("""
                UPDATE User
                SET Category ='""" + category + "', maxBooks = " + maxBooks + ", maxDays = " + maxDays + "\n" +
                "WHERE Login= '" + userLogin + "'");

        LocalDate date = LocalDate.now();

        dbExecuteUpdate("""
                INSERT INTO changeCategory (UserLogin, ManagerLogin, Date, NewCategory)
                VALUES ('""" + userLogin + "','" + managerLogin + "','" + date + "','" + category + "')");
    }

    public static User searchUser(String userLogin, String userName, String userSurname, String userEmail)
            throws SQLException, ClassNotFoundException {
        if (!Objects.equals(userLogin, "")) {
            ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE User.Login = '" + userLogin + "'");
            return buildUser(rs);
        }

        else if (!Objects.equals(userName, "")) {
            ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE User.FirstName = '" + userName + "'");
            return buildUser(rs);
        }

        else if (!Objects.equals(userSurname, "")) {
            ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE User.LastName = '" + userSurname + "'");
            return buildUser(rs);
        }

        else if (!Objects.equals(userEmail, "")) {
            ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE User.EmailAddress = '" + userEmail + "'");
            return buildUser(rs);
        }
        return null;
    }

    public static User searchUser(String userLogin) throws SQLException, ClassNotFoundException {
        ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE User.Login = '" + userLogin + "'");
        return buildUser(rs);
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
        ResultSet rs = dbExecuteQuery("SELECT * FROM User");
        return getUsers(rs);
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
        dbExecuteUpdate("DELETE FROM User WHERE Login='" + login + "';");
    }

    public static boolean checkExistence(String login, String name, String surname, String email)
            throws SQLException, ClassNotFoundException {
        ResultSet rs1 = dbExecuteQuery(" SELECT COUNT(1) FROM User WHERE Login = '" + login + "'");
        ResultSet rs2 = dbExecuteQuery(" SELECT COUNT(1) FROM User WHERE FirstName = '" + name + "'");
        ResultSet rs3 = dbExecuteQuery(" SELECT COUNT(1) FROM User WHERE LastName = '" + surname + "'");
        ResultSet rs4 = dbExecuteQuery(" SELECT COUNT(1) FROM User WHERE EmailAddress = '" + email + "'");
        if (rs1.next() && !Objects.equals(login, "")) {
            // System.out.println(rs1.getString(1));
            return (rs1.getInt(1) == 1);
        }

        if (rs2.next() && !Objects.equals(name, "")) {
            return (rs2.getInt(1) == 1);
        }

        if (rs3.next() && !Objects.equals(surname, "")) {
            return (rs3.getInt(1) == 1);
        }

        if (rs4.next() && !Objects.equals(email, "")) {
            return (rs4.getInt(1) == 1);
        }

        return false;
    }

    public static boolean checkExistence(String login) throws SQLException, ClassNotFoundException {
        ResultSet rs1 = dbExecuteQuery(" SELECT COUNT(1) FROM User WHERE Login = '" + login + "'");
        if (rs1.next()) {
            return (rs1.getInt(1) == 1);
        }
        return false;
    }

    public static ObservableList<User> getBlackList() throws SQLException, ClassNotFoundException {
        ResultSet rs = dbExecuteQuery("SELECT * FROM User WHERE Category = 'S'");
        return getUsers(rs);
    }

}