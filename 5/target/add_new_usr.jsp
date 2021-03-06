<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>

<html>
<head>
	<title>New User</title>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
	<link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
	<div class="container">

		<jsp:include page="nav.jsp" />
		<div class=main>
			<% 
			response.setCharacterEncoding("UTF-8");
			StringWriter sw = new StringWriter();
			new PrintWriter(sw, true).print("پیغام");
			%>
			<jsp:include page="header.jsp">
				<jsp:param name="headerVar" value="<%=sw.toString()%>" />
			</jsp:include>
			<div class="content">
				<form class="form-horizontal my-form" action="add-user" method="POST">
					<div class="form-group">
						<label for="id">شناسه</label>
						<input type="text" class="form-control" name="id" id="id" placeholder="شناسه">
					</div>
					<div class="form-group">
						<label for="id">نام</label>
						<input type="text" class="form-control" name="name" id="name" placeholder="نام">
					</div>
					<div class="form-group">
						<label for="id">نام‌خانوادگی</label>
						<input type="text" class="form-control" name="family" id="family" placeholder="نام‌خانوادگی">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-default submit-btn">ثبت</button>
					</div>
				</form>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
