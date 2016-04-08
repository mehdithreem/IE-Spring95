<<!DOCTYPE html>
<html>
<head>
	<title>Success</title>
</head>
<body>

	<div class="container">
		
		<jsp:include page="header.jsp">
			<jsp:param name="headerVar" value="message" />
		</jsp:include>

		<jsp:include page="nav.jsp" />
		
		<h3><font color="red"><c:out value="${message}" /></font></h3>
	
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>