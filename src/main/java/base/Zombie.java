package base;

import java.util.ArrayList;

import gui.PvzPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.Plantable;
import javafx.scene.control.Alert.AlertType;
import main.GameLogic;

public abstract class Zombie extends Characters implements Cloneable {
    protected int health;
    protected TranslateTransition transition;
    protected static ArrayList<Zombie> allzombies = new ArrayList<Zombie>();
    protected double velocity = -1;
    private boolean isAlive = true;
    private Timeline movementTimeline;
    private boolean froze = false;
    private Timeline attackTimeline;
    private static boolean GameOver = false;

    public Zombie(int health) {
        this.health = health;
        this.setEffect(new DropShadow(15, Color.DARKRED));
        this.setFitWidth(100); // ImageView uses fitWidth/fitHeight
        this.setFitHeight(100);
        allzombies.add(this);

        startMoving();
    }

    private void startMoving() {
        movementTimeline = new Timeline(new KeyFrame(Duration.millis(33), e -> {
            if (this.isAlive()) {
                setLayoutX((double) (getLayoutX()) + (double) this.velocity);
            }
            if (getLayoutX() < 0) {
                GameLogic.shutdown();
            }
        }));
        movementTimeline.setCycleCount(Timeline.INDEFINITE);
        movementTimeline.play();
        Thread thread = new Thread(() -> {
            while (!GameOver) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt(); // Restore the interrupt status
                    break; // Optional: exit loop if interrupted
                }

                if (this.getLayoutX() < -150) {
                    Platform.runLater(() -> {
                        GameOver = true;
                        Alert losingAlert = new Alert(Alert.AlertType.INFORMATION);
                        losingAlert.setHeaderText("Your brain was eaten!!!");
                        losingAlert.setContentText("Try again next time");
                        losingAlert.setTitle("Ahhhhhhhhh!!!!!!!");
                        losingAlert.showAndWait();

                        if (losingAlert.getResult() == ButtonType.OK) {
                            Platform.exit();
                        }
                    });
                    break;
                }
            }
        });
        thread.start();
    }

    public void startAttacking(Plantable plant) {
        this.velocity = 0;

        if (attackTimeline != null)
            return;

        attackTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            plant.takeDamage(10);
            if (plant.isDied()) {
                System.out.println("Plant is died");
                if (isFrozen())
                    this.velocity = -0.25;
                else
                    this.velocity = -1.00;
                // this.setImage(new
                // Image(ClassLoader.getSystemResource("zombie-normal.gif").toString()));
                this.setFitWidth(80);
                this.setFitHeight(80);
                if(this instanceof CapZombie){
                    this.setFitWidth(120);
                    this.setFitHeight(90);
                }
                attackTimeline.stop();
                attackTimeline = null;
            }
        }));
        attackTimeline.setCycleCount(Timeline.INDEFINITE);
        attackTimeline.play();
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            die();
        }
    }

    private void die() {
        isAlive = false;
        attackTimeline.stop();
        if (movementTimeline != null) {
            movementTimeline.stop();
        }
        Platform.runLater(() -> {
            Pane parent = (Pane) this.getParent();
            if (parent != null) {
                parent.getChildren().remove(this);
            }
        });
        GameLogic.setAllZombies(GameLogic.getAllZombies() - 1);
        System.out.println("zombiePos" + this.getPosX());
        // Optionally switch to a dying animation or trigger removal in CollisionManager
    }

    @Override
    protected Zombie clone() {
        try {
            return (Zombie) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // public void stopOtherThread() {
    // for (Zombie zombie: allzombies) {
    // if (zombie!= this) zombie.stopZombie();;
    // }
    // }
    public void Frozen() {
        froze = true;
        this.velocity = -0.50;

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.5); // Slight hue shift (gray → cool tone)
        colorAdjust.setSaturation(0.5); // Add slight color to gray
        colorAdjust.setBrightness(0.2); // Make it a bit frosty-bright

        Glow glow = new Glow(0.4); // Subtle frosty glow
        glow.setInput(colorAdjust);

        this.setEffect(glow);
    }

    public boolean isFrozen() {
        return froze;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}