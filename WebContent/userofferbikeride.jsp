<%@ include file="userheader.jsp"%>
<%
if(request.getAttribute("ridestatus")!=null){
	out.print("<script>alert('"+request.getAttribute("ridestatus")+"');</script>");
}
	if(!(boolean)request.getAttribute("allowed")){
%>
<div class="row">
	<div class="well" style="text-align: center;">
		<h4>Please add bike details first to offer a ride!</h4>
	</div>
</div>
<%
	}
	else
	{
%>
<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-motorcycle fa-fw"></i> Offer New Bike Ride
		</div>

		<div class="panel-body">
			<form action="">
				<div class="form-group form-inline">
					<label>Date : </label> <input type="date" class="form-control"
						id="date" name="startdate" placeholder="MM/DD/YYYY" required>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label>Start Time : </label> <select
						class="form-control" name="starthour" required>
						<option value="" hidden>--</option>
						<%
							for (int count = 0; count < 24; count++) {
						%>
						<option value="<%=count%>"><%=String.format("%02d", count)%></option>
						<%
							}
						%>
					</select><b> :</b> <select class="form-control" name="startminutes"
						required>
						<option value="" hidden>--</option>
						<%
							for (int count = 0; count < 51; count += 10) {
						%>
						<option value="<%=count%>"><%=String.format("%02d", count)%></option>
						<%
							}
						%>
					</select> <b>Hrs</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label>End
						Time : </label> <select class="form-control" name="endhour" required>
						<option value="" hidden>--</option>
						<%
							for (int count = 0; count < 24; count++) {
						%>
						<option value="<%=count%>"><%=String.format("%02d", count)%></option>
						<%
							}
						%>
					</select><b> :</b> <select class="form-control" name="endminutes"
						required>
						<option value="" hidden>--</option>
						<%
							for (int count = 0; count < 51; count += 10) {
						%>
						<option value="<%=count%>"><%=String.format("%02d", count)%></option>
						<%
							}
						%>
					</select> <b>Hrs</b>
				</div>

				<br>

				<div class="form-group">
					<label>Starting Point : </label> <input type="text"
						class="form-control" id="source" name="source"
						placeholder="Ex: Madiwala" required style="width: 50%;">
				</div>

				<div class="form-group">
					<label>Ending Point : </label> <input type="text"
						class="form-control" id="destination" name="destination"
						placeholder="Ex: Madiwala" required style="width: 50%;">
				</div>

				<div class="form-group">
					<label>Intermediate Stops : </label> <input type="text"
						class="form-control" id="stops" name="stops"
						placeholder="Ex: Loaction1,Location2,Location3" required>
				</div>


				<div class="form-group">
					<label>Ride Points : </label> <input type="text"
						class="form-control" id="destination" name="ridepoints"
						placeholder="Ex: 20" required style="width: 30%;">
				</div>

				<input type="hidden" name="ridetype" value="bike">
				<button type="submit" class="btn btn-primary pull-right">
					 Add New Ride </button>
			</form>
		</div>

	</div>
</div>
<%
}
%>