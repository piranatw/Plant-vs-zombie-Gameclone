package base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.Plantable;
import logic.Shootable;

public class FrostyPeashooter extends ImageView implements Plantable,Shootable{
    private Timeline shootingTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane;
    private int health = 60;
    public FrostyPeashooter(Pane bulletPane, double x, double y) {
        // Store the bullet pane and position
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
        Bullet bullet = new Bullet("frost",5);
        ColorInput cyanOverlay = new ColorInput(0, 0, 25, 25, Color.CYAN);

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY); // Try ADD, SCREEN, or MULTIPLY for different effects
        blend.setTopInput(cyanOverlay);

        bullet.setEffect(blend);
        // Position the bullet at the peashooter's mouth
        bullet.setLayoutX(positionX + 90); // Offset to position at plant's "mouth"
        bullet.setLayoutY(positionY + 40); // Centered vertically
        // Debugging output
        // System.out.println("Shooting bullet at: " + bullet.getLayoutX() + ", " + bullet.getLayoutY());

        // Add the bullet to the bullet pane and start its movement
        bulletPane.getChildren().add(bullet);
        bullet.move(this);
    }
    public void takeDamage(int damage){
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
    public boolean isDied(){
        if(this.health > 0)    return false;
        return true;
    }
}