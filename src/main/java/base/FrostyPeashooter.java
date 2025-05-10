package base;

import gui.PvzSquare;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Plantable;
import logic.Shootable;

public class FrostyPeashooter extends ImageView implements Plantable, Shootable {
    private Timeline shootingTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane;
    private int health = 60;
    private PvzSquare pvzSquare;

    public FrostyPeashooter(Pane bulletPane, double x, double y, PvzSquare pvzSquare) {
        // Store the bullet pane and position
        this.pvzSquare = pvzSquare;
        this.bulletPane = bulletPane;
        this.positionX = x;
        this.positionY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("Snowshooting.gif").toString()));
        this.setFitWidth(100);
        this.setFitHeight(100);

        // Set up the shooting timeline to fire bullets at intervals
        shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(1.1), e -> shootBullet()));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    public void shootBullet() {
        // Create a new bullet
        Bullet bullet = new Bullet("frost", 5);

        // Apply a cool blue tone
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.5); // Negative hue = shift green → blue
        colorAdjust.setSaturation(1.0); // Fully saturated for vivid color
        colorAdjust.setBrightness(0.1); // Slightly brighter

        // Optional: Add a glow effect to make it feel icy
        Glow glow = new Glow(0.6);
        glow.setInput(colorAdjust);

        bullet.setEffect(glow);

        bullet.setLayoutX(positionX + 90);
        bullet.setLayoutY(positionY + 40);
        bulletPane.getChildren().add(bullet);
        bullet.move(this);
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
            this.pvzSquare.setPlanted(false);
        }
    }

    public boolean isDied() {
        if (this.health > 0)
            return false;
        return true;
    }

    @Override
    public Pane getBulletPane() {
        // TODO Auto-generated method stub
        return this.bulletPane;
    }

}