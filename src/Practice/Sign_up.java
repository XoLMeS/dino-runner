package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yo.CustomForm;
import yo.MyHash;
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

public class Sign_up extends HttpServlet {

	private String err_01 = "Invalid format, Nickname <= 20 symbols, password <= 16 symbols";
	private String err_02 = "Empty field";
	private String err_03 = "Nickname already in use";
	private String err_04 = "Verification failed";

	private int users = 0;
	private boolean err01 = false;
	private boolean err02 = false;
	private boolean err03 = false;
	private boolean err04 = false;

	private String nickname;
    private String hash;
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private StringBuilder sb = new StringBuilder();
	CustomForm cf = new CustomForm();

	private static final String secretKey = "PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);

		if (err01 == true) {
			resp.getWriter().println(
					"<div style=\" color: red;\">" + err_01 + "</div>");
		}

		if (err02 == true) {
			resp.getWriter().println(
					"<div style=\" color: red;\">" + err_02 + "</div>");
		}

		if (err03 == true) {
			resp.getWriter().println(
					"<div style=\" color: red;\">" + err_03 + "</div>");
		}
		
		if (err04 == true) {
			resp.getWriter().println(
					"<div style=\" color: red;\">" + err_04 + "</div>");
		}

		getSignUpForm();
		resp.getWriter().println(cf.getForm());
		resp.getWriter()
				.println(
						"<div align=\"center\" style=\"color: green;\">If you already have an account <a href=\"/PW_4\">Log in</a></div>");
		err01 = false;
		err02 = false;
		err03 = false;
		err04 = false;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		HttpSession se = req.getSession(true);
		if ((se.getAttribute("loged") != null)
				&& (se.getAttribute("loged").equals("true"))) {
			resp.sendRedirect("/PW_4_Main");

		}
		nickname = req.getParameter("Nickname");
		String password = req.getParameter("Password");
		String verify = req.getParameter("Verify");

		if (nickname == "" | password == "" | verify == "" | nickname == null
				| password == null | verify == null) {
			err02 = true;
			resp.sendRedirect("/PW_4");
		} else if (nickname.length() > 20 | password.length() > 16
				| verify.length() > 16) {
			err01 = true;
			resp.sendRedirect("/PW_4");
		}
		if (lookForColisionsInDatabase(nickname) == false) {
			err03 = true;
			resp.sendRedirect("/Sign_up");
		}
		if(!password.equals(verify)){
			err04 = true;
			resp.sendRedirect("/Sign_up");
		}
		if (err01 == false && err02 == false && err03 == false && err04 == false) {
			hash = MyHash.hmacSha1(password, secretKey);
			User user = new User(nickname, hash);
			users += 1;
			datastore.put(user.getUserEntity());
			se.setAttribute("loged", "true");
			se.setAttribute("Nick", nickname);
			resp.sendRedirect("/PW_4_Main");
		}
	}

	private void getSignUpForm() {

		cf.createForm("center", "Sign up", "loged");
		cf.addField("Nickname");
		cf.addPassField("Password");
		cf.addPassField("Verify");

	}

	public boolean lookForColisionsInDatabase(String nickname) {
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
				return false;
			}
		}
		return true;
	}
}
