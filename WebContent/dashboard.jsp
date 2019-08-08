<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserBean"
    import="System.UserDAO"%>
    
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
            	currentUser = UserDAO.getUserBean(currentUser);
            	System.out.println(currentUser.getGender());
               double BMR = UserDAO.calculateBMR(currentUser);
               currentUser.setBMR(BMR);%>
            
           
<head> 
<meta charset="ISO-8859-1">
<title>Dashboard </title>
</head>
<body>
<h1>Hello <%= currentUser.getFirstName() + " " + currentUser.getLastName() + ""%>!</h1>
<form action="DashboardServlet">

Calories remaining for today:  <%= currentUser.getBMR() %> <br><br>

</form>
<br>
<form>
Foods eaten today: <br><br> 
</form>


<button id="logFood" onclick="openPage('logFood.jsp')"> Log Food</button>

</body>

<script>
window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history

window.onhashchange=function(){
	window.location.hash="no-back-button";
	}
</script> 

		<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}

	</script>


</html>