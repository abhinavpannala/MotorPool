<%@page import="com.motorpool.utils.DateConverter"%>
<%@page import="com.motorpool.dao.RidesDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="userheader.jsp"%>

<h3>Rides Taken</h3> 
<br>
<div class="row">
	<table class="table table-hover">
		<thead>
			<tr class="success">
				<th>Date</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Source</th>
				<th>Destination</th>
				<th>Ride Points Spent</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<%
         ArrayList<RidesDAO> al=(ArrayList<RidesDAO>)request.getAttribute("ridelist");
         for(RidesDAO ride:al){
        	 String startdatetime = DateConverter.formatDateTimeToString(ride.getStarttime());
        	 String enddatetime = DateConverter.formatDateTimeToString(ride.getEndtime());
     %>
			<tr>
				<td><%=startdatetime.substring(0, 11)%></td>
				<td><%=startdatetime.substring(11)%></td>
				<td><%=enddatetime.substring(11)%></td>
				<td><%=ride.getSource() %></td>
				<td><%=ride.getDestination() %></td>
				<td><%=ride.getRidepoints() %></td>
				<td><%=ride.getStatus() %></td>
			</tr>
			<%
         	}
			%>
		</tbody>
	</table>


</div>