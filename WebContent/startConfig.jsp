<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="System.UserBean"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile setup</title>
</head>
<body>

            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));%>
			
            Welcome <%= currentUser.getFirstName() + " " + currentUser.getLastName() %>


	<form action="StartConfigServlet">
		
		<label>
		 Gender
		 </label>
		 <input type="radio" name="gender" value="Female" checked> Female 
		<input type="radio" name="gender" value="Male"> Male <br>
		
		<label>
		Age 
		</label>
		<input type="number" max="150" name="age"><br> 

		<label>
		Current weight (kg) 
		</label>
		<input type="number" name="weight"> <br> 
		
		<label>
		Height (cm)
		</label> 
		<input type="number" name="height" ><br>
		
		<label>
		Activity level 
		</label>
		<select name="activity" >
			<option value="light">Light</option>
			<option value="moderate">Moderate</option> 
			<option value="heavy">Heavy</option>
		</select> <br> 
		
		<label>
		Goal
		</label> 
		<select name="goal">
			<option value="lose">Lose weight</option>
			<option value="maintain">Maintain weight</option>
			<option value="gain">Gain weight</option>
		</select> <br> <br>
		<input type="button" value="Go back" onclick="openPage('LoginPage.jsp')">
		<input type="submit" value="Proceed"> <br><br>
		
	</form>
	<div class="error">${errorMessage}</div>
		<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
	</script>
</body>
</html>