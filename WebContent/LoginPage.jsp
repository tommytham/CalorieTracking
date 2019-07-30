<%@ page language="java" 
    contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>
	<head>
                                <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
		<title>Login Page</title>
	</head>

	<body>
	
	<div class="login">
	<h1>Login Page</h1>
		<form action="LoginServlet" >
			
					
			<input type="text" placeholder="Username" name="un"/><br><br>		
			
			<input type="password" placeholder="Password" name="pw"/><br>
			<br>
			
			<input type="submit" value="Login">	<br><br>		
		<div style="font-size: 13px">Don't have an account yet? <a href="createAccount.jsp" >Sign up here </a></div>
		</form>
		<br>
		<div class="error">${errorMessage}</div>
		

</div>
	</body>
</html>