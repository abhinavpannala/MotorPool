<%@page import="com.motorpool.utils.DateConverter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.motorpool.dao.UsersDAO"%>
<%@ include file="adminheader.jsp"%>

<h3>Existing Users</h3> 
<br>
<%
	if(request.getAttribute("flag") !=null){
		out.println("<script>alert('"+request.getAttribute("flag")+"');</script>");
	}
%>
<div class="row">
	<table class="table table-hover">
		<thead>
			<tr class="success">
				<th>Name</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
		<%
         ArrayList<UsersDAO> al=(ArrayList<UsersDAO>)request.getAttribute("users");
         for(UsersDAO user:al){
     %>
			<tr>
				<td><%=user.getName()%></td>
				<td><%=user.getEmail() %></td>
				<td><%=user.getPhone() %></td>
				<td>
				<% if(user.isActive().equalsIgnoreCase("active")){			
				%>
				<a class="btn btn-danger btn-xs" href="rejectuser?userid=<%=user.getUserid()%>"><i
							class="fa fa-times fa-fw"></i> DEACTIVATE</a>
							
				<%
				}
				if(user.isActive().equalsIgnoreCase("deactive")){	
				%>
						<a class="btn btn-success btn-xs" href="approveuser?userid=<%=user.getUserid()%>"><i
							class="fa fa-check fa-fw"></i> ACTIVATE</a>	
				<%
				}
				%>
							</td>
			</tr>
			<%
         }
			%>
		</tbody>
	</table>


</div>