package jfx.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HasBorrowed {
    private IntegerProperty bookCopyId = new SimpleIntegerProperty();
    private StringProperty bookTitle = new SimpleStringProperty();
    private StringProperty userLogin = new SimpleStringProperty();
    private StringProperty userEmail = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty LimitDate = new SimpleStringProperty();
    private StringProperty GiveBackDate = new SimpleStringProperty();

    public HasBorrowed() {

    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public String getUserLogin() {
        return userLogin.get();
    }

    public StringProperty userLoginProperty() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin.set(userLogin);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLimitDate() {
        return LimitDate.get();
    }

    public StringProperty LimitDateProperty() {
        return LimitDate;
    }

    public void setLimitDate(String LimitDate) {
        this.LimitDate.set(LimitDate);
    }

    public int getBookCopyId() {
        return bookCopyId.get();
    }

    public IntegerProperty bookCopyIdProperty() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId.set(bookCopyId);
    }

    public String getGiveBackDate() {
        return GiveBackDate.get();
    }

    public StringProperty giveBackDateProperty() {
        return GiveBackDate;
    }

    public void setGiveBackDate(String giveBackDate) {
        this.GiveBackDate.set(giveBackDate);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }
}
