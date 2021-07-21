<%@ include file="adminheader.jsp"%>

<div class="row">
	<div class="panel panel-primary text-center no-boder">
		<div class="panel-body blue">
			<i class="fa fa-users fa-3x"></i>
			<h3><%=totalusers%></h3>
		</div>
		<div class="panel-footer">
			<span class="panel-eyecandy-title" style="font-weight: bold;">Total Users Registered</span>
		</div>
	</div>
	<div class="panel panel-primary text-center no-boder">
		<div class="panel-body green">
			<i class="fa fa fa-car fa-3x"></i> <i class="fa fa fa-motorcycle fa-3x"></i>
			<h3><%=totalrides%></h3>
		</div>
		<div class="panel-footer">
			<span class="panel-eyecandy-title" style="font-weight: bold;">Total Rides Created</span>
		</div>
	</div>
</div>