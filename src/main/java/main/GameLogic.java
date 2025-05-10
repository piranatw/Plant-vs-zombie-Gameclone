package main;

import base.BasicZombie;
import base.Sun;
import base.Sunny;
import gui.PvzPane;
import gui.PvzSquare;
import gui.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameLogic {

    private static CollisionManager collisionManager;

    private PvzPane pvzPane;
    private Slot slot;
    private VBox root;
    private int wave = 1;

    public VBox initializeGame() {
        root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setPrefHeight(750);
        root.setPrefWidth(950);

        slot = new Slot();
        pvzPane = new PvzPane(slot);
        collisionManager = new CollisionManager(pvzPane);

        pvzPane.getAllTiles().forEach(tile -> tile.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                tile.plantIfPossible(slot);
            }
        }));

        root.getChildren().addAll(slot, pvzPane, createDebugPanel());

        return root;
    }

    public void startGame() {
        startGameLoop();
    }

    private void startGameLoop() {
        int zombiesPerWave = wave * 10;

        Timeline sunSpawner = new Timeline(new KeyFrame(Duration.seconds(5), e -> spawnSun()));
        sunSpawner.setCycleCount(Timeline.INDEFINITE);
        sunSpawner.play();

        Timeline zombieSpawner = new Timeline();
        for (int i = 0; i < zombiesPerWave; i++) {
            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(i * 7), e -> spawnZombie());
            zombieSpawner.getKeyFrames().add(spawnFrame);
        }
        zombieSpawner.play();
        wave++;
    }

    private void spawnSun() {
        Platform.runLater(() -> {
            double x = Math.random() * (pvzPane.getWidth() - 100);
            double y = -200;
            Sun sun = new Sun(pvzPane);
            sun.setManaged(false);
            sun.setLayoutX(x);
            sun.setLayoutY(y);
            pvzPane.getChildren().add(sun);
            sun.move(x, y, "normal");
        });
    }

    private void spawnZombie() {
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                BasicZombie zombie = new BasicZombie();
                int row = (int) (Math.random() * 5);
                PvzSquare targetTile = pvzPane.getAllTiles().get(row);
                double tileY = targetTile.getLayoutY();
                zombie.setLayoutY(tileY - (zombie.getFitHeight() - targetTile.getHeight()) / 2);
                zombie.setLayoutX(900);
                pvzPane.getZombieLayer().getChildren().add(zombie);
            });
        }).start();
    }

    private HBox createDebugPanel() {
        HBox debugPanel = new HBox(10);
        debugPanel.setPadding(new Insets(5));
        debugPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Button forceSelectBtn = new Button("Force Select First Card");
        forceSelectBtn.setOnAction(e -> slot.forceSelectFirstCard());

        Button printStateBtn = new Button("Print Game State");
        printStateBtn.setOnAction(e -> {
            System.out.println("=== GAME STATE ===");
            System.out.println("Selected card: " +
                    (slot.getSelectedCard() == null ? "NONE" : slot.getSelectedCard().getClass().getSimpleName()));
            System.out.println("Sun amount: " + Sunny.getSunAmount());
            System.out.println("==================");
        });

        debugPanel.getChildren().addAll(forceSelectBtn, printStateBtn);
        return debugPanel;
    }

    public static void shutdown() {
        if (collisionManager != null) {
            collisionManager.stop();
        }
    }
}
