package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
    	stage = primaryStage;
        MainMenu mainMenu = new MainMenu(primaryStage);
        Scene menuScene = new Scene(MainMenu.createMenu());
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Plant VS Zombie - Main Menu");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void reStart() {
        Scene menuScene = new Scene(MainMenu.createMenu());
        stage.setScene(menuScene);
        stage.setTitle("Plant VS Zombie - Main Menu");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        GameLogic.shutdown(); // Static for simplicity, you could refactor
    }

    public static void main(String[] args) {
        launch(args);
    }
}