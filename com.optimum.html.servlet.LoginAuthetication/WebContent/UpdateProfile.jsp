<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Profile Page</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<script>
function myFunction() {
	window.top.location.reload();
}
</script>

<form method="post" action="UpdateProfileController" enctype='multipart/form-data' onsubmit="myFunction()">
			<div class="container" style="margin-top: 0.5px">
				<div class="col-md-6">				
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-title">
								<strong>Update Profile </strong> <br> <br>
								<strong>Please enter your updated details </strong>
								<br>${successUpdate}										
							</div>
						</div>						
						<div class="panel-body">
							<form role="form">

							<div class="form-group">
								<label for="ProfilePic">Upload Profile Picture (Select the file):</label> 
								<input type="file" name="profilePic" accept="image/*">
								<br> 
								<br>
							</div>

							<div class="form-group">
									<label for="Address">Update Address:</label>
									<input type="text" class="form-control" style="border-radius: 0px" id="Address" name="address">
									<br>
							</div>

								
							<div class="form-group">
									<label for="Qualification">Update Qualification:</label>
									<input type="text" class="form-control" pattern="^([A-Za-z]+[,.]?[ ]?|[A-Za-z]+['-]?)+$" style="border-radius: 0px" id="Qualification" name="qualification">
									<br>
							</div>
								
							<div class="form-group">
									<label for="Certificate">Add Certificate (Select the file):</label>
									<input type="file" name="certificate" accept="image/*">
									<br> 
									<br>
							</div>								
								
							<div class="form-group">
									<label for="Mobile">Update Mobile</label> 
									<input type="text" class="form-control" pattern="[0-9]{8}" style="border-radius: 0px" id="Mobile" name="mobile">
									<br>
							</div>
								
								<button type="submit" class="btn btn-sm btn-default center-block">Update</button>
							</form>

						</div>
					</div>
					
				</div>
			</div>
		</form>

</body>
</html>