package System;

public class FoodBean {

	private String itemName;
	private String itemDescription;
	private int Calories;
	private String type;
	private float servingSize;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public int getCalories() {
		return Calories;
	}
	public void setCalories(int calories) {
		Calories = calories;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getServingSize() {
		return servingSize;
	}
	public void setServingSize(float servingSize) {
		this.servingSize = servingSize;
	}
	
}
