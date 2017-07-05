package com.buysmart.business;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.buysmart.api.pojo.EbayPOJO;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
/**
 * 
 * @author Iniyan
 * 
 *	This object is to perform all the business operations 
 * by making API calls
 */
public class EbayBO extends ToUPC implements ProductsBO{
	
	private final String BASE_URL = "http://open.api.ebay.com/";
	private static OAuth10aService service;

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public EbayPOJO getResponseObject(String productID, Store store) {
		String ebayString = null;
		EbayPOJO ebayPojo = null;
		@SuppressWarnings("unused")
		String storeID = null;
		ObjectMapper mapper = new ObjectMapper();
		
		setParas("callname", "GetSingleItem");		
		setParas("responseencoding", "JSON");
		setParas("appid", "buysmart-BuySmart-PRD-0d2ce5828-a111b233");
		setParas("version","515");
		setParas("ItemID",productID);
		setParas("siteid",storeID = store.name().contains(store.EBAY_COM.name()) ? "0" : "2");
		setParas("IncludeSelector","UPC,Details");
		
		try {
			ebayString = getTarget(BASE_URL, "shopping", null)
					.request(MediaType.APPLICATION_JSON).get(String.class);
			
			Gson gson = new Gson();
			ebayPojo =gson.fromJson(ebayString, EbayPOJO.class); 
			ebayPojo = mapper.readValue(ebayString, EbayPOJO.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;		
		}
		
		return ebayPojo;
	}

	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	
public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException{
		
//		String bestBuyString = null;
//		BestBuyPOJO bestBuyPojo = null;
//				String url = "http://svcs.ebay.com/services/search/FindingService/v1?\n" + 
//						"   OPERATION-NAME=findItemsByProduct&\n" + 
//						"   SERVICE-VERSION=1.0.0&\n" + 
//						"   SECURITY-APPNAME=buysmart-BuySmart-SBX-7d2ce5828-d0faa5cb&\n" + 
//						"   RESPONSE-DATA-FORMAT=XML&\n" + 
//						"   REST-PAYLOAD&\n" + 
//						"   paginationInput.entriesPerPage=2&\n" + 
//						"   productId.@type=ReferenceID&\n" + 
//						"   productId=53039031";
				
//				String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByProduct&SERVICE-VERSION=1.12.0&SECURITY-APPNAME=buysmart-BuySmart-PRD-0d2ce5828-a111b233&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=2&productId.@type=UPC&productId=0400037780126&siteid=24342342";
							
//			String url = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=buysmart-BuySmart-PRD-0d2ce5828-a111b233&siteid=2&version=515&ItemID=361496027872&IncludeSelector=UPC,Details";
//		    "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=buysmart-BuySmart-PRD-0d2ce5828-a111b233&siteid=0&version=515&ItemID=361496027872&IncludeSelector=Details"
//				http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByProduct&SERVICE-VERSION=1.12.0&SECURITY-APPNAME=buysmart-BuySmart-PRD-0d2ce5828-a111b233&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD&paginationInput.entriesPerPage=2&productId.@type=ReferenceID&productId=53039031	
//				
//				String BASE_URL = "http://svcs.ebay.com/services/search/FindingService/";
//				String ver ="v1";
//				
				
//				//WebTarget baseTarget = ClientBuilder.newClient().target(BASE_URL);
//				WebTarget path = baseTarget.path(ver);
//				WebTarget oper = path.queryParam("OPERATION-NAME", "getMostWatchedItems");
//				WebTarget version = oper.queryParam("SERVICE-VERSION", "1.0.0");
//				WebTarget sec = version.queryParam("CONSUMER-ID", "buysmart-BuySmart-SBX-7d2ce5828-d0faa5cb");
//				WebTarget res = sec.queryParam("RESPONSE-DATA-FORMAT", "json");
//				WebTarget page = res.queryParam("categoryId", "267");
//				WebTarget protype = page.queryParam("productId.@type", "ReferenceID");
//				WebTarget proid = protype.queryParam("productId", "53039031");
//				WebTarget respay = proid.queryParam("REST-PAYLOAD", "");
	
//				WebTarget baseTarget = ClientBuilder.newClient().target(url);
//				String response = baseTarget.request(MediaType.APPLICATION_XML).get(String.class);
//				System.out.println(response);
//				
//				ObjectMapper mapper = new ObjectMapper();
//				EbayPOJO ebayPojo = mapper.readValue(response, EbayPOJO.class);
//				EbayBO ebaybo = new EbayBO();
//				ebaybo.getResponseObject("361496027872", Store.EBAY_CA);
//				System.out.println(mapper.writeValueAsString(ebaybo.getResponseObject("361496027872", Store.EBAY_CA)));
//		bestBuyString =baseTarget
//				.request(MediaType.APPLICATION_JSON)
//				.get(String.class);
				
//				service = new ServiceBuilder()
//                        .apiKey("G2LkNSHJ3TadNkzXieQStJFMh")
//                        .apiSecret("BN8xVKBsLreJDnjWDbS0H4fpOkPfOv8OyqsrJrcMyd6nO3pL2l")  
//                        .callback("http://buysmart-buysmart.rhcloud.com/loginValidator")
//                        .build(TwitterApi.instance());
//				
////				Token requestToken = new Token(request.getParameter("oauth_token"),request.getParameter("oauth_verifier"));
////				Verifier verifier = new Verifier(request.getParameter("oauth_verifier"));
//				 final Scanner in = new Scanner(System.in);
//
//			        System.out.println("=== Twitter's OAuth Workflow ===");
//			        System.out.println();
//
//			        // Obtain the Request Token
//			        System.out.println("Fetching the Request Token...");
//			        final OAuth1RequestToken requestToken = service.getRequestToken();
//			        System.out.println("Got the Request Token!");
//			        System.out.println();
//	
//			        System.out.println("Now go and authorize ScribeJava here:");
//			        System.out.println(service.getAuthorizationUrl(requestToken));
//			        System.out.println("And paste the verifier here");
//			        System.out.print(">>");
//			        final String oauthVerifier = in.nextLine();
//			        System.out.println();
//
//			        // Trade the Request Token and Verfier for the Access Token
//			        System.out.println("Trading the Request Token for an Access Token...");
//			        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauthVerifier);
//			        System.out.println("Got the Access Token!");
//			        System.out.println("(if your curious it looks like this: " + accessToken
//			                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
//			        System.out.println();
//
//			        // Now let's go and ask for a protected resource!
//			        System.out.println("Now we're going to access a protected resource...");
//			        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
//			        service.signRequest(accessToken, request);
//			        final Response response = request.send();
//			        System.out.println("Got it! Lets see what we found...");
//			        System.out.println();
//			        System.out.println(response.getBody());
//
//			        System.out.println();
//			        System.out.println("That's it man! Go and build something awesome with ScribeJava! :)");
	}


}
