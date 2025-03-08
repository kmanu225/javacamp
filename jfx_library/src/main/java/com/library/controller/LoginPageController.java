package com.library.controller;

import java.util.Objects;

import com.library.model.DbUtils;
import com.library.model.Password;
import com.library.model.Utilisateur;
import com.library.model.UtilisateurDb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPageController {

    public VBox bigVbox;
    @FXML
    private Label AlertUser;
    @FXML
    private CheckBox checkManager;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    @FXML
    public boolean tryConnexion() {
        try {
            if (DbUtils.getConn() == null) {
                this.AlertUser.setText("The server is not available. Please try again later.");
                return false;
            }
            // check if a text has been typed in the userLogin textfield
            if (!Objects.equals(this.login.getText(), "")) {

                String log = this.login.getText();
                String NewLog = log.replaceAll("'", "");
                // System.out.println(this.login.getText());
                // System.out.println(NewLog);
                Utilisateur user = UtilisateurDb.searchUser(NewLog);

                // check if the user is in the database
                if (!Objects.equals(user.getLogin(), "")) {

                    // The user is trying to connect as a manager ?
                    if (checkManager.isSelected() && Objects.equals(user.getCategory(), "M")) {
                        // comparison between the password entered and the password which is in the
                        // database.
                        if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                            // System.out.println("connected as a manager!");
                            this.AlertUser.setText("connected as a manager!");
                            return true;
                        } else {
                            // System.out.println("Your password is not correct!");
                            this.AlertUser.setText("Your password is not correct!");
                            return false;
                        }
                    } else if (checkManager.isSelected() && !Objects.equals(user.getCategory(), "M")) {
                        // System.out.println("Your haven't a manager profile.");
                        this.AlertUser.setText("Your haven't a manager profile.");
                        return false;
                    } else {
                        if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                            // System.out.println("connected as a user!");
                            this.AlertUser.setText("connected as a user!");
                            return true;
                        } else {
                            // System.out.println("Your password is not correct!");
                            this.AlertUser.setText("Your password is not correct!");
                            return false;
                        }
                    }
                } else {
                    // System.out.println("You have not any account in our library!");
                    this.AlertUser.setText("You have not any account in our library!");
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("Fill all the fields please.");
        AlertUser.setText("Fill all the fields please.");

        return false;
    }

    @FXML
    public void goToUserPage(ActionEvent actionEvent) {
        try {
            if (tryConnexion()) {
                Gateway gateway = new Gateway();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                gateway.setUser(UtilisateurDb.searchUser(login.getText()));

                if (!this.checkManager.isSelected()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("UserInterface.fxml"));
                    stage.setUserData(gateway);
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(String.valueOf(App.getResourceOrNull(("UserInterface.css"))));
                    stage.setTitle("UserPage");
                    stage.setScene(scene);

                    stage.show();

                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("AdminInterface.fxml"));
                    stage.setUserData(gateway);
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(String.valueOf(App.getResourceOrNull(("AdminInterface.css"))));
                    stage.setTitle("AdminPage");
                    stage.setScene(scene);

                    stage.show();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
