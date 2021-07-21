<%@page import="com.motorpool.utils.DateConverter"%>
<%@page import="com.motorpool.dao.RidesDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="adminheader.jsp"%>

<h3>Created Rides</h3> 
<br>
<div class="row">
	<table class="table table-hover">
		<thead>
			<tr class="success">
				<th>User Name</th>
				<th>Date</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Source</th>
				<th>Destination</th>
				<th>Ride Type</th>
				<th>Ride Points</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
		<%
         ArrayList<RidesDAO> al=(ArrayList<RidesDAO>)request.getAttribute("ridelist");
         for(RidesDAO ride:al){
        	 String startdatetime = DateConverter.formatDateTimeToString(ride.getStarttime());
        	 String enddatetime = DateConverter.formatDateTimeToString(ride.getEndtime());
        	 String name = DBUtils.fetchUser(ride.getUserid()).getName();
     %>
			<tr>
				<td><%=name%></td>
				<td><%=startdatetime.substring(0, 11)%></td>
				<td><%=startdatetime.substring(11)%></td>
				<td><%=enddatetime.substring(11)%></td>
				<td><%=ride.getSource() %></td>
				<td><%=ride.getDestination() %></td>
				<td><%=ride.getRidetype() %></td>
				<td><%=ride.getRidepoints() %></td>
				<td><%=ride.getStatus() %></td>
			</tr>
			<%
         }
			%>
		</tbody>
	</table>


</div>