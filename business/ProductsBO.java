package com.buysmart.business;


/**
 * 
 * @author Iniyan
 *Interface to all the BO classes.
 */
public interface ProductsBO {
	public <T> T getResponseObject(String productID, Store store);

}
