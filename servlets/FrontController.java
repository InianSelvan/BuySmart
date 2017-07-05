package com.buysmart.servlets;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.buys.auth.crypt;
import com.buysmart.business.Store;
import com.buysmart.dao.ProductsDao;
import com.buysmart.daoImpl.ProductsDaoImpl;
import com.buysmart.daoImpl.ProductsPOJO;
import com.buysmart.daoImpl.UserHistoryPOJO;
import com.buysmart.factory.ProductsFactory;
import com.buysmart.products.Products;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * Servlet implementation class FrontController
 * 
 * Single handler for all kinds of requests coming to the application
 */

@WebServlet(description = "Single Handler for all kinds of requests", urlPatterns = { "/Products", "/updateDB",
		"/compareGenerator", "/login", "/loginValidator", "/OAuthTwitter" })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isAuthenticUser() {
		// here we have to write Authentication logic
		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isAuthenticUser()) {

			boolean otherControllers = false;
			String controllerName = request.getParameter("controllerName");
			// response.getWriter().write("<h2>" + controllerName + "</h2>");
			try {
				if (!controllerName.equals(null)) {
					otherControllers = true;
				}
			} catch (NullPointerException nullPointer) {
				HttpSession session = request.getSession();
				session.invalidate();
				// request.setAttribute("loginerror", "normal");
				response.setStatus(200);
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			}
			if (otherControllers) {
				if (controllerName.equals("productsDisplay")) {
					
					if (request.getParameter("productsInfo") != null) {
						String productsInfo = request.getParameter("productsInfo");
						ObjectMapper mapper = new ObjectMapper();
						UserHistoryPOJO userHistory = mapper.readValue(productsInfo, UserHistoryPOJO.class);
						userHistory.setUser("suriya");
						ProductsFactory productsFactory = new ProductsFactory();
						ProductsDaoImpl daoImpl = new ProductsDaoImpl();
						ArrayList<ProductsPOJO> products = userHistory.getProducts();
						ArrayList<ProductsPOJO> dbProducts = new ArrayList<ProductsPOJO>();
						for (ProductsPOJO dbProduct : products) {
							try {
								Products APIProd = productsFactory.getProducts(dbProduct.getProductID(),
										Store.valueOf(dbProduct.getStore()));
								dbProduct.setName(APIProd.getProductName());
								dbProduct.setImageURL(String.valueOf(APIProd.getProductImage()));
								dbProduct.setPrice(Double.valueOf(APIProd.getProductPrice()));
								dbProducts.add(dbProduct);
							} catch (InvalidKeyException e) {
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								e.printStackTrace();
							} catch (Exception e) {
								continue;
							}
						}
						userHistory.setProducts(dbProducts);
						daoImpl.addProducts(userHistory);
						request.setAttribute("productsInfo", productsInfo);
						RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Products.jsp");
						requestDispatcher.forward(request, response);
					} else {
						request.setAttribute("productsInfo", null);
						RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Products.jsp");
						requestDispatcher.forward(request, response);
					}
//					 response.getWriter().write("<h2>" +  jobj.toString() + "</h2>" );
					
				} else if (controllerName.equals("updateDB")) {
					String productDetails = request.getParameter("productDetails");
					String productTokens[] = productDetails.split(":");
					String productID = productTokens[0];
					String store = productTokens[1];
					boolean ind;
					if (productTokens[2].equals("true"))
						ind = true;
					else
						ind = false;
					ProductsDao productsDaoImpl = new ProductsDaoImpl();
					productsDaoImpl.updateProductWishList(productID, store, "suriya", ind);
					response.setStatus(200);
				} else if (controllerName.equals("loginValidator")) {

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
					if (validator) {
						request.setAttribute("username", userName);
						request.removeAttribute("password");
						request.setAttribute("productsInfo", null);
						RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Products.jsp");
						requestDispatcher.forward(request, response);
					} else {

						response.setStatus(300);
						RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
						requestDispatcher.forward(request, response);

						// response.getWriter().write("<h2>" + "LoginValidator"
						// + "</h2>");
					}
				}

				else if (controllerName.equals("compareGenerator")) {
					String upc = request.getParameter("upc");
					String features = request.getParameter("features");
					String websites = request.getParameter("websites");
					String store = request.getParameter("store");
					request.setAttribute("upc", upc);
					request.setAttribute("features", features);
					request.setAttribute("websites", websites);
					request.setAttribute("store", store);

					RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Comparison.jsp");
					requestDispatcher.forward(request, response);
				}

				else if (controllerName.equals("OAuthTwitter")) {

					OAuth10aService service = new ServiceBuilder().apiKey("G2LkNSHJ3TadNkzXieQStJFMh")
							.apiSecret("BN8xVKBsLreJDnjWDbS0H4fpOkPfOv8OyqsrJrcMyd6nO3pL2l")
							.build(TwitterApi.instance());
					final OAuth1RequestToken requestToken = service.getRequestToken();
					String url = service.getAuthorizationUrl(requestToken);

				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/*********************** For testing **********************/
	/*
	 * public static void main(String[] args) { ProductsFactory productsFactory
	 * = new ProductsFactory(); try { Products bp =
	 * productsFactory.getProducts("017817652490", Store.BESTBUY_COM);
	 * System.out.println(bp.getProductUpcID()); } catch (InvalidKeyException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (NoSuchAlgorithmException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

}
