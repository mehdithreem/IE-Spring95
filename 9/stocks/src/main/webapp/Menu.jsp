<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="ir.stocks.domain.*"%>
<%@page import="ir.stocks.data.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	UserRepo repo = UserRepo.getRepository();
	Map<Role, Boolean> role = null;
	String user = request.getRemoteUser();

	if (user != null) {
		role = repo.getUserRolesMap(user);
	}
%>
<link rel="stylesheet" type="text/css" href="<c:url value="/style/menu.css" />">
<div class="ui labeled icon menu" dir="rtl">
  <a class="item" id="menu-home" href="<c:url value="/" />">
    <i class="home icon"></i>
    وضعیت بازار
  </a>
  <% if (user != null && role != null && (role.get(Role.MEMBER) || role.get(Role.ADMIN))) { %>
  <a class="item" id="menu-credit" href="<c:url value="/credit" />">
    <i class="money icon"></i>
    اعتبار
  </a>
  <% } %>
  
  <% if (user != null && role != null && (role.get(Role.COMPANY) || role.get(Role.ADMIN))) { %>
  <a class="item" id="menu-symbol-manager" href="<c:url value="/symbolmanager" />">
    <i class="copyright icon"></i>
    نمادها
  </a>
  <% } %>
  
  <% if (user != null && role != null && (role.get(Role.FINANCE) || role.get(Role.ADMIN) || role.get(Role.MEMBER))) { %>
  <a class="item" id="menu-order-manager" href="<c:url value="/ordermanager" />">
    <i class="shop icon"></i>
    خرید و فروش
  </a>
  <% } %>
  
  <% if (user != null && role != null && (role.get(Role.ADMIN))) { %>
  <a class="item" id="menu-user-manager" href="<c:url value="/usermanager" />">
    <i class="users icon"></i>
    مدیریت کاربران
  </a>
  <% } %>
  
  <div class="left menu">
  <a class="item" style="background-color: blue; color :white;" href="#">
    	<i class="copyright icon"></i>
    	coolest symbol: 
    	<%= TransactionRepo.getRepository().getMostFolan() %>
    </a>
  <% if (user == null) {%>
    <a class="item" style="background-color: red; color :white;" href="<c:url value='/forcelogin'/>">
    	<i class="sign in icon"></i>
    	ورود به سیستم
    </a>
  <% } else {%>
  	<a class="item">
    	<%=user %>
    </a>
  	<a class="item" style="background-color: red; color :white;" href="<c:url value='/logout'/>">
    	<i class="sign out icon"></i>
    	خروج از سیستم 
    </a>
  <% } %>
  </div>
</div>