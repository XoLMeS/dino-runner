package dino;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dino_Runner_Servlet extends HttpServlet {

    // ATTRS
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;

    // OBJECTS
    private ArrayList<String> objects = new ArrayList<String>();

    // COLORS
    private static final String alert_color = "0xf88600";
    private static final String record_color = "0x800080";
    private static final String score_color = "0x0010ff";
    private static final String coins_score_color = "0xf8ed67";
    // OTHER
    private int id = 1;
    private static final int ground_speed = 5;

    // USER INFO
    private static int conins_cap = 0;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println(getContent(req, resp));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private String getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return "<html> \n" //
                + "<head> \n" //
                + "<title>DINO-RUNNER</title> \n" //
                + "<script type='text/javascript' src='js/jquery-2.2.3.js'></script> \n" //
                + "<script src='https://code.createjs.com/soundjs-0.6.2.min.js'></script>" //
                + "<link rel='stylesheet' type='text/css' href='css/main.css' />" //
                + "<script src='js/pixi.js'></script> \n" //
                + "<script type='text/javascript'>" //
                + "var HEIGHT            = " + HEIGHT + ";\n" //
                + "var WIDTH             = " + WIDTH + ";\n" //
                + "var ALERT_COLOR       = " + alert_color + ";\n" //
                + "var RECORD_COLOR      = " + record_color + ";\n" //
                + "var SCORE_COLOR       = " + score_color + ";\n" //
                + "var GROUND_SPEED      = " + ground_speed + ";\n" //
                + "var COINS_SCORE_COLOR = " + coins_score_color + ";\n" //
                + "</script>" //
                + "<script src='js/game.js'></script>" //
                + "</head> \n" //
                + "<body> \n" //
                + "<div clas='header'>Header</div> \n" //
                + "<div class='local_scores'>Local Scores</div> \n"//
                + "<div class='footer'>Footer</div> \n"//
                + "</body> \n"//
                + "</html> \n";

    }
}
