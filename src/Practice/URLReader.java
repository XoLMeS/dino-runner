package Practice;



import java.net.*;
import java.io.*;

public class URLReader {

	public void showHeaders(String s){
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
					System.out.println("Server HTTP version, Response code:");
					System.out.println(value);
					System.out.print("\n");
				}else{
					System.out.println(name + "=" + value);
				}
			}
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showURL(String s){
		URL url;
		try{
			url = new URL(s);
            URLConnection conn = url.openConnection();
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
            	System.out.println(inputLine);
            }
            br.close();

            System.out.println("Done");
		}catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public URLReader(String url) {

		
		showURL(url);
		showHeaders(url);
	}

}
