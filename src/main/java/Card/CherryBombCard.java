package Card;

import javafx.scene.image.Image;

public class CherryBombCard extends PlantsCard{
    public CherryBombCard(){
        super(20,150);
        String imagePath = "cherryBombCard.jpg";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }

}
