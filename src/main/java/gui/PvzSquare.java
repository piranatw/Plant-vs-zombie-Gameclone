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

    // References to layers
    private static Pane bulletLayer;
    private static PvzPane pvzPane;
    private static Pane plantLayer;

    // Slot for plant placement
    private Slot currentSlot;

    public PvzSquare(int xPosition, int yPosition) {
        super();
        initializeDefaults();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    private void initializeDefaults() {
        this.setStyle("-fx-border-color: #6eaa4c; -fx-border-width: 2; -fx-background-color: #d4f7b2;");
        this.plantImageView = new ImageView();
        this.getChildren().add(plantImageView);
        this.isPlanted = false;
        this.basecolor = Color.LIGHTGREEN;
        this.setPrefHeight(100);
        this.setPrefWidth(100);
        setBackground(new Background(new BackgroundFill(basecolor, null, null)));
        setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    // Set reference to the slot from which the plant is selected
    public void setSlot(Slot slot) {
        this.currentSlot = slot;
    }

    public void plantIfPossible(Slot slot) {
        // Use the provided slot parameter, but fallback to instance variable if necessary
        Slot slotToUse = (slot != null) ? slot : currentSlot;
        if (slotToUse == null || !canPlant(slotToUse)) {
            return;
        }

        // Get selected card and plant
        PlantsCard card = slotToUse.getSelectedCard();
        plantPlant(card, slotToUse);
    }

    private boolean canPlant(Slot slotToUse) {
        if (slotToUse.getSelectedCard() == null) {
            System.out.println("ERROR: No card selected");
            return false;
        }
        if (isPlanted) {
            System.out.println("Square already has a plant");
            return false;
        }
        if (slotToUse.getSelectedCard().isOnCooldown()) {
            System.out.println("Card is on cooldown");
            return false;
        }
        if (Sunny.getSunAmount() < slotToUse.getSelectedCard().getPrice()) {
            System.out.println("Not enough sun: " + Sunny.getSunAmount());
            return false;
        }
        return true;
    }

    private void plantPlant(PlantsCard card, Slot slotToUse) {
        System.out.println("Planting: " + card.getClass().getSimpleName());

        // Mark square as planted
        this.setPlanted(true);
        
        // Spend sun
        Sunny.spendSun(card.getPrice());

        // Get coordinates for the plant
        javafx.geometry.Bounds boundsInScene = this.localToScene(this.getBoundsInLocal());
        double bulletLayerX = bulletLayer.sceneToLocal(boundsInScene.getMinX(), 0).getX();
        double bulletLayerY = bulletLayer.sceneToLocal(0, boundsInScene.getMinY()).getY();

        // Add the corresponding plant based on the card type
        addPlant(card, bulletLayerX, bulletLayerY);

        // Clear selection and start cooldown
        slotToUse.clearSelectedCard();
        startCardCooldown(card);
    }

    private void addPlant(PlantsCard card, double bulletLayerX, double bulletLayerY) {
        if (card instanceof PeaShooterCard) {
            peashooter = new Peashooter(bulletLayer, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(peashooter);
        } else if (card instanceof FrostyPeaShooterCard) {
            frostyshooter = new FrostyPeashooter(bulletLayer, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(frostyshooter);
        } else if (card instanceof WallnutCard) {
            wallnut = new Wallnut(bulletLayer, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(wallnut);
        } else if (card instanceof CherryBombCard) {
            cherry = new Cherrybomb(bulletLayer, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(cherry);
        } else if (card instanceof PotatoMineCard) {
            potatomine = new Potatomine(bulletLayer, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(potatomine);
        } else if (card instanceof SunflowerCard) {
            sunflower = new Sunflower(bulletLayer, pvzPane, bulletLayerX, bulletLayerY, this);
            plantLayer.getChildren().add(sunflower);
        }
    }

    private void startCardCooldown(PlantsCard card) {
        card.startCooldown();
        card.setOpacity(0.5);
        PauseTransition cooldown = new PauseTransition(javafx.util.Duration.seconds(card.getCooldown()));
        cooldown.setOnFinished(e -> {
            card.endCooldown();
            card.setOpacity(1);
            System.out.println("Cooldown ended for: " + card.getClass().getSimpleName());
        });
        cooldown.play();
    }

    public static void setPlantLayer(Pane layer) {
        plantLayer = layer;
    }

    public static void setBulletLayer(Pane layer) {
        bulletLayer = layer;
    }

    public static void setPvzPane(PvzPane layer) {
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