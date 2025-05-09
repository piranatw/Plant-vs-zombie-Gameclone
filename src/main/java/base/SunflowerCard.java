package base;

import gui.PlantsCard;
import javafx.scene.image.Image;

public class SunflowerCard extends PlantsCard{
    public SunflowerCard(){
        super(5,50);
        String imagePath = "SunflowerCard.jpg";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}
