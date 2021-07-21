<%@ include file="adminheader.jsp"%>

<%
	if (request.getAttribute("update") != null) {
		if ((boolean) request.getAttribute("update") == true) {
			out.print("<script>alert('Commission percentage updated successfully!');</script>");
		} else {
			out.print("<script>alert('Error while updating Commission percentage!');</script>");
		}
	}
%>
<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-database fa-fw"></i> Commission Percentage
		</div>

		<div class="panel-body">
			<form action="updatepercentage" method="post">
				<div class="form-group form-inline">
					<input type="text" class="form-control" id="percentage"
						name="percentage" value="<%=request.getAttribute("percentage")%>"
						required> <b>%</b>
				</div>

				<button type="submit" class="btn btn-primary pull-right">
					Update Commission</button>
			</form>
		</div>

	</div>
</div>