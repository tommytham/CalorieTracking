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
			
			Username: <br> 		
			<input type="text" name="un"/><br>		
			
			Password: <br>
			<input type="password" name="pw"/><br>
			<br>
			<input type="button" value="Create Account" onclick="openPage('createAccount.jsp')"/>
			<input type="submit" value="Login">			
		
		</form>
		

<script type="text/javascript">
		
 function openPage(pageURL){
	 
 window.location.href = pageURL;
 
 }
</script>

</div>
	</body>
</html>