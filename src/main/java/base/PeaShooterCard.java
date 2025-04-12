package base;

import javafx.scene.image.Image;

public class PeaShooterCard extends Plant {
    public PeaShooterCard() {
        super(15,100);
        String imagePath = "peashooter.jpg";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(100);
        this.setFitHeight(130);
    }
}