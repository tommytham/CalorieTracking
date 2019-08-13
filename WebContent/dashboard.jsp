<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserBean"
    import="System.UserDAO"
    import="java.sql.ResultSet"%>
    
<!DOCTYPE html>
<link href="styleSheet.css" rel="stylesheet" type="text/css">

<html>
            <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
            	currentUser = UserDAO.getUserBean(currentUser);
            	System.out.println(currentUser.getGender());
               int BMR = UserDAO.calculateBMR(currentUser);
               currentUser.setBMR(BMR);
               double percentage =  (((double) (UserDAO.getConsumedCalories(UserDAO.getUserID(currentUser)))/currentUser.getBMR())*100);
				if(percentage>100){
				percentage = 100;}%>
            
           
<head> 
<meta charset="ISO-8859-1">
<title>Dashboard </title>
</head>
<body>
<h1>Hello <%= currentUser.getFirstName() + " " + currentUser.getLastName() + ""%>!</h1>

<div>
<div class="profile" style="float:left">
Your height: <%= currentUser.getHeight() %> cm <br>
Your weight: <%= currentUser.getCurrentWeight() %> kg <br>
Goal: <%= currentUser.getGoal() %>

<br>
<br>
<br>
<br>
<button id="UpdatePersonalInfo"> Update information</button>

</div> 

<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <p>Some text in the Modal..</p>
    <select>
  <option value="updateHeight">Update height</option>
  <option value="updateWeight">Update weight</option>
  <option value="updateGoal">Update goal</option>
  <option value="updateActivity">Update activity</option>
</select>
  </div>

</div>
<div class ="calories" style="display:inline-block">


<div class="meter">
  <span style="width: <%=percentage%>%"></span>
</div> <br>

Your calorie budget today is : <%= currentUser.getBMR() %> <br>
Calories remaining for today:  <%= currentUser.getBMR()-UserDAO.getConsumedCalories(UserDAO.getUserID(currentUser)) %> <br><br>

</div>
</div>
<br>


<form>


Foods eaten today: <br><br>

 

<div class="table" >

<table width="500" align="center" >


<tr> <th>Item Name</th> <th> Calories </th> </tr>

<div style="float:right">Recommendations <br>

<button>Item 1</button> <br>
<button>Item 2</button> <br>
<button>Item 3</button>

</div>
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
</form>



<button id="logFood" onclick="openPage('logFood.jsp')"> Log Food</button>

</body>

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

<script>
// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("UpdatePersonalInfo");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>	

</html>