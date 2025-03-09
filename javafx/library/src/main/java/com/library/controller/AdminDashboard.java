package com.library.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import com.library.model.BookCopy;
import com.library.model.BookDb;
import com.library.model.HasBorrowed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminDashboard {

    public TextField BookTitle;
    public TextField BookEditor;
    public TextField BookAuthor;
    public TextField BookId;
    public Label resultArea;
    public CheckBox checkBoxAvailableBooks;
    public CheckBox CheckBoxBorrowedBooks;
    public CheckBox CheckBoxAllLoans;
    public TableView<BookCopy> BooksTable;
    public TableColumn<BookCopy, String> TableBookTitle;
    public TableColumn<BookCopy, String> TableAuthor;
    public TableColumn<BookCopy, String> TableEditor;
    public TableColumn<BookCopy, String> TableBookDescription;
    public TableColumn<BookCopy, Integer> TableBookIdAvailable;

    public TableView<HasBorrowed> LoansTable;
    public TableColumn<HasBorrowed, Integer> LoanTableBookId;
    public TableColumn<HasBorrowed, String> LoanTableBookTitle;
    public TableColumn<HasBorrowed, String> LoanTableUserEmail;
    public TableColumn<HasBorrowed, String> LoanTableFirstName;
    public TableColumn<HasBorrowed, String> LoanTableLastName;
    public TableColumn<HasBorrowed, String> LoanTableLimitDate;
    public TableColumn<HasBorrowed, String> LoanTableReturnDate;
    public Label MyName;
    public Label MySurname;
    public Label MyCategory;
    public Label login;

    @FXML
    private void initialize() {
        TableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        TableAuthor.setCellValueFactory(cellData -> cellData.getValue().authorNameProperty());
        TableEditor.setCellValueFactory(cellData -> cellData.getValue().editorNameProperty());
        TableBookDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        TableBookIdAvailable.setCellValueFactory(cellData -> cellData.getValue().copyIdProperty().asObject());

        LoanTableBookId.setCellValueFactory(cellData -> cellData.getValue().bookCopyIdProperty().asObject());
        LoanTableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        LoanTableUserEmail.setCellValueFactory(cellData -> cellData.getValue().userEmailProperty());
        LoanTableLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        LoanTableFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        LoanTableLimitDate.setCellValueFactory(cellData -> cellData.getValue().LimitDateProperty());
        LoanTableReturnDate.setCellValueFactory(cellData -> cellData.getValue().giveBackDateProperty());

    }

    public void goToAdminDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void goToUsersPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("ManageUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(App.getResourceOrNull(("ManageUsers.css"))));
        stage.setTitle("UsersInformation");
        stage.setScene(scene);
        stage.show();

    }

    public void goToBooksPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("ManageBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(App.getResourceOrNull(("ManageBooks.css"))));
        stage.setTitle("BooksInformationPage");
        stage.setScene(scene);
        stage.show();

    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(App.getResourceOrNull(("LoginPage.css"))));
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.show();

    }

    public void SearchBook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.setText("");
        if (!Objects.equals(this.BookTitle.getText(), "") || !Objects.equals(this.BookAuthor.getText(), "")
                || !Objects.equals(this.BookEditor.getText(), "")) {

            if (BookDb.checkBookExistence(this.BookTitle.getText(), this.BookAuthor.getText(),
                    this.BookEditor.getText())) {
                ObservableList<BookCopy> bookCopies = FXCollections.observableArrayList();
                bookCopies.add(BookDb.searchBook(this.BookTitle.getText(), this.BookAuthor.getText(),
                        this.BookEditor.getText()));
                BooksTable.setItems(bookCopies);
            } else {
                this.resultArea.setText("Book not found!");
            }
        } else {
            this.resultArea.setText("Fill all the fields please.");
        }
    }

    public void checkAvailableBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.checkBoxAvailableBooks.isSelected()) {
            this.BooksTable.setItems(null);
            ObservableList<BookCopy> bookCopies = BookDb.searchAvailableBooks();
            BooksTable.setItems(bookCopies);
        } else {
            this.BooksTable.setItems(null);
        }
    }

    public void CheckBoxSeeMyBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        if (this.CheckBoxBorrowedBooks.isSelected()) {
            CheckBoxAllLoans.setSelected(false);
            ObservableList<HasBorrowed> hasBorrowed = BookDb.searchBorrowedBooksByMe(gateway.getUser().getLogin());
            LoansTable.setItems(hasBorrowed);
        } else {
            this.LoansTable.setItems(null);
        }
    }

    public void CheckBoxAllLoansAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.CheckBoxAllLoans.isSelected()) {
            CheckBoxBorrowedBooks.setSelected(false);
            // this.BooksTable.setItems(null);
            ObservableList<HasBorrowed> hasBorrowed = BookDb.searchBorrowedBooks();
            LoansTable.setItems(hasBorrowed);
        } else {
            this.LoansTable.setItems(null);
        }
    }

    public void showProfile(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();

        login.setText(gateway.getUser().getLogin());
        MyName.setText("Surname: " + gateway.getUser().getFirstName());
        MySurname.setText("Name: " + gateway.getUser().getLastName());
        MyCategory.setText("Category: " + gateway.getUser().getCategory());

    }

    public void ClearFields(ActionEvent actionEvent) {

        this.BookTitle.clear();
        this.BookEditor.clear();
        this.BookAuthor.clear();
        this.resultArea.setText("");
    }
}
