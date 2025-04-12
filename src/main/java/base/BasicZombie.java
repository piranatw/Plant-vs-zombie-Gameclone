package base;

import javafx.scene.image.Image;

public class BasicZombie extends Zombie {
    public BasicZombie() {
        super(30, 100); // speed (duration in seconds), health
        String imagePath = "zombie_normal.gif";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(100);
        this.setFitHeight(130);
    }
}