<% 
session.invalidate(); 
request.getRequestDispatcher("/Index.jsp").forward(request, response);
%>