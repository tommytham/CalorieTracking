package System;

public class RecipeBean {
	
	private int recipeID;
	private String recipeName;
	private String recipeDescription;
	private int recipeCalories;
	
	
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public int getRecipeCalories() {
		return recipeCalories;
	}
	public void setRecipeCalories(int recipeCalories) {
		this.recipeCalories = recipeCalories;
	}

}
