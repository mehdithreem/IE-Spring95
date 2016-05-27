<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ir.stocks.domain.*"%>
<%@page import="ir.stocks.data.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/style/semantic.min.css" />">
<script type="text/javascript" src="<c:url value="/style/jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/style/semantic.min.js" />"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/style/common.css" />">
<title>نمادها</title>
</head>
<body>
	<jsp:include page="/Menu.jsp" />
	<script>$('#menu-symbol-manager').addClass('active');</script>
	
	<div class="content" dir="rtl">
		<%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("duplicate-name")) {%>
			<div class="ui red message">
			<div class="header">
			نماد درخواستی قبلا ثبت شده است.
			</div>
			</div>
	     <% } %>
	     
	     <%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("problem")) {%>
			<div class="ui red message">
			<div class="header">
			خطا در عرضه‌ی سهام.
			</div>
			</div>
	     <% } %>
	     
	     <%if(request.getAttribute("message") != null && ((String) request.getAttribute("message")).equals("needs-approve")) {%>
			<div class="ui red message">
			<div class="header">
			نیاز به تایید برای عرضه‌ی سهام می‌باشد.
			</div>
			</div>
	     <% } %>
	     
	     <%
		UserRepo repo = UserRepo.getRepository();
		Map<Role, Boolean> role = null;
		String user = request.getRemoteUser();

		if (user != null) {
			role = repo.getUserRolesMap(user);
		}
		
		SymbolRequestRepo symRepo = SymbolRequestRepo.getRepository();
		List<SymbolRequest> symReqs = null;
		
		if (role.get(Role.ADMIN)) {
			symReqs = symRepo.getAll();
		} else if (role.get(Role.COMPANY)) {
			symReqs = symRepo.getAll(user);
		}
		%>
	
		<div class="ui inverted segment">
		  <form class="ui inverted form" action="<c:url value="/company/symbol/create" />" method="post">
			    <div class="four fields">
			      <div class="field">
			        <input placeholder="نام نماد" name="symbolid" type="text">
			      </div>
			      <div class="field">
			        <input placeholder="تعداد سهم‌ها" name="quantity" type="number">
			      </div>
			      <div class="field">
			        <input placeholder="ارزش هر سهم" name="price" type="number">
			      </div>
			    <input class="ui submit button" type="submit" value="ثبت درخواست عرضه‌ی نماد"></input>
			  </div>
			</form>
		</div>
		
		<table class="ui inverted teal table" dir="rtl">
		  <thead>
		    <tr>
		    <th>نام نماد</th>
		    <th>درخواست دهنده</th>
		    <th>تعداد سهم‌ها</th>
		    <th>ارزش هر سهم</th>
		    <th>وضعیت درخواست</th>
		  <% if (role.get(Role.ADMIN)) { %>
		  	<th colspan="2">عملیات درخواست </th>
		  <%} %>
		  </tr></thead><tbody>
		  <% for(SymbolRequest r : symReqs) { %>
		    <tr>
		      <td><%=r.getSymbol() %></td>
		      <td><%=r.getUser() %></td>
		      <td><%=r.getQuantity() %></td>
		      <td><%=r.getPrice() %></td>
		      <td><%=r.getStatus() %></td>
		    <% if (role.get(Role.ADMIN) && r.getStatus().equals(Status.PENDING)) { %>
			  	<td>
			  		<form method="post" action="<c:url value="/company/symbol/accept" />">
			  			<input type="hidden" name="symbolid" value="<%= r.getSymbol()%>"/>
			  			<input class="ui blue button" type="submit" value="تایید"></input>
					</form>
				</td>
				<td>
					<form method="post" action="<c:url value="/company/symbol/reject" />">
			  		<input type="hidden" name="symbolid" value="<%= r.getSymbol()%>"/>
			  			<input class="red ui button" type="submit" value="رد"></input>
					</form>
			  	</td>
			<%} %>
		    </tr>
		  <% } %>
		  </tbody>
		</table>
	</div>

</body>
</html>