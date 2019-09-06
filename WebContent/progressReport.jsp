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
	ResultSet progressions = UserDAO.getProgress(userID); //get all progression logs
	
	Gson gsonObj = new Gson();
	Map<Object,Object> map = null;
	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();

	while(progressions.next()){
		map = new HashMap<Object,Object>(); 
		map.put("label", progressions.getString("date")); 
		map.put("y", progressions.getInt("weight")); 
		list.add(map);
	}
	
	String dataPoints = gsonObj.toJson(list);
%>

<link href="styleSheet.css" rel="stylesheet" type="text/css">
<!DOCTYPE html>
<!-- Code altered from https://canvasjs.com/jsp-charts/line-chart/ -->
<html>

 <head>
<title>Progress Report</title>
<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />
<br><br><br><br>
<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'progressReport')" id="defaultOpen"> Progress Report</button>
  <button class="tablinks" onclick="openTab(event, 'deleteProgress')">Delete Progress</button>
</div> <br> <br>
  </head>
  <body>
  


<!-- Progress Report Tab -->
<br><br>
<div id="progressReport" class="tabcontent" >
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
</div>

</body>

<!-- Delete Progress Tab -->
<body>
<div id="deleteProgress" class="tabcontent" >
<div class="table" >
<table style="width:80%" align="center">

<tr> <th>Date</th> <th>Weight (kg)</th> </tr>

<form action="RemoveProgressServlet">
<% 
ResultSet progressions3 = UserDAO.getProgress(userID); 
while(progressions3.next())
        {
            %>
                <tr>
                     <td>
                     
        			<input type="checkbox" name="progress" value="<%=progressions3.getString("progressionid")%>"/><%=progressions3.getString("date")%>
                  
                     </td>
                     
                     
                     <td>
                     <%=Integer.toString(progressions3.getInt("weight")) %>
                	</td>
                </tr>
            <% 
        }
    %>



</table>


<br>
<input type="Submit"  name="removeProgress" value="Remove">
</form>
</div>
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

<script>
function openTab(evt, tabName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}
</script>

<script>
// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>
		<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}

	</script>