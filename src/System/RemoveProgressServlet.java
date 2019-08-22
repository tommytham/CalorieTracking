package System;

/***
 * Servlet that enables the user to remove a progression log
 */

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveProgressServlet")
public class RemoveProgressServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		

		String[] loggedProgress = request.getParameterValues("progress");
		if(loggedProgress.length>0) {
			for(String value: loggedProgress) {
				try {
					UserDAO.removeProgressLog(Integer.parseInt(value));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("successMessage", "Removed!");
			request.getRequestDispatcher("/progressReport.jsp").forward(request, response);
		}
	
		else {
			request.setAttribute("errorMessage", "Please select");
			request.getRequestDispatcher("/progressReport.jsp").forward(request, response);
		}

	}
}
