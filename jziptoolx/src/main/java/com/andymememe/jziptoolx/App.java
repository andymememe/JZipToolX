package com.andymememe.jziptoolx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        Controller controller = (Controller)fxmlLoader.getController();

        scene = new Scene(root, 640, 400);
        stage.setTitle("JZipToolX");
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}