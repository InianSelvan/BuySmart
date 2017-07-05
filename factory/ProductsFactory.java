package com.buysmart.factory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.buysmart.business.Store;
import com.buysmart.products.AmazonProductsImpl;
import com.buysmart.products.BestBuyProductsImpl;
import com.buysmart.products.Products;
import com.buysmart.products.WalmartProductsImpl;
/**
 * 
 * @author Iniyan
 *		A factory method that gives the requested object
 */
public class ProductsFactory {
	
	public Products getProducts(String productID, Store store) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		Products products = null;
		switch(store){
		
		case AMAZON_CA:
			products =   new  AmazonProductsImpl(productID, store);
			break;
		case AMAZON_COM:
			products =   new  AmazonProductsImpl(productID, store);
			break;
		case BESTBUY_CA:
			products =  new  BestBuyProductsImpl(productID, store);
			break;
		case BESTBUY_COM:
			products =  new  BestBuyProductsImpl(productID, store);
			break;
			
		case WALMART_CA:
			products = new WalmartProductsImpl(productID, store);
			break;
			
		case WALMART_COM:
			products = new WalmartProductsImpl(productID, store);
			break;
			
		case EBAY_CA:
			
			break;
		case EBAY_COM:
			
			break;
		default:
			break;			
		}
	
		return products;
	}
	
	
	
}
