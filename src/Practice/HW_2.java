package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HW_2 extends HttpServlet{
	
	private StringBuilder sb = new StringBuilder(); 
	private boolean fail = false;
	private boolean empty = false;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		
		resp.getWriter().println(getForm());
		if(fail){resp.getWriter().println("<div align=\"center\" style=\"color: red; font: 25pt sans-serif;\">User Invalid, verification failed</div>");}
		if(empty){resp.getWriter().println("<div align=\"center\" style=\"color: red; font: 25pt sans-serif;\">Empty Username or Email</div>");}
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		String pass = req.getParameter("password");
		String ver = req.getParameter("verify");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		if(isEmpty(username,email)){resp.sendRedirect("/HW_2");}
		if(checkPasswords(pass, ver)){
			
		}
		else
		{		
			resp.sendRedirect("/HW_2");
		}
		if(!fail & !empty){
			resp.sendRedirect("/Hello");
		}
	}
	
	private String getForm(){
		sb.delete(0, sb.length());
		sb.append("<form method=\"post\" align = \"center\">");
		sb.append("<table align=\"center\"> <th>");
		sb.append("Login Tester</th><br><br><tr><td>");
		sb.append("<label>Username</td><td><input type=\"text\" name=\"username\"");
		sb.append("></label></td></tr>");
		sb.append("<br><br><tr><td><label>Password</td><td><input type=\"password\" name=\"password\"");
		sb.append("></label></td></tr>");
		sb.append("<br><br><tr><td><label>Verify Password</td><td><input type=\"password\" name=\"verify\"");
		sb.append("></label></td></tr>");
		sb.append("<br><br><tr><td><label>Email</td><td><input type=\"text\" name=\"email\"");
		sb.append("></label></td></tr>");
		sb.append("<br><br><tr align=\"center\"><td><button>Log in</button></td></tr></table></form>");
		return sb.toString();
	}
	private boolean checkPasswords(String pass, String verify){
		if(pass.equals(verify) && pass != null && pass != ""){
			fail = false;
			return true;
		}
		else
			fail = true;
		return false;
		
	}
	
	private boolean isEmpty(String username, String email){
		if(username == null | username == "" | email == null | email == ""){
			empty = true;
			return true;
			
		}
		else {empty = false;
		return false;}
		
	}
	
}
