<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="System.UserBean"
    import="System.UserDAO" %>
    
    <% 
    UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
    currentUser = UserDAO.getUserBean(currentUser);
            	%>
    
  <link href="styleSheet.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update User Profile</title>
</head>
<body>
<input type="button" value="Go Back" onclick="openPage('dashboard.jsp')" />

<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'updateWeight')" id="defaultOpen">Update Weight</button>
  <button class="tablinks" onclick="openTab(event, 'updateOther')">Update Other</button>
</div>
<br><br><br><br>

<div id="updateWeight" class="tabcontent" >
<div class="borderOne">
<form action="UpdateProfileServlet">
Weight: 
<input type="number" placeholder="enter weight (kg)" name="updateWeight" onkeypress="return checkNumber(event)" min=1> 
<p>Date: <input type="text" id="datepicker" name="calendarDate" readonly='true'></p>
<br><br><input type="submit" value="Update Weight"> 
</form>
</div>
<div class="error">${errorMessage}</div>
</div>


<div id="updateOther" class="tabcontent">

<form action="UpdateOtherServlet">
		
		
		<label>
		Age 
		</label>
		<input type="number" max="150" onkeypress="return checkNumber(event)" name="age" value="<%=currentUser.getAge()%>"><br> 

		<label>
		Current weight (kg) 
		</label>
		<input type="number" name="weight" onkeypress="return checkNumber(event)" value="<%=currentUser.getCurrentWeight()%>" > <br> 
		
		<label>
		Height (cm)
		</label> 
		<input type="number" name="height" onkeypress="return checkNumber(event)" value="<%=currentUser.getHeight()%>" ><br>
		
		<label>
		Activity level 
		</label>
		<select name="activity" >
		<%if(currentUser.getActivityLevel().equals("light")){ %>
			<option selected="selected" value="light">Light</option>
			<option value="moderate">Moderate</option> 
			<option value="heavy">Heavy</option>
			
		<%} 
		else if(currentUser.getActivityLevel().equals("moderate")){ %>
			<option value="light">Light</option>
			<option selected="selected"value="moderate">Moderate</option> 
			<option value="heavy">Heavy</option>
			
		<%} else{ %>
		<option value="light">Light</option>
		<option value="moderate">Moderate</option> 
		<option selected="selected" value="heavy">Heavy</option>
		
	<%} %> 
		
			
			
		</select> <br> 
		
		<label>
		Goal
		</label> 
		<select name="goal">
				<%if(currentUser.getGoal().equals("lose")){ %>
			<option selected="selected" value="lose">Lose weight</option>
			<option value="maintain">Maintain weight</option>
			<option value="gain">Gain weight</option>
			
		<%}else if(currentUser.getGoal().equals("maintain")){ %>
		<option value="lose">Lose weight</option>
		<option selected="selected" value="maintain">Maintain weight</option>
		<option value="gain">Gain weight</option>
		
	<%} else{ %>
	<option value="lose">Lose weight</option>
	<option value="maintain">Maintain weight</option>
	<option selected="selected" value="gain">Gain weight</option>
	
<%}%>
		</select> <br> <br>
		<input type="button" value="Go back" onclick="openPage('LoginPage.jsp')">
		<input type="submit" value="Proceed"> <br><br>
		
	</form>
</div>

</body>
</html>

<!-- Script to validate weight input -->
<script>
	function checkNumber(evt){
	    var charCode = (evt.which) ? evt.which : event.keyCode
	    if (charCode > 31 && (charCode < 48 || charCode > 57))
	        return false;
	    return true;
	}
</script>
	

<!-- Script for calendar https://jqueryui.com/datepicker/ -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({dateFormat: 'yy-mm-dd',
    	maxDate: '0'});
 

  } );
  </script>
  


<script type="text/javascript">

function openPage(pageURL) {
			window.location.href = pageURL;
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