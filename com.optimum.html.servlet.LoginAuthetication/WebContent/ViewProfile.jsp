<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.table-borderless td,
.table-borderless th {
	height: 45px;
    border: 0;
}
</style>
<title>Insert title here</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<form method="get" action="ViewProfileController" >
			<div class="container" style="margin-top: 0.5px">
				<div class="col-md-6">				
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-title">
								<strong>${refEmployee.fullName} Profile Details </strong> <br> <br>
								<br>									
							</div>
						</div>						
						<div class="panel-body">
							<form role="form">
							
							<table class="table-borderless" width="100%" id="tableID">
							<tbody>
							<tr>	
									<th>Full Name:</th>
									<td>${refEmployee.fullName}</td>
							</tr>

							<tr>		
									<th>NRIC:</th>
									<td>${refEmployee.nric}</td>
							</tr>
							<tr>		
									<th>Gender:</th>
									<td>${refEmployee.gender}</td>
							</tr>
							<tr>
									<th>Date Of Birth</th>
									<td>${refEmployee.dateOfBirth}</td>
							</tr>
							<tr>		
									<th>Address:</th>
									<td>${refEmployee.address}</td>
							</tr>
							<tr>		
									<th>Country:</th>
									<td>${refEmployee.country}</td>
							</tr>
							<tr>		
									<th>Qualification:</th>
									<td>${refEmployee.qualification}</td>
							</tr>
							<tr>		
									<th>Department:</th>
									<td>${refEmployee.department}</td>
							</tr>		
							<tr>
									<th>Email Address:</th>
									<td>${refEmployee.email}</td>
							</tr>
							<tr>		
									<th>Mobile:</th>
									<td>${refEmployee.mobile}</td>
							</tr>											
							<tr>
									<th>Employee ID:</th>									
									<td>${refEmployee.empID}</td>
							</tr>
							</tbody>
							</table>
								
							</form>

						</div>
					</div>
					
				</div>
			</div>
		</form>

</body>
</html>