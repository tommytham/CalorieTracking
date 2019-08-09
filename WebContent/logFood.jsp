<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserDAO"
    import="java.sql.ResultSet"%>
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">
<html>


<head>
<meta charset="ISO-8859-1">
<title>Log your food</title>
</head>
<body>

<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />
<h1>What have you eaten today?</h1>

<p>Please select from the food list below</p> <br>
<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Item ID</th> <th>Item Name</th> <th>Item Description</th> <th> Calories </th> </tr>

<% 
ResultSet rs = null;
rs = UserDAO.getFoodTable();
while(rs.next())
        {
            %>
                <tr>
                	 <td>
                	 <%=Integer.toString(rs.getInt("fooditemid")) %>
                	 </td>
                	        
                	 <td>
                     <%=rs.getString("itemname")%>
                     </td>
                     
                     <td>
                     <%=rs.getString("itemdescription") %>
                     </td>
                     
                     <td>
                     <%=Integer.toString(rs.getInt("calories"))%>
                	</td>
                </tr>
            <% 
        }
    %>



</table>
</div>

<br>

<form action="LogFoodServlet">
<input type="text" placeholder="enter id or item name" name="foodItem"><button>Log food item</button>
</form>
<div class="error">${errorMessage}</div>
<div class="success">${successMessage}</div>
</body>
	<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}
	</script>
</html>