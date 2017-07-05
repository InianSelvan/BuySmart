package com.buysmart.business;

import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.buysmart.api.pojo.BestBuyPOJO;
/**
 * 
 * @author Iniyan
 * 
 *	This object is to perform all the business operations 
 * by making API calls
 */
public class BestbuyBO extends ToUPC implements ProductsBO {

	private final String BASE_URL = "http://api.bestbuy.com/v1/";
	private String values = "sku,upc,class,subclass,department,name,"
			+ "shortDescription,longDescription,source,manufacturer," + "regularPrice,salePrice,url,onlineAvailability,"
			+ "inStoreAvailability,addToCartUrl,customerReviewCount,"
			+ "customerReviewAverage,freeShipping,shippingCost," + "image,thumbnailImage";

	/**
	 * Returns the client Object which can be used to hit the target URL
	 * 
	 * @return Client for building the URL
	 */
	public Client getClient() {

		return ClientBuilder.newClient();
	}

	/**
	 * Constructs the web target filling with all the required values for the
	 * request.
	 * 
	 * @param type
	 * @param productId
	 * @return The target URL
	 * @throws Exception 
	 */
	public WebTarget getTarget(String type, String productId, String showValues) throws Exception {
		WebTarget baseTarget = getClient().target(BASE_URL);
		WebTarget targetType = baseTarget.path(type);
		WebTarget toJson = null;

		if (productId != null) {
			WebTarget targetproductId = targetType.path(productId);
			toJson = targetproductId.queryParam("format", "json");
		} else {
			toJson = targetType.queryParam("format", "json");
		}

		WebTarget constructQuery = toJson.queryParam("show", showValues);
		WebTarget constructQueryKey = constructQuery.queryParam("apiKey", IterateKeys.getKey(this));
		return constructQueryKey;
	}

	/**
	 * Constructs the BestBuyPOJO class based on the productID,
	 * 
	 * @param productID
	 * @return BestBuysPOJO with all the attributes of the product
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BestBuyPOJO getResponseObject(String productID, Store store) {

		String bestBuyString = null;
		BestBuyPOJO bestBuyPojo = null;

		try {
			bestBuyString = getTarget("products(UPC=" + toUPC(productID, store) + ")", null, values)
					.request(MediaType.APPLICATION_JSON).get(String.class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = new ObjectMapper().readTree(new StringReader(bestBuyString));
			JsonNode innerNode = rootNode.get("products");

			bestBuyPojo = mapper.readValue(innerNode.get(0), BestBuyPOJO.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			
			return null;
		}
		return bestBuyPojo;
	}
	
	public static void main(String[] args) throws Exception{
		BestbuyBO bo = null;	
				  bo = new BestbuyBO();
		System.out.println(IterateKeys.getKey(bo));
	}

}
