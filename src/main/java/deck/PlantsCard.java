package deck;


import javafx.scene.image.ImageView;

public class PlantsCard extends ImageView {
	
    private int cooldown;
    private int price;
	private boolean onCooldown = false;

	public PlantsCard(int cooldown, int price) {
        this.cooldown = cooldown;
        this.price = price;
	}


	public boolean isOnCooldown() {
		return onCooldown;
	}

	public void startCooldown() {
		onCooldown = true;
	}

	public void endCooldown() {
		onCooldown = false;
	}


	public int getCooldown() {
		return cooldown;
	}


	public int getPrice() {
		return price;
	}


	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public void setOnCooldown(boolean onCooldown) {
		this.onCooldown = onCooldown;
	}

	
	
}