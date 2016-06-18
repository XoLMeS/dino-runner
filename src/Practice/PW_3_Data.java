package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;


public class PW_3_Data extends HttpServlet{

	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	StringBuilder staticSb = new StringBuilder();
	boolean first_page = true;
	public static int page = 0;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.getWriter().println("<html><body><script>function NextPage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) + 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter().println("<script>function PrePage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) - 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter().println("<form method=\"post\"><input  name=\"page\" value=\""+page+"\" type=\"hidden\"></form>");
		System.out.println(page + req.getParameter("page"));
		
		
		resp.getWriter().println(getAllPosts(null));
		resp.getWriter().println("<div align=\"center\"><a href=\"/PW_3\" >Back</a><br><button onclick=\"NextPage()\">Next</button>");
		if(page>0){resp.getWriter().println("<button onclick=\"PrePage()\">Previous</button></div>");}
		resp.getWriter().println("</div></body></html>");
		
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.sendRedirect("/PW_3_Data");
		page = Integer.valueOf(req.getParameter("page"));
		if(page<0){page = 0;}
		
		
		//resp.getWriter().println(page);
	 
	}
	
	private String getAllPosts(String cursor){
		
		Query q = new Query("Student");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		
		int pageSize = (page+1)*10;
		staticSb.delete(0, staticSb.length());
		staticSb.append("<br>");
		
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
        String startCursor = cursor;
        if (startCursor != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
        }
        
        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
        int size = results.size();
        for(int i = 0+page*10; i < 10+page*10; i++){
        if(i<size){
        	
        	staticSb.append("<div>");
        	staticSb.append(results.get(i).getProperty("Last_Name")+" ");
        	staticSb.append(results.get(i).getProperty("Name")+"<br>");  	
        	staticSb.append("Age: "+results.get(i).getProperty("Age")+"<br>");
        	staticSb.append("Faculty: " + results.get(i).getProperty("Faculty")+"<br>");
        	staticSb.append("Course: " + results.get(i).getProperty("course")+"<br>");
        	staticSb.append("</div>");
        	staticSb.append("<hr/>");}
        }
        return staticSb.toString();	
	}
}
