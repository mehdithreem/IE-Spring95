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
<title>ساخت حساب کاربری</title>

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
                  prompt : 'نام کاربری انتخاب کنید.'
                }
              ]
            },
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'پسورد انتخاب کنید'
                }
              ]
            },
            password2: {
                identifier  : 'password2',
                rules: [
                  {
                    type   : 'empty',
                    prompt : 'پسورد را تکرار کنید'
                  },
                  {
                      type   : 'match[password]',
                      prompt : 'پسورد تطابق ندارد'
                    },
                ]
              },
              name: {
                  identifier  : 'name',
                  rules: [
                    {
                      type   : 'empty',
                      prompt : 'نام خود را وارد کنید'
                    }
                  ]
                },
             lastName: {
                    identifier  : 'lastName',
                    rules: [
                      {
                        type   : 'empty',
                        prompt : 'نام خانوادگی خود را وارد کنید'
                      }
                    ]
                  },
             email: {
                 identifier  : 'email',
                 rules: [
                   {
                     type   : 'empty',
                     prompt : 'میل خود را وارد کنید'
                   },
                   {
                     type   : 'email',
                     prompt : 'میل معتبر نیست'
                   }
                 ]
               },
            
          }
        })
      ;
    })
  ;
  </script>

</head>
<body>
	<% session.invalidate(); %>
	<div class="ui middle aligned center aligned grid" dir="rtl">
	  <div class="column">
	    <h2 class="ui teal header">
	        ساخت حساب کاربری
	    </h2>
	    <form class="ui large form" action="<c:url value="/signUp" />" method="post">
	      <div class="ui stacked segment">
	        <div class="field">
	          <div class="ui left icon input">
	            <i class="user icon"></i>
	            <input type="text" name="username" placeholder="نام کاربری">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui left icon input">
	            <i class="lock icon"></i>
	            <input type="password" name="password" placeholder="کلمه‌ی عبور">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui left icon input">
	            <i class="lock icon"></i>
	            <input type="password" name="password2" placeholder="تکرار کلمه‌ی عبور">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui input">
	            <input type="text" name="name" placeholder="نام">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui input">
	            <input type="text" name="lastName" placeholder="نام خانوادگی">
	          </div>
	        </div>
	        <div class="field">
	          <div class="ui input">
	            <input type="text" name="email" placeholder="آدرس پست الکترونیک">
	          </div>
	        </div>
	        <div class="ui fluid large teal submit button">ساخت اکانت</div>
	      </div>
	    </form>
	    <%if(request.getAttribute("duplicated") != null && ((String) request.getAttribute("duplicated")).equals("true")) {%>
			<div class="ui red message">
			<div class="header">
			نام‌کاربری تکراری است.
			</div>
			</div>
	     <% } %>
	  </div>
</div>