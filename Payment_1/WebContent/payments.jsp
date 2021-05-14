<%@ page import="com.payment"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payments.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
  <div class="container">
		<div class="row">
			<div class="col">


				<h1>Payments Management</h1>
				<form method='post' action='Items.jsp' id='formItem' name='formItem'>
					user ID: <input id='itemCode' name='itemCode' type='text' class='form-control col-md-3'><br> 
					Payment Method : <input id='itemName' name='itemName' type='text' class='form-control col-md-3'><br> 
					Total Price: <input id='itemPrice' name='itemPrice' type='text' class='form-control col-md-3'><br> 
					
					<input id='btnSave' name='btnSave' type='button' value='Save' class='btn btn-primary'> 
					<input type='hidden' id='hidItemIDSave' name='hidItemIDSave' value=''>
				</form>

				<br>

				<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
				<div id='alertError' name='alertError' class='alert alert-danger'></div>

				<br>
				<div id="divItemsGrid">
				<%
				  payment itemObjRead = new payment();
				  out.print(itemObjRead.readItems());
				%>
				</div>

			</div>
		</div>
	</div>

</body>
</html>