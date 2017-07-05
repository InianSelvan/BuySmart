package com.buysmart.products;

import com.buysmart.api.pojo.BestBuyPOJO;
import com.buysmart.business.BestbuyBO;
import com.buysmart.business.Store;

/**
 * 
 * @author Iniyan
 *		Used by the products factory this is a refined Impl class
 *	of a POJO class
 */
public class BestBuyProductsImpl implements Products {
	
	private BestBuyPOJO bestbuyPOJO= null;
	
	public BestBuyProductsImpl(String productID, Store store){
		BestbuyBO bestbuyBO = new BestbuyBO();
		bestbuyPOJO = bestbuyBO.getResponseObject(productID, store);
	}
	@Override
	public String getProductName() {
		return bestbuyPOJO.getName();
	}

	@Override
	public String getProductUpcID() {
		return bestbuyPOJO.getUpc();
	}

	@Override
	public Object getProductImage() {
		return bestbuyPOJO.getImage();
	}

	@Override
	public String getProductDesc() {
		return bestbuyPOJO.getLongDescription();
	}

	@Override
	public String getProductPrice() {
		return bestbuyPOJO.getRegularPrice();
	}

	@Override
	public String getProductRating() {
		return bestbuyPOJO.getCustomerReviewAverage();
	}
	@Override
	public String getUrl() {
		return bestbuyPOJO.getUrl();
	}
}
