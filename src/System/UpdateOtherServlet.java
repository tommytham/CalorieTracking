package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateOtherServlet")
public class UpdateOtherServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		HttpSession session = request.getSession(true); //gets current session
		UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser")); //gets bean from previous session (login page) and assigns to new bean
		int userID = 0;
		int progressID = 0;
		try {
			userID = UserDAO.getUserID(currentUser);
			progressID = UserDAO.getLatestProgressID(userID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	if(request.getParameter("activity").length()>0 && request.getParameter("height").length()>0 && request.getParameter("age").length()>0) {
		String activity = request.getParameter("activity");
		float weight = Float.parseFloat(request.getParameter("weight"));
		String goal = request.getParameter("goal");
		int height = Integer.parseInt(request.getParameter("height")) ;
		int age = Integer.parseInt(request.getParameter("age"));
		
		//validate with weight, age and height
		if(weight>0 && height >0 && age>0) {
			System.out.println("Progress ID: " +progressID);
			try {
				UserDAO.updateLatestProgress(progressID, activity, weight, goal, height, age);
				response.sendRedirect("dashboard.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//sql query to update
		}
		
		
		else {
			request.setAttribute("errorMessage", "Invalid input, please try again.");
			request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
			
		}
	}
	
	else {
		request.setAttribute("errorMessage", "Invalid input, please try again.");
		request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
	}
	

}
}
