package dino;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dino_Runner_Servlet extends HttpServlet {

    private static final long serialVersionUID = -325216608537127062L;

    // ATTRS
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;

    // COLORS
    private static final String alert_color = "0xf88600";
    private static final String record_color = "0x800080";
    private static final String score_color = "0x0010ff";
    private static final String coins_score_color = "0xf8ed67";
    // OTHER
    private static final int ground_speed = 5;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println(getContent(req, resp));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private String getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return "<!DOCTYPE html>\n"
        		+ "<html> \n" //
                + "<head> \n"
                + "<meta charset=\"utf-8\">\n"+
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n"+
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> \n"+
             "<title>DINO-RUNNER</title> \n" //
               + " <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                + "<link rel='stylesheet' type='text/css' href='css/main1.css'/>\n" //
                
                + "</head> \n" //
                + "<body> \n" //
                + "<nav class=\"navbar navbar-default\">\n"
          		+"<div class=\"container\">\n"+
          			"<div class=\"navbar-header\">\n"+
          			"<a class=\"brand-name navbar-brand\" href=\"#\">Dinno runner</a>\n"+
          		"</div>\n"+
          		"<div class=\"navbar-collapse collapse\"> \n"+
                    "<ul class=\"nav navbar-nav\">\n"+
                      "<li class=\"active\"><a href=\"#\">Home</a></li>\n"+
                      "<li><a href=\"\\scoreboard\">Scoreboard</a></li>\n"+
        			  "<li class=\"pull-right\"><button id=\"fb_login\" class=\"my-facebook-btn btn btn-primary\" href=\"#\">facebook</button></li>\n"+
                    "</ul>\n"+
                  "</div>\n"+
          		"</div>\n"+
          	"</nav>\n"
          	+ "<div class=\"game-container\">\n"+
        	"<canvas width=\"1000\" height=\"600\" style=\"cursor: inherit;\"></canvas>\n"+
        "</div>\n" //
        + "<script type='text/javascript' src='js/jquery-2.2.3.js'></script> \n"
        + "<script src=\" js/bootstrap.min.js \"></script>\n" //
        + "<script src='https://code.createjs.com/soundjs-0.6.2.min.js'></script>\n" //
                + "<script src='js/pixi.js'></script> \n" //
                + "<script type='text/javascript' src='https://rawgit.com/pieroxy/lz-string/master/libs/lz-string.min.js'></script>\n" //
                + "<script type='text/javascript'>" //
                + "var HEIGHT            = " + HEIGHT + ";\n" //
                + "var WIDTH             = " + WIDTH + ";\n" //
                + "var ALERT_COLOR       = " + alert_color + ";\n" //
                + "var RECORD_COLOR      = " + record_color + ";\n" //
                + "var SCORE_COLOR       = " + score_color + ";\n" //
                + "var GROUND_SPEED      = " + ground_speed + ";\n" //
                + "var COINS_SCORE_COLOR = " + coins_score_color + ";\n" //
                + "</script>" //
                + "<script src='js/game.js'></script>\n" //
               + "<script src='js/face1.js'></script>\n" //
         
                + "</body> \n"//
                + "</html> \n";
    }
}
