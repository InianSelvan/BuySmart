package com.buysmart.products;


import com.buysmart.api.pojo.EbayPOJO;
import com.buysmart.business.EbayBO;
import com.buysmart.business.Store;

/**
 * 
 * @author Iniyan
 *		Used by the products factory this is a refined Impl class
 *	of a POJO class
 */
public class EbayProductsImpl implements Products{
	
	private EbayPOJO ebayPOJO = null;
	
	public EbayProductsImpl(String productID, Store store)
			 {
		EbayBO ebayBO = new EbayBO();
		ebayPOJO = ebayBO.getResponseObject(productID, store);
	}

	@Override
	public String getProductName() {
		
		return ebayPOJO.getItem().getTitle();
	}

	@Override
	public String getProductUpcID() {
		
		return  ebayPOJO.getItem().getItemID();
	}

	@Override
	public Object getProductImage() {
		
		return ebayPOJO.getItem().getPictureURL();
	}

	@Override
	public String getProductDesc() {
		
		return ebayPOJO.getItem().getItemID();
	}

	@Override
	public String getProductPrice() {
		
		return String.valueOf(ebayPOJO.getItem().getCurrentPrice().getValue());
	}

	@Override
	public String getProductRating() {
		return String.valueOf(ebayPOJO.getItem().getSeller().getFeedbackScore());
	}

	@Override
	public String getUrl() {
		
		return ebayPOJO.getItem().getViewItemURLForNaturalSearch();
	}

}
