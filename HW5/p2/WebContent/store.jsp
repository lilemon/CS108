<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, store.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Store</title>
</head>
<body>
<h1>Student Store</h1>
<a>Items Available:</a>
<ul>
<%

Map<String, Product> catalog = (Map<String, Product>) getServletContext().getAttribute("catalog");

String url = "\"item.jsp?";

for (String id: catalog.keySet()) {
	Product product = catalog.get(id);
	out.println("<li> <a href=" + url + "id=" + id + "\">" + product.getName() + "</a></li>");
}
%>
</ul>
<form action="cart.jsp" method="post" id="cart">
<button type="submit" value="See Cart">See Cart</button>
</form>


</body>
</html>