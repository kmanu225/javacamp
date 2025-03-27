package jfx.app.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfx.app.Launch;
import jfx.app.model.BookDb;
import jfx.app.model.UserDb;

public class ManageBooks {

    public TextField bookToAddTitle;
    public TextField bookToAddFirstEdition;
    public TextField bookToAddDescription;
    public TextField bookToAddId;
    public TextField bookToAddAuthor;
    public TextField bookToAddBirthYear;
    public TextField bookToAddEditor;
    public TextField bookToAddEditionYear;
    public TextField bookToLendId;
    public TextField bookToLendBorrowerLogin;
    public TextField bookToAddEditorISBN;
    public Label resultArea;
    public Label resultArea2;
    public Label resultArea1;
    public TextField bookToReturnId;
    public TextField bookToReturnBorrowerLogin;
    public TextField GiveBackDate;

    public void goToAdminDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void goToUsersPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("ManageUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(Launch.getResourceOrNull(("ManageUsers.css"))));
        stage.setTitle("UsersInformation");
        stage.setScene(scene);
        stage.show();

    }

    public void goToBooksPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("ManageBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(Launch.getResourceOrNull(("ManageBooks.css"))));
        stage.setTitle("BooksInformationPage");
        stage.setScene(scene);
        stage.show();

    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void Lend(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.setText("");
        if (!Objects.equals(bookToLendId.getText(), "") && !Objects.equals(bookToLendBorrowerLogin.getText(), "")) {
            if (BookDb.checkBookCopyExistence(Integer.valueOf(bookToLendId.getText()))) {
                if (BookDb.checkForLend(Integer.valueOf(bookToLendId.getText()))) {
                    if (UserDb.checkExistence(bookToLendBorrowerLogin.getText())) {
                        BookDb.updateBorrowedBooks(Integer.valueOf(bookToLendId.getText()),
                                bookToLendBorrowerLogin.getText());
                        this.resultArea1.setText("Operation succeeded!");
                    } else {
                        this.resultArea1.setText("The user does not exists in our database.");
                    }
                } else {
                    this.resultArea1.setText("This book has not yet been returned in the library.");
                }
            } else {
                this.resultArea1.setText("This book doesn't exits in our database.");
            }
        } else {
            this.resultArea1.setText("Fill all the fields please.");
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (!Objects.equals(this.bookToAddTitle.getText(), "")
                    && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
                if (!BookDb.checkBookExistence(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText())) {
                    BookDb.AddBook(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText(),
                            this.bookToAddDescription.getText());
                    this.resultArea.setText("The book has been successfully added to the database!");
                }
            }
        } catch (Exception e) {
            this.resultArea.setText("Edition year should be an integer!");
        }

        try {
            if (!Objects.equals(this.bookToAddAuthor.getText(), "")
                    && !Objects.equals(this.bookToAddBirthYear.getText(), "")) {
                if (!BookDb.checkAuthorExistence(this.bookToAddAuthor.getText(),
                        Integer.valueOf(this.bookToAddBirthYear.getText()))) {
                    BookDb.AddAuthor(this.bookToAddAuthor.getText(),
                            Integer.valueOf(this.bookToAddBirthYear.getText()));
                    this.resultArea.setText("The author have been successfully added in the database!");
                }
            } else {
                boolean a = Objects.equals(this.bookToAddAuthor.getText(), "");
                boolean b = Objects.equals(this.bookToAddBirthYear.getText(), "");
                if (a && !b || !a && b) {
                    this.resultArea.setText("Fill author information!");
                }
            }
        } catch (Exception e) {
            Platform.exit();
        }

        try {
            if (!Objects.equals(this.bookToAddAuthor.getText(), "")
                    && !Objects.equals(this.bookToAddBirthYear.getText(), "")
                    && !Objects.equals(this.bookToAddTitle.getText(), "")
                    && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
                int id = BookDb.getAuthorId(this.bookToAddAuthor.getText(),
                        Integer.valueOf(this.bookToAddBirthYear.getText()));

                if (!BookDb.checkHasWrittenExistence(this.bookToAddTitle.getText(),
                        Integer.valueOf(this.bookToAddFirstEdition.getText()), id)
                        && BookDb.checkAuthorExistence(this.bookToAddAuthor.getText(),
                                Integer.valueOf(this.bookToAddBirthYear.getText()))
                        && BookDb.checkBookExistence(this.bookToAddTitle.getText(),
                                this.bookToAddFirstEdition.getText())) {
                    BookDb.updateHasWritten(this.bookToAddTitle.getText(),
                            Integer.valueOf(this.bookToAddFirstEdition.getText()), id);
                }
            }
        } catch (Exception e) {
            Platform.exit();
        }

        if (!Objects.equals(this.bookToAddEditorISBN.getText(), "")) {
            if (!BookDb.checkEditorExistence(this.bookToAddEditorISBN.getText())
                    && !Objects.equals(this.bookToAddEditorISBN.getText(), "")
                    && !Objects.equals(this.bookToAddEditionYear.getText(), "")
                    && !Objects.equals(this.bookToAddEditor.getText(), "")) {
                BookDb.AddEditor(this.bookToAddEditor.getText(), this.bookToAddEditorISBN.getText());
                this.resultArea.setText("The editor have been successfully added!");
            }

        }

        if (!Objects.equals(this.bookToAddEditorISBN.getText(), "")
                && !Objects.equals(this.bookToAddEditionYear.getText(), "")
                && !Objects.equals(this.bookToAddEditor.getText(), "")
                && !Objects.equals(this.bookToAddTitle.getText(), "")
                && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
            if (BookDb.checkEditorExistence(this.bookToAddEditorISBN.getText())
                    && BookDb.checkBookExistence(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText())) {
                BookDb.AddBookCopy(this.bookToAddId.getText(), this.bookToAddTitle.getText(),
                        Integer.valueOf(this.bookToAddFirstEdition.getText()), this.bookToAddEditorISBN.getText());
            }
        }

        this.resultArea.setText("Operation succeeded!");

        boolean a = Objects.equals(this.bookToAddEditorISBN.getText(), "");
        boolean b = Objects.equals(this.bookToAddEditionYear.getText(), "");
        boolean c = Objects.equals(this.bookToAddEditor.getText(), "");

        boolean d = Objects.equals(this.bookToAddTitle.getText(), "");
        boolean e = Objects.equals(this.bookToAddFirstEdition.getText(), "");
        boolean f = Objects.equals(this.bookToAddDescription.getText(), "");

        if (a && b && !c || a && !b && c || !a && b && c) {
            this.resultArea.setText("Fill editor information!");
        }

        if ((d || e) && !f || !e && f || !d && e) {
            this.resultArea.setText("Fill book information!");
        }

        if (a && b && c && d && e && f) {
            this.resultArea.setText("Fill the fields!");
        }

    }

    public void ValidateReturn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.setText("");
        if (!Objects.equals(bookToReturnId.getText(), "") && !Objects.equals(bookToReturnBorrowerLogin.getText(), "")
                && !Objects.equals(GiveBackDate.getText(), "")) {
            if (BookDb.checkBookCopyExistence(Integer.valueOf(bookToReturnId.getText()))) {
                if (UserDb.checkExistence(bookToReturnBorrowerLogin.getText())) {
                    BookDb.updateReturnDate(Integer.valueOf(bookToReturnId.getText()),
                            bookToReturnBorrowerLogin.getText(), LocalDate.parse(GiveBackDate.getText()));
                    this.resultArea2.setText("Operation succeeded!");
                } else {
                    this.resultArea2.setText("The user does not exists in the database.");
                }
            } else {
                this.resultArea2.setText("This book doesn't exits in the database.");
            }
        } else {
            this.resultArea2.setText("Fill all the fields please.");
        }
    }
}
