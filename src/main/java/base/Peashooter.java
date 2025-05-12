package base;

import gui.PvzSquare;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Shootable;

public class Peashooter extends Plant implements Shootable {
    private Timeline shootingTimeline;
    private Pane bulletPane;

    public Peashooter(Pane bulletPane, double x, double y, PvzSquare pvzSquare) {
        super(60, x, y, 10, pvzSquare);  // Using parent constructor
        this.bulletPane = bulletPane;
        this.setImage(new Image(ClassLoader.getSystemResource("PeaShooterGif.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);
        this.setLayoutX(x);
        this.setLayoutY(y);

        // Set up the shooting timeline
        shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(1.1), e -> shootBullet()));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    public void shootBullet() {
        Bullet bullet = new Bullet("normal", 10);
        bullet.setLayoutX(this.getPosX() + 90);
        bullet.setLayoutY(this.getPosY() + 20);
        bulletPane.getChildren().add(bullet);
        bullet.move(this);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            // Remove the plant from the scene
            Platform.runLater(() -> {
                Pane parent = (Pane) this.getParent();
                if (parent != null) {
                    parent.getChildren().remove(this); // 💥 Remove plant from scene
                    this.pvzSquare.setPlanted(false);
                }
                shootingTimeline.stop();
            });
        }
    }

    public boolean isDied() {
        return this.hp <= 0;
    }

    @Override
    public Pane getBulletPane() {
        return this.bulletPane;
    }
}