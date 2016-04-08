<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
	<title>Message</title>
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
			<jsp:include page="header.jsp">
				<jsp:param name="headerVar" value="پیغام" />
			</jsp:include>
			<div class="content">
				<h3>${message}</h3>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>