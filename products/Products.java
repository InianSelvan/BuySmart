package com.buysmart.products;

/**
 * 
 * @author Iniyan
 *	This is a generic interface that can be used for all the 
 *	Commerce API calls
 */
public interface Products {

	public String getProductName();
	
	public String getProductUpcID();
	
	public Object getProductImage();
	
	public String getProductDesc();
	
	public String getProductPrice();
	
	public String getProductRating();
	
	public String getUrl();
	
}
