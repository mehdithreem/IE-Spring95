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
<title>مدیریت کاربران</title>
</head>
<body>
	<jsp:include page="/Menu.jsp" />
	<script>$('#menu-user-manager').addClass('active');</script>
	
	<div class="content" dir="rtl">	
		<%
		UserRepo repo = UserRepo.getRepository();
		List<User> userList = repo.getAll();
		%>
		<table class="ui inverted teal table" dir="rtl">
		  <thead>
		    <tr>
		    <th>نام کاربری</th>
		    <th>نام</th>
		    <th>نام خانوادگی</th>
		    <th>پست الکترونیک</th>
		    <th>اعتبار</th>
		    <th>دسترسی‌ها</th>
		  </tr></thead><tbody>
		  <% for(User u : userList) { %>
		    <tr>
		      <td><%=u.getUsername() %></td>
		      <td><%=u.getName() %></td>
		      <td><%=u.getLastName() %></td>
		      <td><%=u.getEmail() %></td>
		      <td><%=u.getCredit() %></td>
		      <td>
				  <form id='<%="roleForm" + u.getUsername()%>' action="<c:url value="/admin/setroles" />" method="post">
				  		<input type='hidden' name='target-user' value='<%=u.getUsername()%>'/>
				  		<div class="inline fields">
				  		
				  		<%
				  		Map<Role, Boolean> roles = repo.getUserRolesMap(u.getUsername());
				  		for(Role r : roles.keySet()) {
				  		%>
				  			<div class="field">
				  				<div class="ui toggle checkbox">
				  					<input 
				  						type="checkbox"
				  						tabindex="0"
				  						name="<%=r.toString()%>"
				  						<%= roles.get(r) ? " CHECKED " : "" %>
				  						onChange="this.form.submit()"
				  						>
				  					<label><%=r.toString() %></label>
				  				</div>
				  			</div>
				  		<%} // for Role end %>
				  			
				  		</div>
				  </form>
		      </td>
		    </tr>
		  <% } // for user end %>
		  </tbody>
		</table>
	</div>

</body>
</html>