package base;

import gui.PlantsCard;
import javafx.scene.image.Image;

public class PeaShooterCard extends PlantsCard{
    public PeaShooterCard() {
        super(10,100);
        String imagePath = "peaShooterCard.jpg";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}