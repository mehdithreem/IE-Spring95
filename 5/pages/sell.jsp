<!DOCTYPE html>

<html>
<head>
	<title>Sell</title>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
	<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
	
<body>
	<div class="container">

		<jsp:include page="nav.jsp" />
		<jsp:include page="sell_buy_content.jsp">
			<jsp:param name="headerVar" value="${فروش سهام}"/>
			<jsp:param  name="submitVar" value="${فروش}"/>
			<jsp:param  name="action" value="${sell-controll}"/>
		</jsp:include>
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>


