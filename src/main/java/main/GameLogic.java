package main;

import base.BasicZombie;
import base.CapZombie;
import base.Sun;
import base.Sunny;
import gui.PvzPane;
import gui.PvzSquare;
import gui.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameLogic {
    private static GameLogic instance;
    private static CollisionManager collisionManager;

    private static PvzPane pvzPane;
    private Slot slot;
    private VBox root;
    private static int wave = 1;
    private static int allZombies;

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

        root.getChildren().addAll(slot, pvzPane, createDebugPanel());

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
        System.out.println("zombies perwave" + zombiesPerWave);
        Timeline zombieSpawner = new Timeline();
        for (int i = 0; i < zombiesPerWave; i++) {
            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(i * 10), e -> spawnZombie());
            // spawn 2 zombies at a time after 5th wave
            if (i > 5)
                spawnFrame = new KeyFrame(Duration.seconds(i * 9), e -> {
                    for (int j = 0; j < 2; j++)
                        spawnZombie();
                });
            if (i > 12)
                spawnFrame = new KeyFrame(Duration.seconds(i * 8), e -> {
                    for (int j = 0; j < 3; j++){
                        spawnZombie();
                    }
                    spawnCapZombie();
                });
            if (i > 20)
                spawnFrame = new KeyFrame(Duration.seconds(i * 7), e -> {
                    for (int j = 0; j < 4; j++)
                        spawnZombie();
                });
            if (i > 25)
                spawnFrame = new KeyFrame(Duration.seconds(i * 6), e -> {
                    for (int j = 0; j < 5; j++)
                        spawnZombie();
                    for(int j = 0; j < 3 ;j++)
                        spawnCapZombie();
                });
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

    public static void countZombies() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(GameLogic.getAllZombies());
                if (GameLogic.getAllZombies() == 0) {
                    Platform.runLater(() -> {
                        Alert winningAlert = new Alert(Alert.AlertType.INFORMATION);
                        winningAlert.setHeaderText("You just defeated all zombies");
                        winningAlert.setContentText("See you next time");
                        winningAlert.setTitle("VICTORY!!!");
                        winningAlert.showAndWait();

                        if (winningAlert.getResult() == ButtonType.OK) {
                            Platform.exit();
                        }
                    });
                    // Platform.runLater(() -> {
                    // Alert cnfrmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    // "VICTORY!!!/n you just defeated all zombies",
                    // ButtonType.YES, ButtonType.NO);
                    // cnfrmAlert.setTitle("Congratulations!");
                    // cnfrmAlert.setHeaderText(null);
                    // cnfrmAlert.showAndWait();
                    //
                    // if (cnfrmAlert.getResult() == ButtonType.YES) {
                    // Main.reStart();
                    // } else {
                    // Platform.exit();
                    // }
                    // });
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
        if (allZombies < 0)
            allZombies = 0;
        GameLogic.allZombies = allZombies;
    }

}
