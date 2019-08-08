package System;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		try {
			
			//get input from form
			UserBean user = new UserBean();
			user.setUserName(request.getParameter("un"));
			user.setPassword(request.getParameter("pw"));

			user = UserDAO.login(user);

			if (user.isValid()) {

				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				
				if(UserDAO.checkSetup(user) == false) {
				response.sendRedirect("startConfig.jsp"); // logged-in page
				
				//if current weight is null (implies not entered personal info
				//then direct to start config
			}
				else {
					user = UserDAO.getUserBean(user);
					session.setAttribute("currentSessionUser", user);
					response.sendRedirect("dashboard.jsp");
				}
				}

			else
				request.setAttribute("errorMessage", "User details do not match. Please try again.");
				request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
				
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
}
}