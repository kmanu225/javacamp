package jfx.app;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launch extends Application {

    static final String BASE_PATH = "/jfx/app/view/html/";

    public static URL getResourceOrNull(String name) {
        return Launch.class.getResource(BASE_PATH + name);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("LoginPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.getIcons()
                .add(new Image(Objects.requireNonNull(Launch.class.getResourceAsStream("/jfx/app/view/images/logo-b.png"))));
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
