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
<title>خطا در دسترسی</title>
<style type="text/css">
    body {
      background-color: #DADADA;
    }
    body > .grid {
      height: 100%;
    }
    .image {
      margin-top: -100px;
    }
    .column {
      max-width: 450px;
    }
  </style>
</head>
<body>
	<div class="ui middle aligned center aligned grid" dir="rtl">
	  <div class="column">
	    <h2 class="ui teal header">
	        Error 403
	    </h2>
		    <div class="ui message">
		        <h1>خطای دسترسی به محتوا</h1>
		        <br>
		        <a href="<c:url value="/" />">بازگشت به صفحه‌ی اصلی</a>
		    </div>
	  </div>
</div>

</body>
</html>