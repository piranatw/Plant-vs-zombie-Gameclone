package base;

import javafx.scene.image.Image;

public class CapZombie extends Zombie {

    public CapZombie() {
        super(250); // duration in seconds, initial health
        this.setPickOnBounds(false);
        this.setMouseTransparent(true);
        String imagePath = "capzombie.gif";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(120);
        this.setFitHeight(90);

    }

}
