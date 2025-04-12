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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PvzPane extends GridPane {
	private ArrayList<PvzSquare> allTiles;
	
	public PvzPane() {
		this.allTiles = new ArrayList<PvzSquare>();
		this.setPadding(new Insets(15));
		this.prefWidth(900);
		this.setAlignment(Pos.CENTER);
		this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		for (int i = 0 ; i < 9; i++) {
			for (int j =0 ; j < 5;j++) {
				PvzSquare pvzSquare = new PvzSquare(i, j);
				this.allTiles.add(pvzSquare);
				this.add(pvzSquare, i, j);
			}

		}
	}
}
	
	
	
	