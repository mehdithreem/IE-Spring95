<%@page import="java.util.*"%>
<%@page import="java.net.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<div class="header">
	<h1 class="header-title">
        <%=request.getParameter("headerVar")%>
	</h1>
	<div class="fade-box">
		<img class="logo" id="seo" src="/stocks/stuff/seologo.png" alt="" />
		<img class="logo" id="ut" src="/stocks/stuff/utlogo.png" alt=""/>
	</div>
</div>