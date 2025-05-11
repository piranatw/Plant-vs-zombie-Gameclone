package main;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu {
    private static Stage primaryStage;

    public MainMenu(Stage stage) {
        MainMenu.primaryStage = stage;
    }

    public static VBox createMenu() {
        VBox menuLayout = new VBox(25);
        menuLayout.setPrefSize(950, 750);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #c1f0c1, #ffffff);");

        // Title
        Label title = new Label("🌻 Plants vs Zombies 🌻");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 48));
        title.setTextFill(Color.DARKGREEN);
        title.setStyle("-fx-effect: dropshadow(gaussian, darkgreen, 4, 0.5, 2, 2);");

        // Difficulty buttons
        Button easyButton = createStyledButton("Easy", 1, 15, "#a3d977", "#b5ec85");
        Button normalButton = createStyledButton("Normal", 2, 45, "#f4d35e", "#f7e17a");
        Button hardButton = createStyledButton("Hard", 3, 84, "#ff6b6b", "#ff8787");

        // Quit button
        Button quitButton = createStyledButton("Quit", "#ff4c4c", "#ff0000");
        quitButton.setOnAction(e -> Platform.exit());

        menuLayout.getChildren().addAll(title, easyButton, normalButton, hardButton, quitButton);
        return menuLayout;
    }

    private static Button createStyledButton(String label, int wave, int zombies, String baseColor, String hoverColor) {
        Button button = new Button(label);
        button.setFont(Font.font("Comic Sans MS", 24));
        button.setStyle(getButtonStyle(baseColor));
        button.setOnMouseEntered(e -> button.setStyle(getButtonStyle(hoverColor)));
        button.setOnMouseExited(e -> button.setStyle(getButtonStyle(baseColor)));
        button.setOnAction(e -> {
            GameLogic.setWave(wave);
            GameLogic.setAllZombies(zombies);
            startGame();
            GameLogic.countZombies();
        });
        return button;
    }

    private static Button createStyledButton(String label, String baseColor, String hoverColor) {
        Button button = new Button(label);
        button.setFont(Font.font("Comic Sans MS", 24));
        button.setStyle(getButtonStyle(baseColor));
        button.setOnMouseEntered(e -> button.setStyle(getButtonStyle(hoverColor)));
        button.setOnMouseExited(e -> button.setStyle(getButtonStyle(baseColor)));
        return button;
    }

    private static String getButtonStyle(String color) {
        return "-fx-background-color: " + color + "; " +
               "-fx-text-fill: darkgreen; " +
               "-fx-background-radius: 15; " +
               "-fx-pref-width: 200;";
    }

    private static void startGame() {
        GameLogic gameLogic = GameLogic.getInstance();
        VBox gameRoot = gameLogic.initializeGame();
        Scene gameScene = new Scene(gameRoot, 950, 750);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(false);

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.F11) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        GameLogic.startGame();
    }
}
