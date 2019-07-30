package System;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StartConfigServlet")
public class StartConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
			
		
		UserBean user = new UserBean();
		user.setActivityLevel(request.getParameter("activity"));
		user.setCurrentWeight(Integer.parseInt(request.getParameter("weight")));
		user.setGender(request.getParameter("gender"));
		user.setGoal(request.getParameter("goal"));
		user.setHeight(Integer.parseInt(request.getParameter("height")));
	}
}
