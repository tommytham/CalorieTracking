package System;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateProfileServlet")

public class UpdateProfileServlet extends HttpServlet {
	int newWeight;
	String date;
	String todaysDate;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		HttpSession session = request.getSession(true);
		UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
		todaysDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(LocalDate.now());
		
		if ((request.getParameter("updateWeight").length() > 0 && request.getParameter("calendarDate").length()>0 )) {
			try {
				int newWeight = Integer.parseInt(request.getParameter("updateWeight")); 
				String date = request.getParameter("calendarDate");
				int userID = UserDAO.getUserID(currentUser);
				//need to check if already progress exists on the date picked
				//do this by querying database select where user id = ? and date = calendarDate
				//if  result set empty then insert, else update
				if(UserDAO.checkProgressExists(userID, date)) {
					currentUser.setCurrentWeight(newWeight);
					UserDAO.updateExistingProgress(userID, date, newWeight);
					//update sql query
					session.setAttribute("currentSessionUser", currentUser); 
					response.sendRedirect("dashboard.jsp");
				}
				else {
				UserDAO.insertWeightProgress(currentUser, newWeight, date);
				response.sendRedirect("dashboard.jsp");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

			
		}
		else {
			System.out.println("BAD");
			request.setAttribute("errorMessage", "Invalid input, please try again.");
			request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
		}

		
		

	}
}
