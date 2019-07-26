<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Account Page </title>
</head>
<body>
<h1>Create Account</h1>

<form action="CreateAccountServlet">

Username: <input type="text" name="un" /> <br>
Password: <input type="text" name="pw" /> <br>
FirstName: <input type="text" name="fn"/> <br>
LastName: <input type="text" name="ln"/> <br> <br>

<input type="button" value="Go Back" onclick="openPage('LoginPage.jsp')"/>
<input type="submit" value="Create">	

<div style="color: #FF0000;">${errorMessage}</div>

</form>

<script type="text/javascript">
		
 function openPage(pageURL){
	 
 window.location.href = pageURL;
 
 }
</script>
</body>
</html>