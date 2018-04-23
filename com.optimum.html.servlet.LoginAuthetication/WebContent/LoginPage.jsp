<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.header {
	background-color: #cceeff;
	color: black;
	text-align: center;
}

.footer {
	position: fixed;
	left: 0;
	bottom: 0;
	width: 100%;
	background-color: #cceeff;
	color: black;
	text-align: center;
}
</style>

<title>Login Page</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<div class="header">
	<img src="123.png" alt="Optimum Solution Logo" class="img-fluid">
</div>

<body>

	<br>
	<br>
	<br>
	<br>
	<br>

	<div class="container" style="margin-top: 15px">
		<div class="col-md-8 ">
			<div id="myCarousel" class="carousel slide" data-ride="carousel">

				<!-- Wrapper for slides -->
				<div class="carousel-inner">
					<div class="item active">
						<img src="flower.jpg" alt="Flower" style="width: 100%; height:450px;">
					</div>
					<div class="item">
						<img src="tiger.jpg" alt="Tiger" style="width: 100%; height:450px;">
					</div>
					<div class="item">
						<img src="peacock.jpg" alt="Peacock" style="width: 100%; height:450px;">
					</div>
				</div>

				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel" data-slide="prev"> 
				<span class="glyphicon glyphicon-chevron-left"></span> 
				<span class="sr-only">Previous</span>
				</a> 
				<a class="right carousel-control" href="#myCarousel" data-slide="next"> 
				<span class="glyphicon glyphicon-chevron-right"></span> 
				<span class="sr-only">Next</span>
				</a>
			</div>
		</div>

	<form method="post" action="EmployeeController">		
			<div class="col-md-4 ">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-title">
							<strong>Sign in </strong>
						</div>
					</div>
					<div class="panel-body">

							<div class="form-group">
								${invalidError}
								<br> 
								<label for="UserID">User ID</label> 
								<input type="text" class="form-control" style="border-radius: 0px" id="UserID" placeholder="Enter User ID" name="uid" required="required"> 
								<br>
							</div>

							<div class="form-group">
								<label for="Password">Password</label> 
								<input type="password" class="form-control" style="border-radius: 0px" id="Password" placeholder="Enter Password" name="pass" required="required">
								<br>
							</div>

							<a href="forgetPass" data-toggle="modal" data-target="#forgetPass"><u>Forgot Password?</u></a>
							<button type="submit" class="btn btn-sm btn-default pull-right">Log in</button>
							<br>
							<h4>${success}${failure}</h4>
					</div>
				</div>
			</div>
	</form>
	</div>

	<form method="post" action="ForgotPasswordController">
		<div class="modal fade" id="forgetPass" role="dialog">
			<div class="modal-dialog modal-m">
				<div class="modal-content">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Forget Password?</h4>
					</div>

					<div class="modal-body">
						<div class="form-group">
							<label for="UserID">Enter Email Address:</label> 
							<input type="email" class="form-control" pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" style="border-radius: 0px" id="Email" name="email"> 
							<br>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default center-block">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</form>


	<div class="footer">
		<p>&copy; Optimum Solutions Ptd Ltd</p>
	</div>
</body>

</html>