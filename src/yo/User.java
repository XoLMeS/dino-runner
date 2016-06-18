package yo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class User {

	String nickname,
	password;

	
	public User(String nickname, String password){
		this.nickname = nickname;
		this.password = password;
		
	}
	
	public Entity getUserEntity(){
		Entity user = new Entity("User");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		user.setProperty("Name", nickname);
		user.setProperty("Password", password);
		user.setProperty("date", dateFormat.format(date));
		return user;
	}
}