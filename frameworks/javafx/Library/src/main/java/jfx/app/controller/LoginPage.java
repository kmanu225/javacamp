package jfx.app.controller;

import java.util.Objects;

import javafx.application.Platform;
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
import jfx.app.Launch;
import jfx.app.database.DbUtils;
import jfx.app.model.Password;
import jfx.app.model.User;
import jfx.app.model.UserDb;

public class LoginPage {

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
            if (!Objects.equals(this.login.getText(), "") || !Objects.equals(this.password.getText(), "")) {

                String log = this.login.getText();
                String NewLog = log.replaceAll("'", "");
                User user = UserDb.searchUser(NewLog);

                if (!Objects.equals(user.getLogin(), "")) {

                    if (checkManager.isSelected() && Objects.equals(user.getCategory(), "M")) {
                        if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                            this.AlertUser.setText("Connected as a manager!");
                            return true;
                        } else {
                            this.AlertUser.setText("User does not exists or password incorrect!");
                            return false;
                        }
                    } else if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                        this.AlertUser.setText("Connected as a user!");
                        return true;
                    } else {
                        this.AlertUser.setText("User does not exists or password incorrect!");
                        return false;
                    }
                } else {
                    this.AlertUser.setText("User does not exists or password incorrect!");
                    return false;
                }
            } else {
                this.AlertUser.setText("Fill all the fields!");
                return false;
            }

        } catch (Exception e) {
            Platform.exit();
        }
        return false;
    }

    @FXML
    public void goToUserPage(ActionEvent actionEvent) {
        try {
            if (tryConnexion()) {
                Gateway gateway = new Gateway();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                gateway.setUser(UserDb.searchUser(login.getText()));

                if (!checkManager.isSelected()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("UserDashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setUserData(gateway);
                    stage.setTitle("UserPage");
                    stage.setScene(scene);
                    stage.show();

                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("AdminDashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setUserData(gateway);
                    stage.setTitle("AdminPage");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            Platform.exit();
        }
    }
}
