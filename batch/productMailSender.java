package com.buysmart.batch;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;

import com.buysmart.business.Store;
import com.buysmart.dao.ProductsDao;
import com.buysmart.daoImpl.ProductsDaoImpl;
import com.buysmart.factory.ProductsFactory;
import com.buysmart.products.Products;

public class productMailSender {

	public static void main(String[] args) throws JSONException, AddressException, MessagingException {
		// TODO Auto-generated method stub
      System.out.println("hello");

      ProductsDao pDao = new ProductsDaoImpl();
      List<String> prod = pDao.getAllProductIds();
	   
      ProductsFactory productsFactory = new ProductsFactory();
      Products bp = null;
      for (String productPOJO : prod) {
    	  String tokens [] = productPOJO.split(":");
    	  System.out.println(tokens[0] + tokens[1] + tokens[2]);
    	  List<String> mailList = pDao.getAllUsers(tokens[1]);
      try {
         if ( tokens[0].equals("BESTBUY_COM"))
		    bp = productsFactory.getProducts(tokens[1], Store.BESTBUY_COM);
         else if (tokens[0].equals("BESTBUY_CA"))
        	bp = productsFactory.getProducts(tokens[1], Store.BESTBUY_CA);
         else if (tokens[0].equals("AMAZON_CA"))
        	bp = productsFactory.getProducts(tokens[1], Store.AMAZON_CA);
         else if (tokens[0].equals("AMAZON_COM"))
         	bp = productsFactory.getProducts(tokens[1], Store.AMAZON_COM);
         else if (tokens[0].equals("EBAY_CA"))
          	bp = productsFactory.getProducts(tokens[1], Store.EBAY_CA);
         else if (tokens[0].equals("EBAY_COM"))
           	bp = productsFactory.getProducts(tokens[1], Store.EBAY_COM);
         else if (tokens[0].equals("WALMART_COM"))
            	bp = productsFactory.getProducts(tokens[1], Store.WALMART_COM);
         else if (tokens[0].equals("WALMART_CA"))
         	bp = productsFactory.getProducts(tokens[1], Store.WALMART_CA);
         
         pDao.getAllUsers(tokens[1]);
         Double newPrice = 0.0,oldPrice = 0.0;
         if (bp.getProductPrice().equals(null) || bp.getProductPrice().trim().equals("") )
        	 newPrice = 0.0;
         else
        	 newPrice = Double.parseDouble(bp.getProductPrice());
         if (tokens[2].equals(null) || tokens[2].trim().equals("") )
        	 oldPrice = 0.0;
         else
        	 oldPrice = Double.parseDouble(tokens[2]);
		//pDao.updateProductPrice("suriya", tokens[1], tokens[0] ,bp.getProductPrice());
		if ( newPrice < oldPrice )
		{
			System.out.println("Exception & Sending Mail");
			SendMailSSL.generateAndSendEmail(mailList);
		}
		else
			System.out.println("Not an Exception");
		pDao.updateProductPrice(tokens[1],tokens[0] ,"10000");
		//System.out.println(bp.getProductPrice());
	} catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      }
	}

}
