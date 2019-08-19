package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/***
 * Servlet class handling the create account jsp
 * @author tommy
 *
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		UserBean user = new UserBean();
		user.setUserName(request.getParameter("un"));
		user.setPassword(request.getParameter("pw"));
		user.setFirstName(request.getParameter("fn"));
		user.setLastName(request.getParameter("ln"));
		String validatePass = request.getParameter("confirmpw");
		
		try {
			user = UserDAO.createUser(user);
			
			if (user.isValid() && validatePass.equals(user.getPassword()) && !UserDAO.checkUsernameExists(user.getUsername())){
				
			response.sendRedirect("LoginPage.jsp"); // logged-in page
			}
			else if(!validatePass.equals(user.getPassword())) {
				request.setAttribute("errorMessage", "Passwords do not match, please try again.");
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			}
			else if(UserDAO.checkUsernameExists(user.getUsername())) {
				request.setAttribute("errorMessage", "Username already exists.");
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			}
			else {
				request.setAttribute("errorMessage", "Invalid input, please try again.");
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
				//response.sendRedirect("createAccount.jsp"); //retry
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}
