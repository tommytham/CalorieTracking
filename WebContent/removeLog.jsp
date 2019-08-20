<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserDAO"
    import="System.UserBean"
    import="java.sql.ResultSet"%>
                <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
            	currentUser = UserDAO.getUserBean(currentUser);%>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Remove Log</title>

</head>
<body>
<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />
<br>

<div class="table">


<table width="500" align="center" >
<tr> <th>Item Name</th> <th> Calories </th> </tr>

<!-- table contents -->
<form action="RemoveLogServlet">
<% 
ResultSet rs = null;
rs = UserDAO.getTodaysLogs(UserDAO.getUserID(currentUser));
while(rs.next())
        {
            %>
                <tr>
                     <td>
                     
        			<input type="checkbox" name="loggedItem" value="<%=rs.getInt("logid")%>"/><%=rs.getString("itemname")%>
                  
                     </td>
                     
                     
                     <td>
                     <%=Integer.toString(rs.getInt("calories"))%>
                	</td>
                </tr>
            <% 
        }
    %>



</table>


<br>
Total calories: <%=UserDAO.getConsumedCalories(UserDAO.getUserID(currentUser)) %>
</div>


<input type="Submit"  name="logRecipe" value="Remove">
</form>

</body>
</html>

<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
</script>