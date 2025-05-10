package base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.Plantable;

public class BasicZombie extends Zombie {
    private double velocity = -1; // Pixels per frame
    private boolean isAlive = true, froze = false;
    private Timeline movementTimeline;
    private Timeline attackTimeline;

    public BasicZombie() {
        super(30, 100); // duration in seconds, initial health
        this.setPickOnBounds(false);
        this.setMouseTransparent(true);
        String imagePath = "zombie_normal.gif";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(80);
        this.setFitHeight(80);

        startMoving();
    }

    private void startMoving() {
        movementTimeline = new Timeline(new KeyFrame(Duration.millis(33), e -> {
            if (isAlive) {
                setLayoutX((double)(getLayoutX()) + (double)this.velocity);
            }
        }));
        movementTimeline.setCycleCount(Timeline.INDEFINITE);
        movementTimeline.play();
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            die();
        }
    }

    public void startAttacking(Plantable plant) {
        this.velocity = 0;
        
        if (attackTimeline != null)
            return;
            
        attackTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            plant.takeDamage(10);
            if (plant.isDied()) {
                System.out.println("Plant is died");
                if(froze)   this.velocity = -0.25;
                else this.velocity = -1.00;
                // this.setImage(new Image(ClassLoader.getSystemResource("zombie-normal.gif").toString()));
                this.setFitWidth(80);
                this.setFitHeight(80);
                attackTimeline.stop();
                attackTimeline = null;
            }
        }));
        attackTimeline.setCycleCount(Timeline.INDEFINITE);
        attackTimeline.play();
    }

    public void Frozen() {
        ColorInput cyanOverlay = new ColorInput(0, 0, 50, 50, Color.CYAN);
        froze = true;
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY); // Try ADD, SCREEN, or MULTIPLY for different effects
        blend.setTopInput(cyanOverlay);
        this.velocity = -0.25;
        this.setEffect(blend);
    }

    private void die() {
        isAlive = false;
        if (movementTimeline != null) {
            movementTimeline.stop();
        }
        // Optionally switch to a dying animation or trigger removal in CollisionManager
    }

    public boolean isFrozen() {
        return froze;
    }

    public boolean isAlive() {
        return isAlive;
    }

}
