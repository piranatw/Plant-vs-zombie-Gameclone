package main;

import base.BasicZombie;
import gui.PvzPane;
import gui.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private PvzPane pvzPane;
    private int wave = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Create main layout with proper dimensions
            VBox root = new VBox();
            root.setPadding(new Insets(10));
            root.setSpacing(10);
            root.setPrefHeight(750);
            root.setPrefWidth(950); // Ensure proper width
            
            // Initialize game components
            Slot slot = new Slot();
            pvzPane = new PvzPane(); // Assign to the class field
            
            // Add components to layout
            root.getChildren().addAll(slot, pvzPane);
            
            // Create and set scene with explicit dimensions
            Scene scene = new Scene(root, 950, 750);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Plants Vs Zombies");
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("UI loaded successfully");
            
        } catch (Exception e) {
            // Print any exceptions for debugging
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
        startGameLoop();
        //spawnZombie();
    }
    private void startGameLoop(){
        Timeline timeline = new Timeline(
            new KeyFrame(
                Duration.seconds(3),
                event -> spawnZombie()
            )
        );
        timeline.setCycleCount(wave*10);
        timeline.play();
    }
    
    private void spawnZombie() {
        BasicZombie zombie = new BasicZombie();
        int lane = (int)(Math.random() * 5);
        
        // Don't use layoutY — use translateY to force position
        zombie.setTranslateY(lane * 100 + 10);
        zombie.setLayoutX(900); // LayoutX still works fine here
    
        pvzPane.getChildren().add(zombie);
        zombie.move();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}