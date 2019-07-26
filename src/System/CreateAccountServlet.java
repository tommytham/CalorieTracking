package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		try {
			user = UserDAO.createUser(user);
			
			if (user.isValid()){
			response.sendRedirect("LoginPage.jsp"); // logged-in page
			}
			else {
				request.setAttribute("errorMessage", "Invalid input");
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
				//response.sendRedirect("createAccount.jsp"); //retry
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}
