<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Account Page </title>
</head>
<body>

<form action="CreateAccountServlet">

Username: <input type="text" name="un" /> <br>
Password: <input type="text" name="pw" /> <br>
FirstName: <input type="text" name="fn"/> <br>
LastName: <input type="text" name="ln"/> <br> <br>
<input type="submit" value="Create">	

</form>
${errorMessage}

</body>
</html>