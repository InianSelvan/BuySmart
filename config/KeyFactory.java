package com.buysmart.config;

import com.buysmart.business.ProductsBO;

public class KeyFactory {
	
	private ProductsBO bo;
	
	public KeyFactory(ProductsBO bo) {
		this.bo = bo;
	}
	
	public String[] getApiKeys() {
		
		if(bo.getClass().getName().contains("Bestbuy")) {
			return new BestbuyKeys().getKeys();
		}
		
		if(bo.getClass().getName().contains("Amazon")) {
			return new AmazonKeys().getKeys();
		}
		
		if(bo.getClass().getName().contains("Walmart")) {
			return new WalmartKeys().getKeys();
		}
		
		if(bo.getClass().getName().contains("Ebay")) {
			return new EbayKeys().getKeys();
		}
		
		return null;
	}

}
