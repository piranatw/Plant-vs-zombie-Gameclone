package base;

import gui.PvzPane;
import gui.PvzSquare;
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
    private Pane bulletPane ;
    private Sun sun;
    private PvzPane sunPane;
    private int health = 60;
    private PvzSquare pvzSquare;
    public Sunflower(Pane bulletPane,PvzPane sunPane,double x, double y,PvzSquare pvzSquare) {
        // Store the bullet pane and position
        this.pvzSquare = pvzSquare;
        this.bulletPane = bulletPane;
        this.sunPane = sunPane;
        this.positionX = x;
        this.positionY = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        // Set the image for visual representation
        this.setImage(new Image(ClassLoader.getSystemResource("sunflowergif.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);

        // Set up the shooting timeline to fire bullets at intervals
        sundropTimeline = new Timeline(new KeyFrame(Duration.seconds(7), e -> dropSun()));
        sundropTimeline.setCycleCount(Timeline.INDEFINITE);
        sundropTimeline.play();
    }

    private void dropSun() {
        sun = new Sun(this.sunPane);
        sun.setManaged(false);
        System.out.println("sun drop");
        sun.setLayoutX(positionX + 10);
        sun.setLayoutY(positionY);
        // System.out.println(positionX + "...." + positionY);
        System.out.println("Sun drop from" + positionX + "and" + positionY);
        this.sunPane.getChildren().add(sun);
        sun.move(positionX + 10,positionY ,"sun");
    }
    public void takeDamage(int damage){
        this.health = this.health - damage;
        if (health <= 0) {
        Platform.runLater(() -> {
            Pane parent = (Pane) this.getParent();
            if (parent != null) {
                parent.getChildren().remove(this); // 💥 Remove plant from scene
            }
            sundropTimeline.stop();
        });
        this.pvzSquare.setPlanted(false);
    }
    }
    public boolean isDied(){
        if(this.health > 0)    return false;
        return true;
    }
}