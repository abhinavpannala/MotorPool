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
	if(request.getAttribute("message")!=null){
		System.out.println("yesssss");
		out.print("<script>alert('"+request.getAttribute("message")+"');</script>");
	}

%>

	<div class="container">

		<div class="row">
			<div class="col-md-4 col-md-offset-4 text-center logo-margin">
				<img src="assets/img/logo.png" alt="LOGO" />
			</div>
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">

					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#home">User
								Sign In</a></li>
						<li><a data-toggle="tab" href="#menu1">Admin Sign In</a></li>
					</ul>

					<div class="panel-body">
						<div class="tab-content">
							<div id="home" class="tab-pane fade in active">
								<form role="form" action="LoginServlet?role=user" method="post">
									<fieldset>
										<div class="form-group">
											<input class="form-control" placeholder="User E-Mail ID"
												name="email" type="email" autofocus required>
										</div>
										<div class="form-group">
											<input class="form-control" placeholder="Password"
												name="password" type="password" required>
										</div>
										<input type="submit" class="btn btn-lg btn-success btn-block"
											value="Login">
									</fieldset>
								</form>
								<br> <a class="pull-right" href="userregister.jsp">New
									User? Register Here</a>
							</div>
							<div id="menu1" class="tab-pane fade">
								<form role="form" action="LoginServlet?role=admin" method="post">
									<fieldset>
										<div class="form-group">
											<input class="form-control" placeholder="User ID" name="email"
												type="text" autofocus required>
										</div>
										<div class="form-group">
											<input class="form-control" placeholder="Password"
												name="password" type="password" required>
										</div>
										<input type="submit" class="btn btn-lg btn-success btn-block"
											value="Login">
									</fieldset>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Core Scripts - Include with every page -->
	<script src="assets/plugins/jquery-1.10.2.js"></script>
	<script src="assets/plugins/bootstrap/bootstrap.min.js"></script>
	<script src="assets/plugins/metisMenu/jquery.metisMenu.js"></script>

</body>

</html>
