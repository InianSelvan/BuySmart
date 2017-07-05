package com.buysmart.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 * 
 * Single handler for all kinds of requests coming to the application
 */

public class compareGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String upc = request.getParameter("upc");
		String features = request.getParameter("features");
		String websites = request.getParameter("websites");
		String store = request.getParameter("store");
		request.setAttribute("productID", "1234");
		request.setAttribute("store", "nothing");
		request.setAttribute("upc", upc);
		request.setAttribute("features", features);
		request.setAttribute("websites", websites);
		request.setAttribute("store", store);
		
		
		RequestDispatcher requestDispatcher = getServletContext()
				.getRequestDispatcher("/CompareProductsView.jsp");
		requestDispatcher.forward(request, response);
//		response.getWriter().write("<h2>" + "  " + store  + "</h2>");
//		response.setStatus(200);


	}
	
}
