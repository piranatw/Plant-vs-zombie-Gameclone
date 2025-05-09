package gui;

import base.Plant;

public class PlantsCard extends Plant {

	public PlantsCard(int cooldown, int price) {
		super(cooldown, price);
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