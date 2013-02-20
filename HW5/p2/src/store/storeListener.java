package store;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.sql.SQLException;
import java.util.*;

/**
 * Application Lifecycle Listener implementation class storeListener
 *
 */
@WebListener
public class storeListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public storeListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	Map<String, Product> data = null;
		try {
			data = Catalog.load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	ServletContext context = event.getServletContext();
    	context.setAttribute("catalog", data);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
