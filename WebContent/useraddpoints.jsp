<%@ include file="userheader.jsp"%>

<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-database fa-fw"></i> Add More Points
		</div>
		<%
			if(request.getAttribute("flag")!=null){
				out.println("<script>alert('Points added successfully!');</script>");
			}
		%>

		<div class="panel-body">
			<form action="addpoints">
				<div class="form-group">
					<label>Number of Points to Add</label> <input
						type="text" class="form-control" id="points" name="points"
						 placeholder="Ex: 40, 50, etc." required>
				</div>

				<button type="submit" class="btn btn-primary pull-right"> Add and Make Payment </button>
			</form>
		</div>

	</div>
</div>