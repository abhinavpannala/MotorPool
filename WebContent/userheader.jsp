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
	if(session.getAttribute("userid") == null){
		response.sendRedirect("index.jsp");
	}

	int ridesoffered = DBUtils.fetchRides((int)session.getAttribute("userid")).size();
	int ridestaken = DBUtils.fetchRidesTaken((int)session.getAttribute("userid")).size();
	String name = DBUtils.fetchUser((int)session.getAttribute("userid")).getName();
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
				<a class="navbar-brand" href="userhome.jsp"> <img
					src="assets/img/logo.png" alt="" />
				</a>
			</div>
			<!-- end navbar-header -->
			<!-- navbar-top-links -->
			<ul class="nav navbar-top-links navbar-right">
			<%
				int points = DBUtils.fetchPoints("user", (int)session.getAttribute("userid"));
			%>
				<li ><label><h4>Available points in Account : <b><%=points%></b></h4></label>
				</li>
				
				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
				
				<li class="dropdown">
                    <a  href="ridepage"><i class="fa fa-telegram fa-3x"></i>
                    </a>
                </li>
				
				<li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-pencil-square-o fa-3x"></i>
                    </a>
                    <!-- dropdown vehicle-details-->
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="updatevehicle?ridetype=car">
                                <div>
                                    <i class="fa fa-car fa-fw"></i> &nbsp; Add/Update Car Details
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                    </ul>
                    <!-- end dropdown-vehicle-details -->
                </li>
                
				<li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-line-chart fa-3x"></i>
                    </a>
                    <!-- dropdown rides-->
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="viewridesoffered">
                                <div>
                                    <i class="fa fa-eye fa-fw"></i> &nbsp; View Rides Offered
                                    <span class="pull-right text-muted small"><%=ridesoffered%></span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="viewridestaken">
                                <div>
                                    <i class="fa fa-eye fa-fw"></i> &nbsp; View Rides Taken
                                    <span class="pull-right text-muted small"><%=ridestaken %></span>
                                </div>
                            </a>
                        </li>
                    </ul>
                    <!-- end dropdown-rides -->
                </li>
                
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-3x"></i>
				</a> <!-- dropdown user-->
					<ul class="dropdown-menu dropdown-user">
						<li><a href="useraddpoints.jsp"><i
								class="fa fa-database fa-fw"></i> Add Points to Account</a></li>
						<li><a href="userchangepassword.jsp"><i
								class="fa fa-unlock-alt fa-fw"></i> Change Password</a></li>

						<li class="divider"></li>
						<li><a href="logout"><i class="fa fa-sign-out fa-fw"></i>Logout</a>
						</li>
					</ul></li>
			</ul>
			<!-- end dropdown-user -->
			</li>
			<!-- end main dropdown -->
			</ul>
			<!-- end navbar-top-links -->

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
								<i class="fa fa-user-circle-o fa-3x" aria-hidden="true"></i>
							</div>
							<div class="user-info">
								<div>
									<br> <strong><%=name%> </strong>
								</div>
							</div>
						</div> <!--end user image section-->
					</li>

					<li><a href="userhome.jsp"><i class="fa fa-home fa-fw"></i>
							Home</a></li>

					<li></li>
					<li></li>
					<li></li>
					<li></li>

					<li><a href="setridepage?ridetype=car"><i class="fa fa-car fa-fw"></i>
							Offer Car Ride</a></li>

					<li></li>
					<li></li>
					<li></li>
					<li></li>

					<li><a href="usertakecarride.jsp"><i class="fa fa-car fa-fw"></i>
							Take a Car Ride</a></li>

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