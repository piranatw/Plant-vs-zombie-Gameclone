package gui;


import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Slot extends HBox{
	private VBox sunBox;
	private PlantsCard card1;
	private PlantsCard card2;
	private PlantsCard card3;
	private PlantsCard card4;
	private PlantsCard card5;
	private PlantsCard card6;
	private VBox excavator;
	
	public Slot() {
	    super();
	    this.setAlignment(Pos.CENTER);
	    this.setPrefHeight(120); // Adjust height as needed
	    this.setSpacing(20);
		
		BackgroundFill backgroundFill = new BackgroundFill(Color.SADDLEBROWN, null, null);
		this.setBackground(new Background(backgroundFill));
        
	}
	
	
	

}