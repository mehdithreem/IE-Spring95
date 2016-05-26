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
<title>ورود</title>

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
  <script>
  $(document)
    .ready(function() {
      $('.ui.form')
        .form({
          fields: {
            username: {
              identifier  : 'username',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'نام کاربری خود را وارد کنید'
                }
              ]
            },
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'رمز عبور خود را وارد کنید'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script>

</head>
<body>
	<div class="ui middle aligned center aligned grid" dir="rtl">
	  <div class="column">
	    <h2 class="ui teal header">
	        ورود به حساب کاربری
	    </h2>
	    <form class="ui large form" action="j_security_check" method="post">
	      <div class="ui stacked segment">
	        <div class="field">
	          <div class="ui left icon input">
	            <i class="user icon"></i>
	            <input type="text" name="j_username" placeholder="نام کاربری">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui left icon input">
	            <i class="lock icon"></i>
	            <input type="password" name="j_password" placeholder="کلمه‌ی عبور">
	          </div>
	        </div>
	        <div class="ui fluid large teal submit button">ورود</div>
	      </div>
	
		<%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("true")) {%>
			<div class="ui red message">
			<div class="header">
			نام کاربری یا رمز عبور اشتباه است.
			</div>
			</div>
	     <% } %>
	
	    </form>
	
	     <%if(request.getAttribute("new_account") != null && ((String) request.getAttribute("new_account")).equals("true")) {%>
			<div class="ui green message">
			<div class="header">
			حساب کاربری ایجاد شد.
			</div>
			</div>
	     <% } else { %>
		    <div class="ui message">
		        اکانت ندارید؟  <a href="<c:url value="/signUp-form" />">ساخت حساب جدید</a>
		    </div>
		 <%} %>
	  </div>
</div>

</body>
</html>

