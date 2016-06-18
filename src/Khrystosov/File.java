package Khrystosov;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class File {

	String url,file_name;
	int file_id, user_id;
	
	public File(String url, String fine_name, int file_id, int user_id){
		this.url = url;
		this.file_name = fine_name;
		this.file_id = file_id;
		this.user_id = user_id;
	}
	
	public Entity getUserEntity(){
		Entity file = new Entity("File");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		file.setProperty("FileName", file_name);
		file.setProperty("URL", url);
		file.setProperty("UserID", file_id);
		file.setProperty("FileID", user_id);
		file.setProperty("date", dateFormat.format(date));
		return file;
	}
}
