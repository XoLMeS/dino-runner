package Practice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yo.CustomForm;
import yo.Post;
import yo.Student;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;

public class HW_3 extends HttpServlet {

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private StringBuilder sb = new StringBuilder();
	StringBuilder staticSb = new StringBuilder();

	CustomForm cf = new CustomForm();

	boolean first_page = true;
	public static int page = 0;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html><body><script>function NextPage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) + 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter().println("<script>function PrePage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) - 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter().println("<form method=\"post\"><input  name=\"page\" value=\""+page+"\" type=\"hidden\"></form>");
		
		
		resp.getWriter().println(getAllPosts(null));
		resp.getWriter().println("<div align=\"center\"><br><button onclick=\"NextPage()\">Next</button>");
		
		if(page>0){resp.getWriter().println("<button onclick=\"PrePage()\">Previous</button></div>");}
		resp.getWriter().println("</div></body></html>");
		getForm();
		resp.getWriter().println(cf.getForm());
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		if(req.getParameter("page")!=null){
		page = Integer.valueOf(req.getParameter("page"));}
		if(page<0){page = 0;}
		String author = req.getParameter("Author");
		if(author==null || author == "" || author==" "){
			author = "Anonym";
		}
		String title = req.getParameter("Title");
		if(title==null || title == "" || title==" "){
			title = "Common";
		}
		String text = req.getParameter("Text");
		if(req.getParameter("Text")!=null){Post p = new Post(title,author,text);
		datastore.put(p.getPostEntity());}
		resp.sendRedirect("/HW_3");

	}

	private void getForm() {
		cf.createForm("center");
		cf.addField("Title");
		cf.addField("Author");
		cf.addTextArea("Text", 100, 11, 1000);
	}

	private String getAllPosts(String cursor) {

		Query q = new Query("Post");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);

		int pageSize = (page + 1) * 10;
		staticSb.delete(0, staticSb.length());
		staticSb.append("<br>");

		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		String startCursor = cursor;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int size = results.size();
		for (int i = 0 + page * 10; i < 10 + page * 10; i++) {
			if (i < size) {

				staticSb.append("<div align=\"center\" style=\"font-size: 16pt;\">");
				staticSb.append(results.get(i).getProperty("Title") + ":  </div><div align=\"center\" style=\"font-size: 14pt;\"><br><br>");
				staticSb.append(results.get(i).getProperty("Text"));
				staticSb.append(" <br></div><div style=\"position: absolute; left: 300px;\">"+results.get(i).getProperty("date")+"</div><div style=\"position: absolute; right: 500px;\">"+results.get(i).getProperty("Author"));
				staticSb.append("</div><br>");
				staticSb.append("<hr/>");
			}
		}
		return staticSb.toString();
	}
}
