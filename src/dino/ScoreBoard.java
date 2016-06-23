package dino;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ScoreBoard extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821100601729793654L;
	
	private static final String PAGE_TITLE = "Dino runner - scoreboard";
	
	 @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        resp.setContentType("text/html");
	        resp.getWriter().println(setHeader());
	        resp.getWriter().println(setBody());
	        resp.getWriter().println(setFooter());
	    }

	    private String setFooter() {
		// TODO Auto-generated method stub
	    	String footer = "";
	    	footer = " <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script> \n"
        + "<script src=\" js/bootstrap.min.js \"></script>\n" +//
	    			"<script src=\"https://code.createjs.com/soundjs-0.6.2.min.js\"></script><link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"><script src=\"js/pixi.js\"></script>"
        +"<script src='js/face.js'></script>\n"
        + "<script type=\"text/javascript\" src=\"js/score.js\"></script></body></html>";
        
		return footer;
	}

		private String setBody() {
		// TODO Auto-generated method stub
			String body = "";
			body = "<body>\n"+
    
"<nav class=\"navbar navbar-default\">\n"
	+"<div class=\"container\">\n"+
		"<div class=\"navbar-header\">\n"+
		"<a class=\"brand-name navbar-brand\" href=\"#\">Dinno runner</a>\n"+
	"</div>\n"+
	"<div class=\"navbar-collapse collapse\"> \n"+
    "<ul class=\"nav navbar-nav\">\n"+
      "<li class=\"active\"><a href=\"\\\">Home</a></li>\n"+
      "<li><a href=\"#\">Scoreboard</a></li>\n"+
	  "<li class=\"pull-right\"><button id=\"fb_login\" class=\"my-facebook-btn btn btn-primary\" href=\"#\">facebook</button></li>\n"+
    "</ul>\n"+
    "</div>\n"+
	"</div>\n"+
	"</nav>\n"+

	"<div class=\"score-board col-md-6\">\n"+
	"<ul class=\"nav nav-tabs\" role=\"tablist\">\n"+
        "<li id=\"global\" role=\"presentation\"><a href=\"#\">Global Score</a></li>\n"+
        "<li id=\"local\" role=\"presentation\" class=\"active\"><a href=\"#\">Local score</a></li>\n"+
      "</ul>\n"
	+"<table class=\"table\"><thead><tr><th>#</th>\n"+
                "<th>Username</th>\n"+
                "<th>Highscore</th>\n"
              +"</tr></thead><tbody id=\"scores\"></tbody></table>\n"
+"</div>\n";
		return body;
	}

		private String setHeader() {
		// TODO Auto-generated method stub
			String header = "";
			header = "<!DOCTYPE html>\n"+
					"<html>\n"+
  "<head>\n"+
    "<meta charset=\"utf-8\">"+
    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"+
    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
    +"<title>" + PAGE_TITLE + "</title>"

    
    +"<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">"+

    "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main1.css\">"

    
  +"</head>";
		return header;
	}

		@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	    }

}
