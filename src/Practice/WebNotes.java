package Practice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebNotes extends HttpServlet{

	ArrayList<String> save = new ArrayList<String>();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.getWriter().println("<html><head>");
		resp.getWriter().println("<title>WebNotes</title>");
		resp.getWriter().println("</head>");
		resp.getWriter().println("<body>");
		resp.getWriter().println("<form method='post'>"
				+ "<input type='text' name='note' placeholder='add your note here' size='100'>"
				+ "<button>POST</button></form>");
		resp.getWriter().println("-----------------------------------------------------------------------------<br>");
		for(int i = 0; i < save.size(); i++){
			resp.getWriter().println("<p> Note #" + i + ": " + save.get(i) + "</p>");
		}
		resp.getWriter().println("-----------------------------------------------------------------------------<br>");
		resp.getWriter().println("<form method='post'>"
				+ "<input type='number' name='number' placeholder='number' size='5'>"
				+ "<input type='text' name='new_note' placeholder='leave blank to delete' size='75'>"
				+ "<button>SAVE</button></form>");
		resp.getWriter().println("</body></html>");
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String buff = getParameter(req, "note");
		if(buff!=null && !buff.equals("")){
			save.add(buff);
		}
		if(getParameter(req, "number")!=null){
		
			int number = Integer.parseInt(getParameter(req, "number"));
			if(number >= 0 && number < save.size()){
				buff = getParameter(req, "new_note");
				if(buff!=null && buff.equals("")){
					save.remove(number);
				}
				else if(buff!=null){
					save.remove(number);
					save.add(buff);
				}
			}
		}
		
		resp.sendRedirect(getServletName());
	}
	
	public String getParameter(HttpServletRequest req, String name){
		return req.getParameter(name);
	}
	
}
