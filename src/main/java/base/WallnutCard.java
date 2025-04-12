package base;

import gui.PlantsCard;
import javafx.scene.image.Image;

public class WallnutCard extends PlantsCard{
    public WallnutCard(){
        super(40,50);
        String imagePath = "wallnutCard.jpg";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}
