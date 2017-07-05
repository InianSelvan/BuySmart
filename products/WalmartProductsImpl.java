package com.buysmart.products;

import com.buysmart.business.Store;
import com.buysmart.business.WalmartBO;
import com.buysmart.wallmart.pojo.WalmartPojo;


/**
 * 
 * @author Iniyan
 *		Used by the products factory this is a refined Impl class
 *	of a POJO class
 */
public class WalmartProductsImpl implements Products{
	
	private WalmartPojo walmartPOJO = null;
	
	public WalmartProductsImpl(String productID, Store store)
			 {
		WalmartBO amazonBO = new WalmartBO();
		walmartPOJO = amazonBO.getResponseObject(productID, store);
	}

	@Override
	public String getProductName() {
		return walmartPOJO.getItems()[0].getName();
	}

	@Override
	public String getProductUpcID() {
		return walmartPOJO.getItems()[0].getUpc();
	}

	@Override
	public Object getProductImage() {
		return walmartPOJO.getItems()[0].getMediumImage();
	}

	@Override
	public String getProductDesc() {
		return walmartPOJO.getItems()[0].getShortDescription();
	}

	@Override
	public String getProductPrice() {
		return walmartPOJO.getItems()[0].getSalePrice();
	}

	@Override
	public String getProductRating() {
		return walmartPOJO.getItems()[0].getCustomerRating();
	}

	@Override
	public String getUrl() {
		return walmartPOJO.getItems()[0].getProductUrl();
		
	}

}
