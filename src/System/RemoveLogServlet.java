package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveLogServlet")
public class RemoveLogServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		

		String[] loggedFoods = request.getParameterValues("loggedItem");
		if(loggedFoods.length>0) {
			for(String value: loggedFoods) {
				try {
					UserDAO.removeEatLog(Integer.parseInt(value));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("successMessage", "Removed!");
			request.getRequestDispatcher("/removeLog.jsp").forward(request, response);
		}
	
		else {
			request.setAttribute("errorMessage", "Please select");
			request.getRequestDispatcher("/removeLog.jsp").forward(request, response);
		}

	}
}
