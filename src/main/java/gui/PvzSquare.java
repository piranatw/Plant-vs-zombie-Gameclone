package gui;

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
	
	
	
	public PvzSquare(int xPosition, int yPosition) {
		super();
		this.isPlanted = false;
		this.basecolor = Color.LIGHTGREEN;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.setPrefHeight(100);
		this.setPrefWidth(100);
		this.setMinHeight(100);
		this.setMinWidth(100);
		//fill Tile with color
		BackgroundFill backgroundFill = new BackgroundFill(basecolor, null, null);
		this.setBackground(new Background(backgroundFill));
		//add border
		BorderStroke borderStroke = new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
		this.setBorder(new Border(borderStroke));
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