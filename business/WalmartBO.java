package com.buysmart.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import com.buysmart.factory.ProductsFactory;
import com.buysmart.products.Products;
import com.buysmart.wallmart.pojo.WalmartPojo;

public class WalmartBO extends ToUPC implements ProductsBO  {

	private final String BASE_URL = "http://api.walmartlabs.com/v1/";
	private static Map<String, String> paras = new HashMap<String, String>() ;
	
	

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
	 * 		It defines before query and after / e.g http://<base url>/Item? here item is the type.
	 * @param productId
	 * @return The target URL
	 * @throws Exception 
	 */
	public WebTarget getTarget(String type, String productId) throws Exception {
		WebTarget baseTarget = getClient().target(BASE_URL);
		WebTarget targetType = baseTarget.path(type);
		WebTarget targetproductId = null;
		WebTarget target = null;
			
		if(productId !=null) {
			targetproductId = targetType.path(productId);
			target = constructedParams(getParas(), targetproductId);
		}else {
			target = constructedParams(getParas(), targetType);
		}
		
		return target;
	}
	
	private WebTarget constructedParams(Map<String, String> params, WebTarget base){
		WebTarget webParams = null;
		int count = 0 ;
		for(Map.Entry<String, String> entry: params.entrySet()) {
			if(count ==0) {
				webParams = base.queryParam(entry.getKey(), entry.getValue());
			}else {
				webParams = webParams.queryParam(entry.getKey(), entry.getValue());
			}
			count++;
		}
		return webParams;
	}

	/**
	 * Constructs the BestBuyPOJO class based on the productID,
	 * 
	 * @param productID
	 * @return BestBuysPOJO with all the attributes of the product
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WalmartPojo getResponseObject(String productID, Store store) {

		String walmartString = null;
		WalmartPojo walmartPojo = null;
		try {
			setParas("apiKey", IterateKeys.getKey(this));
			setParas("format", "json");
			setParas("upc", toUPC(productID, store));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			walmartString = getTarget("items", null)
					.request(MediaType.APPLICATION_JSON).get(String.class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			walmartPojo = mapper.readValue(walmartString, WalmartPojo.class);
			System.out.println(walmartString);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			
			return null;
		}
		return walmartPojo;
	}
	
	public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		
				ProductsBO p = new WalmartBO();
				ProductsFactory pf = new ProductsFactory();
				p.getResponseObject("48695113", Store.WALMART_COM);
				//Products prodcuts = pf.getProducts("48695113", Store.WALMART_COM);	
				ProductsFactory  pfs = new ProductsFactory();
				Products prodcuts =pfs.getProducts("630509391325", Store.WALMART_COM);
				//		BestBuyPOJO pojo = bo.getResponseObject("4331863", Store.BESTBUY_COM);getTarget("items", "12417832")
				//System.out.println(prodcuts.get);
				
	
	
	}
	
	public static Map<String, String> getParas() {
		return paras;
	}

	public static void setParas(String key, String value) {
		paras.put(key, value);
	}

}
