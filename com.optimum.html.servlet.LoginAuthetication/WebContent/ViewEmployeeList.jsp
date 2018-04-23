<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Employee List</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>

</head>
<body>
	<%@ page import="java.util.ArrayList"%>
	<%@ page import="java.util.List"%>

	<script>
		 $.extend( true, $.fn.dataTable.defaults, {
		 "searching": false,
		 "ordering": false,
		 "bLengthChange": false
		 } );

		$(document).ready(function() {
			$('#tableID').DataTable({
				"lengthMenu" : [ [ 5, 10, -1 ], [ 5, 10, "All" ] ]
			});
		});

		function lockMessage(clicked_id) {
			alert("This Employee with the Employee ID:" + clicked_id + " has been locked ");
		}
	</script>

	<form method="post" action="SearchListController">
		<div style="margin-top: 0.5px" >
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<strong>Display All Employees </strong> <br>
						<h5>Click on the Employee ID of the Employee you wish to lock:</h5>
					</div>
						<table class="table" width="100%" id="tableID">
							<thead>
								<tr>
									<th scope="col">Employee ID</th>
									<th scope="col">Name</th>
									<th scope="col">NRIC</th>
									<th scope="col">Department</th>
									<th scope="col">Date Of Birth</th>
									<th scope="col">Mobile</th>
									<th scope="col">Email</th>
								</tr>
							</thead>
							<tbody>
								<%
									ArrayList<String> list = (ArrayList<String>) request.getAttribute("List");
									if (list != null) {
										for (String str : list) {
											String[] content = str.split("/");
											String empID = content[0];
											String name = content[1];
											String nric = content[2];
											String dept = content[3];
											String doB = content[4];
											String mobile = content[5];
											String email = content[6];
								%>
								<tr>
									<td><a id=<%=empID%> name="lock" href="ViewListController?lock=<%=empID%>" onClick="lockMessage(this.id)"><%=empID%></a></td>
									<td><%=name%></td>
									<td><%=nric%></td>
									<td><%=dept%></td>
									<td><%=doB%></td>
									<td><%=mobile%></td>
									<td><%=email%></td>
								</tr>

								<%
									}
									}
								%>
							</tbody>
						</table>
					<div class="col-md-2">
						<label for="Search">Search :</label> 
						<input type="text" class="form-control" style="border-radius: 0px" id="Search" name="search" required="required"> 
							<label for="Type">Search By:</label> 
							<select class="form-control" style="border-radius: 0px" id="Type" name="type" required="required">
							<option value=""></option>
							<option value="full_name">Name</option>
							<option value="employee_id">Employee ID</option>
							<option value="nric">NRIC</option>
							<option value="department">Department</option>
						</select>

						<button type="submit" class="btn btn-sm btn-default pull-right">Search</button>
					</div>
					<br>
					<br>
				</div>
				
			</div>
		</div>
	</form>
</body>
</html>