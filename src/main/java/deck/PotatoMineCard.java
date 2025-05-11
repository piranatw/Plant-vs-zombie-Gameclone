package deck;

import javafx.scene.image.Image;

public class PotatoMineCard extends PlantsCard{
    public PotatoMineCard(){
        super(7,25);
        String imagePath = "potatoMine.jpg";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}
