package yo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Post {

	String author,title,text;
	public Post(String title, String author, String text){
		this.title = title;
		this.author = author;
		this.text = text;
	}
	
	public Entity getPostEntity(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Entity student = new Entity("Post");
		student.setProperty("Title", title);
		student.setProperty("Author", author);
		student.setProperty("Text", text);
		student.setProperty("date", dateFormat.format(date));
		return student;
	}
}
