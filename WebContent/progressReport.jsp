<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserDAO"
    import="java.sql.ResultSet"
    import="java.util.*"
    import="com.google.gson.Gson"
    import="System.UserBean"%>    
    <%
    UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
	currentUser = UserDAO.getUserBean(currentUser);
	int userID = UserDAO.getUserID(currentUser);
	ResultSet progressions = UserDAO.getProgress(userID); 
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();

while(progressions.next()){
map = new HashMap<Object,Object>(); map.put("label", progressions.getString("date")); map.put("y", progressions.getInt("weight")); list.add(map);
}
String dataPoints = gsonObj.toJson(list);
%>

<link href="styleSheet.css" rel="stylesheet" type="text/css">
<!DOCTYPE html>
<!-- Code altered from https://developers.google.com/chart/interactive/docs/gallery/linechart -->
<html>

 <head>
<title>Progress Report</title>
  </head>
  <body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<br><br>

<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Date</th> <th>Weight (kg)</th> </tr>


<% 
ResultSet progressions2 = UserDAO.getProgress(userID); 
while(progressions2.next())
        {
            %>
                <tr>

                	 <td>
                     <%=progressions2.getString("date")%>
                     </td>
                     
                     <td>
                	 <%=Integer.toString(progressions2.getInt("weight")) %>
                	 </td>
                	        
                     
                </tr>
            <% 
        }
    %>



</table>
</div>
  </body>
</html>

<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2",
	title: {
		text: "Your weight progress"
	},
	axisX: {
		title: "Date"
	},
	axisY: {
		title: "Weight (in kg)"
	},
	data: [{
		type: "line",
		yValueFormatString: "#,##0kg",
		dataPoints : <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>