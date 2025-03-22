package com.library.controller;

import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    static final String BASE_PATH = "/com/library/view/";

    public static URL getResourceOrNull(String name) {
        return App.class.getResource(BASE_PATH + name);
    }

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.getResourceOrNull("LoginPage.fxml"));
            
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(String.valueOf(App.getResourceOrNull("LoginPage.css")));

            stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream(BASE_PATH + "zlogo-b.png"))));
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
