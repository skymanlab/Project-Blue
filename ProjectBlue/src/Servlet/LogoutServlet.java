package Servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Enums.PrintType;
import Enums.RequestType;
import HttpNetwork.HttpConnector;
import HttpNetwork.HttpManager;
import HttpRequest.HttpParameterData;
import HttpResponse.ResponseTokenData;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final PrintType PRINT_TYPE = PrintType.ON;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// step 0. html 문서 encoding 방식 설정
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// step 1. session vlaue
		ResponseTokenData responseTokenData = (ResponseTokenData) request.getSession().getAttribute("session_token");
		
		if( responseTokenData != null) {
			printDeveloperMessage("LogoutServlet : session_token :" + responseTokenData.getAccess_token());
			
			//
			HashMap<String, String> setting = new HashMap<String, String>();
			setting.put("Authorization", "Bearer " + responseTokenData.getAccess_token());
			
			// step 2. Request - GET method : Logout + Unlink
			HttpConnector connector_request_1 = new HttpConnector(HttpParameterData.mappingQueryStringLogoutUnlinkReceive(), RequestType.GET, "Json");
//			HttpConnector connector_request_1 = new HttpConnector(HttpParameterData.mappingQueryStringLogoutReceive(), RequestType.POST, "Json", setting);
			HttpManager manager_request_1 = new HttpManager(connector_request_1);
			
			
			// step 3. Response : Logout + Unlink
			String result = manager_request_1.startManager();
			printDeveloperMessage("LogoutServlet : " + result);
			
			// step 4. Session remove
			 request.getSession().removeAttribute("session_token");
			 request.getSession().removeAttribute("session_user_info");
			 
			 // step 5. sendRedirect
			 response.sendRedirect("login.jsp");
				
		} else {
			printDeveloperMessage("LogoutServlet : responseTokenData object is null. session is nothing.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// print developer message
	private void printDeveloperMessage(String message) {
		if (PRINT_TYPE == PRINT_TYPE.ON) {
			System.out.println(message);
		} else {
		}
	}

}
