package gui;

import java.util.ArrayList;

import Card.PlantsCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PvzPane extends StackPane {
    private ArrayList<PvzSquare> allTiles;
    private GridPane gridPane;
    private Pane bulletLayer;
    private Pane zombieLayer;
    private Pane plantLayer;

    // IMPORTANT FIX: Store a reference to the slot
    private Slot slot;

    public PvzPane(Slot slot) {
        this.allTiles = new ArrayList<>();

        // IMPORTANT FIX: Store the slot reference
        this.slot = slot;
        this.setPadding(new Insets(15));
        this.prefWidth(900);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create grid pane for the tiles
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        // Create bullet layer
        bulletLayer = new Pane();
        bulletLayer.setPickOnBounds(false); // Makes the pane transparent to mouse events
        bulletLayer.setBackground(Background.EMPTY);
        // Create Sun Layer

        // Create zombie layer
        zombieLayer = new Pane();
        zombieLayer.setPickOnBounds(false);
        zombieLayer.setBackground(Background.EMPTY);

        plantLayer = new Pane();
        plantLayer.setPickOnBounds(false);
        plantLayer.setBackground(Background.EMPTY);
        // Add layers to stack pane - order matters for z-index
        // Bottom to top: gridPane, bulletLayer, zombieLayer
        this.getChildren().addAll(gridPane, bulletLayer, zombieLayer, plantLayer);

        // Create tiles and add to grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                PvzSquare tile = new PvzSquare(i, j);

                // IMPORTANT FIX: Pass the current slot to the tile and store reference
                tile.setSlot(slot);

                tile.setOnMouseClicked(e -> {
                    // DEBUG: When a tile is clicked, verify the slot and selected card
                    System.out.println(
                            "Tile clicked at position (" + tile.getxPosition() + "," + tile.getyPosition() + ")");
                    PlantsCard selectedCard = slot.getSelectedCard();
                    if (selectedCard != null) {
                        System.out.println("Selected card confirmed: " + selectedCard.getClass().getSimpleName());
                    }

                    tile.plantIfPossible(slot);
                });

                this.allTiles.add(tile);
                gridPane.add(tile, i, j);
            }
        }

        // IMPORTANT FIX: Add debug message to confirm initialization
        System.out.println("PvzPane initialized with " + allTiles.size() + " tiles");

        // IMPORTANT FIX: Force select the first card for testing if needed
        // Uncomment this line if you want to automatically select the first card
        // slot.forceSelectFirstCard();

        // IMPORTANT: Set the bullet layer reference in PvzSquare
        // Must be done after initialization
        PvzSquare.setBulletLayer(bulletLayer);
        PvzSquare.setPvzPane(this);
        PvzSquare.setPlantLayer(plantLayer);

        // Ensure the layers are positioned correctly
        layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            // Keep all layers aligned with the grid
            bulletLayer.setLayoutX(gridPane.getLayoutX());
            bulletLayer.setLayoutY(gridPane.getLayoutY());
            zombieLayer.setLayoutX(gridPane.getLayoutX());
            zombieLayer.setLayoutY(gridPane.getLayoutY());
            plantLayer.setLayoutX(gridPane.getLayoutX());
            plantLayer.setLayoutY(gridPane.getLayoutY());
        });
    }

    public ArrayList<PvzSquare> getAllTiles() {
        return this.allTiles;
    }

    public Pane getPlantLayer() {
        return plantLayer;
    }

    public Pane getBulletLayer() {
        return bulletLayer;
    }

    public Pane getZombieLayer() {
        return zombieLayer;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}