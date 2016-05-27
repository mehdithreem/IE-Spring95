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
<title>اعتبار</title>
</head>
<body>
	<jsp:include page="/Menu.jsp" />
	<script>$('#menu-credit').addClass('active');</script>
	
	<div class="content" dir="rtl">
		<%if(request.getAttribute("message") != null && ((String) request.getAttribute("message")).equals("success")) {%>
			<div class="ui green message">
			<div class="header">
			درخواست شما ثبت شد.
			</div>
			</div>
	     <% } %>
	
		<div class="ui inverted segment">
		  <form class="ui inverted form" action="<c:url value="/user/deposit" />" method="post">
			    <div class="two fields">
			      <div class="field">
			        <input placeholder="مقدار" name="amount" type="number">
			      </div>
			    <input class="ui submit button" type="submit" value="ثبت درخواست کاهش یا افزایش اعتبار"></input>
			  </div>
			</form>
			</div>
		
		<%
		UserRepo repo = UserRepo.getRepository();
		Map<Role, Boolean> role = null;
		String user = request.getRemoteUser();

		if (user != null) {
			role = repo.getUserRolesMap(user);
		}
		
		DepositRequestRepo depRepo = DepositRequestRepo.getRepository();
		List<DepositRequest> depReqs = null;
		
		if (role.get(Role.FINANCE) || role.get(Role.ADMIN)) {
			depReqs = depRepo.getAll();
		} else if (role.get(Role.MEMBER)) {
			depReqs = depRepo.getAll(user);
		}
		%>
		<table class="ui inverted teal table" dir="rtl">
		  <thead>
		    <tr>
		    <th>نام کاربری</th>
		    <th>مقدار درخواست</th>
		    <th>وضعیت درخواست</th>
		  <% if (role.get(Role.FINANCE) || role.get(Role.ADMIN)) { %>
		  	<th colspan="2">عملیات درخواست </th>
		  <%} %>
		  </tr></thead><tbody>
		  <% for(DepositRequest r : depReqs) { %>
		    <tr>
		      <td><%=r.getId() %></td>
		      <td><%=r.getAmount() %></td>
		      <td><%=r.getStatus() %></td>
		    <% if ((role.get(Role.FINANCE) || role.get(Role.ADMIN)) && r.getStatus().equals(Status.PENDING)) { %>
			  	<td>
			  		<form method="post" action="<c:url value="/finance/deposit/accept" />">
			  			<input type="hidden" name="reqid" value="<%= r.getReqID()%>"/>
			  			<input class="ui blue button" type="submit" value="تایید"></input>
					</form>
				</td>
				<td>
					<form method="post" action="<c:url value="/finance/deposit/reject" />">
			  		<input type="hidden" name="reqid" value="<%= r.getReqID()%>"/>
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