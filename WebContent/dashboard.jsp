<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserBean"
    import="System.UserDAO"
    import="System.RecipeBean"
    import="System.FoodBean"
    import="java.sql.ResultSet"
    import="java.util.ArrayList"%>
    
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">


<html>
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
            	currentUser = UserDAO.getUserBean(currentUser);
               int BMR = UserDAO.calculateBMR(currentUser);
               currentUser.setBMR(BMR);
               double percentage =  (((double) (UserDAO.getConsumedCalories(UserDAO.getUserID(currentUser)))/currentUser.getBMR())*100);
				if(percentage>100){
				percentage = 100;}
				
				ArrayList<RecipeBean> recommendations = UserDAO.getRecommendations(currentUser);
			
				%>
				
            
<head> 
<meta charset="ISO-8859-1">
<title>Dashboard </title>

</head>
<body>
	<img src="trackorieLogo.png" width="100" height="100" >
<form action="LogoutServlet">
<button class="logout"> Log Out</button>
</form>
<h1>Hello <%= currentUser.getFirstName() + " " + currentUser.getLastName() + ""%>!</h1>

<!-- User profile information -->
<div>
<div class="profile" style="float:left">
Height: <%= currentUser.getHeight() %> cm <br><br>
Weight: <%= currentUser.getCurrentWeight() %> kg <br><br>
Gender: <%= currentUser.getGender() %><br><br>
Age: <%= currentUser.getAge() %> <br><br>
Activity levels: <%= currentUser.getActivityLevel() %> <br><br>
Goal: <%= currentUser.getGoal() %> weight 


<br><br>

<button id="UpdatePersonalInfo" onclick="openPage('updateProfile.jsp')"> Update information</button>
<button onclick="openPage('progressReport.jsp')"> Progress Report</button>
</div> 
<br><br>


<!-- Calorie bar and information -->
<div class ="calories" style="display:inline-block"><br>
<div class="meter meterText">
  <span style="width: <%=percentage%>%"><%=Math.round(percentage) %>%</span>
</div> <br>
<br><br>
<button onclick="openPage('calorieReport.jsp')" style="float: right;"> Calorie report</button>
Your calorie budget today is : <%= currentUser.getBMR() %> <br><br>
Calories remaining for today:  <%= currentUser.getBMR()-UserDAO.getConsumedCalories(UserDAO.getUserID(currentUser)) %> <br><br>
</div>
</div>
<br>

<!-- foods eaten + recommendations -->
<div class="borderOne">

Foods eaten today: <br><br>
<div class="table">

<table width="500" align="center" >
<tr> <th>Item Name</th> <th> Calories </th> </tr>

<!-- recommendations -->

<%if(recommendations.size() != 0){ %>
<div style="float:right">Don't know what to eat with your remaining calories? <br>

<!-- Recommend 1 -->
<div class="w3-container">
  <button onclick="document.getElementById('firstRecommend').style.display='block'" class="w3-button "><%=recommendations.get(0).getRecipeName()%></button>

  <div id="firstRecommend" class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-container">
        <span onclick="document.getElementById('firstRecommend').style.display='none'" class="w3-button w3-display-topright">&times;</span>
        <p><%= recommendations.get(0).getRecipeName() %></p>
        <br><%= recommendations.get(0).getRecipeDescription() %><br><br>
        Ingredients: 
        
        <%ResultSet fooditems = UserDAO.getRecipeFoodItems(UserDAO.getRecipeID(recommendations.get(0).getRecipeName()));
        while(fooditems.next()){
        %>
        <%=fooditems.getString("itemname") %>,
		<%} %>
		<br><br>
        Calories: <%= UserDAO.caloriesGivenRecipeID(UserDAO.getRecipeID(recommendations.get(0).getRecipeName()))%>
      </div>
    </div>
  </div>
</div> <br>
<%} %>


<!-- Recommend 2 -->

<%if(recommendations.size() >= 2){ %>
<div class="w3-container">
  <button onclick="document.getElementById('secondRecommend').style.display='block'" class="w3-button w3-black"><%=recommendations.get(1).getRecipeName()%></button>

  <div id="secondRecommend" class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-container">
        <span onclick="document.getElementById('secondRecommend').style.display='none'" class="w3-button w3-display-topright">&times;</span>
       	<p><%= recommendations.get(1).getRecipeName()%></p><br>
        <%= recommendations.get(1).getRecipeDescription()%><br><br>
        Ingredients: 
        <% ResultSet fooditems2 = UserDAO.getRecipeFoodItems(UserDAO.getRecipeID(recommendations.get(1).getRecipeName()));
        while(fooditems2.next()){
        %>
        <%=fooditems2.getString("itemname")%>,
		<%} %>
		<br><br>
        Calories: <%= UserDAO.caloriesGivenRecipeID(UserDAO.getRecipeID(recommendations.get(1).getRecipeName()))%>
      </div>
    </div>
  </div>
</div> <br>

<%} %>

<%if(recommendations.size() >= 3){ %>
<!-- Recommend 3 -->
<div class="w3-container">
  <button onclick="document.getElementById('thirdRecommend').style.display='block'" class="w3-button w3-black"><%=recommendations.get(2).getRecipeName()%></button>

  <div id="thirdRecommend" class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-container">
        <span onclick="document.getElementById('thirdRecommend').style.display='none'" class="w3-button w3-display-topright">&times;</span>
        <p><%= recommendations.get(2).getRecipeName() %></p><br>
        <%= recommendations.get(2).getRecipeDescription() %><br><br>
        Ingredients:
        <%ResultSet fooditems3 = UserDAO.getRecipeFoodItems(UserDAO.getRecipeID(recommendations.get(2).getRecipeName()));
        while(fooditems3.next()){
        %>
        <%=fooditems3.getString("itemname") %>,
		<%} %>
		<br><br>
        Calories <%= UserDAO.caloriesGivenRecipeID(UserDAO.getRecipeID(recommendations.get(2).getRecipeName()))%></p>
      </div>
    </div>
  </div>
</div> <br>

</div>
<%} %>

<br><br><br>
<!-- recommendation modal -->


<!-- table contents -->
<% 
ResultSet rs = null;
rs = UserDAO.getTodaysLogs(UserDAO.getUserID(currentUser));
while(rs.next())
        {
            %>
                <tr>
                     <td>
                     <%=rs.getString("itemname")%>
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
</div>



<button onclick="openPage('logFood.jsp')"> Log Food</button>
<button onclick="openPage('removeLog.jsp')"> Remove Log</button>



<script>

window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history

window.onhashchange=function(){
	window.location.hash="no-back-button";
	}
</script> 

<script type="text/javascript">
		function openPage(pageURL) {

			window.location.href = pageURL;

		}

</script>



</html>