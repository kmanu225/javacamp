package jfx.app.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfx.app.Launch;
import jfx.app.model.Password;
import jfx.app.model.User;
import jfx.app.model.UserDb;

public class ManageUsers {

    public TextField changeCategoryLogin;
    public TextField newCategory;
    public Button searchUser;
    public Button updateUser;
    public Button deleteUser;
    public Button searchUsers;
    public TextField nameText;
    public TextField surnameText;
    public TextField emailText;
    public TextField loginText;
    public TableView<User> UserTable;
    public TableColumn<User, String> userLogin;
    public TableColumn<User, String> userFirstName;
    public TableColumn<User, String> userLastName;
    public TableColumn<User, String> userCategory;

    public TableColumn<User, String> userEmail;
    public TableColumn<User, Integer> maxBooks;
    public TableColumn<User, Integer> maxDays;
    public TextField NewUserName;
    public TextField NewUserSurname;
    public TextField NewUserEmail;
    public TextField NewUserLogin;
    public TextField NewUserPassword;

    public TextArea resultArea;

    public TextField NewUserCategory;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        /*
         * The setCellValueFactory(...) that we set on the table columns are used to
         * determine
         * which field inside the Employee objects should be used for the particular
         * column.
         * The arrow -> indicates that we're using a Java 8 feature called Lambdas.
         * (Another option would be to use a PropertyValueFactory, but this is not
         * type-safe
         * We're only using StringProperty values for our table columns in this example.
         * When you want to use IntegerProperty or DoubleProperty, the
         * setCellValueFactory(...)
         * must have an additional asObject()):
         */
        userLogin.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        userFirstName.setCellValueFactory(cellData -> cellData.getValue().FirstNameProperty());
        userLastName.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
        userEmail.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        userCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        maxBooks.setCellValueFactory(cellData -> cellData.getValue().maxBooksProperty().asObject());
        maxDays.setCellValueFactory(cellData -> cellData.getValue().maxDaysProperty().asObject());

        updateView();
    }

    public void goToAdminDashboard(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        stage.setUserData(gateway);

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void goToUsersPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("ManageUsers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("UsersInformation");
        stage.setScene(scene);
        stage.show();

    }

    public void goToBooksPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("ManageBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
        stage.show();

    }

    public void searchUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        if (!Objects.equals(this.loginText.getText(), "") || !Objects.equals(this.nameText.getText(), "")
                || !Objects.equals(this.surnameText.getText(), "") || !Objects.equals(this.emailText.getText(), "")) {

            if (UserDb.checkExistence(this.loginText.getText(), this.nameText.getText(), this.surnameText.getText(),
                    this.emailText.getText())) {
                ObservableList<User> users = FXCollections.observableArrayList();
                users.add(UserDb.searchUser(this.loginText.getText(), this.nameText.getText(),
                        this.surnameText.getText(), this.emailText.getText()));
                UserTable.setItems(users);
            } else {
                this.resultArea.setText("This user is not in our data base");
            }
        } else {
            this.resultArea.setText("Operation failed because the fields are empty.");
        }

    }

    public void searchUsers(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        ObservableList<User> users = UserDb.searchUsers();
        UserTable.setItems(users);
    }

    public void getBlackList(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.clear();
        ObservableList<User> users = UserDb.getBlackList();
        if (users.isEmpty()) {
            this.resultArea.setText("There is nobody on the black list.");
        }
        UserTable.setItems(users);
    }

    public void updateView() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = UserDb.searchUsers();
        UserTable.setItems(users);
    }

    public void generatePassword(ActionEvent actionEvent) {
        resultArea.clear();
        this.NewUserPassword.setText(Password.generateRandomPassword(10));
    }

    public void AddUser(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (!Objects.equals(this.NewUserLogin.getText(), "") && !Objects.equals(this.NewUserName.getText(), "")
                && !Objects.equals(this.NewUserSurname.getText(), "")
                && !Objects.equals(this.NewUserEmail.getText(), "")
                && !Objects.equals(this.NewUserPassword.getText(), "")
                && !Objects.equals(this.NewUserCategory.getText(), "")) {
            boolean presentLogin = UserDb.checkExistence(this.NewUserLogin.getText());

            if (!presentLogin) {
                UserDb.AddUser(this.NewUserLogin.getText(), this.NewUserName.getText(), this.NewUserSurname.getText(),
                        this.NewUserEmail.getText(), this.NewUserPassword.getText(), this.NewUserCategory.getText());
                this.resultArea.setText("The new user has been added.");
            } else {
                resultArea.setText("This login is ever used! Choose an other one.");
            }

        } else {
            resultArea.setText("adding failed! Fill correctly all the fields before trying again.");
        }
    }

    public void ClearFields(ActionEvent actionEvent) {
        this.resultArea.clear();
        this.NewUserSurname.clear();
        this.NewUserName.clear();
        this.NewUserEmail.clear();
        this.NewUserLogin.clear();
        this.NewUserCategory.clear();
        this.NewUserPassword.clear();
    }

    public void updateUserCategory(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        String myLogin = gateway.getUser().getLogin();

        resultArea.clear();
        if (!Objects.equals(this.changeCategoryLogin.getText(), "")
                && !Objects.equals(this.newCategory.getText(), "")) {
            UserDb.updateCategory(this.changeCategoryLogin.getText(), myLogin, this.newCategory.getText());
            updateView();
            this.resultArea.setText("The user category has been updated.");
        } else {
            resultArea.setText("Update failed! Fill correctly all the fields before trying again.");
        }
    }

    public void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        if (!Objects.equals(this.loginText.getText(), "")) {
            if (UserDb.checkExistence(this.loginText.getText())) {
                UserDb.deleteUser(this.loginText.getText());
            } else {
                this.resultArea.setText("The user you look for is not in our data base");
            }
        } else {
            this.resultArea.setText("Operation failed because the user login is empty.");
        }
    }

    public void genLogin(MouseEvent mouseEvent) {
        resultArea.clear();
        if (!Objects.equals(this.NewUserName.getText(), "") && !Objects.equals(this.NewUserSurname.getText(), "")) {
            String login = this.NewUserName.getText().substring(0, 1) + this.NewUserSurname.getText()
                    + new Random().nextInt(20);
            this.NewUserLogin.setText(login);
        }
    }

}
