package base;

import gui.PvzSquare;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Walnut extends Plant {
    private Pane bulletPane;

    public Walnut(Pane bulletPane, double x, double y, PvzSquare pvzSquare) {
        super(200, x, y, 10, pvzSquare);  // Using parent constructor
        this.bulletPane = bulletPane;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setImage(new Image(ClassLoader.getSystemResource("walnut.gif").toString()));
        this.setFitWidth(80);
        this.setFitHeight(80);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            Platform.runLater(() -> {
                Pane parent = (Pane) this.getParent();
                if (parent != null) {
                    parent.getChildren().remove(this); // 💥 Remove plant from scene
                }
            });
        }
    }

    public boolean isDied() {
        return this.hp <= 0;
    }
}
