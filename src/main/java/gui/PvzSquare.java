package gui;

import base.Cherrybomb;
import base.FrostyPeashooter;
import base.Peashooter;
import base.Potatomine;
import base.Sunflower;
import base.Sunny;
import base.Wallnut;
import deck.CherryBombCard;
import deck.FrostyPeaShooterCard;
import deck.PeaShooterCard;
import deck.PlantsCard;
import deck.PotatoMineCard;
import deck.SunflowerCard;
import deck.WallnutCard;
import javafx.animation.PauseTransition;
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

public class PvzSquare extends Pane {
    private boolean isPlanted;
    private Color basecolor;
    private int xPosition;
    private int yPosition;
    private ImageView plantImageView;
    private Peashooter peashooter;
    private FrostyPeashooter frostyshooter;
    private Potatomine potatomine;
    private Wallnut wallnut;
    private Cherrybomb cherry;
    private Sunflower sunflower;
    // Reference to the main game pane (will be set by PvzPane)
    private static Pane bulletLayer;
    private static PvzPane pvzPane;
    private static Pane plantLayer;
    
    // IMPORTANT FIX: Keep a reference to the slot for reliable access
    private Slot currentSlot;

    public PvzSquare(int xPosition, int yPosition) {
        super();
        this.setStyle("-fx-border-color: #6eaa4c; -fx-border-width: 2; -fx-background-color: #d4f7b2;");
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
        BorderStroke borderStroke = new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(1));
        this.setBorder(new Border(borderStroke));
    }
    
    // IMPORTANT FIX: New method to set the slot reference
    public void setSlot(Slot slot) {
        this.currentSlot = slot;
    }

    public void plantIfPossible(Slot slot) {
        // Use the provided slot parameter, but fall back to the instance variable if needed
        Slot slotToUse = (slot != null) ? slot : currentSlot;
        
        if (slotToUse == null) {
            System.out.println("ERROR: No slot available to plant from!");
            return;
        }
        
        // Debug output to trace execution
        System.out.println("Attempting to plant. Current state: isPlanted=" + isPlanted);
        System.out.println(this.getxPosition());
        
        // Get the selected card
        PlantsCard card = slotToUse.getSelectedCard();
        
        // Validation checks with detailed logging
        if (card == null) {
            System.out.println("No card selected");
            return;
        }
        
        if (isPlanted) {
            System.out.println("Square already has a plant");
            return;
        }
        
        if (card.isOnCooldown()) {
            System.out.println("Card is on cooldown");
            return;
        }
        
        if (Sunny.getSunAmount() < card.getPrice()) {
            System.out.println("Not enough sun. Have: " + Sunny.getSunAmount() + ", Need: " + card.getPrice());
            return;
        }
    
        // If we passed all checks, plant the plant
        System.out.println("Planting: " + card.getClass().getSimpleName());
        
        // Mark the square as planted FIRST
        this.setPlanted(true);
        
        // Spend sun
        Sunny.spendSun(card.getPrice());
    
        // Get scene location
        javafx.geometry.Bounds boundsInScene = this.localToScene(this.getBoundsInLocal());
        double bulletLayerX = bulletLayer.sceneToLocal(boundsInScene.getMinX(), 0).getX();
        double bulletLayerY = bulletLayer.sceneToLocal(0, boundsInScene.getMinY()).getY();
    
        System.out.println("Plant location: " + bulletLayerX + ", " + bulletLayerY);
        
        // Add correct plant
        if (card instanceof PeaShooterCard) {
            peashooter = new Peashooter(bulletLayer, bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(peashooter);
        } else if (card instanceof FrostyPeaShooterCard) {
            frostyshooter = new FrostyPeashooter(bulletLayer, bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(frostyshooter);
        } else if (card instanceof WallnutCard) {
            wallnut = new Wallnut(bulletLayer, bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(wallnut);
        } else if (card instanceof CherryBombCard) {
            cherry = new Cherrybomb(bulletLayer, bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(cherry);
        } else if (card instanceof PotatoMineCard) {
            potatomine = new Potatomine(bulletLayer, bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(potatomine);
        } else if (card instanceof SunflowerCard) {
            sunflower = new Sunflower(bulletLayer, pvzPane,bulletLayerX, bulletLayerY,this);
            plantLayer.getChildren().add(sunflower);
        }
        
        // Visual indication that the square is planted
        // BackgroundFill backgroundFill = new BackgroundFill(Color.web("#A8C66C"), null, null);
        // this.setBackground(new Background(backgroundFill));
    
        // Clear selection
        slotToUse.clearSelectedCard();
    
        // Start cooldown
        card.startCooldown();
        card.setOpacity(0.5);
        PauseTransition cooldown = new PauseTransition(javafx.util.Duration.seconds(card.getCooldown()));
        cooldown.setOnFinished(e -> {
            card.endCooldown();
            card.setOpacity(1);
            System.out.println("Cooldown ended for: " + card.getClass().getSimpleName());
        });
        cooldown.play();
        
        System.out.println("Plant added successfully");
    }
    
    public static void setPlantLayer(Pane layer) {
        plantLayer = layer;
    }

    // Static method to set the bullet layer
    public static void setBulletLayer(Pane layer) {
        bulletLayer = layer;
    }
    public static void setPvzPane(PvzPane layer){
        pvzPane = layer;
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