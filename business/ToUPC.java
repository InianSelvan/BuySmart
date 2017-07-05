package com.buysmart.business;

import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.buysmart.business.AmazonBO.AmazonID;

/**
 * 	
 * @author Iniyan
 *	The abstract class for the BO class 
 */
public abstract class ToUPC {
	private static Map<String, String> paras = new HashMap<String, String>();
	
	public static String toUPC(String productID, Store store) throws Exception {
		
		String upcString = null;
		switch(store){
		
			case BESTBUY_COM: 
				if (productID.length() != 12){
					BestbuyBO bo = new BestbuyBO();
					String upcStringJson = bo.getTarget("products(SKU="+productID+")", null, "upc")
							.request(MediaType.APPLICATION_JSON)
							.get(String.class);
					try {
						
						JsonNode rootNode=null;
						rootNode = new ObjectMapper().readTree(new StringReader(upcStringJson));
						JsonNode innerNode = rootNode.get("products");  
						JsonNode upc = innerNode.get(0).get("upc");
						upcString = upc.asText();
											
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e){
						return "NOUPC";
					}
					
				}else if(productID.length() == 12){
					upcString = productID;
				}
				break;
				
			case BESTBUY_CA:
				upcString = null;
				break;
				
			case AMAZON_CA:
				NodeList nodes = null;
				if(productID.length() != 12){
					try {					
						AmazonBO amazonBo = new AmazonBO();
						Map<String, String> setParams = new HashMap<String, String>();
					
						setParams.put("ResponseGroup", "ItemAttributes");
						//setParams.put("SearchIndex", "All");
						setParams.put("IdType",AmazonID.ASIN.name());
						setParams.put("ItemId",productID);
					
						String responseCa = amazonBo.customResponse(setParams, store);
						DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();				
						InputSource is = new InputSource();
						is.setCharacterStream(new StringReader(responseCa));				
						Document doc = db.parse(is);
						nodes = doc.getElementsByTagName(AmazonID.UPC.name());
						upcString = nodes.item(0).getFirstChild().getTextContent();
					
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidKeyException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (NullPointerException e){
						return "NOUPC";
					}
					
				}else if(productID.length() == 12){
					upcString = productID;
				}
				break;
				
			case AMAZON_COM:
				
				DocumentBuilder dbcom;
				NodeList nodesCom = null;
				if(productID.length() != 12){
				try {
					AmazonBO amazonBo = new AmazonBO();
					Map<String, String> setParams = new HashMap<String, String>();
					
					setParams.put("ResponseGroup", "ItemAttributes");
					setParams.put("IdType",AmazonID.ASIN.name());
					setParams.put("ItemId",productID);
					
					String response = amazonBo.customResponse(setParams, store);
					dbcom = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				
					InputSource is = new InputSource();
					is.setCharacterStream(new StringReader(response));
				
					Document doc = dbcom.parse(is);
					nodesCom = doc.getElementsByTagName("UPC");
					upcString = nodesCom.item(0).getFirstChild().getTextContent();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {					
					e.printStackTrace();
				} catch (NullPointerException e){
					return "NOUPC";
				}				
				}else if((productID.length() == 12)){
					upcString = productID;
				}
				break;
				
			case EBAY_CA:
				upcString = null;
				break;
				
			case EBAY_COM:
				upcString = null;
				break;
				
			case WALMART_CA:
				upcString = null;
				break;
			case WALMART_COM:
				if (productID.length() != 12){
					WalmartBO bo = new WalmartBO();
					System.out.println(bo.getTarget("items/"+productID, null));
					String response = bo.getTarget("items/"+productID, null)
							.request(MediaType.APPLICATION_JSON)
							.get(String.class);
					try {
						
						JsonNode rootNode=null;
						rootNode = new ObjectMapper().readTree(new StringReader(response));
						JsonNode upc = rootNode.get("upc");  
						upcString = upc.asText();
											
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e){
						return "NOUPC";
					}
					
				}else if(productID.length() == 12){
					upcString = productID;
				}
				break;
				

			default:
				upcString = null;
		}
	return upcString;
		
	}
	
	
	
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
