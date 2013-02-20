<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, store.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Map<String, Product> catalog = (Map<String, Product>) getServletContext()
			.getAttribute("catalog");
	Cart cart = (Cart) request.getSession().getAttribute("cart");
	if (cart == null) {
		cart = new Cart();
		request.getSession().setAttribute("cart", cart);
	}

	Enumeration<String> requests = request.getParameterNames();
	while (requests.hasMoreElements()) {
		String id = requests.nextElement();
		if (id.equals("id")) {
			id = request.getParameter("id");
			Product product = catalog.get(id);
			cart.update(id, cart.getCount(product) + 1, catalog);
		} else {
			Product product = catalog.get(id);
			Integer count = Integer.parseInt(request.getParameter(id));
			cart.update(id, count, catalog);
		}
	}
	double total = 0;
	
	Map<Product, Integer> content = cart.getContent();
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body>
	<h1>Shopping Cart</h1>
	<form action="cart.jsp" method="post">
			<%
				out.println("<ul>");
				for (Product product : content.keySet()) {
					int count = content.get(product);
					double price = product.getPrice() * count;
					total = total + price;
					out.println("<li> <input type=\"text\" value=\"" + count
							+ "\" name=\"" + product.getId() + "\">"
							+ " " + product.getName() + ", $" + String.format("%.2f", price) + "</input>");
				}
				out.println("</ul>");
				out.println("Total: $" + String.format("%.2f", total));
				out.println("<button type=\"submit\" value=\"Update Cart\">Update Cart</button>");
			%>
	</form>
	<br/>
	<a href='store.jsp'>Continue Shopping</a>

</body>
</html>