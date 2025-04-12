package main;

import base.BasicZombie;
import gui.PvzPane;
import gui.Slot;
import gui.PvzSquare;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private PvzPane pvzPane;
    private Slot slot;
    private int wave = 1;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Main layout container
            VBox root = new VBox();
            root.setPadding(new Insets(10));
            root.setSpacing(10);
            root.setPrefHeight(750);
            root.setPrefWidth(950);

            // Initialize UI components
            slot = new Slot(); // this manages card selection
            pvzPane = new PvzPane(slot); // this holds the game grid

            // Hook up click listeners on PvzTiles to plant
            pvzPane.getAllTiles().forEach(tile -> {
                tile.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        tile.plantIfPossible(slot);
                    }
                });
            });

            // Add components to root
            root.getChildren().addAll(slot, pvzPane);

            // Scene setup
            Scene scene = new Scene(root, 950, 750);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Plants Vs Zombies");
            primaryStage.setResizable(false);
            primaryStage.show();

            System.out.println("UI loaded successfully");
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }

        // Start game loop
        startGameLoop();
    }

    private void startGameLoop() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), event -> spawnZombie())
        );
        timeline.setCycleCount(wave * 10);
        timeline.play();
    }

    private void spawnZombie() {
        BasicZombie zombie = new BasicZombie();
        int lane = (int)(Math.random() * 5);

        zombie.setTranslateY(lane * 100 + 10); 
        zombie.setLayoutX(900);

        pvzPane.getChildren().add(zombie);
        zombie.move();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
