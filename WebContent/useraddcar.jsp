<%@page import="com.motorpool.dao.UsersDAO"%>
<%@ include file="userheader.jsp"%>

<%
	if (request.getAttribute("flag")!=null){
		out.println("<script>alert('Car updated successfully!')</script>");
	}
%>

<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-car fa-fw"></i> Add/Update Car Details
		</div>

		<div class="panel-body">
			<form method="post" action="updatevehicletype">
			<%UsersDAO user = (UsersDAO)request.getAttribute("user"); %>
				<div class="form-group">
					<label>Car Registration Number</label> <input
						type="text" class="form-control" id="carno" name="carno"
						 placeholder="Ex: KA-51-AB-4040" value="<%=user.getCarno()==null?"":user.getCarno() %>" required>
						 
				</div>
				<div class="form-group">
					<label>Car Description</label> <input
						type="text" class="form-control" id="cartype" name="cartype"
						 placeholder="Ex: Blue Hyundai Elite i20" value="<%=user.getCartype()==null?"":user.getCartype() %>" required>
				</div>
				<input type="hidden" name="ridetype" value="car">
				<button type="submit" class="btn btn-primary pull-right"> Update </button>
			</form>
		</div>

	</div>
</div>