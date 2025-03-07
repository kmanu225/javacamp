package com.library.model;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import static com.library.model.DbUtils.dbExecuteQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UtilisateurDb {
    public static void AddUser(String login, String lastName, String firstName, String emailAddress, String password, String category) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Integer maxBooks = new Category().getMaxBooks(category);
        Integer maxDays = new Category().getMaxDays(category);
        DbUtils.dbExecuteUpdate("""
                                INSERT INTO Utilisateur (Login, LastName, FirstName, EmailAddress, HashedPassword, Category, maxBooks, maxDays)
                                VALUES ('"""+login+"','"+lastName+"','" +firstName+ "','" +emailAddress+ "','" + Password.sha256(password)+ "','" +category+ "'," +maxBooks+ "," +maxDays+")");
    }
    public static void updateCategory(String userLogin, String managerLogin, String category) throws SQLException, ClassNotFoundException {
        Integer maxBooks = new Category().getMaxBooks(category);
        Integer maxDays = new Category().getMaxDays(category);
        DbUtils.dbExecuteUpdate("""
                                UPDATE Utilisateur
                                SET Category ='""" +category+"', maxBooks = "+maxBooks+", maxDays = "+maxDays+"\n"+
                "WHERE Login= '"+userLogin+"'");


        LocalDate date = LocalDate.now();
       // System.out.println(date);

        DbUtils.dbExecuteUpdate("""
                                INSERT INTO changeCategory (UserLogin, ManagerLogin, Date, NewCategory)
                                VALUES ('"""+userLogin+"','"+managerLogin+"','" +date+ "','" +category+"')");
    }
    public static Utilisateur searchUser(String userLogin, String userName, String userSurname, String userEmail) throws SQLException, ClassNotFoundException {
        if(!Objects.equals(userLogin, "")){
            ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Utilisateur.Login = '"+userLogin+"'");
            return buildUser(rs);
        }

        else if(!Objects.equals(userName, "")){
            ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Utilisateur.FirstName = '"+userName+"'");
            return buildUser(rs);
        }

        else if(!Objects.equals(userSurname, "")){
            ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Utilisateur.LastName = '"+userSurname+"'");
            return buildUser(rs);
        }

        else if(!Objects.equals(userEmail, "")){
            ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Utilisateur.EmailAddress = '"+userEmail+"'");
            return buildUser(rs);
        }
        return null;
    }


    public static Utilisateur searchUser(String userLogin) throws SQLException, ClassNotFoundException {
        ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Utilisateur.Login = '"+userLogin+"'");
        return buildUser(rs);
    }


    private static Utilisateur buildUser(ResultSet rs) throws SQLException {
        Utilisateur user = new Utilisateur();
        if(rs.next()){
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

    public static ObservableList<Utilisateur> searchUsers() throws SQLException, ClassNotFoundException {
        ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur");
        return getUsers(rs);
    }

    private static ObservableList<Utilisateur> getUsers(ResultSet rs) throws SQLException {
        ObservableList<Utilisateur> users;
        users = FXCollections.observableArrayList();

        while (rs.next()) {
            Utilisateur user = new Utilisateur();
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
        DbUtils.dbExecuteUpdate("DELETE FROM Utilisateur WHERE Login='"+login+"';");
    }

    public static boolean checkExistence(String login, String name, String surname, String email) throws SQLException, ClassNotFoundException {
        ResultSet rs1 = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Utilisateur WHERE Login = '"+login+"'");
        ResultSet rs2 = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Utilisateur WHERE FirstName = '"+name+"'");
        ResultSet rs3 = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Utilisateur WHERE LastName = '"+surname+"'");
        ResultSet rs4 = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Utilisateur WHERE EmailAddress = '"+email+"'");
        if(rs1.next() && !Objects.equals(login, "")){
            //System.out.println(rs1.getString(1));
            return (rs1.getInt(1) == 1);
        }

        if(rs2.next() && !Objects.equals(name, "")){
            //System.out.println(rs2.getString(1));
            return (rs2.getInt(1) == 1);
        }

        if(rs3.next() && !Objects.equals(surname, "")){
            //System.out.println(rs3.getString(1));
            return (rs3.getInt(1) == 1);
        }

        if(rs4.next() && !Objects.equals(email, "")){
            //System.out.println(rs4.getString(1));
            return (rs4.getInt(1) == 1);
        }


        return false;
    }


    public static boolean checkExistence(String login) throws SQLException, ClassNotFoundException {
        ResultSet rs1 = DbUtils.dbExecuteQuery(" SELECT COUNT(1) FROM Utilisateur WHERE Login = '"+login+"'");
        if(rs1.next()){
            return (rs1.getInt(1) == 1);
        }
        return false;
    }

    public static ObservableList<Utilisateur> getBlackList() throws SQLException, ClassNotFoundException {
        ResultSet rs = dbExecuteQuery("SELECT * FROM Utilisateur WHERE Category = 'S'");
        return getUsers(rs);
    }



}