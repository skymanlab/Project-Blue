package Servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Developer.DeveloperManager;
import Enums.PrintType;
import Enums.RequestType;
import HttpNetwork.HttpConnector;
import HttpNetwork.HttpManager;
import HttpRequest.HttpParameterData;
import HttpResponse.ResponseTokenData;
import Session.SessionManager;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// step 0. html encoding setting / request, response : UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// step 1. session vlaue
		SessionManager sessionManager = (SessionManager) request.getSession().getAttribute("session_manager");

		if (sessionManager != null) {
			DeveloperManager.printDeveloperMessage("LogoutServlet / F / access token :" + sessionManager.getToken().getAccess_token());

			// Logout + Unlink Request ( 카카오 로그인 / REST API / 카카오계정과 함께 로그아웃 )
			// ===============================================================================
			// step 2. Request - setting HashMap to make : Logout + Unlink
			HashMap<String, String> setting = new HashMap<String, String>();
			setting.put("Authorization", "Bearer " + sessionManager.getToken().getAccess_token());

			// step 3. Request - GET method : Logout + Unlink
			HttpConnector connector_request_1 = new HttpConnector(
					HttpParameterData.mappingQueryStringLogoutUnlinkReceive(), RequestType.GET, "Json");
			HttpManager manager_request_1 = new HttpManager(connector_request_1);
			
			// step 4. Response : Logout + Unlink
			String result = manager_request_1.startManager();
			DeveloperManager.printDeveloperMessage("LogoutServlet / E / result : " + result);

			// step 5. Session remove
			request.getSession().removeAttribute("session_manager");

			// step 6. sendRedirect
			response.sendRedirect("login.jsp");
		} else {
			DeveloperManager.printDeveloperMessage("LogoutServlet : responseTokenData object is null. session is nothing.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
