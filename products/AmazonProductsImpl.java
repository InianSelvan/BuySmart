package com.buysmart.products;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.buysmart.api.pojo.AmazonPOJO;
import com.buysmart.business.AmazonBO;
import com.buysmart.business.Store;

/**
 * 
 * @author Iniyan Used by the products factory this is a refined Impl class of a
 *         POJO class
 */
public class AmazonProductsImpl implements Products {

	private AmazonPOJO amazonPOJO = null;

	public AmazonProductsImpl(String productID, Store store)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		AmazonBO amazonBO = new AmazonBO();
		amazonPOJO = amazonBO.getResponseObject(productID, store);
	}

	@Override
	public String getProductName() {

		return amazonPOJO.getName();
	}

	@Override
	public String getProductUpcID() {

		return amazonPOJO.getUpc();
	}

	@Override
	public Object getProductImage() {

		return amazonPOJO.getImage();
	}

	@Override
	public String getProductDesc() {

		return amazonPOJO.getLongDescription();
	}

	@Override
	public String getProductPrice() {

		return String.valueOf((Double.parseDouble(amazonPOJO.getSalePrice()) / 100));
	}

	@Override
	public String getProductRating() {

		return amazonPOJO.getCustomerReviewAverage();
	}

	@Override
	public String getUrl() {

		return amazonPOJO.getUrl();
	}

}
