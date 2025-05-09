package base;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Plantable;

public class Wallnut extends ImageView implements Plantable {
    private Timeline shootingTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane;
    private int health = 200;

    public Wallnut(Pane bulletPane, double x, double y) {
        // Store the bullet pane and position
        this.bulletPane = bulletPane;
        this.positionX = x;
        this.positionY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("walnut.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);
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
        }
    }

    public boolean isDied() {
        if (this.health > 0)
            return false;
        return true;
    }
}