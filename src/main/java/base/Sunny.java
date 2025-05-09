package base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Sunny extends VBox{
	
	private static int sunAmount;
	private static Text sunAmountText;
	
    public Sunny(){
    	this.setPrefHeight(100);
    	this.setPrefWidth(100);
    	// sunImage
        this.setPickOnBounds(false);
        String imagePath = "sun.png";
        String resourceURL = ClassLoader.getSystemResource(imagePath).toString();
        ImageView sunImage = new ImageView(new Image(resourceURL));
        sunImage.setFitHeight(80);
        sunImage.setFitWidth(80);
        // sunAmountText
        Sunny.sunAmountText = new Text();
        Sunny.sunAmountText.setFont(new Font(35));
        Sunny.sunAmountText.setTextAlignment(TextAlignment.CENTER);
        Sunny.setSunAmount(0);
        this.getChildren().addAll(sunImage,sunAmountText);
    }
    
    public static void setSunAmount(int sunAmount) {
    	sunAmount = Math.max(0, sunAmount);
    	Sunny.sunAmount = sunAmount;
    	Sunny.sunAmountText.setText(""+Sunny.sunAmount);
    }
    
    public static int getSunAmount() {
    	return Sunny.sunAmount;
    }
    
    public static void collectSun() {
    	Sunny.setSunAmount(Sunny.getSunAmount()+25);
    }
    
    public static boolean spendSun(int price) {
        if (Sunny.sunAmount >= price) {
            setSunAmount(sunAmount - price);
            return true;
        }
        return false;
    }
    
    
}