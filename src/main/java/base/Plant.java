package base;

import gui.PvzSquare;

public abstract class Plant extends Characters{
	
	protected PvzSquare pvzSquare;
    public Plant(int hp, double posX, double posY, int power, PvzSquare pvzSquare){
    	super();
		this.hp = hp;
		this.posX = posX;
		this.posY = posY;
		this.power = power;
		this.pvzSquare = pvzSquare;
    }

    
    public abstract void takeDamage(int damage);
    public abstract boolean  isDied();
}
