package game.zombie;

import game.base.Plant;
import game.base.Zombie;

public class NormalZombie extends Zombie{
    public NormalZombie(){
        super("NormalZombie",10,1);
    }
    public int Attack(Plant plant){
        int p = getPower();
        int hp = plant.getHp();
        int newHp = p - hp;
        plant.setHp(Math.max(newHp,0));
        return p;
    }
}
