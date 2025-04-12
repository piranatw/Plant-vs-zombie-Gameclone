package base;

import javafx.scene.image.ImageView;

public class Characters extends ImageView{
    protected int hp,posX,posY,power;
    public void attack(){
        System.out.println("Attack");
    }
    public void die(){
        System.out.println("die");
    }
}
