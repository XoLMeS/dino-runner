package Practice;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RespectBinding;

import yo.CustomForm;
import yo.Student;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class PW_3 extends HttpServlet{
	
	private String err_01 = "Invalid format";
	private String err_02 = "Empty field";
	private boolean err01 = false;
	private boolean err02 = false;
	private boolean added = false;

	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private StringBuilder sb = new StringBuilder();
	CustomForm cf = new CustomForm();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		PW_3_Data.page = 0;
		if(err01 == true){
			resp.getWriter().println("<div style=\" color: red;\">"+err_01+"</div>");
		}
		
		if(err02 == true){
			resp.getWriter().println("<div style=\" color: red;\">"+err_02+"</div>");
		}
		
		getForm();
		resp.getWriter().println(cf.getForm());
		err01 = false;
		err02 = false;
		resp.getWriter().println("<div align=\"center\"><a href=\"/PW_3_Data\"</div> Show DataBase</a>");
		if(added==true){resp.getWriter().println("<div align=\"center\" style=\"color: green; font-size: 20pt;\">Added</div>");}
		added = false;
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		String name = req.getParameter("Name");
		String last_Name = req.getParameter("Last_Name");
		String faculty = req.getParameter("Faculty");
		int age = 0;
		if(checkString(req.getParameter("Age"))){
			age = Integer.valueOf(req.getParameter("Age"));
			if(age<=15 | age>=100){
				err01 = true;
				resp.sendRedirect("/PW_3");
			}
		}
		else {
			err01 = true;
			resp.sendRedirect("/PW_3");
		}
		int course = 0;
		if(checkString(req.getParameter("Course"))){
			course = Integer.valueOf(req.getParameter("Course"));
			if(course<=0 | course>=7){
				err01 = true;
				resp.sendRedirect("/PW_3");
			}
			
		}
		else {
			err01 = true;
			resp.sendRedirect("/PW_3");
		}
		if(name == "" | last_Name == "" | faculty == ""){
			err02 = true;
			resp.sendRedirect("/PW_3");
		}
		if(name.length()>20 | last_Name.length()>25 | faculty.length()>20){
			err01 = true;
			resp.sendRedirect("/PW_3");
		}
		if(err01==false && err02==false){
			Student st = new Student(name,last_Name,faculty,age,course);
			datastore.put(st.getStudentEntity());
			added = true;
			resp.sendRedirect("/PW_3");
			
		}
		
		
	
	}
	
	private void getForm(){
		
		cf.createForm("center");
		cf.addField("Name");
		cf.addField("Last_Name");
		cf.addField("Age");
		cf.addField("Course");
		cf.addField("Faculty");
	   
	}
	
	private boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	
	
}
