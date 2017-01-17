package main.java.tavizu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/uibase.fxml"));
        primaryStage.setTitle("tavizu");
        Scene scene = new Scene(root, 800,500);
//        scene.getStylesheets().add(getClass().getResource("/uibase.css"));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                Platform.exit();
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
