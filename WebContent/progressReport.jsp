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
<!DOCTYPE html>
<!-- Code altered from https://developers.google.com/chart/interactive/docs/gallery/linechart -->
<html>

 <head>
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
  </head>
  <body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
  </body>
</html>