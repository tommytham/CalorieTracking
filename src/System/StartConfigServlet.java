package System;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StartConfigServlet")
public class StartConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static double BMR;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
			
		if(request.getParameter("activity").length()>0 && request.getParameter("height").length()>0 && request.getParameter("age").length()>0) {
			UserBean bean = new UserBean();
			bean.setActivityLevel(request.getParameter("activity"));
			bean.setCurrentWeight(Integer.parseInt(request.getParameter("weight")));
			bean.setGender(request.getParameter("gender"));
			bean.setGoal(request.getParameter("goal"));
			bean.setHeight(Integer.parseInt(request.getParameter("height")));
			bean.setAge(Integer.parseInt(request.getParameter("age")));
			
			//validate with weight, age and height
			if(bean.getCurrentWeight()>0 && bean.getHeight()>0 && bean.getAge()>0) {
				if(bean.getGender().equals("Male")) {
					BMR = 10*bean.getCurrentWeight() + 6.25*bean.getHeight()
							- 5*bean.getAge() + 5;
							
				}
				else{
					BMR = 10*bean.getCurrentWeight() + 6.25*bean.getHeight()
					- 5*bean.getAge() - 161;
				}
			
				bean.setBMR(BMR);
				///////////////// replace this later with sql statement updating BMR attribute
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionbean", bean);
				/////////////////
				System.out.println(bean.getBMR());
				response.sendRedirect("preferences.jsp");
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
