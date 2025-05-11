package deck;

import base.Plant;
import base.Sunny;
import gui.Slot;

public class PlantsCard extends Plant {

	public PlantsCard(int cooldown, int price) {
		super(cooldown, price);
	}

	public void setupCardClickHandling(Slot parentSlot) {
		// Ensure only non-transparent areas are clickable
		this.setPickOnBounds(false);

		// Use direct event handler instead of filters
		this.setOnMouseClicked(event -> {
			System.out.println("Card direct click: " + this.getClass().getSimpleName());

			boolean canSelect = !this.isOnCooldown() && Sunny.getSunAmount() >= this.getPrice();

			if (canSelect) {
				// Clear previous selection
				PlantsCard previousSelection = parentSlot.getSelectedCard();
				if (previousSelection != null) {
					previousSelection.setStyle("-fx-border-color: #444444; -fx-border-width: 2px;");
				}

				// Set this as selected
				parentSlot.setSelectedCard(this); // Assuming you add this method
				this.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; " +
						"-fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.8), 10, 0, 0, 0);");

				System.out.println("Card selected: " + this.getClass().getSimpleName());
			}

			// CRITICAL: Prevent event from bubbling up
			event.consume();
		});
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