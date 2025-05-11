package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        Main.primaryStage = stage;

        Scene menuScene = new Scene(MainMenu.createMenu());
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Plants vs. Zombies - Main Menu");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
