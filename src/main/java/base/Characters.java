package base;

import javafx.scene.image.ImageView;

public class Characters extends ImageView{
    protected int hp,posX,posY,power;
    public void attack(){
        System.out.println("Attack");
    }
	public int getHp() {
		return hp;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public int getPower() {
		return power;
	}
    
    
}
