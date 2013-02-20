package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "handling login action", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		boolean success = manager.check(key, value);
		
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
			out.println("<title>Information Incorrect</title>");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<h1>Please Try Again </h1>");
			
			out.println("<a>Either your user name or your password is incorrect. Please try again.</a>");
			out.println("<form action=\"LoginServlet\" method=\"post\">");
			out.println("<p>User Name: <input type=\"text\" name=\"name\" /></p>");
			out.println("<p>Password: <input type=\"password\" name=\"password\" /></p>");
			out.println("<p><input type=\"submit\" value=\"Login\"></input></p></form>");
			out.println("<p><a href=\"create.html\">Create New Account</a></p>");
			
			out.println("</body>");
			out.println("</html>");
		}
	}

}
