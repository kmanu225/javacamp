package jfx.app;

import java.net.URL;
import java.util.Objects;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;

public class Launch extends Application {

    static final String BASE_PATH = "/jfx/app/view/";

    public static URL getResourceOrNull(String name) {
        return Launch.class.getResource(BASE_PATH + name);
    }

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("LoginPage.fxml"));
            
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(String.valueOf(Launch.getResourceOrNull("LoginPage.css")));

            stage.getIcons().add(new Image(Objects.requireNonNull(Launch.class.getResourceAsStream(BASE_PATH + "zlogo-b.png"))));
            stage.setTitle("LoginPage");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            Platform.exit();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
