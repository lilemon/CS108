<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, store.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
Map<String, Product> catalog = (Map<String, Product>) getServletContext().getAttribute("catalog");
Product product = catalog.get(request.getParameter("id"));
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= product.getName() %></title>
</head>
<body>
<h1><%= product.getName() %></h1>
<img src="<%= product.getImg() %>" />
<p>$<%= product.getPrice() %></p>
<form action="cart.jsp" method="post">
<input name="id" type="hidden" value="<%= product.getId()%>">
<button type="submit" value="Add to Cart">Add to Cart</button>
</form>
</body>
</html>