<%@page import="com.motorpool.dao.UsersDAO"%>
<%@ include file="userheader.jsp"%>

<%
	if ((request.getAttribute("flag")!=null)){
		out.println("<script>alert('Bike updated successfully!')</script>");
	}
%>
<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-motorcycle fa-fw"></i> Add/Update Bike Details
		</div>

		<div class="panel-body">
			<form method="post" action="updatevehicletype">
			<%UsersDAO user = (UsersDAO)request.getAttribute("user"); %>
				<div class="form-group">
					<label>Bike Registration Number</label> <input
						type="text" class="form-control" id="bikeno" name="bikeno"
						 placeholder="Ex: KA-51-AB-4040" value="<%=user.getBikeno()==null?"":user.getBikeno() %>" required>
				</div>
				<div class="form-group">
					<label>Bike Description</label> <input
						type="text" class="form-control" id="biketype" name="biketype"
						 placeholder="Ex: Blue Bajaj Avenger" value="<%=user.getBiketype()==null?"":user.getBiketype() %>" required>
				</div>
				<input type="hidden" name="ridetype" value="bike">
				<button type="submit" class="btn btn-primary pull-right"> Update </button>
			</form>
		</div>

	</div>
</div>