<%@page import="java.util.List"%>
<%@page import="com.motorpool.dao.UsersDAO"%>
<%@ include file="adminheader.jsp"%>

<%

	if(request.getAttribute("flag")!=null){
		out.println("<script>alert('User approved! Email has been sent to User.');</script>");
	}
	List<UsersDAO> users = request.getAttribute("users") != null
			? (List<UsersDAO>) request.getAttribute("users")
			: null;
%>

<h3>Pending Requests</h3>
<br>
<div class="row">

	<%
		if (users == null) {
	%>

	<div class="well" style="text-align: center; color: gray;">
		<h3>No New Requests.</h3>
	</div>
	<%
		} else {
	%>

	<table class="table table-hover">
		<thead>
			<tr class="success">
				<th>Name</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Approve</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (UsersDAO user : users) {
			%>
			<tr>
				<td><%=user.getName()%></td>
				<td><%=user.getEmail()%></td>
				<td><%=user.getPhone()%></td>
				<td><a class="btn btn-success btn-xs" href="approveuser?userid=<%=user.getUserid()%>"><i
						class="fa fa-check fa-fw"></i> APPROVE</a></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>

	<%
		}
	%>

</div>