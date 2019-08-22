<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

<div class="borderOne">
<form action="UpdateProfileServlet">
Weight: 
<input type="number" placeholder="enter weight (kg)" name="updateWeight" onkeypress="return checkNumber(event)" min=1> 
<p>Date: <input type="text" id="datepicker" name="calendarDate" readonly='true'></p>
<br><br><input type="submit" value="Update Weight"> 
</form>
</div>
<div class="error">${errorMessage}</div>

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