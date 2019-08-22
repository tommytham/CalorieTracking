<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserDAO"
    import="java.sql.ResultSet"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>


<head>
<meta charset="ISO-8859-1">
<title>Log your food</title>
</head>
<body>

<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />
<h1>What have you eaten today?</h1>

<p>Please select from the food list below</p> <br>

<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'items')" id="defaultOpen">Food Items</button>
  <button class="tablinks" onclick="openTab(event, 'recipe')">Food Recipes</button>
</div>
<br><br><br> <br>

<div id="items" class="tabcontent" >
<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Item ID</th> <th>Item Name</th> <th>Item Description</th> <th> Calories </th> </tr>


<% 
ResultSet rs = null;
rs = UserDAO.getFoodTable();
while(rs.next())
        {
            %>
                <tr>
                	 <td>
                	 <%=Integer.toString(rs.getInt("fooditemid")) %>
                	 </td>
                	        
                	 <td>
                     <%=rs.getString("itemname")%>
                     </td>
                     
                     <td>
                     <%=rs.getString("itemdescription") %>
                     </td>
                     
                     <td>
                     <%=Integer.toString(rs.getInt("calories"))%>
                	</td>
                </tr>
            <% 
        }
    %>



</table>
</div>

<br>

<form action="LogFoodServlet">
<input type="text" placeholder="enter id or item name" name="foodItem"><button>Log food item</button>
</form>
<div class="error">${errorMessage}</div>
<div class="success">${successMessage}</div>
</div>

<!-- what is shown when user clicks on recipe tag -->
<div id="recipe" class="tabcontent">
<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Recipe ID</th> <th>Recipe Name</th> <th>Recipe Description</th> <th>Recipe Ingredients <th> Calories </th> </tr>


<% 
ResultSet rs2 = UserDAO.getRecipeTable();

while(rs2.next())
        {
	ResultSet rs3 = UserDAO.getRecipeFoodItems(rs2.getInt("recipeid"));
	int totalCalories = UserDAO.caloriesGivenRecipeID(rs2.getInt("recipeid"));
            %>
                <tr>
                	 <td>
                	 <%=Integer.toString(rs2.getInt("recipeid")) %>
                	 </td>
                	        
                	 <td>
                     <%=rs2.getString("recipename")%>
                     </td>
                     
                     <td>
                     <%=rs2.getString("recipedescription") %>
                     </td>
                     
                     <td>
                     <%while(rs3.next()){ %>
                    	 <%=rs3.getString("itemname")%><br>
                     <%} %>
                     </td>
                     
                     <td>
                     <%=totalCalories%>
                	</td>
                </tr>
            <% 
        }
    %>



</table>
</div>
<br><br>
<form action="LogRecipeServlet">
<input type="text" placeholder="enter id or recipe name" name="logRecipe"><button>Log recipe</button>
</form>
<div class="error">${errorRecipeMessage}</div>
<div class="success">${successRecipeMessage}</div>
</div>




</body>
	<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
	</script>
	
	<script>
function openTab(evt, tabName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace("active", "");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}
</script>

<script>
// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>

<script>
function popupMessage() {
  alert("I am an alert box!");
}
</script>

</html>