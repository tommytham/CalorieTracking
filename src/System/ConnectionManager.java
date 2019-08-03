package System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private final static String url = "jdbc:postgresql://localhost/calorietracking";
    private final static String user = "postgres";
    private final static String password = "1234x";
          
    public static Connection getConnection()
    {
      Connection con = null;

	  
	  try
	  {            	
	     con = DriverManager.getConnection(url,user,password); 
	     System.out.println("Connected to the PostgreSQL server successfully.");	
	  // assuming your SQL Server's	username is "username"               
	  // and password is "password"
	       
	  }
	  
	  catch (SQLException ex)
	  {
		  System.out.println(ex.getMessage());
	     
	  }

    return con;
}

}
