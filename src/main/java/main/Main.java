package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu(primaryStage);
        Scene menuScene = new Scene(mainMenu.createMenu());
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Plant VS Zombie - Main Menu");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        GameLogic.shutdown(); // Static for simplicity, you could refactor
    }

    public static void main(String[] args) {
        launch(args);
    }
}
