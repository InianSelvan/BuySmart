package com.buysmart.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.buysmart.api.pojo.AmazonPOJO;

/**
 * 
 * @author Iniyan
 * 
 *	This object is to perform all the business operations 
 * by making API calls
 */
public class AmazonBO extends ToUPC implements ProductsBO{
	
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String REQUEST_URI = "/onca/xml";
	private static final String REQUEST_METHOD = "GET";
	private String BASE_URL= "webservices.amazon"; 
	private final static String ASSOCIATE_TAG = "buysmart02a-20";
	private final static String awsAccessKeyId = "AKIAJDLV5AJNXMOQYOZA";
	private String awsSecretKey = "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";
	private SecretKeySpec secretKeySpec = null;
	private Mac mac = null;
	private Map<String, String> paras = null;
	
	public AmazonBO() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		byte[] secretyKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
	
		secretKeySpec = new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
		mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		mac.init(secretKeySpec);
	 }
	
	public static void main(String[] args) throws ClientProtocolException, IOException{
		AmazonBO bo = null;
	
			try {
				bo = new AmazonBO();
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			}
			
			
			
			AmazonPOJO pojo = bo.getResponseObject("B00I15SB16", Store.AMAZON_COM);
		System.out.println(pojo.getCustomerReviewAverage());
	}
	
	/**
	 * 
	 * @param productID
	 * @param store
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getCustomerReviews(String productID, Store store) throws ClientProtocolException, IOException{
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = null;
		if(store.equals(Store.AMAZON_COM)){
			request = new HttpGet(
				"http://www.amazon.com/gp/customer-reviews/widgets/average-customer-review/popover/ref=dpx_acr_pop_?contextId=dpx&asin="
						+ productID);
		}else if(store.equals(Store.AMAZON_CA)){
			request = new HttpGet(
					"http://www.amazon.ca/gp/customer-reviews/widgets/average-customer-review/popover/ref=dpx_acr_pop_?contextId=dpx&asin="
							+ productID);	
		}

		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String result = "";
		String line = "";
		while ((line = rd.readLine()) != null) {
			if (line.contains("out of 5 stars")) {
				result = line;
				break;
			}
		}
		return result;
	}
	
	/**
	 * This method returns Amazon POJO based on the ASIN ID and store name.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AmazonPOJO getResponseObject(String productID, Store store)  {
		
		AmazonPOJO amazon = new AmazonPOJO();
		
		
			Map<String, String> setParams = new HashMap<String, String>();
			
			setParams.put("ResponseGroup", "ItemAttributes,Offers,Large");			
			try {
				if(!toUPC(productID, store).equals("NOUPC")){
					setParams.put("SearchIndex", "All");
					setParams.put("IdType",AmazonID.UPC.name());
					setParams.put("ItemId",toUPC(productID, store));
				}else{
					setParams.put("IdType", AmazonID.ASIN.name());
					setParams.put("ItemId", productID);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String AmazonResponse = null;
			try {
				AmazonResponse = customResponse(setParams, store);
				System.out.println(AmazonResponse);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
				return null;
			}
			
			//Construct the xml doc to parse			
			DocumentBuilder db = null;
			try {
				db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				
				e.printStackTrace();
				return null;
			}
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(AmazonResponse));
		    Document doc = null;
			try {
				doc = db.parse(is);
			} catch (SAXException e) {				
				e.printStackTrace();
				return null;
			} catch (IOException e) {				
				e.printStackTrace();
				return null;
			}
		    
		    //Constructing the object Amazon POJO	    
		    //Price
		    NodeList amount = doc.getElementsByTagName("Amount");
		    amazon.setSalePrice(amount.item(0).getFirstChild().getTextContent());
		    
		    //UPC
		    NodeList upc = doc.getElementsByTagName("UPC");
		    amazon.setUpc(upc.item(0).getFirstChild().getTextContent());
		    
		    //Brand 
		    NodeList brand = doc.getElementsByTagName("Brand");
		    amazon.setManufacturer("brand:" +brand.item(0).getFirstChild().getTextContent());
		    
		    //Name
		    NodeList title = doc.getElementsByTagName("Title");
		    amazon.setName(title.item(0).getFirstChild().getTextContent());
		    
		    //LargeImage
		    NodeList LargeImage = doc.getElementsByTagName("LargeImage");
		    amazon.setImage(LargeImage.item(0).getFirstChild().getTextContent());
		    
		    //URL
		    NodeList url = doc.getElementsByTagName("DetailPageURL");
		    amazon.setUrl(url.item(0).getFirstChild().getTextContent());
		    
		    //Customer Rating
		    try {
				amazon.setCustomerReviewAverage(getCustomerReviews(productID, store));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    	
		return amazon;
	}
	

	public enum AmazonID{
		ASIN, UPC, EAN, ISBN
	}
	
	/**
	 * Using the IDtype and ID number it returns XML response
	 * 
	 * @param IdType
	 * 		UPC or ASIN
	 * @param ItemId
	 * 		ID in the form of string
	 * @return
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@Deprecated	
	public String getResponse(AmazonID IdType, String ItemId, Store store) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		AmazonBO sign = new AmazonBO();
		Map<String, String> paras = new TreeMap<String, String>();
		paras.put("Service", "AWSECommerceService");
		paras.put("Operation", "ItemLookup");
		paras.put("AssociateTag", ASSOCIATE_TAG);
		paras.put("ResponseGroup", "Small");
		paras.put("ResponseGroup", "ItemAttributes");
		paras.put("ItemId",ItemId);
		paras.put("IdType",IdType.name());
		
		//constructs the url based on the parameters
		String url = sign.sign(paras, store);
		WebTarget baseTarget = sign.getClient().target(url);
		String response = baseTarget.request(MediaType.APPLICATION_XML).get(String.class);
		
		return response;
	}
	

	/**
	 * This method constructs custom parameter based on the hash map keys and values, and
	 * return response string
	 * 
	 * @param setParams
	 * @return
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String customResponse(Map<String, String> setParams, Store store) 
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		AmazonBO sign = new AmazonBO();
		paras = new TreeMap<String, String>();
		paras.put("Service", "AWSECommerceService");
		paras.put("Operation", "ItemLookup");
		paras.put("AssociateTag", ASSOCIATE_TAG);
				
		for (Map.Entry<String, String> entry : setParams.entrySet()) {
			paras.put(entry.getKey(), entry.getValue());
		}
		
		//constructs the url based on the parameters
		String url = sign.sign(paras, store);
		
		WebTarget baseTarget = sign.getClient().target(url);
		String response = baseTarget.request(MediaType.APPLICATION_XML).get(String.class);
				
		return response;	
	}
	
	/**
	 * Return the base target
	 * @param paras
	 * @return
	 */
	public WebTarget getTarget(Map<String, String> paras, Store store){
		String url = sign(paras, store);
		WebTarget baseTarget = getClient().target(url);
		return baseTarget;
	}
	
	/**
	 * Returns the client Object which can be used to hit the target URL
	 * @return
	 * 		Client for building the URL
	 */		
	public Client getClient(){
		
		return ClientBuilder.newClient();	
	}
	
	
	/**
	 * This method signs and returns url with signature
	 * @param params
	 * @return
	 * 
	 * 	
	 */
	 public String sign(Map<String, String> params, Store store) {
		 params.put("AWSAccessKeyId", awsAccessKeyId);
		 params.put("Timestamp", timestamp());
		 SortedMap<String, String> sortedParamMap =
				 new TreeMap<String, String>(params);
		 String canonicalQS = canonicalize(sortedParamMap);
		 
		 if(store.equals(Store.AMAZON_COM)) {
			 BASE_URL = BASE_URL.replace(BASE_URL, BASE_URL+".com");
		} else if(store.equals(Store.AMAZON_CA)) {
			BASE_URL = BASE_URL.replace(BASE_URL, BASE_URL+".ca");
		}
		 
		 String toSign =
				 		   REQUEST_METHOD + "\n"
						 + BASE_URL+"\n"
						 + REQUEST_URI + "\n"
						 + canonicalQS;
		 String hmac = hmac(toSign);
		 String sig = percentEncodeRfc3986(hmac);
		 String url = "http://" + BASE_URL + REQUEST_URI + "?" +
				 canonicalQS + "&Signature=" + sig;
	 return url;
	 }
	 
	 /**
	  * This is used to hash the url string that will be used in 
	  * the final url for parsing.
	  * @param stringToSign
	  * @return
	  */
	 private String hmac(String stringToSign) {
		 String signature = null;
		 byte[] data;
		 byte[] rawHmac;
		 try {
			 data = stringToSign.getBytes(UTF8_CHARSET);
			 rawHmac = mac.doFinal(data);
			 Base64 encoder = new Base64();
			 signature = new String(encoder.encode(rawHmac));
		 } catch (UnsupportedEncodingException e) {
			 throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
		 }
		 return signature;
	 }
	 
	 /**
	  * This is used to attach the time stamp along with the url
	  * @return
	  */
	 private String timestamp() {
		 String timestamp = null;
		 Calendar cal = Calendar.getInstance();
		 DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		 dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		 timestamp = dfm.format(cal.getTime());
	 return timestamp;
	 }
	 
	 /**
	  * This method is to order the parameters.
	  * @param sortedParamMap
	  * @return
	  */
	 private String canonicalize(SortedMap<String, String> sortedParamMap){
		 if (sortedParamMap.isEmpty()) {
			 return "";
		 }
		 StringBuffer buffer = new StringBuffer();
		 Iterator<Map.Entry<String, String>> iter =
				 sortedParamMap.entrySet().iterator();
		 while (iter.hasNext()) {
			 Map.Entry<String, String> kvpair = iter.next();
			 buffer.append(percentEncodeRfc3986(kvpair.getKey()));
			 buffer.append("=");
			 buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			 if (iter.hasNext()) {
				 buffer.append("&");
			 }
		 }
		 String canonical = buffer.toString();
	  return canonical;
	  }
	 
	 /**
	  * This method is to encode the URL to rfc 3986 format 
	  * @param s
	  * @return
	  */
	  private String percentEncodeRfc3986(String s) {
		  String out;
	  try {
		  out = URLEncoder.encode(s, UTF8_CHARSET)
				  .replace("+", "%20")
				  .replace("*", "%2A")
				  .replace("%7E", "~");
	  } catch (UnsupportedEncodingException e) {
		  out = s;
	  }
	  	return out;
	  }
}
