<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<title>Employee Login</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<div class="header">
	<img src="123.png" alt="Optimum Solution Logo" class="img-fluid">
</div>
<body>
<%
	//allow access only if session exist
	String user = null;
	String username=null;
	if (session.getAttribute("refEmployeeJSP") == null) {
		response.sendRedirect("LoginPage.jsp");
	} else {
		user = (String) session.getAttribute("refEmployeeJSP");
		username=(String) session.getAttribute("refEmployeeJSPName");
	}
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refEmployeeJSP")) {
				userName = cookie.getValue();
			}
			if (cookie.getName().equals("JSESSIONID")) {
				sessionID = cookie.getValue();
			}
		}
	} else {
		sessionID = session.getId();
	}
%>
<%@ page import="java.util.Date" %>
<% Date lastAccessed=new Date(session.getLastAccessedTime()); %>

	<div class="container" style="margin-top: 30px">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<strong>Welcome <%=username %> </strong>
						<br>
						<img class="img-rounded" src="DisplayImageController?email=<%=user%>" alt="Image unavailable" onerror="this.src='blank.png'" width=200px height=150px/>
					</div>
					<br>
					<div>
						Last Login Date and Time: <br>
						<%= lastAccessed %>
					</div>
					<div class="panel-body">
						<form action="<%=response.encodeURL("LogoutServlet") %>" method="post">
							<button type="submit" class="btn btn-sm btn-default pull-right">Log out</button>
							<br> 
							<br> 
							<a href="UpdateProfile.jsp" target="iframe_cne"><u>Update Profile</u></a>
							<br>
							<a href="ViewProfileController" target="iframe_cne"><u>View Profile</u></a>
							<br>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<iframe height="650px" width="60%" src="ViewProfileController" name="iframe_cne" style="border:none;" style="margin-top: 0.5px"></iframe>
		
		</div> 

		<div class="footer">
			<p>&copy; Optimum Solutions Ptd Ltd</p>
		</div>
</body>
</html>