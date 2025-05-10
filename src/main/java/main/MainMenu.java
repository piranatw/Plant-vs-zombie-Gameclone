package main;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private final Stage primaryStage;
    
    public MainMenu(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public VBox createMenu(){
        VBox menuLayout = new VBox(20);
        menuLayout.setPrefSize(950, 750);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

        Button playButton = new Button("Play");
        playButton.setStyle("-fx-font-size: 24px; -fx-pref-width: 200;");
        playButton.setOnAction(e -> startGame());

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-font-size: 24px; -fx-pref-width: 200;");
        quitButton.setOnAction(e -> Platform.exit());

        menuLayout.getChildren().addAll(playButton, quitButton);

        return menuLayout;
    }

    private void startGame(){
        GameLogic gameLogic = new GameLogic();
        VBox gameRoot = gameLogic.initializeGame();
        Scene gameScene = new Scene(gameRoot,950,750);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Plants Vs Zombies");
        gameLogic.startGame();
    }
}
