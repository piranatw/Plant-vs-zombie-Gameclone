package base;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public abstract class Zombie extends Characters implements Cloneable {
    protected double speed;
    protected int health;
    protected TranslateTransition transition;

    public Zombie(double speed, int health) {
        this.speed = speed;
        this.health = health;
        this.setFitWidth(100);  // ImageView uses fitWidth/fitHeight
        this.setFitHeight(100);
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

    public  void move() {
        //TranslateTransition this = new TranslateTransition();
        this.transition = new TranslateTransition();
        this.transition.setDuration(Duration.seconds(speed));
        this.transition.setNode(this);
        this.transition.setFromX(900);
        this.transition.setToX(-900);
        this.transition.setCycleCount(1);
        this.transition.play();
        System.out.println("Moving");
    }
}
