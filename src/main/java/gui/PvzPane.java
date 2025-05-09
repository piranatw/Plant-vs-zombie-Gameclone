package gui;

import java.util.ArrayList;

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
    private Pane sunLayer;
    private Pane plantLayer;
    
    public PvzPane(Slot slot) {
        this.allTiles = new ArrayList<>();
        
        // Create the main layout
        this.setPadding(new Insets(15));
        this.prefWidth(900);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Create grid pane for the tiles
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        // Create bullet layer
        bulletLayer = new Pane();
        bulletLayer.setPickOnBounds(false); // Makes the pane transparent to mouse events
        bulletLayer.setBackground(Background.EMPTY);
        // Create Sun Layer
        sunLayer = new Pane();
        sunLayer.setPickOnBounds(false);
        sunLayer.setBackground(Background.EMPTY);

        // Create zombie layer
        zombieLayer = new Pane();
        zombieLayer.setPickOnBounds(false);
        zombieLayer.setBackground(Background.EMPTY);
        
        plantLayer = new Pane();
        plantLayer.setPickOnBounds(false);
        plantLayer.setBackground(Background.EMPTY);
        // Add layers to stack pane - order matters for z-index
        // Bottom to top: gridPane, bulletLayer, zombieLayer
        this.getChildren().addAll(gridPane, bulletLayer, zombieLayer, plantLayer, sunLayer);
        
        // Create tiles and add to grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                PvzSquare tile = new PvzSquare(i, j);
                tile.setOnMouseClicked(e -> tile.plantIfPossible(slot));
                this.allTiles.add(tile);
                gridPane.add(tile, i, j);
            }
        }
        
        // IMPORTANT: Set the bullet layer reference in PvzSquare
        // Must be done after initialization
        PvzSquare.setBulletLayer(bulletLayer);
        PvzSquare.setPlantLayer(plantLayer);
        PvzSquare.setSunLayer(sunLayer);
        
        // Ensure the layers are positioned correctly
        layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            // Keep all layers aligned with the grid
            bulletLayer.setLayoutX(gridPane.getLayoutX());
            bulletLayer.setLayoutY(gridPane.getLayoutY());
            zombieLayer.setLayoutX(gridPane.getLayoutX());
            zombieLayer.setLayoutY(gridPane.getLayoutY());
            plantLayer.setLayoutX(gridPane.getLayoutX());
            plantLayer.setLayoutY(gridPane.getLayoutY());
            sunLayer.setLayoutX(gridPane.getLayoutX());
            sunLayer.setLayoutY(gridPane.getLayoutY());
        });
    }
    public ArrayList<PvzSquare> getAllTiles() {
        return this.allTiles;
    }
    public Pane getPlantLayer(){
        return plantLayer;
    }
    public Pane getSunLayer(){
        return sunLayer;
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