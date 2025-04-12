package base;

public class Plant extends Characters{
    protected int cooldown;
    protected int price;
    public Plant(int cooldown,int price){
        this.cooldown = cooldown;
        this.price = price;
    }
}
