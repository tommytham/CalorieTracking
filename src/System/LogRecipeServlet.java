package System;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogRecipeServlet")
public class LogRecipeServlet extends HttpServlet {

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
			int recipeID = Integer.parseInt(request.getParameter("logRecipe"));
			RecipeBean recipe = UserDAO.getRecipeBean(recipeID);
			if(!recipe.getRecipeName().equals(null)) {
				UserDAO.insertRecipeEatLog(UserDAO.getUserID(currentUser), recipeID);
				request.setAttribute("successRecipeMessage", recipe.getRecipeName()+" has been logged!");
				request.getRequestDispatcher("/logFood.jsp").forward(request, response);
			}
			else {
				request.setAttribute("errorRecipeMessage", "Recipe does not exist");
				request.getRequestDispatcher("/logFood.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			System.out.println("Letters are not numbers!" + request.getParameter("logRecipe"));
			try {
				//if valid name -> id will be greater than 0 as it means item exist in db
				if(UserDAO.getRecipeID(request.getParameter("logRecipe"))>0) {
					int recipeID = UserDAO.getRecipeID(request.getParameter("logRecipe"));
					System.out.println(recipeID);
					UserDAO.insertRecipeEatLog(UserDAO.getUserID(currentUser), recipeID);
					request.setAttribute("successRecipeMessage", request.getParameter("logRecipe")+ " has been logged!");
					request.getRequestDispatcher("/logFood.jsp").forward(request, response);
				}
					
					else {
						request.setAttribute("errorRecipeMessage", "Recipe does not exist");
						request.getRequestDispatcher("/logFood.jsp").forward(request, response);
					}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		

}
}
