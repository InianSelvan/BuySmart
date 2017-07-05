package com.buysmart.daoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.buysmart.dao.ProductsDao;
import com.buysmart.db.MongoDB;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author Suriya
 *
 *
 */
public class ProductsDaoImpl implements ProductsDao {

	private DB db = MongoDB.getDatabase("buysmart");

	@Override
	public List<ProductsPOJO> getAllProducts(String user) {
		List<ProductsPOJO> productList = new ArrayList<ProductsPOJO>();
		int counter = 0;
		try {
			BasicDBObject query = new BasicDBObject().append("user", user);
			BasicDBObject fields = new BasicDBObject().append("_id", 0);
			DBCursor cursor = db.getCollection("producthistory").find(query, fields);
			ObjectMapper mapper = new ObjectMapper();
			while (cursor.hasNext() && counter < 10) {
				DBObject obj = cursor.next();
				// System.out.println(obj);
				UserHistoryPOJO userHistoryPOJO = mapper.convertValue(obj, UserHistoryPOJO.class);
				for (ProductsPOJO product : userHistoryPOJO.getProducts()) {
					productList.add(product);
					counter++;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public void addProducts(UserHistoryPOJO userHistory) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// ArrayList<ProductsPOJO> productList = userHistory.getProducts();
			// ProductsFactory productsFactory = new ProductsFactory();
			// for (ProductsPOJO productPOJO : productList) {
			// Products product =
			// productsFactory.getProducts(productPOJO.getProductID(),
			// Store.valueOf(productPOJO.getStore()));
			// productPOJO.setImageURL(String.valueOf(product.getProductImage()));
			// productPOJO.setPrice(product.getProductPrice());
			// }
			// userHistory.setProducts(productList);
			userHistory = removeDupProducts(userHistory);
			if (!userHistory.getProducts().isEmpty()) {
				String json = mapper.writeValueAsString(userHistory);
				// System.out.println(json);
				DBObject dbObject = (DBObject) JSON.parse(json);
				db.getCollection("producthistory").insert(dbObject);
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UserHistoryPOJO removeDupProducts(UserHistoryPOJO userHistory) {
		String user = userHistory.getUser();
		ArrayList<ProductsPOJO> products = userHistory.getProducts();
		long exists;
		BasicDBObject andQuery = null;
		ProductsPOJO product = null;
		ProductsPOJO removeProduct = null;
		Iterator<ProductsPOJO> iter = userHistory.getProducts().iterator();
		while (iter.hasNext()) {
			product = iter.next();
			andQuery = new BasicDBObject().append("user", user).append("products.productID", product.getProductID());
			exists = db.getCollection("producthistory").count(andQuery);
			if (exists > 0)
				removeProduct = product;
		}
		if (removeProduct != null)
			products.remove(removeProduct);
		userHistory.setProducts(products);
		return userHistory;
	}

	// will implement later if necessary
	@Override
	public void updateProducts(String User, String ProductID, String Store) {
		// TODO Auto-generated method stub

	}

	// will implement later if necessary
	@Override
	public void deleteProducts(String User, String ProductID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProductWishList(String productID, String store, String user, boolean ind) {
		// TODO Auto-generated method stub
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("products.productID", productID));
		obj.add(new BasicDBObject("products.store", store));
		obj.add(new BasicDBObject("user", user));
		andQuery.put("$and", obj);

		DBObject updateMatchingElem = new BasicDBObject("$set", new BasicDBObject("products.$.wishList", ind));

		try {
			db.getCollection("producthistory").update(andQuery, updateMatchingElem);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateProductPrice(String productID, String store, String price) {
		// TODO Auto-generated method stub
		DBObject queryForElem = new BasicDBObject("products",
				new BasicDBObject("$elemMatch", new BasicDBObject("productID", productID)));
		DBObject updateMatchingElem = new BasicDBObject("$set", new BasicDBObject("products.$.price", price));
		try {
			db.getCollection("producthistory").update(queryForElem, updateMatchingElem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getAllProductIds() throws JSONException {
		// TODO Auto-generated method stub
		List<String> productList = new ArrayList<String>();
		DBCollection col = db.getCollection("producthistory");
		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		dbObjIdMap.put("productID", "$products.productID");
		dbObjIdMap.put("store", "$products.store");
		dbObjIdMap.put("price", "$products.price");
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
		List<DBObject> lo = new ArrayList<DBObject>();
		lo.add(new BasicDBObject("$group", groupFields));

		AggregationOutput agout = col.aggregate(lo);

		Iterator<DBObject> results = agout.results().iterator();

		while (results.hasNext()) {
			DBObject obj = results.next();
			JSONObject jobj = new JSONObject(obj.toString());
			System.out.println(jobj);
			JSONArray arr = jobj.getJSONObject("_id").getJSONArray("store");
			JSONArray prodarr = jobj.getJSONObject("_id").getJSONArray("productID");
			JSONArray pricearr = jobj.getJSONObject("_id").getJSONArray("price");
			for (int i = 0; i < arr.length(); i++) {
				productList.add(
						arr.get(i).toString() + ":" + prodarr.get(i).toString() + ":" + pricearr.get(i).toString());
			}
		}
		return productList;
	}

	@Override
	public List<String> getAllUsers(String productID) {
		// TODO Auto-generated method stub
		List<String> usersMailList = new ArrayList<String>();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("products.productID", productID);
		DBCursor cursor = db.getCollection("producthistory").find(whereQuery);
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			try {
				JSONObject jobj = new JSONObject(obj.toString());
				BasicDBObject whereQueryMail = new BasicDBObject();
				whereQueryMail.put("username", jobj.get("user"));
				DBCursor cursorSub = db.getCollection("user").find(whereQueryMail);
				while (cursorSub.hasNext()) {
					DBObject objSub = cursorSub.next();
					JSONObject jobjSub = new JSONObject(objSub.toString());
					usersMailList.add(jobjSub.getJSONObject("details").get("email").toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return usersMailList;
	}

	@Override
	public boolean findUser(String user, String password) {
		// TODO Auto-generated method stub
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", user);
		DBCursor cursor = db.getCollection("user").find(whereQuery);
		if (cursor.hasNext()) {
			DBObject objSub = cursor.next();
			String pass = null;
			try {
				JSONObject jobjSub = new JSONObject(objSub.toString());
				pass = jobjSub.getJSONObject("details").get("password").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pass.equals(password))
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public void registerUser(String username, String password, String email, String age) {
		ObjectMapper mapper = new ObjectMapper();
		UserPOJO user = new UserPOJO();
		ArrayList<UserDetailsPOJO> details = new ArrayList<UserDetailsPOJO>();
		UserDetailsPOJO detail = new UserDetailsPOJO();
		detail.setAge(age);
		detail.setEmail(email);
		detail.setPassword(password);
		details.add(detail);
		user.setUsername(username);
		user.setDetails(details);
		if (!user.getDetails().isEmpty()) {
			String json = null;
			try {
				json = mapper.writeValueAsString(user);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// System.out.println(json);
			DBObject dbObject = (DBObject) JSON.parse(json);
			db.getCollection("user").insert(dbObject);
		}
	}

	public static void main(String[] args) {
		// ProductsDaoImpl productsDaoImpl = new ProductsDaoImpl();

		// ProductsFactory productsFactory = new ProductsFactory();
		// List<ProductsPOJO> getAllProducts =
		// productsDaoImpl.getAllProducts("suriya");
		// for (ProductsPOJO productPOJO : getAllProducts) {
		// Products product;
		// try {
		// product = productsFactory.getProducts(productPOJO.getProductID(),
		// Store.valueOf(productPOJO.getStore()));
		// } catch (Exception e) {
		// continue;
		// }
		// System.out.println(product.getProductName());
		// }

		// productsDaoImpl.updateProductWishList("3953166", "BESTBUY_COM",
		// "suriya", false);

		// Remove Duplicates
		// UserHistoryPOJO userHistory = new UserHistoryPOJO();
		// userHistory.setUser("suriya");
		// ArrayList<ProductsPOJO> prods = new ArrayList<ProductsPOJO>();
		// ProductsPOJO prod = new ProductsPOJO();
		// prod.setProductID("8390036");
		// prod.setStore("BESTBUY_COM");
		// prods.add(prod);
		// userHistory.setProducts(prods);
		// userHistory = productsDaoImpl.removeDupProducts(userHistory);
		// System.out.println(userHistory.getProducts().size());
		ProductsDaoImpl impl = new ProductsDaoImpl();
		// impl.registerUser("suriyaaa", "1234", "abcd", "23");
		impl.getAllProducts("suriya");

	}

	@Override
	public List<String> getAllUsers() {
		// TODO Auto-generated method stub
		DBCursor cursorSub = db.getCollection("user").find();
		List<String> users = new ArrayList<String>();
		while (cursorSub.hasNext()) {
			DBObject objSub = cursorSub.next();
			JSONObject jobjSub;
			try {
				jobjSub = new JSONObject(objSub.toString());
				users.add(jobjSub.get("username").toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return users;
	}

}
