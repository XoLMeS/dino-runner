package dino;

import java.io.IOException;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Collection;
>>>>>>> 04fa2bd19f843d7c0b760e7c2a6efd08508087be
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
=======
import com.google.appengine.api.datastore.Cursor;
>>>>>>> 04fa2bd19f843d7c0b760e7c2a6efd08508087be
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
<<<<<<< HEAD
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.repackaged.com.google.gson.Gson;

public class Service extends HttpServlet {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Entity> list = new ArrayList<>();

        String user_name = req.getParameter("user_name");
        String user_link = req.getParameter("user_link");
        String capacity = req.getParameter("capacity");

        if ((user_name != null) && (user_link != null) && capacity.equals("")) {

            Entity user = lookForColisionsInDatabase(user_name, user_link);
            if (user != null) {

                list.add(user);

            }

        } else if ((capacity != null) && !capacity.equals("")) {
            list = getUsers(Integer.valueOf(capacity));
        }

        String json = new Gson().toJson(list);
        if (list.size() == 0) {
            resp.getWriter().write(new Gson().toJson(null));
        } else {
            resp.getWriter().write(json);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        String user_name = req.getParameter("user_name");
        String user_link = req.getParameter("user_link");
        String shadow = req.getParameter("shadow");
        String coins = req.getParameter("coins");
        String score = req.getParameter("score");

        if (((user_name != null) && (user_link != null)) && !user_name.equals("") && !user_link.equals("")) {
            switch (method) {
            case "saveNewUser":
                Entity user = lookForColisionsInDatabase(user_name, user_link);
                if (user == null) {
                    saveNewUser(user_name, user_link);
                }
                break;

            case "update":
                Entity user2 = lookForColisionsInDatabase(user_name, user_link);
                if (user2 != null) {
                    if (!shadow.equals("")) {
                        user2.setProperty("Shadow", shadow);
                    }
                    if (!coins.equals("")) {
                        user2.setProperty("Coins", coins);
                    }
                    if (!score.equals("")) {
                        user2.setProperty("Highscore", score);
                    }
                    datastore.put(user2);
                }
                break;
            default:
                break;
            }
        }
    }

    public Entity lookForColisionsInDatabase(String nickname, String link) {
        Query q = new Query("User");
        q.addSort("Highscore", SortDirection.DESCENDING);
        PreparedQuery pq = datastore.prepare(q);
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);

        int size = results.size();
        for (int i = 0; i < size; i++) {
            if (((results.get(i).getProperty("Name") != null) && results.get(i).getProperty("Name").equals(nickname))
                    && ((results.get(i).getProperty("Link") != null) && results.get(i).getProperty("Link").equals(link)))
                return results.get(i);
        }
        return null;
    }

    private List<Entity> getUsers(int cap) {
        Query q = new Query("User");
        q.addSort("Highscore", SortDirection.DESCENDING);
        PreparedQuery pq = datastore.prepare(q);
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(cap);
        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
        return results;
    }

    private boolean saveNewUser(String user_name, String user_link) {
        Key key = datastore.put(new User(user_name, user_link).getUserEntity());
        if ((key != null) || !key.equals(""))
            return true;
        return false;
    }

=======
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonObject;


public class Service extends HttpServlet {
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	 @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 	resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        List<Entity> list = new ArrayList<>();
	        
	        String user_name = req.getParameter("user_name");
	        String user_link = req.getParameter("user_link");
	        String capacity = req.getParameter("capacity");
	        
	     
	        if(user_name != null && user_link != null && capacity.equals("")){
	        	
	        	Entity user = lookForColisionsInDatabase(user_name,user_link);
	        	if(user != null){
	        	
		        	list.add(user);
		        	
	        	}
	        	
	        }
	        else if(capacity!=null && !capacity.equals("")){
	        	list = getUsers(Integer.valueOf(capacity));
	        }
	       
	        String json = new Gson().toJson(list);
	        if(list.size() == 0){
	        	resp.getWriter().write(new Gson().toJson(null));
	        }
	        else
	        resp.getWriter().write(json);
	    }

	    @Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    	resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
			String method = req.getParameter("method");
			String user_name = req.getParameter("user_name");
	        String user_link = req.getParameter("user_link");
	        String shadow = req.getParameter("shadow");
	        String coins = req.getParameter("coins");
	        String score = req.getParameter("score");
	        
	        if((user_name !=null && user_link != null) 
	        		&& !user_name.equals("") && !user_link.equals("")){
		        switch(method){
		        case "saveNewUser":
		        	Entity user = lookForColisionsInDatabase(user_name, user_link);
		        	if(user==null){
		        		saveNewUser(user_name,user_link);
		        	}
		        	break;
		        	
		        case "update":
		        	Entity user2 = lookForColisionsInDatabase(user_name, user_link);
		        	if(user2!=null){
		        		if(!shadow.equals("")){
		        			user2.setProperty("Shadow", shadow);
		        		}
		        		if(!coins.equals("")){
		        			user2.setProperty("Coins", coins);
		        		}
		        		if(!score.equals("")){
		        			user2.setProperty("Highscore", score);
		        		}
		    	    	datastore.put(user2);
		        	}
		        	break;
		        default:
		        	break;
		        }
	        }
	    }
	    
	    public Entity lookForColisionsInDatabase(String nickname, String link) {
			Query q = new Query("User");
			q.addSort("Highscore", SortDirection.DESCENDING);
			PreparedQuery pq = datastore.prepare(q);
			FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
			QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);

			int size = results.size();
			for (int i = 0; i < size; i++) {
				if ((results.get(i).getProperty("Name") != null
						&& results.get(i).getProperty("Name").equals(nickname)) 
						&& (results.get(i).getProperty("Link") != null
						&& results.get(i).getProperty("Link").equals(link))) {
					return results.get(i);
				}
			}
			return null;
		}
	    
	    private List<Entity> getUsers(int cap){
			Query q = new Query("User");
			q.addSort("Highscore", SortDirection.DESCENDING);
			PreparedQuery pq = datastore.prepare(q);		
			FetchOptions fetchOptions = FetchOptions.Builder.withLimit(cap);
	        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
	        return results;
	    }
	    

	    private boolean saveNewUser(String user_name, String user_link){
	    	Key key = datastore.put(new User(user_name,user_link).getUserEntity());
	    	if(key!=null || !key.equals("")){ 
	    		return true;
	    	}
	    	return false;
	    }
	    
>>>>>>> 04fa2bd19f843d7c0b760e7c2a6efd08508087be
}
