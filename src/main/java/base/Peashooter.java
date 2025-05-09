package base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Plantable;

public class Peashooter extends ImageView implements Plantable{
    private Timeline shootingTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane;
    private int health = 60;
    public Peashooter(Pane bulletPane, double x, double y) {
        // Store the bullet pane and position
        this.bulletPane = bulletPane;
        this.positionX = x;
        this.positionY = y;
        
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("PeaShooterGif.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set up the shooting timeline to fire bullets at intervals
        shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(1.1), e -> shootBullet()));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    private void shootBullet() {
        // Create a new bullet
        Bullet bullet = new Bullet("normal",10);
        
        // Position the bullet at the peashooter's mouth
        bullet.setLayoutX(positionX + 90); // Offset to position at plant's "mouth"
        bullet.setLayoutY(positionY + 27); // Centered vertically
        
        // Debugging output
        //System.out.println("Shooting bullet at: " + positionX + ", " + positionY);
        
        // Add the bullet to the bullet pane and start its movement
        bulletPane.getChildren().add(bullet);
        bullet.move();
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