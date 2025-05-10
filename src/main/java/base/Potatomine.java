package base;

import gui.PvzSquare;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Plantable;

public class Potatomine extends ImageView implements Plantable {
    private Timeline shootingTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane;
    private int health = 100000;
    private PvzSquare pvzSquare;

    public Potatomine(Pane bulletPane, double x, double y, PvzSquare pvzSquare) {
        this.pvzSquare = pvzSquare;
        // Store the bullet pane and position
        this.bulletPane = bulletPane;
        this.positionX = x;
        this.positionY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("potatoMine.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);
    }

    private void explosion() {
        // Create a new bullet

        this.health = 0;
        this.setImage(new Image(ClassLoader.getSystemResource("bomb.gif").toString()));
        this.setFitHeight(200);
        this.setFitHeight(200);
        Platform.runLater(() -> {
            Pane parent = (Pane) this.getParent();
            if (parent != null) {
                parent.getChildren().remove(this); // 💥 Remove plant from scene
            }
        });
        this.pvzSquare.setPlanted(false);

    }

    public void takeDamage(int damage) {
        this.health = this.health - damage;
        explosion();
    }

    public boolean isDied() {
        if (this.health > 0)
            return false;
        return true;
    }
}