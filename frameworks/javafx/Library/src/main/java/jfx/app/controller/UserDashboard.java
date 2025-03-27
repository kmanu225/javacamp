package jfx.app.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

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
import jfx.app.Launch;
import jfx.app.model.BookCopy;
import jfx.app.model.BookDb;
import jfx.app.model.HasBorrowed;

public class UserDashboard {

    public TextField BookTitle;
    public TextField BookEditor;
    public TextField BookAuthor;
    public TextField BookId;
    public Label resultArea;
    public CheckBox checkBoxAvailableBooks;
    public CheckBox CheckBoxBorrowedBooks;

    public TableView<BookCopy> BooksTable;
    public TableColumn<BookCopy, String> TableBookTitle;
    public TableColumn<BookCopy, String> TableAuthor;
    public TableColumn<BookCopy, String> TableEditor;
    public TableColumn<BookCopy, String> TableBookDescription;

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

        // books in the library
        TableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        TableAuthor.setCellValueFactory(cellData -> cellData.getValue().authorNameProperty());
        TableEditor.setCellValueFactory(cellData -> cellData.getValue().editorNameProperty());
        TableBookDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // books borrowed
        LoanTableBookId.setCellValueFactory(cellData -> cellData.getValue().bookCopyIdProperty().asObject());
        LoanTableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        LoanTableUserEmail.setCellValueFactory(cellData -> cellData.getValue().userEmailProperty());
        LoanTableLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        LoanTableFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        LoanTableLimitDate.setCellValueFactory(cellData -> cellData.getValue().LimitDateProperty());
        LoanTableReturnDate.setCellValueFactory(cellData -> cellData.getValue().giveBackDateProperty());

    }

    public void goToUserDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("UserDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(Launch.getResourceOrNull(("UserDashboard.css"))));
        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(Launch.getResourceOrNull(("LoginPage.css"))));
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.show();

    }

    public void SearchBook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(this.BookTitle.getText(), "") || !Objects.equals(this.BookAuthor.getText(), "")
                || !Objects.equals(this.BookEditor.getText(), "")) {

            if (BookDb.checkBookExistence(this.BookTitle.getText(), this.BookAuthor.getText(),
                    this.BookEditor.getText())) {
                ObservableList<BookCopy> bookCopies = FXCollections.observableArrayList();
                bookCopies.add(BookDb.searchBook1(this.BookTitle.getText(), this.BookAuthor.getText(),
                        this.BookEditor.getText()));
                BooksTable.setItems(bookCopies);
            } else {
                this.resultArea.setText("Book not found!");
            }
        } else {
            this.resultArea.setText("Fill the fields!");
        }
    }

    public void checkAvailableBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.checkBoxAvailableBooks.isSelected()) {
            this.BooksTable.setItems(null);
            ObservableList<BookCopy> bookCopies = BookDb.searchAvailableBooks2();
            BooksTable.setItems(bookCopies);
        } else {
            this.BooksTable.setItems(null);
        }
    }

    public void CheckBoxSeeMyBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        if (this.CheckBoxBorrowedBooks.isSelected()) {
            ObservableList<HasBorrowed> hasBorrowed = BookDb.searchBorrowedBooksByMe(gateway.getUser().getLogin());
            LoansTable.setItems(hasBorrowed);
        } else {
            this.LoansTable.setItems(null);
        }
    }

    public void ClearFields(ActionEvent actionEvent) {
        this.BookTitle.clear();
        this.BookEditor.clear();
        this.BookAuthor.clear();
        this.resultArea.setText("");
    }

    public void showProfile(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();

        login.setText(gateway.getUser().getLogin());
        MyName.setText("Surname: " + gateway.getUser().getFirstName());
        MySurname.setText("Name: " + gateway.getUser().getLastName());
        MyCategory.setText("Category: " + gateway.getUser().getCategory());
    }
}
