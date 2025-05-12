package base;

import gui.PvzSquare;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import logic.Explodable;

public class Cherrybomb extends Plant implements Explodable {
    private Pane bulletPane;

    public Cherrybomb(Pane bulletPane, double x, double y, PvzSquare pvzSquare) {
        super(100000, x, y, 10, pvzSquare);  // Using parent constructor
        this.bulletPane = bulletPane;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setImage(new Image(ClassLoader.getSystemResource("cherrybomb.png").toString()));
        this.setFitWidth(100);
        this.setFitHeight(80);
    }

    public void explosion() {
        this.hp = 0;
        this.setImage(new Image(ClassLoader.getSystemResource("bomb.gif").toString()));
        this.setFitHeight(200);
        this.setFitHeight(200);
        if (hp <= 0) {
            Platform.runLater(() -> {
                Pane parent = (Pane) this.getParent();
                if (parent != null) {
                    parent.getChildren().remove(this); // 💥 Remove plant from scene
                    this.pvzSquare.setPlanted(false);
                }
            });
        }
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        explosion();
    }

    public boolean isDied() {
        return this.hp <= 0;
    }
}
