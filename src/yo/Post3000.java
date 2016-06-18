package yo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Post3000 {

	String author,title,text,ip,coord;
	public Post3000(String title, String author, String text,String ip, String coord){
		this.title = title;
		this.author = author;
		this.text = text;
		this.ip = ip;
		this.coord = coord;
	}
	
	public Entity getPostEntity(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Entity post = new Entity("Post3000");
		post.setProperty("Title", title);
		post.setProperty("Author", author);
		post.setProperty("Text", text);
		post.setProperty("IP", ip);
		post.setProperty("Coordinate", coord);
		post.setProperty("date", dateFormat.format(date));
		return post;
	}
}
