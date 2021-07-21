<%@page import="com.motorpool.utils.DateConverter"%>
<%@page import="com.motorpool.dao.RidesDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="userheader.jsp"%>
<%
	if(request.getAttribute("ridestatus")!=null){
		out.println("<script>alert('"+request.getAttribute("ridestatus")+"');</script>");
	}

%>
<h3>Scheduled Rides</h3> 
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
				<th>Action</th>
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
				<td>
					<% if(ride.getStatus().equalsIgnoreCase("created")){ %>
					<a href="startride?rideid=<%=ride.getRideid()%>" class="btn btn-success">Start Ride</a>
				<%} %>
					<%if(ride.getStatus().equalsIgnoreCase("progress")) { %>
					<a href="endride?rideid=<%=ride.getRideid()%>" class="btn btn-warning">End Ride</a>
					<%} %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%if(ride.getStatus().equalsIgnoreCase("created")) { %>
					<a href="cancelride?rideid=<%=ride.getRideid()%>" class="btn btn-danger"><i class="fa fa-times fa-fw"></i> Cancel Ride</a>
					<%} %>
				</td>
			</tr>
		<%
         }
		%>
		</tbody>
	</table>


</div>