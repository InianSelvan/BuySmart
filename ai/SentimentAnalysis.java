package com.buysmart.ai;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import com.buysmart.api.pojo.AIResponsePOJO;
import com.buysmart.api.pojo.AIResponsePOJO.Taxonomy;
import com.buysmart.business.ToUPC;

/**
 * AI based sentiment analysis 
 * 
 * 
 * @author SELIN01
 *
 */

public class SentimentAnalysis extends ToUPC {
	
	private String url;
	private List<String> urls;
	private final String BASE_URL = "http://access.alchemyapi.com/calls/url/";
	private final String API_KEY = "fde75784e9a3d48b1cfde425d1d06290eb19211e";
	
	public SentimentAnalysis(String url){
		this.url = url;
	}
	
	public SentimentAnalysis(List<String> urls){
		this.urls = urls;
	}
	
	
	/**
	 * Based on the URL passed it analysis the text on the pages and classifies and updates
	 * the template object based on the json
	 * 
	 * @return
	 */
	public AIResponsePOJO getAiResponse(){
	
				
			String aiResponse = null;
			ObjectMapper mapper = new ObjectMapper();
			setParas("apikey", API_KEY);
			setParas("outputMode", "json");
			setParas("url", url);
			setParas("extract", "taxonomy");

			AIResponsePOJO aiResponsePojo = null;
			if (url != null) {
				try {
					aiResponse = getTarget(BASE_URL, "URLGetCombinedData", null).request(MediaType.APPLICATION_JSON)
							.get(String.class);

					aiResponsePojo   = mapper.readValue(aiResponse, AIResponsePOJO.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		return aiResponsePojo;
	}
	
	/**
	 * Based on the URL's it constructs all the pojo's
	 * @return
	 */
	public List<AIResponsePOJO> getSentiments(){
		
		AIResponsePOJO aiResponsePojo = null;
		List<AIResponsePOJO> aiResponsePojos = new ArrayList<AIResponsePOJO>();
		String aiResponse = null;
		
		for(String urlValue : urls){
			ObjectMapper mapper = new ObjectMapper();
			setParas("apikey", API_KEY);
			setParas("outputMode", "json");
			setParas("url", urlValue);
			setParas("extract", "taxonomy");

			if (urlValue != null) {
				try {
					aiResponse = getTarget(BASE_URL, "URLGetCombinedData", null).request(MediaType.APPLICATION_JSON)
						.get(String.class);

					aiResponsePojo = mapper.readValue(aiResponse, AIResponsePOJO.class);
					aiResponsePojos.add(aiResponsePojo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	return aiResponsePojos;
		
	}
	
	/**
	 * Returns average good score of the product 
	 * 
	 * @param pojo
	 * @return
	 */
	public double getTotalGoodScore(AIResponsePOJO pojo){
		Double average = 0.0;
		int count = 1;
		for(Taxonomy tax : pojo.getTaxonomy()){
			if(Double.parseDouble(tax.getScore()) >= 0.6){
				average = average +  Double.parseDouble(tax.getScore()) / count ;
			}
			count++;
		}
		return round(average * 100, 2);
	}
	
	/**
	 * Returns average bad score for the product
	 * 
	 * @param pojo
	 * @return
	 */
	public double getTotalBadScore(AIResponsePOJO pojo){
		Double average = 0.0;
		int count = 1;
		for(Taxonomy tax : pojo.getTaxonomy()){
			if(Double.parseDouble(tax.getScore()) <= 0.6){
				average = average +  Double.parseDouble(tax.getScore()) / count ;
			}
			count++;
		}
		return round(average * 100, 2);
	}
	
	
	/**
	 * Returns list of labels 
	 * @param list
	 * @return
	 */
	public LinkedHashSet<String> getTaxanomy(List<AIResponsePOJO> list){
		
		LinkedHashSet<String> labels = new LinkedHashSet<String>();
		for(AIResponsePOJO pojo : list){
			for(Taxonomy taxonomy : pojo.getTaxonomy()){
				labels.add(taxonomy.getLabel().substring(1, taxonomy.getLabel().length()));
			}
		}
		
		return labels;
	}
	
	
	public List<String> getTaxanomyForUrl(AIResponsePOJO pojo){

		ArrayList<String> scores = new ArrayList<String>();
		try{
			int c = 0;
		for(String label : getTaxanomy(getSentiments())){
			int count = 0;
			for(Taxonomy taxonomy : pojo.getTaxonomy()){
				System.out.println(label);
				System.out.println(taxonomy.getLabel());
				System.out.println("");
				count++;
				if(taxonomy.getLabel().contains(label)){
					scores.add(taxonomy.getScore().substring(0, 4));
					if(count == pojo.getTaxonomy().length ){
						
						count = -1;
					}
					break;
				}
			}
			if(count == pojo.getTaxonomy().length ){
				scores.add("NA");
				System.out.println(c++);
			}
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return scores;
	}
	
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static void main(String[] args) {
		
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("https://www.amazon.ca/Fitbit-Wireless-Activity-Tracker-Blue/dp/B0098U75I8/ref=lp_10263464011_1_8?s=hpc&ie=UTF8&qid=1461104007&sr=1-8");
		urls.add("http://www.bestbuy.ca/en-CA/product/fitbit-fitbit-flex-fitness-tracker-black-fb401bk-can/10252519.aspx?path=f34a28047bd9e56bab400b0da425f07den02");
		SentimentAnalysis sss = new SentimentAnalysis("https://www.amazon.ca/Fitbit-Wireless-Activity-Tracker-Blue/dp/B0098U75I8/ref=lp_10263464011_1_8?s=hpc&ie=UTF8&qid=1461104007&sr=1-8");
//		
		SentimentAnalysis ss = new SentimentAnalysis(urls);
		System.out.println(sss.getTaxanomy(ss.getSentiments()).size());
		System.out.println(ss.getTaxanomyForUrl(sss.getAiResponse()));
		


	}
	
	
}
