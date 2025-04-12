package gui;

import base.CherryBombCard;
import base.FrostyPeaShooterCard;
import base.PeaShooterCard;
import base.PotatoMineCard;
import base.SunflowerCard;
import base.WallnutCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PvzSquare extends Pane{
	private boolean isPlanted;
	private Color basecolor;
	private int xPosition;
	private int yPosition;
    private ImageView plantImageView;
	public PvzSquare(int xPosition, int yPosition) {
		super();
        this.plantImageView = new ImageView();
        this.getChildren().add(plantImageView);
		this.isPlanted = false;
		this.basecolor = Color.LIGHTGREEN;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.setPrefHeight(100);
		this.setPrefWidth(100);
		this.setMinHeight(100);
		this.setMinWidth(100);
		BackgroundFill backgroundFill = new BackgroundFill(basecolor, null, null);
		this.setBackground(new Background(backgroundFill));
		BorderStroke borderStroke = new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
		this.setBorder(new Border(borderStroke));
	}
    public void plantIfPossible(Slot slot) {
        if (!isPlanted && slot.getSelectedCard() != null) {
            this.setPlanted(true);
            //this.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
            String imagePath = "";
            if(slot.getSelectedCard() instanceof PeaShooterCard) {
                imagePath = "PeaShooterGif.gif";
            }
            if(slot.getSelectedCard() instanceof FrostyPeaShooterCard) {
                imagePath = "Snowshooting.gif";
            }
            if(slot.getSelectedCard() instanceof WallnutCard) {
                imagePath = "walnut.gif";
            }
            if(slot.getSelectedCard() instanceof CherryBombCard) {
                imagePath = "cherrybomb.png";
            }
            if(slot.getSelectedCard() instanceof PotatoMineCard) {
                imagePath = "potatoMine.gif";
            }
            if(slot.getSelectedCard() instanceof SunflowerCard) {
                imagePath = "sunflowergif.gif";
            }
            String resourceUrl = ClassLoader.getSystemResource(imagePath).toString();
            plantImageView.setImage(new Image(resourceUrl));
            plantImageView.setFitWidth(100);
            plantImageView.setFitHeight(100);
        }
    }
    
	public boolean isPlanted() {
		return isPlanted;
	}
	public Color getBasecolor() {
		return basecolor;
	}
	public int getxPosition() {
		return xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setPlanted(boolean isPlanted) {
		this.isPlanted = isPlanted;
	}
	public void setBasecolor(Color basecolor) {
		this.basecolor = basecolor;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
}