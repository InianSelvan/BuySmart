package com.buysmart.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buys.auth.crypt;

import com.buysmart.dao.ProductsDao;
import com.buysmart.daoImpl.ProductsDaoImpl;
import com.github.scribejava.core.model.Token;



/**
 * Servlet implementation class FrontController
 * 
 * Single handler for all kinds of requests coming to the application
 */


public class loginValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		if(request.getParameter("oauth_verifier") != null){
			String verifier = request.getParameter("oauth_verifier");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Products");
			requestDispatcher.forward(request, response);
		}

		ProductsDao obj = new ProductsDaoImpl();
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		boolean validator = false;
		try {
			crypt imp = new crypt();
			passWord = imp.encrypt(passWord);
			request.setAttribute("password", passWord);
			validator = obj.findUser(userName, passWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( validator)
		{   
			request.setAttribute("username", userName);
			request.removeAttribute("password");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Products");
			requestDispatcher.forward(request, response);
		}
		else
		{
//			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login");
//			requestDispatcher.forward(request, response);
			response.getWriter().write("<h2>" + "InvaliD Request" + "</h2>");
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);

	}
	
	

}
