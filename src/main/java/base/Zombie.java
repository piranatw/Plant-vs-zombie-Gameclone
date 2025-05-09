package base;

import javafx.animation.TranslateTransition;

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
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
}