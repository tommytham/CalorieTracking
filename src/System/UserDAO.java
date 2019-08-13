package System;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

public class UserDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;
	static Statement stmt = null;

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

	public static UserBean createUser(UserBean bean) throws SQLException {

		String username = bean.getUsername();
		String password = bean.getPassword();
		String firstname = bean.getFirstName();
		String lastname = bean.getLastName();

		String createQuery = "insert into users(firstname,lastname,username,password)" + "values('" + firstname + "','"
				+ lastname + "','" + username + "','" + password + "')";

		// validation
		if (bean.getUsername().length() > 3 && bean.getPassword().length() > 3 && bean.getFirstName().length() > 1
				&& bean.getLastName().length() > 1) {
			try {
				// connect to DB
				currentCon = ConnectionManager.getConnection();
				stmt = currentCon.createStatement();
				stmt.executeUpdate(createQuery);

				bean.setValid(true);

			} finally {

			}

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
				+ activityLevel + "','" + java.time.LocalDate.now() + "','"+gender+ "')";
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
		//activity levels
		
		if (bean.getActivityLevel().equals("light")){
			BMR = (int) (BMR*1.375);
		}
		
		else if (bean.getActivityLevel().equals("moderate")) {
			BMR = (int) (BMR*1.55);
		}
		
		else {
			BMR = (int) (BMR*1.725); 
		}
		
		//goals
		
		if (bean.getGoal().equals("lose")) {
			BMR = (int) (BMR*0.8);
		}
		
		else if(bean.getGoal().equals("gain")) {
			BMR = (int) (BMR*1.2);
		}
		

		return BMR;

	}
	
	public static UserBean getUserBean(UserBean bean) throws SQLException {
		int userID = getUserID(bean);
		String searchQuery = "select * from progression where userid='" + userID + "'";
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
	
	//input food name
	//return itemid
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
	
	
	//call getitemid and getuserid if need to
	
	public static void insertEatLog(int userID, int itemID) throws SQLException{
		String insertQuery = "INSERT INTO eatlog (userid,fooditemid,date) values('" +userID+"','" +itemID+ "','"
		+java.time.LocalDate.now() +"')";
		
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		stmt.executeUpdate(insertQuery);
	}
	
	// input id
	//return bean
	public static FoodBean getFoodBean (int itemID) throws SQLException {
		String searchQuery = "select * from fooditems where fooditemid ='"+itemID+"'";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		FoodBean bean = new FoodBean();
		while (rs.next()) {
			bean.setItemName(rs.getString("itemname"));
			bean.setItemDescription(rs.getString("itemdescription"));
			bean.setCalories(rs.getInt("calories"));
			bean.setType(rs.getString("type"));
			bean.setServingSize(rs.getFloat("servingsize"));
		}
		return bean;
	
	}
	
	//use method to display foods eaten at dash board
	public static ResultSet getTodaysLogs (int userID) throws SQLException {
		String searchQuery = "select fooditems.itemname,fooditems.calories from eatlog,fooditems"
				+ " where eatlog.userID ='"+userID+"' AND eatlog.date='"+java.time.LocalDate.now()+"'"
						+ "AND eatlog.fooditemid=fooditems.fooditemid";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		return rs;
	}
	
	//use method to get the number of calories consumed so far
	public static int getConsumedCalories(int userID) throws SQLException {
		int caloriesConsumed = 0;
		rs = getTodaysLogs(userID);
		while(rs.next()) {
			caloriesConsumed += rs.getInt("calories");
			
		}
		return caloriesConsumed;
	}
	
	//recommendation based on remaining calories and past eating logs
	public static ArrayList getRecommendations(int userID) throws SQLException {
		ArrayList<?> recommendations = new ArrayList<>();
		List<Integer> sortCount = new ArrayList<Integer>();
		int fruitCount = 0;
		int vegetableCount = 0;
		int gbnCount = 0; // grains, beans and nuts
		int meatCount = 0; // meat and poultry
		int seafoodCount = 0;
		int dairyCount = 0;
		int otherCount = 0;
		//get the latest 20 eat logs
		String searchQuery = "SELECT fooditems.type"
				+ " from eatlog where userid = '"+userID+"' "
						+ "AND fooditems.fooditemid=eatlog.fooditemid"
						+ " ORDER BY logid DESC LIMIT 20";
		currentCon = ConnectionManager.getConnection();
		stmt = currentCon.createStatement();
		rs = stmt.executeQuery(searchQuery);
		while (rs.next()) {
			if(rs.getString("type").equals("Fruit")) {
				fruitCount ++;
			}
			else if (rs.getString("type").equals("Vegetable")) {
				vegetableCount ++;
			}
			else if (rs.getString("type").equals("Grains, Beans and Nuts")) {
				gbnCount ++;
				
			}
			else if (rs.getString("type").equals("Meat and Poultry")) {
				meatCount ++;
			}
			else if (rs.getString("type").equals("Fish and Seafood")) {
				seafoodCount ++;
			}
			else if (rs.getString("type").equals("Dairy")) {
				dairyCount ++;
			}
			else {
				otherCount ++;
			}
			
			//sort out the counts and find what has the highest count
			//with the highest count, get the meat type and calories remaining
			
		}
		return recommendations;
		
	}

}
