<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/style/semantic.min.css" />">
<script type="text/javascript" src="<c:url value="/style/jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/style/semantic.min.js" />"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/style/common.css" />">
<title>اعتبار</title>
</head>
<body>
	<jsp:include page="/Menu.jsp" />
	<script>$('#menu-credit').addClass('active');</script>
	
	<div class="content" dir="ltr">
		<div class="ui form">
			<div class="inline fields">
			    <div class="eight wide field">
			      <label>Name</label>
			      <input type="text" placeholder="First Name">
			    </div>
			    <div class="three wide field">
			      <input type="text" placeholder="Middle Name">
			    </div>
			    <div class="three wide field ui left labeled input">
				  <div class="ui label">$</div>
				  <input type="number" placeholder="Amount">
				  <div class="ui basic label">.00</div>
				</div>
			</div>
		  <div class="two fields">
		 	 <div class="field">
		      <button class="ui button">
				  ثبت درخواست افزایش یا کاهش اعتبار
				</button>
		    </div>
		    
		  </div>
		</div>
		
	</div>

</body>
</html>