<%@page import="com.motorpool.dao.UsersDAO"%>
<%@page import="com.motorpool.utils.DateConverter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.motorpool.dao.RidesDAO"%>
<%@page import="java.util.List"%>
<%@ include file="userheader.jsp"%>

<!-- Search Section -->
<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-search fa-fw"></i> Search Rides
		</div>

		<div class="panel-body">
			<form action="searchride" method="post">
				<div class="form-group form-inline">
					<label>Starting Point : </label> <input type="text"
						class="form-control" id="source" name="source"
						placeholder="Ex: Madiwala" required>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label>Dropping Point : </label> <input type="text"
						class="form-control" id="destination" name="destination"
						placeholder="Ex: Madiwala" required>
				</div>
				<input type="hidden" name="ridetype" value="car">
				<button type="submit" class="btn btn-primary pull-right">
					<i class="fa fa-search fa-fw"></i> Search
				</button>
			</form>
		</div>

	</div>
</div>


<!-- Search Results -->
<%
	if(request.getAttribute("status")!=null){
		out.print("<script>alert('"+request.getAttribute("status")+"');</script>");
	}

	if(request.getAttribute("ridelist")!=null){
		
		List<RidesDAO> rides = (ArrayList<RidesDAO>)request.getAttribute("ridelist");
		for(RidesDAO ride:rides){
			String startdatetime = DateConverter.formatDateTimeToString(ride.getStarttime());
       	 	String enddatetime = DateConverter.formatDateTimeToString(ride.getEndtime());
       	 	UsersDAO user= DBUtils.fetchUser(ride.getUserid());
%>
<div class="row">
	<div class="panel panel-info">
		<div class="panel-heading">
			<i class="fa fa-car fa-fw"></i> Available Rides
		</div>

		<div class="panel-body">

			<table class="table table-hover">
				<thead>
					<tr>
						<th>Date</th>
						<th>Start Time</th>
						<th>End Time</th>
						<th>Offered By</th>
						<th>Contact</th>
						<th>Car No.</th>
						<th>Car Description</th>
						<th>Ride Points</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr><td><%=startdatetime.substring(0, 11)%></td>
						<td><%=startdatetime.substring(11)%></td>
						<td><%=enddatetime.substring(11)%></td>
						<td><%=user.getName() %></td>
						<td><%=user.getPhone() %></td>
						<td><%=user.getCarno() %></td>
						<td><%=user.getCartype() %></td>
						<td><%=ride.getRidepoints() %></td>
						<td>
							<a href="joinride?ridetype=car&rideid=<%=ride.getRideid()%>" class="btn btn-warning"><i class="fa fa-hand-o-right" aria-hidden="true"></i> Join Ride</a>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%
}
%>
