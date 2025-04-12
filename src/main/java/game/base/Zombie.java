package game.base;

public class Zombie {
    private String name;
    private int hp,power;
    public Zombie(String name,int hp,int power){
        this.name = name;
        this.setHp(hp);
        this.power = power;
    }
    public void setHp(int hp){
        this.hp = Math.max(0,hp);
    }
    public void setPower(int power){
        this.power = Math.max(power,0);
    }
    public int getHp(){
        return this.hp;
    }
    public int getPower(){
        return this.power;
    }
}
