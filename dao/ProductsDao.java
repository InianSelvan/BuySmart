package com.buysmart.dao;

import java.util.List;

import org.json.JSONException;

import com.buysmart.daoImpl.ProductsPOJO;
import com.buysmart.daoImpl.UserHistoryPOJO;

/**
 * @author Suriya
 *
 *         DAO interface for products
 */
public interface ProductsDao {

	public List<ProductsPOJO> getAllProducts(String user);

	public List<String> getAllProductIds() throws JSONException;

	public List<String> getAllUsers(String productID);

	public void addProducts(UserHistoryPOJO userHistory);

	public void updateProducts(String user, String productID, String store);

	public void updateProductPrice(String productID, String store, String price);

	public void updateProductWishList(String productID, String store, String user, boolean ind);

	public void deleteProducts(String user, String productID);

	public void registerUser(String username, String password, String email, String age);

	public boolean findUser(String user, String password);
	
	public List<String> getAllUsers();

}
