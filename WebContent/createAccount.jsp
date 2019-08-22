<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Account Page</title>
</head>
<body>
	<h1>Create Account</h1>
<div class="borderOne">
	<div class="createAccount">
		<form action="CreateAccountServlet">

			<label>Username:</label> <input type="text" name="un" /> <br> <br>
			<label>Password:</label> <input type="password" name="pw" /> <br> <br> 
			<label>Confirm Password:</label> <input type="password" name="confirmpw"/><br> <br>
			<label>First Name:</label> <input type="text" name="fn" /> <br> <br>
			 <label>Last Name:</label> <input type="text" name="ln" /> <br> <br>
			<br> 
			<input type="button" value="Go Back" onclick="openPage('LoginPage.jsp')" />
			 <input type="submit" value="Create">

			<div class="error">${errorMessage}</div>

		</form>
	</div>
</div>

	<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
	</script>
</body>
</html>