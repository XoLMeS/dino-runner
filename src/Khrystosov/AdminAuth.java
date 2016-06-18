package Khrystosov;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yo.CustomForm;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class AdminAuth extends HttpServlet{
	private static final String DROP_BOX_APP_KEY = "yrqi3nkwfs0ltk9";
	private static final String DROP_BOX_APP_SECRET = "layvzg3xiqlh92v";
	public static DbxClient dbxClient;
	DbxWebAuthNoRedirect dbxWebAuthNoRedirect;
	DbxRequestConfig dbxRequestConfig;
	CustomForm cf = new CustomForm();
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		cf.createForm("center","Start","start");
		cf.addField("Key");
		DbxAppInfo dbxAppInfo = new DbxAppInfo(DROP_BOX_APP_KEY, DROP_BOX_APP_SECRET);
	    dbxRequestConfig = new DbxRequestConfig(
				"JavaDropboxTutorial/1.0", Locale.getDefault().toString(), AppengineHttpRequestor.Instance);
		dbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(
				dbxRequestConfig, dbxAppInfo);
		String authorizeUrl = dbxWebAuthNoRedirect.start();
		resp.getWriter().println("1. Authorize: Go to URL and click Allow : "
				+ authorizeUrl);
		resp.getWriter().println("2. Auth Code: Copy authorization code and input here ");
		resp.getWriter().println(cf.getForm());
		
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		String dropboxAuthCode = req.getParameter("Key");
		if(dropboxAuthCode.equals(null)){resp.sendError(101, "Empty field");}
		DbxAuthFinish authFinish = null;
		try {
			authFinish = dbxWebAuthNoRedirect.finish(dropboxAuthCode);
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String authAccessToken = authFinish.accessToken;
		dbxClient = new DbxClient(dbxRequestConfig, authAccessToken);
		resp.sendRedirect("/UDFS");
	}
	
	

}
