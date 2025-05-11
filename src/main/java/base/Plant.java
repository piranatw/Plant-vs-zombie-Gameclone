package base;

public class Plant extends Characters{
    private int cooldown;
    private int price;
    public Plant(int cooldown,int price){
        this.cooldown = cooldown;
        this.price = price;
    }
    public int getPrice(){
        return this.price;
    }
    public int getCooldown(){
        return this.cooldown;
    }
}
