package dino;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.appengine.api.datastore.Entity;

public class User {

	
	HashMap<String, String> data;
	String nickname,
	link;

	
	public User(String nickname, String link){
		this.nickname = nickname;
		this.link = link;
	}
	
	public Entity getUserEntity(){
		Entity user = new Entity("User");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		user.setProperty("Name", nickname);
		user.setProperty("Link", link);
		user.setProperty("Highscore", 0);
		user.setProperty("Coins", 0);
		user.setProperty("Shadow", "");
		user.setProperty("date", dateFormat.format(date));
		return user;
	}
	
	
}