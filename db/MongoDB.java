package com.buysmart.db;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * @author Suriya
 * 
 *         Description: Creates and returns the instance of MongoDB database
 *         connection using singleton pattern.
 * 
 *         Usage: DB db=MongoDB.getDatabase("buysmart");
 */
public class MongoDB {
	private static DB db;

	private MongoDB() {
		new MongoDB();
	}

	/**
	 * @param database
	 * @return connected database instance
	 */
	@SuppressWarnings("deprecation")
	public static DB getDatabase(String database) {
		db = null;
		try {
			MongoClient mongo = new MongoClient("127.10.63.130"  ,/* "localhost" ,*/ 27017);
			db = mongo.getDB(database);
			db.authenticate("admin", "vkXWfL8JbMTU".toCharArray());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return db;
	}

}
