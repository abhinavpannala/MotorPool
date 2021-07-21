<%@ include file="userheader.jsp"%>

<div class="row">
	<div class="panel panel-success">
		<div class="panel-heading">
			<i class="fa fa-unlock-alt fa-fw"></i> Change Password
		</div>
		<%
	if(request.getAttribute("flag") !=null){
		out.println("<script>alert('"+request.getAttribute("flag")+"');</script>");
	}
%>

		<div class="panel-body">
			<form method="post" action="changepassword">
				<div class="form-group">
					<label>Current Password</label> <input
						type="password" class="form-control" id="currentpassword" name="currentpassword"
						 placeholder="Current Password" required>
				</div>
				
				<div class="form-group">
					<label>New Password</label> <input
						type="password" class="form-control" id="newpassword" name="newpassword"
						 placeholder="New Password" required>
				</div>

				<button type="submit" class="btn btn-primary pull-right"> Change Password </button>
			</form>
		</div>

	</div>
</div>