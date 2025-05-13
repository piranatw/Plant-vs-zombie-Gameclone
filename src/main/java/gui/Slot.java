package gui;
import base.Sunny;
import deck.CherryBombCard;
import deck.FrostyPeaShooterCard;
import deck.PeaShooterCard;
import deck.PlantsCard;
import deck.PotatoMineCard;
import deck.SunflowerCard;
import deck.WalnutCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class Slot extends HBox {

    private PlantsCard selectedCard;
    private PlantsCard[] cards;

    public Slot() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(130); // Adjust to fit the card height
        this.setSpacing(30); // Space between cards
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #fffacd; -fx-padding: 10; -fx-border-color: #c2b280; -fx-border-width: 2;");

        // Set background
        BackgroundFill backgroundFill = new BackgroundFill(Color.SADDLEBROWN, null, null);
        this.setBackground(new Background(backgroundFill));

        // Create cards array
        cards = new PlantsCard[] {
            new SunflowerCard(),
            new PeaShooterCard(),
            new WalnutCard(),
            new CherryBombCard(),
            new PotatoMineCard(),
            new FrostyPeaShooterCard()
        };
        
        // Add debug info directly to the UI
        
        // Initialize Sunny first
        Sunny sun = new Sunny();
        
        // Add sun meter first
        this.getChildren().add(sun);
        
        // Force starting sun amount for testing
        Sunny.setSunAmount(100);
        System.out.println("Initial sun amount set to: " + Sunny.getSunAmount());
        
        // Add debug label
        
        // Add all cards and configure them
        for (PlantsCard card : cards) {
            configureCard(card);
            this.getChildren().add(card);
            
            // Make sure cards are properly sized and visible
            card.setFitWidth(80);
            card.setFitHeight(100);
            HBox.setHgrow(card, Priority.ALWAYS);
            
            // Print debug info about each card
            System.out.println("Added card: " + card.getClass().getSimpleName() + 
                               " (Price: " + card.getPrice() + ")");
        }
        
        System.out.println("Slot panel initialized with " + this.getChildren().size() + " children");
    }
    
    private void configureCard(PlantsCard card) {
        // Add bright border to make cards more visible
        card.setStyle("-fx-border-color: #444444; -fx-border-width: 2px;");
        
        // IMPORTANT FIX: Use setOnMouseClicked instead of addEventFilter
        // This ensures we directly set the handler rather than adding a filter
        card.setOnMouseClicked(event -> {
            // System.out.println("YOoooooooooo");
            System.out.println("Card clicked: " + card.getClass().getSimpleName() + 
                              " (Cooldown: " + card.isOnCooldown() + 
                              ", Sun needed: " + card.getPrice() + 
                              ", Current sun: " + Sunny.getSunAmount() + ")");
            
            boolean canSelect = !card.isOnCooldown() && Sunny.getSunAmount() >= card.getPrice();
            
            if (canSelect) {
                // Clear previous selection
                if (selectedCard != null) {
                    selectedCard.setStyle("-fx-border-color: #444444; -fx-border-width: 2px;");
                }
                
                // Set new selection
                this.selectedCard = card;
                card.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.8), 10, 0, 0, 0);");
                
                System.out.println("Selected card: " + card.getClass().getSimpleName());
            } else {
                if (card.isOnCooldown()) {
                    System.out.println("Card is on cooldown, cannot select");
                    card.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                }
                if (Sunny.getSunAmount() < card.getPrice()) {
                    System.out.println("Not enough sun: " + Sunny.getSunAmount() + "/" + card.getPrice());
                    card.setStyle("-fx-border-color: orange; -fx-border-width: 2px;");
                }
            }
            
            // DEBUG: Print the currently selected card after handling
            System.out.println("After click processing, selectedCard = " + 
                (selectedCard == null ? "null" : selectedCard.getClass().getSimpleName()));
            
            // Make sure the event is consumed to prevent propagation issues
            event.consume();
        });
        
        // Add hover effects for better UX
        card.setOnMouseEntered(e -> {
            if (!card.isOnCooldown() && Sunny.getSunAmount() >= card.getPrice()) {
                if (selectedCard != card) {
                    card.setStyle("-fx-border-color: #88FF88; -fx-border-width: 2px;");
                }
            }
        });
        
        card.setOnMouseExited(e -> {
            if (selectedCard != card) {
                // IMPORTANT FIX: Make sure we're not resetting the style of the selected card
                if (selectedCard != card) {
                    card.setStyle("-fx-border-color: #444444; -fx-border-width: 2px;");
                }
            }
        });
    }
    
    public PlantsCard getSelectedCard() {
        // IMPORTANT FIX: Add more debugging to track state
        if (selectedCard == null) {
            System.out.println("WARNING: No card is currently selected when getSelectedCard() was called");
        } else {
            System.out.println("getSelectedCard() returning: " + selectedCard.getClass().getSimpleName());
        }
        return selectedCard;
    }
    
    public void clearSelectedCard() {
        if (selectedCard != null) {
            selectedCard.setStyle("-fx-border-color: #444444; -fx-border-width: 2px;");
            System.out.println("Cleared selected card: " + selectedCard.getClass().getSimpleName());
            selectedCard = null;
        } else {
            System.out.println("clearSelectedCard() called but no card was selected");
        }
    }
    
    // For debugging - force select the first card
    public void setSelectedCard(PlantsCard card) {
        this.selectedCard = card;
        System.out.println("selectedCard explicitly set to: " + 
            (card == null ? "null" : card.getClass().getSimpleName()));
    }
    
}