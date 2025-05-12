package base;

import gui.PvzPane;
import gui.PvzSquare;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
public class Sunflower extends Plant {
    private Timeline sundropTimeline;
    private Pane bulletPane;
    private Sun sun;
    private PvzPane sunPane;
    private int health = 60;
    private PvzSquare pvzSquare;

    public Sunflower(Pane bulletPane, PvzPane sunPane, double x, double y, PvzSquare pvzSquare) {
        super(60, x, y, 10, pvzSquare);
        this.pvzSquare = pvzSquare;
        this.bulletPane = bulletPane;
        this.sunPane = sunPane;
        this.posX = x;
        this.posY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setImage(new Image(ClassLoader.getSystemResource("sunflowergif.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);

        // Set up the shooting timeline to drop suns at intervals
        sundropTimeline = new Timeline(new KeyFrame(Duration.seconds(7), e -> dropSun()));
        sundropTimeline.setCycleCount(Timeline.INDEFINITE);
        sundropTimeline.play();
    }

    private void dropSun() {
        sun = new Sun(this.sunPane);
        sun.setManaged(false);
        sun.setLayoutX(posX + 10);
        sun.setLayoutY(posY);

        // Add the sun to the sun pane
        this.sunPane.getChildren().add(sun);

        // Move the sun towards the player for collection
        sun.move(posX + 10, posY, "sun");
    }

    public void takeDamage(int damage) {
        this.health = this.health - damage;
        if (health <= 0) {
            Platform.runLater(() -> {
                Pane parent = (Pane) this.getParent();
                if (parent != null) {
                    parent.getChildren().remove(this); // 💥 Remove plant from scene
                }
            });
            sundropTimeline.stop();
            this.pvzSquare.setPlanted(false);
        }
    }

    public boolean isDied() {
        return health <= 0;
    }
}
