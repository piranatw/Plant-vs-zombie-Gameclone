package base;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sun extends ImageView {
    private TranslateTransition transition;
    private Pane sunLayer;
    private int n = 100 ,time = 5;
   // private String name;
    public Sun(Pane sunLayer) {
        this.sunLayer = sunLayer;
        String imagePath = "sun.png";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.setOnMouseClicked(event -> {
            Sunny.collectSun();
            Platform.runLater(() -> {
                sunLayer.getChildren().remove(this);
            });
            event.consume();
        });
        this.setCursor(Cursor.HAND);
    }

    public void move(double x, double y,String name) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        if(name.equals("normal")){
            n = 100000;
            time = 300;
        }
        this.transition = new TranslateTransition(javafx.util.Duration.seconds(time),this);
        this.transition.setFromY(y - 50); // start at top
        this.transition.setToY(y + n); // fall down to bottom of screen
        this.transition.setCycleCount(1);
        this.transition.play();
        
        System.out.println("Sun Falling");
    }

}