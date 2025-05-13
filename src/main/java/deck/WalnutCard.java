package deck;

import javafx.scene.image.Image;

public class WalnutCard extends PlantsCard{
    public WalnutCard(){
        super(20,50);
        String imagePath = "wallnutCard.jpg";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}
