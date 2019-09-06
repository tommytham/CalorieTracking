package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StartConfigServlet")
public class StartConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int BMR;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		HttpSession session = request.getSession(true); //gets current session
		UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser")); //gets bean from previous session (login page) and assigns to new bean
		if(request.getParameter("weight").length()>0 && request.getParameter("height").length()>0 && request.getParameter("age").length()>0) {
			currentUser.setActivityLevel(request.getParameter("activity"));
			currentUser.setCurrentWeight(Float.parseFloat(request.getParameter("weight")));
			currentUser.setGender(request.getParameter("gender"));
			currentUser.setGoal(request.getParameter("goal"));
			currentUser.setHeight(Integer.parseInt(request.getParameter("height")));
			currentUser.setAge(Integer.parseInt(request.getParameter("age")));
			
			//validate with weight, age and height
			if(currentUser.getCurrentWeight()>0 && currentUser.getHeight()>3 && currentUser.getAge()>0) {
				try {
					BMR = UserDAO.calculateBMR(currentUser);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				currentUser.setBMR(BMR);
				
				session.setAttribute("currentSessionUser", currentUser);
				System.out.println(currentUser.getBMR());
				
				try {
					UserDAO.setupProfile(currentUser);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				response.sendRedirect("dashboard.jsp");
			}
			
			
			else {
				request.setAttribute("errorMessage", "Invalid input, please try again.");
				request.getRequestDispatcher("/startConfig.jsp").forward(request, response);
				
			}
		}
		
		else {
			request.setAttribute("errorMessage", "Invalid input, please try again.");
			request.getRequestDispatcher("/startConfig.jsp").forward(request, response);
		}
		

	}

}
