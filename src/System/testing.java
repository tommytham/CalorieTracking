package System;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class testing {

	public static void main(String[] args) throws SQLException {
		/***
		String [] keyValues = new String [2];
		HashMap<String, Integer> sortCount = new HashMap();
		int fruitCount = 2;
		int vegetableCount = 5;
		int gbnCount = 3; // grains, beans and nuts
		int meatCount = 1; // meat and poultry
		int seafoodCount = 8;
		int dairyCount = 4;
		int otherCount = 6;

		sortCount.put("Fruit", fruitCount);
		sortCount.put("Vegetable", vegetableCount);
		sortCount.put("Grains, Beans and Nuts", gbnCount);
		sortCount.put("Meat and Poultry", meatCount);
		sortCount.put("Seafood", seafoodCount);
		sortCount.put("Dairy", dairyCount);
		sortCount.put("Other", otherCount);

		
		int maxValue = (Collections.max(sortCount.values()));
	
		sortCount.forEach((k, v) -> {
			if (v.equals(maxValue)) {
				System.out.println(k);
				keyValues[0] = k;
				
				
			}

		});
		
		sortCount.remove(keyValues[0]);
		int maxValue2 = (Collections.max(sortCount.values())); // get new max value
		sortCount.forEach((k, v) -> {
			if (v.equals(maxValue2)) {
				System.out.println(k);
				keyValues[1] = k;
				
				
			}
		
			//algorithm flawed if multiple variables have same max value
		
		
	});
	
	*/
		/***
	ResultSet rs = UserDAO.getRecipeIDsFromFoodItemType("Protein");	
	while(rs.next()) {
		System.out.println(rs.getInt(1));
	}
	*/

		
		String type =UserDAO.getLogPattern(1)[0];
		System.out.println("TYPE 1: " +type);
		String type2 = UserDAO.getLogPattern(1)[1];
		System.out.println("Type 2: " +type2);
		ArrayList<RecipeBean> recs = new ArrayList<>();
		ResultSet rs = UserDAO.getRecipeIDsFromFoodItemType(type);
		ResultSet rs2 = UserDAO.getRecipeIDsFromFoodItemType(type2);
		while(rs.next()) {
			System.out.println(rs.getInt("recipeid") + " IS THE RECIPE ID BEING ADDED");
			recs.add(UserDAO.getRecipeBean(rs.getInt("recipeid")));
		}
		
		while(rs2.next()) {
			System.out.println(rs2.getInt("recipeid") + "IS THE RECIPE ID");
			int recID = rs2.getInt("recipeid");
			recs.add(UserDAO.getRecipeBean(recID));
		}
		
		System.out.println("SIZE OF ARRAY IS: " +recs.size());
		
		
		
		ArrayList<RecipeBean> hoo = recs;
		//System.out.println(hoo.size());

		System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(LocalDate.now()));
		String todaysDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(LocalDate.now());
		String date = "2019-08-18";
		if(date.equals(todaysDate)) {
			System.out.println("matching date");
		}
		
	}
	
}
