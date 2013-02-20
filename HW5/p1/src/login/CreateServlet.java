package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet(description = "create a new account", urlPatterns = { "/CreateServlet" })
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("name");
		String value = request.getParameter("password");
		Manager manager = (Manager) getServletContext().getAttribute("manager");
		
		boolean success = manager.add(key, value);
		
		PrintWriter out = response.getWriter();
		
		if (success) {
			out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
					      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Welcome</title>");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<h1>Welcome " + key + "</h1>");
			
			out.println("</body>");
			out.println("</html>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
					      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Create Account</title>");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<h1>The Name " + key + " is Already in Use </h1>");
			
			out.println("<a>Please enter another name and password</a>");
			out.println("<form action=\"CreateServlet\" method=\"post\">");
			out.println("<p>User Name: <input type=\"text\" name=\"name\" /></p>");
			out.println("<p>Password: <input type=\"password\" name=\"password\" /></p>");
			out.println("<p><input type=\"submit\" value=\"Create Account\"></input></p></form>");
			
			out.println("</body>");
			out.println("</html>");
		}
		
	}

}
