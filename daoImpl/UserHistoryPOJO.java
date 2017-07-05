package com.buysmart.daoImpl;

import java.util.ArrayList;

/**
 * @author Suriya
 *
 *         POJO for the user history.
 */
public class UserHistoryPOJO {
	private String user;
	private ArrayList<ProductsPOJO> products;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<ProductsPOJO> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductsPOJO> products) {
		this.products = products;
	}

}
