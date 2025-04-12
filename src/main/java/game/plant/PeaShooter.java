package game.plant;

import game.base.Plant;
import game.base.Zombie;


public class PeaShooter extends Plant {
    public PeaShooter(){
        super("PeaShooter",10,1);
    }
    public int Attack(Zombie zombie){
        int p = getPower();
        int hp = zombie.getHp();
        int newHp = hp - p;
        zombie.setHp(Math.max(0, newHp));
        return p;
    }
}
