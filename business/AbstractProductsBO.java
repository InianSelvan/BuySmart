package com.buysmart.business;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * 
 * @author Iniyan
 *	
 *
 */
public abstract class AbstractProductsBO {
	
	
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
	 * @param productId
	 * @return The target URL
	 * @throws Exception 
	 */
	public WebTarget getTarget(String baseUrl, String type, String productId) throws Exception {
		WebTarget baseTarget = getClient().target(baseUrl);
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
	
	public static Map<String, String> getParas() {
		return paras;
	}

	public static void setParas(String key, String value) {
		paras.put(key, value);
	}


}
