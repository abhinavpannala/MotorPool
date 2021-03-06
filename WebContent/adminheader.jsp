<!DOCTYPE html>
<%@page import="com.motorpool.utils.DBUtils"%>
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
<body>

	<%
		int[] adminCounts = DBUtils.fetchAdminHomeCounts();
		int pendingrequests = adminCounts[0];
		int totalusers = adminCounts[1];
		int totalrides = adminCounts[2];
	%>
	<!--  wrapper -->
	<div id="wrapper">
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
				<a class="navbar-brand" href="adminhome.jsp"> <img
					src="assets/img/logo.png" alt="" />
				</a>
			</div>
			<!-- end navbar-header -->
			<!-- navbar-top-links -->
			<ul class="nav navbar-top-links navbar-right">
				<li><label><h4>
				<%
					int points = DBUtils.fetchPoints("admin", 1);
				%>
							Total Points Collected : <b><%=points%></b>
						</h4></label></li>

				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>

				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <%
 	if (pendingrequests > 0) {
 %> <span class="top-label label label-warning"><%=pendingrequests%></span>
						<%
							}
						%> <i class="fa fa-bell fa-3x"></i>
				</a> <!-- dropdown alerts-->
					<ul class="dropdown-menu dropdown-alerts">
						<li style="text-align: center; padding: 5px 0 5px 0;">
							<%
								if (pendingrequests > 0) {
							%> <a href="viewnewusers"> <i
								class="fa fa-user-plus fa-fw"></i> New Registration Requests are
								Pending
						</a> <%
 	} else {
 %> <i class="fa fa-exclamation-triangle fa-fw"></i> No New Registration
							Requests <%
 	}
 %>
						</li>
					</ul> <!-- end dropdown-alerts --></li>


				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-3x"></i>
				</a> <!-- dropdown user-->
					<ul class="dropdown-menu dropdown-user">
						<li><a href="updatepercentagepage"><i
								class="fa fa-gear fa-fw"></i> Update Commission</a></li>
						<li class="divider"></li>
						<li><a href="logout"><i class="fa fa-sign-out fa-fw"></i>
								Logout</a></li>
					</ul></li>
			</ul>

		</nav>
		<!-- end navbar top -->

		<!-- navbar side -->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<!-- sidebar-collapse -->
			<div class="sidebar-collapse">
				<!-- side-menu -->
				<ul class="nav" id="side-menu">
					<li>
						<!-- user image section-->
						<div class="user-section">
							<div class="user-section-inner">
								<i class="fa fa-cogs fa-3x" aria-hidden="true"></i>
							</div>
							<div class="user-info">
								<div>
									<br> <strong>Admin</strong>
								</div>
							</div>
						</div> <!--end user image section-->
					</li>

					<li><a href="adminhome.jsp"><i class="fa fa-home fa-fw"></i>
							Home</a></li>

					<li></li>
					<li></li>
					<li></li>
					<li></li>

					<li><a href="viewnewusers"><i
							class="fa fa-user-plus fa-fw"></i> Registration Requests</a></li>

					<li></li>
					<li></li>
					<li></li>
					<li></li>

					<li><a href="viewallusers"><i
							class="fa fa-users fa-fw"></i> View Existing Users</a></li>
					<li><a href="viewrides"><i
							class="fa fa-car fa-fw"></i> View Created Rides</a></li>

					<li></li>
					<li></li>
					<li></li>
					<li></li>

				</ul>
				<!-- end side-menu -->
			</div>
			<!-- end sidebar-collapse -->
		</nav>
		<!-- end navbar side -->

		<!--  page-wrapper -->
		<div id="page-wrapper">
			<br> <br> <br> <br>


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