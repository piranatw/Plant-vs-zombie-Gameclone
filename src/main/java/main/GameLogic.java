package main;

import base.BasicZombie;
import base.CapZombie;
import base.Sun;
import gui.PvzPane;
import gui.PvzSquare;
import gui.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameLogic {
    private static GameLogic instance;
    private static CollisionManager collisionManager;

    private static PvzPane pvzPane;
    private Slot slot;
    private VBox root;

    private static int wave = 1;
    private static int allZombies;
    private static boolean gameOver = false;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public VBox initializeGame() {
        root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setPrefHeight(750);
        root.setPrefWidth(950);
        root.setStyle("-fx-font-family: 'Comic Sans MS';");

        slot = new Slot();
        pvzPane = new PvzPane(slot);
        pvzPane.setStyle("-fx-background-color: linear-gradient(to bottom, #b7e085, #e0ffc1);");

        collisionManager = new CollisionManager(pvzPane);

        pvzPane.getAllTiles().forEach(tile -> tile.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                tile.plantIfPossible(slot);
            }
        }));

        root.getChildren().addAll(slot, pvzPane);
        return root;
    }

    public static void startGame() {
        startGameLoop();
    }

    private static void startGameLoop() {
        int zombiesPerWave = wave * 10;

        Timeline sunSpawner = new Timeline(new KeyFrame(Duration.seconds(6), e -> spawnSun()));
        sunSpawner.setCycleCount(Timeline.INDEFINITE);
        sunSpawner.play();

        Timeline zombieSpawner = new Timeline();
        for (int i = 0; i < zombiesPerWave; i++) {
            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(i * 10), e -> spawnZombie());

            if (i > 25) {
                spawnFrame = new KeyFrame(Duration.seconds(i * 6), e -> {
                    spawnZombies(5);
                    spawnCapZombies(3);
                });
            }
            else if (i > 20) {
                spawnFrame = new KeyFrame(Duration.seconds(i * 7), e -> spawnZombies(4));
            }
            else if (i > 12) {
                spawnFrame = new KeyFrame(Duration.seconds(i * 8), e -> {
                    spawnZombies(3);
                    spawnCapZombie();
                });
            }
            else if (i > 5) {
                spawnFrame = new KeyFrame(Duration.seconds(i * 9), e -> spawnZombies(2));
            }
            zombieSpawner.getKeyFrames().add(spawnFrame);
        }
        zombieSpawner.play();
        wave++;
    }

    private static void spawnSun() {
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

    private static void spawnZombie() {
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

    private static void spawnCapZombie() {
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                CapZombie zombie = new CapZombie();
                int row = (int) (Math.random() * 5);
                PvzSquare targetTile = pvzPane.getAllTiles().get(row);
                double tileY = targetTile.getLayoutY();
                zombie.setLayoutY(tileY - (zombie.getFitHeight() - targetTile.getHeight()) / 2);
                zombie.setLayoutX(900);
                pvzPane.getZombieLayer().getChildren().add(zombie);
            });
        }).start();
    }

    private static void spawnZombies(int count) {
        for (int j = 0; j < count; j++) {
            spawnZombie();
        }
    }

    private static void spawnCapZombies(int count) {
        for (int j = 0; j < count; j++) {
            spawnCapZombie();
        }
    }

    public static void shutdown() {
        if (collisionManager != null) {
            collisionManager.stop();
        }
    }

    public static void countZombies() {
        Thread thread = new Thread(() -> {
            while (!gameOver) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(GameLogic.getAllZombies());
                if (GameLogic.getAllZombies() == 0) {
                    Platform.runLater(() -> {
                    	GameLogic.gameOver = true;
                        Alert winningAlert = new Alert(Alert.AlertType.INFORMATION);
                        winningAlert.setHeaderText("You just defeated all zombies");
                        winningAlert.setContentText("See you next time");
                        winningAlert.setTitle("VICTORY!!!");
                        winningAlert.showAndWait();

                        if (winningAlert.getResult() == ButtonType.OK) {
                            Platform.exit();
                        }
                    });
                    break;
                }
            }
        });
        thread.start();
    }

    public static int getWave() {
        return wave;
    }

    public static void setWave(int wave) {
        GameLogic.wave = wave;
    }

    public static int getAllZombies() {
        return allZombies;
    }

    public static void setAllZombies(int allZombies) {
        GameLogic.allZombies = Math.max(allZombies, 0);
    }

	public static boolean isGameOver() {
		return gameOver;
	}

	public static void setGameOver(boolean gameOver) {
		GameLogic.gameOver = gameOver;
	}
    
}
