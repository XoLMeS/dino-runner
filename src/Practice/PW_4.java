package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RespectBinding;

import yo.CustomForm;
import yo.MyHash;
import yo.Student;
import yo.User;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;

public class PW_4 extends HttpServlet{
	
	private String err_01 = "Invalid format, Nickname <= 20 symbols, password <= 16 symbols";
	private String err_02 = "Empty field";
	private String err_03 = "User not found or invalid password";

	private boolean err01 = false;
	private boolean err02 = false;
	private boolean err03 = false;


	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private StringBuilder sb = new StringBuilder();
	private String hash;
	
	CustomForm cf2 = new CustomForm();

	private static final String secretKey = "PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);
	
		if(err01 == true){
			resp.getWriter().println("<div style=\" color: red;\">"+err_01+"</div>");
		}
		
		if(err02 == true){
			resp.getWriter().println("<div style=\" color: red;\">"+err_02+"</div>");
		}
		if(err03 == true){
			resp.getWriter().println("<div style=\" color: red;\">"+err_03+"</div>");
		}
		
		getLogInForm();
		resp.getWriter().println(cf2.getForm());
       
		resp.getWriter().println("<div align=\"center\" style=\"color: green;\">If you not signed up ---> <a href=\"/Sign_up\">Sign up</a></div>");
		resp.getWriter().println("<a href=\"/te\">link</a>");
		err01 = false;
		err02 = false;
		err03 = false;
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);

		String nickname = req.getParameter("Nickname");
		String password = req.getParameter("Password");
	
		
		
		if(nickname == "" | password == ""  | nickname == null | password == null ){
			err02 = true;
			resp.sendRedirect("/PW_4");
		}
		else if(nickname.length()>20 | password.length()>16){
			err01 = true;
			resp.sendRedirect("/PW_4");
		}
		if(lookForColisionsInDatabase(nickname,password)==true){
			err03 = true;
			resp.sendRedirect("/PW_4");
		}
		
		if(err01==false && err02==false && err03==false){
			se.setAttribute("loged", true);
			se.setAttribute("Nick", nickname);
			resp.sendRedirect("/PW_4_Main");
			
		}	
		if ((se.getAttribute("loged")!= null)&&(se.getAttribute("loged").equals("true")))
			resp.sendRedirect("/PW_4_Main");
		String login = req.getParameter("login");
		String log_password = req.getParameter("password");
		if ((login!=null)&&(password!=null)){
			se.setAttribute("loged", "true");
			resp.sendRedirect("/PW_4_Main");
		}
	
	}
	
	
	private void getLogInForm(){
		cf2.createForm("center","Log in","loged");
		cf2.addField("Nickname");
		cf2.addPassField("Password");
	}
	

	public boolean lookForColisionsInDatabase(String nickname,String password) {
		hash = MyHash.hmacSha1(password, secretKey);
		Query q = new Query("User");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		String startCursor = null;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);

		int size = results.size();
		for (int i = 0; i < size; i++) {
			if (results.get(i).getProperty("Name") != null
					&& results.get(i).getProperty("Name").equals(nickname)) {
				
				if(results.get(i).getProperty("Password") != null
						&& results.get(i).getProperty("Password").equals(hash)){
					return false;
				}
				
			}
			
		}
		return true;
	}
	
	
	
}
