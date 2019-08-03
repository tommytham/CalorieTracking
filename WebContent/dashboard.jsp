<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserBean"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));%>
			<% UserBean currentUser2 = (UserBean) (session.getAttribute("currentSessionbean"));%>
           
<head>
<meta charset="ISO-8859-1">
<title>Dashboard </title>
</head>
<body>
<h1>Hello <%= currentUser.getFirstName() + " " + currentUser.getLastName() + ""%>!</h1>
<form action="DashboardServlet">

Calories remaining for today:  <%= currentUser2.getBMR() %> <br><br>

</form>
<br>
<form>
Foods eaten today: <br><br> 
</form>

<input type="button" value="Log food"/>

</body>
</html>