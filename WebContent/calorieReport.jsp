<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
        import="System.UserDAO"
    import="java.sql.ResultSet"
    import="java.util.*"
    import="com.google.gson.Gson"
    import="System.UserBean"%>
    <link href="styleSheet.css" rel="stylesheet" type="text/css">
        <%
    UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
	currentUser = UserDAO.getUserBean(currentUser);
	int userID = UserDAO.getUserID(currentUser);
	ResultSet allIntakes = UserDAO.getAllCalorieIntakes(userID);
	HashMap<String,Integer> calorieGoals = UserDAO.getAllCalorieGoals(userID);
	
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> list2 = new ArrayList<Map<Object,Object>>();
while(allIntakes.next()){
map = new HashMap<Object,Object>(); map.put("label", allIntakes.getString("date")); map.put("y", allIntakes.getInt("totalcalories")); list.add(map);
}


for (Map.Entry<String, Integer> entry : calorieGoals.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
	map = new HashMap<Object,Object>(); 
	map.put("label", key); 
	map.put("y", value); 
	list2.add(map);
}



String dataPoints = gsonObj.toJson(list);
//String dataPoints2 =gsonObj.toJson(list2);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Calorie Report</title>
</head>
<body>
<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />
<br><br><br><br>
<div id="chartContainer" style="height: 370px; width: 100%;"></div><br><br>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>

<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}

</script>

<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2",
	title: {
		text: "Your Calorie Intake"
	},
	axisX: {
		title: "Date"
	},
	axisY: {
		title: "Calories"
	},
	data: [{
		type: "line",
		yValueFormatString: "###kcal",
		dataPoints : <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>

<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Item Name</th> <th>Quantity Consumed</th> </tr>


<% 
ResultSet progressions2 = UserDAO.getFoodLogCount(userID); 
while(progressions2.next())
        {
            %>
                <tr>

                	 <td>
                     <%=progressions2.getString("itemname")%>
                     </td>
                     
                     <td>
                	 <%=Integer.toString(progressions2.getInt("amount")) %>
                	 </td>
                	        
                     
                </tr>
            <% 
        }
    %>



</table>
</div>