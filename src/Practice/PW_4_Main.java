package Practice;

import java.io.*;
import java.net.*;
import java.util.Collections;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import yo.Coordinate;
import yo.CustomForm;
import yo.Post;
import yo.Post3000;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class PW_4_Main extends HttpServlet {

	private boolean first_ip = false;
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private StringBuilder sb = new StringBuilder();
	StringBuilder staticSb = new StringBuilder();
	private static final String IMAGE_URL = "http://maps.googleapis.com/maps/api/staticmap?zoom=2&size=800x800&sensor=false";
	private static final String MARKER = "&markers=color:green%7Clabel:S%7C";
	private static final String IP_URL = "http://api.hostip.info/?ip=";
	private String map;
	private static Cache cache;
	CustomForm cf = new CustomForm();

	boolean first_page = true;
	public static int page = 0;

	
	public PW_4_Main(){
		if (cache == null)
			initializeMemCache();
		
	}
	
	private void initializeMemCache(){
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			// ...
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);
		if ("XoLMeS".equals(se.getAttribute("Nick"))) {
			resp.getWriter()
					.println(
							"<div style=\"font-size: 18pt; color: gold;\">Welcome Your Majesty XoLMeS</div>");
		} else
			resp.getWriter().println(
					"<div style=\"font-size: 14pt; color: green;\">Welcome "
							+ se.getAttribute("Nick")
							+ "! It is your n visit</div>");
		resp.getWriter().println(
				"<div><br><button onclick=\"logout()\">Log out</button>");
		resp.getWriter()
				.println(
						"<html><body><script>function NextPage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) + 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter()
				.println(
						"<script>function PrePage(){document.getElementsByName(\"page\")[0].value = parseInt(document.getElementsByName(\"page\")[0].value) - 1; document.getElementsByTagName(\"form\")[0].submit();}</script>");
		resp.getWriter()
				.println(
						"<script>function logout(){document.getElementsByName(\"loged\")[0].value = false; document.getElementsByTagName(\"form\")[1].submit();}</script>");
		resp.getWriter().println(
				"<form method=\"post\"><input  name=\"page\" value=\"" + page
						+ "\" type=\"hidden\"></form>");
		resp.getWriter()
				.println(
						"<form method=\"get\"><input name=\"loged\" value=\"true\" type=\"hidden\"></form>");
		createMap();
		resp.getWriter().println(getAllPosts(null,false));
		resp.getWriter()
				.println(
						"<div align=\"center\"><br><button onclick=\"NextPage()\">Next</button>");

		if (page > 0) {
			resp.getWriter().println(
					"<button onclick=\"PrePage()\">Previous</button></div>");
		}
		resp.getWriter().println("</div></body></html>");
		getForm();
		resp.getWriter().println(cf.getForm());
		if ("false".equals(req.getParameter("loged"))) {
			se.removeAttribute("loged");
			resp.sendRedirect("/PW_4");
		}
		if (se.getAttribute("loged") == null) {
			resp.sendRedirect("/PW_4");
		}
		// String client_ip=getClientIpAddr(req);
		String client_ip=req.getRemoteAddr();
		if("XoLMeS".equals(se.getAttribute("Nick"))){
			client_ip = "255.255.255.2";
		}
		// System.out.println(client_ip);
		/* Just for test */
		//String client_ip = "255.255.255.2";

		//Coordinate c = new Coordinate(get_coords(client_ip));
		//String client_ip2 = "216.39.58.17";

		//Coordinate c2 = new Coordinate(get_coords(client_ip2));
		// End of test
	
		//addCoord(c);
		//addCoord(c2);
		resp.getWriter().println("<img src=\"");
		resp.getWriter().println(map);
		resp.getWriter().println("\" alt=\"карта\">");

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);
		if (req.getParameter("page") != null) {
			page = Integer.valueOf(req.getParameter("page"));
		}
		if (page < 0) {
			page = 0;
		}
		String author = (String) se.getAttribute("Nick");

		String title = req.getParameter("Title");
		if (title == null || title == "" || title == " ") {
			title = "Common";
		}
		String text = req.getParameter("Text");
		if (req.getParameter("Text") != null) {
			String ip=req.getRemoteAddr();
			if("XoLMeS".equals(se.getAttribute("Nick"))){
				ip = "255.255.255.2";
			}
			Coordinate c = new Coordinate(get_coords(ip));
			String coord = c.toString();
			Post3000 p = new Post3000(title, author, text,ip,coord);
			datastore.put(p.getPostEntity());
		}
		resp.getWriter().println(getAllPosts(null,true));
		resp.getWriter().println(cf.getForm());
		resp.getWriter().println("<img src=\"");
		resp.getWriter().println(map);
		resp.getWriter().println("\" alt=\"карта\">");
		

	}

	private void getForm() {
		cf.createForm("center", "Add", "null");
		cf.addField("Title");
		cf.addTextArea("Text", 100, 11, 1000);
	}

	private String getAllPosts(String cursor,boolean update) {
		if ((cache.containsKey("posts"))&&(update==false))
			return (String) cache.get("posts");
		else{
		Query q = new Query("Post3000");
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
				staticSb.append(results.get(i).getProperty("Title")
						+ ":  </div><div align=\"center\" style=\"font-size: 14pt;\"><br><br>");
				staticSb.append(results.get(i).getProperty("Text"));
				staticSb.append(" <br></div><div style=\"position: absolute; left: 300px;\">"
						+ results.get(i).getProperty("date")
						+ "</div><div style=\"position: absolute; right: 500px;\">"
						+ results.get(i).getProperty("Author"));
				staticSb.append("</div><br>");
				staticSb.append("<hr/>");
				addCoord((String)results.get(i).getProperty("Coordinate"));
			}
		}
		cache.put("posts", staticSb.toString());
		return staticSb.toString();
		}
	}

	private String get_coords(String ip) throws IOException {
		String urlI = IP_URL + ip;
		StringBuilder content = new StringBuilder();
		URL url;
		try {
			url = new URL(urlI);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				content.append(inputLine);
			}
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(content.toString());
		String coords = parseXML(content.toString());
		System.out.println(coords);
		return coords;
	}

	private String getImgUrl(Coordinate c, Coordinate c2) {
		return IMAGE_URL + MARKER + c.toString()+"|"+c2.toString() ;
	}
	
	private void addCoord(String c){
		if(first_ip==false){
			map = map + c; first_ip = true;}
		else
		map = map + "|" + c;
	}
	
	private void createMap(){
		map = IMAGE_URL + MARKER;
	}

	public String traverse(Node n) {
		// Extract node info:
		String coord = null;
		String nodename = n.getNodeName();
		String test = n.getNodeValue();
		// Print and continue traversing.

		if (n.getNodeName().equals("gml:coordinates")) {
			Node child = n.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				coord = cd.getData();
			}
		} else {
			// Now traverse the rest of the tree in depth-first order.
			if (n.hasChildNodes()) {
				// Get the children in a list.
				NodeList nl = n.getChildNodes();
				// How many of them?
				int size = nl.getLength();
				String temp = null;
				for (int i = 0; i < size; i++) {
					// Recursively traverse each of the children.
					temp = traverse(nl.item(i));
					if (temp != null)
						coord = temp;
				}
			}
		}
		return coord;
	}

	private String parseXML(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String coord = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(is);
			Element root = doc.getDocumentElement();
			coord = traverse(root);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return coord;
	}

	private static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
