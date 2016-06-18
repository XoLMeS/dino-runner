package Practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dropbox.core.DbxException;

import Khrystosov.JavaDropbox;

public class te extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		  resp.setCharacterEncoding("UTF-8");
	//ff("http://distedu.ukma.kiev.ua",req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		
	}
	
	public void showHeaders(String s,HttpServletRequest req, HttpServletResponse resp){
		URL url;
		try{
			url = new URL(s);
			URLConnection conn = url.openConnection();
			for (int i=0;;i++){
				String name = conn.getHeaderFieldKey(i);
				String value = conn.getHeaderField(i);
				if ((name==null)&& (value == null))
					break;
				if (name == null){
					 resp.getWriter().println("Server HTTP version, Response code:");
					 resp.getWriter().println(value);
					 resp.getWriter().print("\n");
				}else{
					 resp.getWriter().println(name + "=" + value);
				}
			}
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showURL(String s,HttpServletRequest req, HttpServletResponse resp){
		URL url;
		try{
			url = new URL(s);
            URLConnection conn = url.openConnection();
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
            	 resp.getWriter().println(inputLine);
            }
            br.close();

            resp.getWriter().println("Done");
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void ff(String url,HttpServletRequest req, HttpServletResponse resp) throws IOException, DbxException {
//JavaDropbox jd = new JavaDropbox();

	//	jd.uploadToDropbox("happy.png", fis)
	//	showURL(url,req,resp);
	//	showHeaders(url,req,resp);
	}
}
