<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Share Ride</title>
<!-- Core CSS - Include with every page -->
<link href="assets/plugins/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="assets/css/style.css" rel="stylesheet" />
<link href="assets/css/main-style.css" rel="stylesheet" />
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />

</head>
<body class="body-Login-back">

<%
	if(request.getAttribute("update")!=null){
		System.out.println("yesssss");
		out.print("<script>alert('"+request.getAttribute("update")+"');</script>");
	}

%>

	<!-- navbar top -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation"
		id="navbar">
		<!-- navbar-header -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".sidebar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp"> <img
				src="assets/img/logo.png" alt="" />
			</a>
		</div>
		<!-- end navbar-header -->
		<!-- navbar-top-links -->
		<ul class="nav navbar-top-links navbar-right">
			<!-- main dropdown -->

			<li class="dropdown"><a href="index.jsp"> <i
					class="fa fa-home fa-3x"></i>
			</a></li>
			<!-- end main dropdown -->
		</ul>
		<!-- end navbar-top-links -->
	</nav>
	<!-- end navbar top -->


	<br>
	<br>
	<br>
	<br>
	<br>

	<div class="row" style="margin-right: 50px;">
		<div class="panel panel-success pull-right" style="width: 40%">
			<div class="panel-heading">
				<h3>
					<i class="fa fa-user"></i> New User Registration
				</h3>
			</div>
			<div class="panel-body">
				<form action="registeruser" method="post">
					<div class="form-group">
						<label>Name</label> <input type="text" class="form-control"
							id="name" name="name" placeholder="Enter your Name" required>
					</div>
					<div class="form-group">
						<label>Email ID</label> <input type="text" class="form-control"
							id="email" name="email" placeholder="Enter your Email ID"
							required>
					</div>
					<div class="form-group">
						<label>Password</label> <input type="password"
							class="form-control" id="password" name="password"
							placeholder="Enter Password" required>
					</div>
					<div class="form-group">
						<label>Phone</label> <input type="text" class="form-control"
							id="phone" name="phone" placeholder="Enter your Phone Number"
							pattern="[0-9]{10,10}" maxlength="10" required>
					</div>

					<button type="submit" class="btn btn-primary pull-right">
						Register</button>
				</form>
				<br>
				<br>
				<br> <span class="pull-right" style="color: red;">*You
					will be able to login only after verification is completed.</span>
			</div>
		</div>

	</div>
	<!-- end page-wrapper -->

	<!-- end wrapper -->

	<!-- Core Scripts - Include with every page -->
	<script src="assets/plugins/jquery-1.10.2.js"></script>
	<script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
	<script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="assets/plugins/pace/pace.js"></script>
	<script src="assets/scripts/siminta.js"></script>
	<!-- Page-Level Plugin Scripts-->
	<script src="assets/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="assets/plugins/morris/morris.js"></script>
	<script src="assets/scripts/dashboard-demo.js"></script>
</body>

</html>
