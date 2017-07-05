package com.buysmart.daoImpl;

/**
 * @author Suriya
 *
 *         POJO for the Products
 */
public class ProductsPOJO {
	private String productID;
	private String store;
	private String name;
	private String imageURL;
	private double price;
	private boolean wishList;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isWishList() {
		return wishList;
	}

	public void setWishList(boolean wishList) {
		this.wishList = wishList;
	}
}
