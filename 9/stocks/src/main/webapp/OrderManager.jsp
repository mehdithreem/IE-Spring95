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
<script>
$('select.dropdown')
.dropdown()
;
</script>
</head>
<body>
	<jsp:include page="/Menu.jsp" />
	<script>$('#menu-order-manager').addClass('active');</script>
	
	<div class="content" dir="rtl">
		<%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("not-enough-money")) {%>
			<div class="ui red message">
			<div class="header">
			اعتبار کافی نیست.
			</div>
			</div>
	     <% } %>
	     
	     <%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("not-enough-share")) {%>
			<div class="ui red message">
			<div class="header">
			تعداد سهام کافی نیست.
			</div>
			</div>
	     <% } %>
	     
	     <%if(request.getAttribute("error") != null && ((String) request.getAttribute("error")).equals("instr-not-exist")) {%>
			<div class="ui red message">
			<div class="header">
			نماد پیدا نشد.
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
			<div class="ui green message">
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
		
		OrderRepo ordRepo = OrderRepo.getRepository();
		List<Order> ords = null;
		
		if (role.get(Role.ADMIN) || role.get(Role.FINANCE)) {
			ords = ordRepo.getAll();
		} else if (role.get(Role.MEMBER)) {
			ords = ordRepo.getAll(user);
		}
		%>
	
		<% if(role.get(Role.ADMIN) || role.get(Role.MEMBER)) { %>
		<div class="ui inverted segment">
		  <form class="ui inverted form" action="<c:url value="/user/order/buy" />" method="post">
			    <div class="four fields">
			      <div class="field">
			    	<select class="ui fluid dropdown" name="instrument">
			    		<option value="">نام نماد</option>
			    		<% for(String s : SymbolRepo.getInstance().getNames()) {%>
			    			<option value="<%=s %>"><%=s %></option>
			    		<%} %>
			    	</select>
			      </div>
			      <div class="field">
			        <input placeholder="تعداد سهم‌ها" name="quantity" type="number">
			      </div>
			      <div class="field">
			        <input placeholder="ارزش هر سهم" name="price" type="number">
			      </div>
			    <input class="ui submit button" type="submit" value="ثبت درخواست خرید سهام"></input>
			  </div>
			</form>
		</div>
		<br>
		<div class="ui inverted segment">
		  <form class="ui inverted form" action="<c:url value="/user/order/sell" />" method="post">
			    <div class="four fields">
			      <div class="field">
			    	<select class="ui fluid dropdown" name="instrument">
			    		<option value="">نام نماد</option>
			    		<% for(String s : SymbolRepo.getInstance().getNames()) {%>
			    			<option value="<%=s %>"><%=s %></option>
			    		<%} %>
			    	</select>
			      </div>
			      <div class="field">
			        <input placeholder="تعداد سهم‌ها" name="quantity" type="number">
			      </div>
			      <div class="field">
			        <input placeholder="ارزش هر سهم" name="price" type="number">
			      </div>
			    <input class="ui submit button" type="submit" value="ثبت درخواست فروش سهام"></input>
			  </div>
			</form>
		</div>
		<%} %>
		
		<table class="ui inverted teal table" dir="rtl">
		  <thead>
		    <tr>
		    <th>درخواست دهنده</th>
		    <th>نماد</th>
		    <th>تعداد سهم‌ها</th>
		    <th>ارزش هر سهم</th>
		    <th>نوع درخواست</th>
		    <th>وضعیت درخواست</th>
		  <% if (role.get(Role.ADMIN) || role.get(Role.FINANCE)) { %>
		  	<th colspan="2">عملیات درخواست </th>
		  <%} %>
		  </tr></thead><tbody>
		  <% for(Order r : ords) { %>
		    <tr>
		      <td><%=r.getOwner() %></td>
		      <td><%=r.getInstrument() %></td>
		      <td><%=r.getQuantity() %></td>
		      <td><%=r.getPrice() %></td>
		      <td><%=r.getCommand().toString() %></td>
		      <td><%=r.getStatus() %></td>
		   <% if ((role.get(Role.ADMIN) || role.get(Role.FINANCE)) && r.getStatus().equals(Status.PENDING)) { %>
			  	<td>
			  		<form method="post" action="<c:url value="/finance/order/accept" />">
			  			<input type="hidden" name="orderid" value="<%= r.getId()%>"/>
			  			<input class="ui blue button" type="submit" value="تایید"></input>
					</form>
				</td>
				<td>
					<form method="post" action="<c:url value="/finance/order/reject" />">
			  		<input type="hidden" name="orderid" value="<%= r.getId()%>"/>
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