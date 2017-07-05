package com.buysmart.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.buysmart.dao.ProductsDao;
import com.buysmart.daoImpl.ProductsDaoImpl;


/**
 * Servlet implementation class FrontController
 * 
 * Single handler for all kinds of requests coming to the application
 */


public class updateDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String productDetails = request.getParameter("productDetails");
		String productTokens [] = productDetails.split(":");
		String productID = productTokens[0];
		String store = productTokens[1];
		boolean ind; 
		if (productTokens[2].equals("true"))
		 ind = true;
		else
		 ind = false;
		ProductsDao productsDaoImpl = new ProductsDaoImpl();
		productsDaoImpl.updateProductWishList(productID, store, "suriya",ind);
		response.setStatus(200);

	}
	
	

}
