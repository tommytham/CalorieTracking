package System;

/***
 * Class that handles all the SQL Queries
 * 
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

public class UserDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;
	static Statement stmt = null;
	static Statement stmt2 = null;
	
	public static int getUserID(UserBean bean) throws SQLException {
		String username = bean.getUsername();
		String password = bean.getPassword();
		String searchQuery = "select userid from users where username='" + username + "' AND password='" + password
				+ "'";
		int userID = 0;
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			userID = rs.getInt(1);
		}
		return userID;
	}

	public static UserBean login(UserBean bean) {

		// preparing some objects for connection

		String username = bean.getUsername();
		String password = bean.getPassword();

		String searchQuery = "select * from users where username='" + username + "' AND password='" + password + "'";

		// "System.out.println" prints in the console; Normally used to trace the
		// process
		System.out.println("Your user name is " + username);
		System.out.println("Your password is " + password);
		System.out.println("Query: " + searchQuery);

		try {
			// connect to DB
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();

			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			}

			// if user exists set the isValid variable to true
			else if (more) {
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");

				System.out.println("Welcome " + firstName);
				bean.setFirstName(firstName);
				bean.setLastName(lastName);
				bean.setValid(true);
			}
		}

		catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// some exception handling
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}

				currentCon = null;
			}
		}

		return bean;
	}

	// Method which is called upon to create a user account
	public static UserBean createUser(UserBean bean) throws SQLException {

		String username = bean.getUsername();
		String password = bean.getPassword();
		String firstname = bean.getFirstName();
		String lastname = bean.getLastName();

		String createQuery = "insert into users(firstname,lastname,username,password)" + "values('" + firstname + "','"
				+ lastname + "','" + username + "','" + password + "')";

		// validation
		if (bean.getUsername().length() > 3 && bean.getPassword().length() > 3 && bean.getFirstName().length() > 1
				&& bean.getLastName().length() > 1 && !UserDAO.checkUsernameExists(username)) {
			try {
				// connect to DB
				currentCon = ConnectionManager.getConnection();
				stmt = currentCon.createStatement();
				stmt.executeUpdate(createQuery);

				bean.setValid(true);

			} finally {}
			
			

		} else {
			bean.setValid(false);
		}
		return bean;

	}

	public static void setupProfile(UserBean bean) throws SQLException {
		String activityLevel = bean.getActivityLevel();
		float startingWeight = bean.getCurrentWeight();
		String gender = bean.getGender();
		String goal = bean.getGoal();
		int height = bean.getHeight();
		int age = bean.getAge();

		// get userid of current user
		int userID = getUserID(bean);

		System.out.println("THE USER ID RETRIEVED IS: " + userID);

		// insert statement
		String createQuery = "INSERT INTO Progression (userID, weight, age, height, goal, activityLevel, date, gender)"
				+ " values('" + userID + "','" + startingWeight + "','" + age + "','" + height + "','" + goal + "','"
				+ activityLevel + "','" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "','" + gender + "')";
		stmt.executeUpdate(createQuery);
	}

	public static boolean checkSetup(UserBean bean) throws SQLException {
		// return true if user has done setup already
		// get userid of current user
		int userID = getUserID(bean);

		// check if any progression entered yet (if not then havent setup profile)
		String searchProgression = "SELECT * FROM Progression where userid='" + userID + "'";
		rs = stmt.executeQuery(searchProgression);
		if (rs.next()) {
			return true;
		}

		return false;
	}

	public static int calculateBMR(UserBean bean) throws SQLException {
		int userID = getUserID(bean);
		int BMR;

		if (bean.getGender().equals("Male")) {
			BMR = (int) (10 * bean.getCurrentWeight() + 6.25 * bean.getHeight() - 5 * bean.getAge() + 5);

		} else {
			BMR = (int) (10 * bean.getCurrentWeight() + 6.25 * bean.getHeight() - 5 * bean.getAge() - 161);

		}

		System.out.println(bean.getBMR());
		// activity levels

		if (bean.getActivityLevel().equals("light")) {
			BMR = (int) (BMR * 1.375);
		}

		else if (bean.getActivityLevel().equals("moderate")) {
			BMR = (int) (BMR * 1.55);
		}

		else {
			BMR = (int) (BMR * 1.725);
		}

		// goals

		if (bean.getGoal().equals("lose")) {
			BMR = (int) (BMR * 0.8);
		}

		else if (bean.getGoal().equals("gain")) {
			BMR = (int) (BMR * 1.2);
		}

		return BMR;

	}

	public static UserBean getUserBean(UserBean bean) throws SQLException {
		int userID = getUserID(bean);
		String searchQuery = "SELECT *\r\n" + 
				"FROM Progression\r\n" + 
				"WHERE userID = '"+userID+"'\r\n" + 
				"ORDER BY date DESC\r\n" + 
				"LIMIT 1";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			bean.setGender(rs.getString("gender"));
			bean.setCurrentWeight(rs.getFloat("weight"));
			bean.setHeight(rs.getInt("height"));
			bean.setActivityLevel(rs.getString("activitylevel"));
			bean.setGoal(rs.getString("goal"));
			bean.setAge(rs.getInt("age"));
		}
		return bean;
	}

	public static ResultSet getFoodTable() throws SQLException {
		String searchQuery = "select * from fooditems";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}
	
	public static ResultSet getRecipeTable() throws SQLException {
		String searchQuery = "select * from recipe";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}

	// input food name
	// return itemid
	public static int getItemID(String itemName) throws SQLException {
		itemName = StringUtils.capitalize(itemName);
		String searchQuery = "select fooditemid from fooditems where itemname='" + itemName + "'";
		int itemID = 0;
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			itemID = rs.getInt(1);
		}
		return itemID;
	}

	// input recipe name
	// return recipeid
	public static int getRecipeID(String recipeName) throws SQLException {
		recipeName = StringUtils.capitalize(recipeName);
		String searchQuery = "select recipeid from recipe where recipename='" + recipeName + "'";
		int recipeID = 0;
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			recipeID = rs.getInt(1);
		}
		return recipeID;
	}
	/***
	 * pass in new weight and the date user has picked
	 * @param bean
	 * @param weight
	 * @param date
	 * @throws SQLException
	 */
	public static void insertWeightProgress(UserBean bean, int weight, String date) throws SQLException {
		String activityLevel = bean.getActivityLevel();
		String gender = bean.getGender();
		String goal = bean.getGoal();
		int height = bean.getHeight();
		int age = bean.getAge();

		// get userid of current user
		int userID = getUserID(bean);

		System.out.println("THE USER ID RETRIEVED IS: " + userID);

		// insert statement
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		String createQuery = "INSERT INTO Progression (userID, weight, age, height, goal, activityLevel, date, gender)"
				+ " values('" + userID + "','" + weight + "','" + age + "','" + height + "','" + goal + "','"
				+ activityLevel + "','" + date + "','" + gender + "')";
		stmt.executeUpdate(createQuery);
		
	}
	
	// call getitemid and getuserid methods if need to
	public static void insertEatLog(int userID, int itemID) throws SQLException {
		String insertQuery = "INSERT INTO eatlog (userid,fooditemid,date) values('" + userID + "','" + itemID + "','"
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "')";

		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		stmt.executeUpdate(insertQuery);
	}
	
	public static void insertRecipeEatLog(int userID, int recipeID) throws SQLException {
		//get fooditem ids that exist in recipe
		//while(rs.next)
		//insert into eatlog table the fooditemid one by one
		String searchQuery = "SELECT fi.fooditemid, fi.itemname\r\n" + 
				"FROM recipe as r, recipeitems as ri, fooditems as fi\r\n" + 
				"WHERE r.recipeid = ri.recipeid\r\n" + 
				"AND ri.fooditemid = fi.fooditemid\r\n" + 
				"AND r.recipeid = '"+recipeID+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		stmt2 = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while(rs.next()) {
			System.out.println(rs.getInt("fooditemid"));
			String insertQuery = "INSERT INTO eatlog (userid,fooditemid,date) values('" + userID + "','" + rs.getInt("fooditemid") + "','"
					+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "')";
			stmt2.executeUpdate(insertQuery);
			
		}

	}

	// input id
	// return bean
	public static FoodBean getFoodBean(int itemID) throws SQLException {
		String searchQuery = "select * from fooditems where fooditemid ='" + itemID + "'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		FoodBean bean = new FoodBean();
		while (rs.next()) {
			bean.setItemName(rs.getString("itemname"));
			bean.setItemDescription(rs.getString("itemdescription"));
			bean.setCalories(rs.getInt("calories"));
			bean.setType(rs.getString("type"));
			bean.setServingSize(rs.getString("servingsize"));
		}
		return bean;

	}
	
	//input recipeid
	//return bean
	
	public static RecipeBean getRecipeBean (int recipeID) throws SQLException {
		String searchQuery = "select * from recipe where recipeid ='" + recipeID + "'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		RecipeBean bean = new RecipeBean();
		while (rs.next()) {
			bean.setRecipeID(rs.getInt("recipeid"));
			bean.setRecipeName(rs.getString("recipename"));
			bean.setRecipeDescription(rs.getString("recipedescription"));

		}
		return bean;
	}

	// Use this method to display foods eaten at dash board
	public static ResultSet getTodaysLogs(int userID) throws SQLException {
		String searchQuery = "select fooditems.itemname,fooditems.calories from eatlog,fooditems"
				+ " where eatlog.userID ='" + userID + "' AND eatlog.date='" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "'"
				+ "AND eatlog.fooditemid=fooditems.fooditemid";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}

	// Use this method to get the number of calories consumed so far of 'today'
	public static int getConsumedCalories(int userID) throws SQLException {
		int caloriesConsumed = 0;
		rs = getTodaysLogs(userID);
		while (rs.next()) {
			caloriesConsumed += rs.getInt("calories");

		}
		return caloriesConsumed;
	}

	
	// input userID
	//return top 2 food types user eats based on past 20 logs
	//helped method for recommendation
	public static String[] getLogPattern(int userID) throws SQLException {

		String[] keyValues = new String[2];
		HashMap<String, Integer> sortCount = new HashMap();
		int carbohydrateCount = 0;
		int proteinCount = 0;
		int milkDairyCount = 0; // milk 
		int fruitVegCount = 0; // fruit and vegetables
		int fatSugarCount = 0;
		int otherCount = 0;

		// get the latest 20 eat logs
		String searchQuery = "SELECT fooditems.type" + " from eatlog,fooditems where userid = '" + userID + "' "
				+ "AND fooditems.fooditemid=eatlog.fooditemid" + " ORDER BY logid DESC LIMIT 20";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			if (rs.getString("type").equals("Carbohydrate")) {
				carbohydrateCount++;
			} else if (rs.getString("type").equals("Protein")) {
				proteinCount++;
			} else if (rs.getString("type").equals("Milk and Dairy")) {
				milkDairyCount++;
			} else if (rs.getString("type").contains("Fruit")|| (rs.getString("type").contains("Vegetable"))) {
				fruitVegCount++;
			} else if (rs.getString("type").equals("Fats and Sugar")) {
				fatSugarCount++;
			} else { 
				otherCount++;
			}
		}
			
			sortCount.put("Carbohydrate", carbohydrateCount);
			sortCount.put("Protein", proteinCount);
			sortCount.put("Milk and Dairy", milkDairyCount);
			sortCount.put("Fruit and Vegetable", fruitVegCount);
			sortCount.put("Fats and Sugars", fatSugarCount);
			sortCount.put("Other", otherCount);

			//get the two top food item type which has been logged the most by user
			
			int maxValue = (Collections.max(sortCount.values()));
			
			sortCount.forEach((k, v) -> {
				if (v.equals(maxValue)) {
					keyValues[0] = k;

				}

			});

			sortCount.remove(keyValues[0]);
			int maxValue2 = (Collections.max(sortCount.values())); // get new max value
			sortCount.forEach((k, v) -> {
				if (v.equals(maxValue2)) {
					//System.out.println(k);
					keyValues[1] = k;

				}

				// algorithm flawed if multiple variables have same max value

			});
			return keyValues;
		}
		// sort out the counts and find what has the highest count
		// with the highest count, get the meat type and calories remaining
		
	
	
	//method to get recipe ids, given food item id
	//helper method for recommendations
	public static ResultSet getRecipeIDsFromFoodItemType(String type) throws SQLException {
		String searchQuery = "SELECT r.recipeid\r\n" + 
				"FROM recipe as r, recipeitems as ri, fooditems as fi\r\n" + 
				"WHERE r.recipeid = ri.recipeid\r\n" + 
				"AND ri.fooditemid = fi.fooditemid\r\n" + 
				"AND fi.type = '"+type+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
		
	}
	
	//get total calories of a recipe dish 
	
	public static int caloriesGivenRecipeID(int recipeID) throws SQLException {
		int totalCalories = 0;
		String searchQuery = "SELECT r.recipeid,r.recipename, r.recipedescription, SUM(fi.calories) as Calories\r\n" + 
				"FROM recipe as r, recipeitems as ri, fooditems as fi\r\n" + 
				"WHERE r.recipeid = ri.recipeid\r\n" + 
				"AND ri.fooditemid = fi.fooditemid\r\n" + 
				"AND r.recipeid ="+recipeID+" \r\n" + 
				"GROUP BY r.recipename, r.recipeid\r\n" + 
				"ORDER BY Calories DESC";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while(rs.next()) {
			totalCalories = rs.getInt("Calories");
		}
		return totalCalories;
		
	}

	public static ArrayList<RecipeBean> getRecommendations (UserBean bean) throws SQLException{
		ArrayList<RecipeBean> recommendations = new ArrayList<>();

		int typeOneCount = 0; // only want 3 recipes in array thus when this count hits 3 stop
		int typeTwoCount = 0;
		int userID = getUserID(bean); //used to get calories
		int remainingCalories = calculateBMR(bean) - getConsumedCalories(userID); // remaining calories
		String typeOne = getLogPattern(userID)[0]; // get first food item type
		String typeTwo = getLogPattern(userID)[1]; // get second food item type
		ResultSet typeOneResults = getRecipeIDsFromFoodItemType(typeOne); //holds a list of recipeids
		ResultSet typeTwoResults = getRecipeIDsFromFoodItemType(typeTwo);
		
		//need to filter out results 
		//so get list of recipes that have calories under remaining
		
		while(typeOneResults.next()) {
			int recipeID = typeOneResults.getInt("recipeid");
			if(caloriesGivenRecipeID(recipeID)<= remainingCalories) {
				
				RecipeBean recipe = getRecipeBean(recipeID);
				recipe.setRecipeCalories(caloriesGivenRecipeID(recipeID));
				recommendations.add(recipe);
				typeOneCount ++;
			}	
		}
		
		while(typeTwoResults.next()) {
			int recipeID = typeTwoResults.getInt("recipeid");
			if(caloriesGivenRecipeID(recipeID)<= remainingCalories) {
				
				RecipeBean recipe = getRecipeBean(recipeID);
				recipe.setRecipeCalories(caloriesGivenRecipeID(recipeID));
				recommendations.add(recipe);
				typeTwoCount ++;
			}	
		}	
		for (int i = 0; i<recommendations.size(); i++) {
			System.out.println("RECIPE NAME: " +recommendations.get(i).getRecipeName());
		}
		return recommendations;
	}
	
	public static ResultSet getRecipeFoodItems(int recipeID) throws SQLException {
		String searchQuery = "SELECT fi.itemname\r\n" + 
				"FROM recipe as r, recipeitems as ri, fooditems as fi\r\n" + 
				"WHERE r.recipeid = ri.recipeid\r\n" + 
				"AND ri.fooditemid = fi.fooditemid\r\n" + 
				"AND r.recipeid = '"+recipeID+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}
	
	public static ResultSet getProgress(int userID) throws SQLException {
		String searchQuery = "SELECT *\r\n" + 
				"FROM Progression\r\n" + 
				"WHERE userID = '"+userID+"'\r\n" + 
				"ORDER BY date ASC";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}
	
	public static boolean checkProgressExists(int userID, String inputDate) throws SQLException {
		boolean exists = false;
		String searchQuery = "SELECT * \r\n" + 
				"FROM progression\r\n" + 
				"WHERE userid = '"+userID+"' AND date ='" +inputDate+ "'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while(rs.next()) {
			exists = true;
		}
		return exists;
	}
	
	public static boolean checkUsernameExists(String username) throws SQLException {
		boolean exists = false;
		String searchQuery = "SELECT * FROM users WHERE username ='"+username+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while(rs.next()) {
			exists = true;
		}
		return exists;
	}
	
	public static void updateExistingProgress(int userID,String inputDate, int newWeight) throws SQLException {
	
		String updateQuery = "UPDATE Progression\r\n" + 
				"SET weight = '"+newWeight+"'\r\n" + 
				"WHERE userid = '" +userID+"' AND date='"+inputDate+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		stmt.executeUpdate(updateQuery);
	}
}
