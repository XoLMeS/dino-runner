package Khrystosov;

import java.util.ArrayList;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.SortDirection;

public class BD_API {

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public BD_API() {

	}

	public Entity getFileWhereFleID(int file_id) {

		Query q = new Query("File");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);

		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		String startCursor = null;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int size = results.size();
		for (int i = 0; i < size; i++) {

			if (file_id == Integer.valueOf(results.get(i).getProperty("FileID")
					.toString())) {
				return results.get(i);
			}
		}

		return null;

	}

	public ArrayList<Entity> getFilesWhereFleID(int start, int end) {
		ArrayList<Entity> array = new ArrayList<Entity>();
		Query q = new Query("File");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);

		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		String startCursor = null;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int size = results.size();
		for (int i = 0; i < size; i++) {

			if (Integer.valueOf(results.get(i).getProperty("FileID")
					.toString())>=start && Integer.valueOf(results.get(i).getProperty("FileID")
							.toString())<=end) {
				array.add(results.get(i));
			}
		}

		return array;

	}
	
	public String getLinkWhereFleID(int file_id) {

		Query q = new Query("File");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);

		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		String startCursor = null;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int size = results.size();
		for (int i = 0; i < size; i++) {

			if (file_id == Integer.valueOf(results.get(i).getProperty("FileID")
					.toString())) {
				return results.get(i).getProperty("URL").toString();
			}
		}

		return null;

	}

	public ArrayList<String> getLinksWhereFleID(int start, int end) {
		ArrayList<String> array = new ArrayList<String>();
		Query q = new Query("File");
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);

		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		String startCursor = null;
		if (startCursor != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int size = results.size();
		for (int i = 0; i < size; i++) {

			if (Integer.valueOf(results.get(i).getProperty("FileID")
					.toString())>=start && Integer.valueOf(results.get(i).getProperty("FileID")
							.toString())<=end) {
				array.add(results.get(i).getProperty("URL").toString());
			}
		}

		return array;

	}

}
