<%@page import="java.util.*"%>
<!-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/index.css">
	<link rel="stylesheet" type="text/css" href="css/home.css">

	<title>Home</title>
</head>
	
<body>
<div class="container">

<jsp:include page="nav.jsp" />

<div class="main">

	<jsp:include page="header.jsp">
		<jsp:param name="headerVar" value="اطلاعات نماد‌ها" />
	</jsp:include>

	<div class="content">
		<c:forEach var="symb" items="${symbols}">
			<div class="symb-box">
				<div class="symb-name">${symb.ID}</div>
				<div class="symb-qbox">
					<div class="symb-q">
						<p>صف خرید</p>
						<c:forEach var="qitem" items="${symb.buys}" end="5">
							<ul>
								<li>${qitem.owner.ID}</li>
								<li>${qitem.quantity}</li>
								<li>$${qitem.price}</li>
							</ul>
						</c:forEach>
					</div>
				</div>
				<div class="symb-qbox">
					<div class="symb-q">
						<p>صف فروش</p>
						<c:forEach var="qitem" items="${symb.sells}" end="5">
							<ul>
								<li>${qitem.owner.ID}</li>
								<li>${qitem.quantity}</li>
								<li>$${qitem.price}</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>