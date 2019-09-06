package System;

/***
 * Class that establishes connection to the database
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private final static String url = "jdbc:postgresql://localhost/calorietracking";
	private final static String user = "postgres";
	private final static String password = "1234x";

	public static Connection getConnection() {
		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, password);
		}

		catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}

		return con;
	}

}
