package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet{
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		
		resp.getWriter().println("<H1 style=\"color: navy;\" align=\"center\" height=\"500\">WELCOME</H1>");
		
		
	}

}
