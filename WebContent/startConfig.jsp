<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="System.UserBean"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));%>
			
            Welcome <%= currentUser.getFirstName() + " " + currentUser.getLastName() %>

	<form action="StartConfigServlet">
		Gender <input type="radio" name="gender" value="Female" checked>
		Female <input type="radio" name="gender" value="Male"> Male <br>
		Age <input type="number" max="150" name="age"><br> 
		Current Weight (kg) <input type="number" name="weight"> <br> 
		Height (cm) <input type="number" name="height" ><br>
		Activity level <select name="activity">
			<option value="light">Light</option>
			<option value="moderate">Moderate</option> 
			<option value="heavy">Heavy</option>
		</select> <br> Goal <select name="goal">
			<option value="lose">Lose weight</option>
			<option value="maintain">Maintain weight</option>
			<option value="gain">Gain weight</option>
		</select> <br> <br>
		<input type="button" value="Go back" onclick="openPage('LoginPage.jsp')">
		<input type="submit" value="Proceed" onclick="openPage('preferences.jsp')"> 
	</form>
	
		<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
	</script>
</body>
</html>