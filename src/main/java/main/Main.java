package main;

import base.BasicZombie;
import base.Sun;
import gui.PvzPane;
import gui.PvzSquare;
import gui.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
    private CollisionManager collisionManager;

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

            // Initialize collision manager
            collisionManager = new CollisionManager(pvzPane);

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

            // Start game loop
            startGameLoop();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void startGameLoop() {
        int zombiesPerWave = wave * 10;
        Timeline sunSpawner = new Timeline();
        KeyFrame sunFrame = new KeyFrame(Duration.seconds(5),e ->{
            spawnSun();
        });
        sunSpawner.getKeyFrames().add(sunFrame);
        sunSpawner.setCycleCount(Timeline.INDEFINITE);
        sunSpawner.play();
        Timeline zombieSpawner = new Timeline();
        for (int i = 0; i < zombiesPerWave; i++) {
            // Delay each zombie spawn by i * 3 seconds
            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(i * 3), e -> spawnZombie());
            zombieSpawner.getKeyFrames().add(spawnFrame);
        }
        wave = wave + 1;
        zombieSpawner.play();
    }
    
    private void spawnSun(){
        Platform.runLater(() ->{
            double x = Math.random() * (pvzPane.getWidth() - 100);
            double y = -100;
            Sun sun = new Sun(pvzPane.getSunLayer());
            pvzPane.getSunLayer().getChildren().add(sun);
            sun.move(x,y,"normal");
        });
    }
    private void spawnZombie() {
        new Thread(() -> {
            try {
                Thread.sleep(10000); // Sleep 1 second in background thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI after delay
            javafx.application.Platform.runLater(() -> {
                BasicZombie zombie = new BasicZombie();
                // Get a random row (0-4) for zombie placement
                int row = (int) (Math.random() * 5);

                // Add zombie to the right side of the grid (column 8)
                // Position zombie at the right edge of the grid in the appropriate row // Assuming each column is ~100px wide (8 * 100)
                PvzSquare targetTile = pvzPane.getAllTiles().get(row); // First column of the row
                double tileY = targetTile.getLayoutY();
                zombie.setLayoutY(tileY - (zombie.getFitHeight() - targetTile.getHeight()) / 2); // vertically center
                zombie.setLayoutX(900);
                // Add zombie to the proper layer in PvzPane
                pvzPane.getZombieLayer().getChildren().add(zombie);
            });
        }).start();
    }

    @Override
    public void stop() {
        // Clean up resources
        if (collisionManager != null) {
            collisionManager.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}