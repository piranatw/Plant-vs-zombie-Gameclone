package base;

import javafx.animation.Timeline;
import javafx.scene.image.Image;

public class BasicZombie extends Zombie {

    public BasicZombie() {
        super(100); // duration in seconds, initial health
        this.setPickOnBounds(false);
        this.setMouseTransparent(true);
        String imagePath = "zombie_normal.gif";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(80);
        this.setFitHeight(80);

    }

    


}
