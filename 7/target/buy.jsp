<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
	<title>Buy</title>
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
			<jsp:param name="headerVar" value="خرید سهام" />
			<jsp:param name="submitVar" value="خرید"/>
			<jsp:param  name="action" value="buy-controll"/>
		</jsp:include>
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>


