package gui;
import base.CherryBombCard;
import base.FrostyPeaShooterCard;
import base.PeaShooterCard;
import base.PotatoMineCard;
import base.SunflowerCard;
import base.Sunny;
import base.WallnutCard;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Slot extends HBox {

    private PlantsCard selectedCard;

    public Slot() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(130); // Adjust to fit the card height
        this.setSpacing(30); // Space between cards

        // Set background
        BackgroundFill backgroundFill = new BackgroundFill(Color.SADDLEBROWN, null, null);
        this.setBackground(new Background(backgroundFill));

        // Create and add cards
        PlantsCard[] cards = {
            new SunflowerCard(),
            new PeaShooterCard(),
            new WallnutCard(),
            new CherryBombCard(),
            new PotatoMineCard(),
            new FrostyPeaShooterCard()
        };
        
        for (PlantsCard card : cards) {
            card.setOnMouseClicked(e ->{ 
                System.out.println("Hello");
                selectedCard = card;
                e.consume();
            });
        }
        Sunny sun = new Sunny();
        this.getChildren().add(sun);
        this.getChildren().addAll(cards);
        
    }
    public PlantsCard getSelectedCard(){
        return this.selectedCard;
    }
    public void clearSelectedCard(){
        this.selectedCard = null;
    }
}
