package base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.Plantable;

public class Sunflower extends ImageView implements Plantable{
    private Timeline sundropTimeline;
    private double positionX;
    private double positionY;
    private Pane bulletPane ,sunLayer;
    private int health = 60;
    public Sunflower(Pane bulletPane, Pane sunLayer,double x, double y) {
        // Store the bullet pane and position
        this.sunLayer = sunLayer;
        this.bulletPane = bulletPane;
        this.positionX = x;
        this.positionY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("sunflowergif.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);

        // Set up the shooting timeline to fire bullets at intervals
        sundropTimeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> dropSun()));
        sundropTimeline.setCycleCount(Timeline.INDEFINITE);
        sundropTimeline.play();
    }

    private void dropSun() {
        Sun sun = new Sun(this.sunLayer);
        System.out.println("sun drop");
        // sun.setLayoutX(positionX);
        // sun.setLayoutY(positionY + 30);
        System.out.println(positionX + "...." + positionY);
        sun.move(positionX,30 + positionY,"sun");
        Platform.runLater(() -> sunLayer.getChildren().add(sun));
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