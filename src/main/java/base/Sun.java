package base;

import javafx.animation.TranslateTransition;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sun extends ImageView {
    private TranslateTransition transition;
    private Pane sunPane;
    // private int n = 100, time = 5;

    // private String name;
    public Sun(Pane sunPane) {
        this.sunPane = sunPane;
        this.setPickOnBounds(false);
        String imagePath = "sun.png";
        String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
        this.setImage(new Image(resourceUrl));
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.setOnMouseClicked(event -> {
            Sunny.collectSun();
            sunPane.getChildren().remove(this);
            event.consume();
        });
        this.setCursor(Cursor.HAND);
    }

    public void move(double x, double y, String name) {
        int fallDistance = 100;
        int fallDuration = 5;

        if ("normal".equals(name)) {
            fallDistance = 1000;
            fallDuration = 10;
        }

        this.transition = new TranslateTransition(javafx.util.Duration.seconds(fallDuration), this);
        this.transition.setByY(fallDistance);
        this.transition.setCycleCount(1);
        this.transition.setOnFinished(e -> sunPane.getChildren().remove(this)); // optional auto-remove
        this.transition.play();

        System.out.println("Sun falling from " + getLayoutX() + ", " + getLayoutY());
    }

}