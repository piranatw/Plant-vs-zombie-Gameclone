package base;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Plantable;
import logic.Shootable;

public class Bullet extends ImageView {
    private int damage;
    private static final double SPEED = 5; // pixels per frame
    private AnimationTimer timer;
    private String name;
    public Bullet(String name,int damage) {
        this.name = name;
        this.damage = damage;
        this.setImage(new Image(ClassLoader.getSystemResource("bullet.png").toString()));
        this.setFitWidth(25);
        this.setFitHeight(25);
        this.setViewOrder(-1); // Appear above other elements
    }

    public void move(Shootable plant) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if (plant instanceof Plantable) {
	                if(((Plantable) plant).isDied()){
                        plant.getBulletPane().getChildren().remove(Bullet.this);
	                    timer.stop();
	                }
            	}
                setLayoutX(getLayoutX() + SPEED);

                // Remove if off-screen
                if (getLayoutX() > 950) {
                    if (getParent() != null) {
                        ((Pane) getParent()).getChildren().remove(Bullet.this);
                    }
                    timer.stop();
                }
            }
        };
        timer.start();
    }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public String getName(){
        return this.name;
    }
}
