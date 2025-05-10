package gui;

import base.Plant;
import javafx.scene.Cursor;

public class PlantsCard extends Plant {

	public PlantsCard(int cooldown, int price) {
		super(cooldown, price);
		this.setCursor(Cursor.HAND);
	}

	private boolean onCooldown = false;

	public boolean isOnCooldown() {
		return onCooldown;
	}

	public void startCooldown() {
		onCooldown = true;
	}

	public void endCooldown() {
		onCooldown = false;
	}

}