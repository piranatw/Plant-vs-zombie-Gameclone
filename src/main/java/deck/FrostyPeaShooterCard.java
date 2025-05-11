package deck;

import javafx.scene.image.Image;

public class FrostyPeaShooterCard extends PlantsCard{
    public FrostyPeaShooterCard(){
        super(20,175);
        String imagePath = "frostyPea.jpg";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(100);
        this.setFitHeight(90);
    }
}
