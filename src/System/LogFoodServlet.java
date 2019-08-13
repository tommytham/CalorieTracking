package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

@WebServlet("/LogFoodServlet")
public class LogFoodServlet extends HttpServlet {
	

	/***
	 * error will occur if trying to parse letter into integer
	 * knowing that letter inputted, check if in db
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws java.io.IOException
	 */
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		//get session so can get userid
		HttpSession session = request.getSession(true);
		UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
		
		//get userbean to get userid
    	try {
			currentUser = UserDAO.getUserBean(currentUser);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	
    	//try parsing input into integer, if possible then check if exist in db by setting bean
    	//if details of bean are null, means food item does not exist
		try {
			int itemID = Integer.parseInt(request.getParameter("foodItem"));
			FoodBean food = UserDAO.getFoodBean(itemID);
			if(!food.getItemName().equals(null)) {
				UserDAO.insertEatLog(UserDAO.getUserID(currentUser), itemID);
				request.setAttribute("successMessage", food.getItemName()+" has been logged!");
				request.getRequestDispatcher("/logFood.jsp").forward(request, response);
			}
			else {
				request.setAttribute("errorMessage", "Item does not exist");
				request.getRequestDispatcher("/logFood.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			System.out.println("Letters are not numbers!" + request.getParameter("foodItem"));
			try {
				//if valid name -> id will be greater than 0 as it means item exist in db
				if(UserDAO.getItemID(request.getParameter("foodItem"))>0) {
					int itemID = UserDAO.getItemID(request.getParameter("foodItem"));
					System.out.println(itemID);
					UserDAO.insertEatLog(UserDAO.getUserID(currentUser), itemID);
					request.setAttribute("successMessage", request.getParameter("foodItem")+ " has been logged!");
					request.getRequestDispatcher("/logFood.jsp").forward(request, response);
				}
					
					else {
						request.setAttribute("errorMessage", "Item does not exist");
						request.getRequestDispatcher("/logFood.jsp").forward(request, response);
					}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		

}

}