package com.buysmart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buys.auth.crypt;
import com.buysmart.dao.ProductsDao;
import com.buysmart.daoImpl.ProductsDaoImpl;

/**
 * Servlet implementation class UserRegistration
 */
@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductsDao obj = new ProductsDaoImpl();
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String email = request.getParameter("email");
		String age = request.getParameter("age");
		try {
			crypt imp = new crypt();
			passWord = imp.encrypt(passWord);
			obj.registerUser(userName, passWord, email, age);
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			response.getWriter().write("<h2>" + "InvaliD Request" + "</h2>");
		}

	}

}
