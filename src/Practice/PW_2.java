package Practice;

import java.io.IOException;

import javax.servlet.http.*;

public class PW_2 extends HttpServlet{
	private StringBuilder sb = new StringBuilder(); 
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.getWriter().println(getForm());
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		String str = req.getParameter("String");
		String code = codeString(str);
		resp.getWriter().println(code);
	}
	
	private String getForm(){
		sb.delete(0, sb.length());
		sb.append("<form method=\"post\" align = \"center\">");
		sb.append("Enter String to code it");
		sb.append("<br><label>String<input type=\"text\" name=\"String\"");
		sb.append("></label>");
		sb.append("<br><button>Code</button></form>");
		return sb.toString();
	}
	
	private String codeString(String str){
		String code = "";
		
		for(int i = 0; i < str.length(); i++){
			char ch = 0;
			if(str.charAt(i)>=60 & str.charAt(i)<=77){ch = (char) (str.charAt(i)+13);}
			if(str.charAt(i)>=97 & str.charAt(i)<=109){ch = (char) (str.charAt(i)+13);}
			if(str.charAt(i)>77 & str.charAt(i)<=90){ch = (char) (60-91+str.charAt(i)+13);}
			if(str.charAt(i)>109 & str.charAt(i)<=122){ch = (char) (97-123+str.charAt(i)+13);}
			code = code + ch;
		}
		return code;
	}
}
