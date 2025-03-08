package com.library.model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;




public class Utilisateur {

    private StringProperty Login =  new SimpleStringProperty();
    private StringProperty LastName = new SimpleStringProperty();
    private StringProperty FirstName =  new SimpleStringProperty();
    private StringProperty emailAddress = new SimpleStringProperty();
    private StringProperty HashedPassword =  new SimpleStringProperty();
    private StringProperty Category = new SimpleStringProperty();
    private IntegerProperty maxBooks = new SimpleIntegerProperty();
    private IntegerProperty maxDays = new SimpleIntegerProperty();



    //constructor
    public Utilisateur(){
    }


    //Login
    public String getLogin() {
        return Login.get();
    }

    public StringProperty loginProperty() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login.set(login);
    }

    //LastName
    public String getLastName() {
        return LastName.get();
    }

    public StringProperty LastNameProperty() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName.set(LastName);
    }


    //FirstName
    public String getFirstName() {
        return FirstName.get();
    }

    public StringProperty FirstNameProperty() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName.set(FirstName);
    }


    //User EmailAddress
    public String getEmailAddress() {
        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }


    //HashedPassword
    public String getHashedPassword() {
        return HashedPassword.get();
    }

    public StringProperty hashedPasswordProperty() {
        return HashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.HashedPassword.set(hashedPassword);
    }


    //Category
    public String getCategory() {
        return Category.get();
    }

    public StringProperty categoryProperty() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category.set(category);
    }


    //maxBooks
    public int getMaxBooks() {
        return maxBooks.get();
    }

    public IntegerProperty maxBooksProperty() {
        return maxBooks;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks.set(maxBooks);
    }


    //maxDays
    public int getMaxDays() {
        return maxDays.get();
    }

    public IntegerProperty maxDaysProperty() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays.set(maxDays);
    }


}
