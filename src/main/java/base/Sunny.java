package base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sunny extends ImageView{
    public Sunny(){
        String imagePath = "sun.png";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceURL));
        this.setFitWidth(80);
        this.setFitHeight(80);
    }
}
