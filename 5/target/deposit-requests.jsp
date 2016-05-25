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
	<link rel="stylesheet" type="text/css" href="/stocks/css/index.css">
	<link rel="stylesheet" type="text/css" href="/stocks/css/home.css">

	<title>Requests</title>
</head>
	
<body>
<div class="container">

<jsp:include page="nav.jsp" />

<div class="main">

	<jsp:include page="header.jsp">
		<jsp:param name="headerVar" value="درخواست‌های موجود" />
	</jsp:include>

	<div class="content">
	<div class="symb-box">
	<ul class="hlist">
		<li>شناسه‌ی کاربر</li>
		<li>مقدار درخواستی</li>
		<li>تایید</li>
		<li>حذف</li>
	</ul>
	</div>
		<c:forEach var="req" items="${requests}">
			<div class="symb-box">
			<ul class="hlist">
				<li><c:out value="${req.id}"/></li>
				<li>$<c:out value="${req.amount}"/></li>
				<li><a href="/stocks/admin/requests/accept?id=${req.reqID}">تایید</a></li>
				<li><a href="/stocks/admin/requests/decline?id=${req.reqID}">حذف</a></li>
			</ul>
			</div>
		</c:forEach>
	</div>
</div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>