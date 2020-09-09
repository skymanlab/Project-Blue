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
		ResponseTokenData responseTokenData = (ResponseTokenData) request.getSession().getAttribute("session_token");

		if (responseTokenData != null) {
			DeveloperManager.printDeveloperMessage("LogoutServlet : session_token :" + responseTokenData.getAccess_token());

			// Logout + Unlink Request ( 카카오 로그인 / REST API / 카카오계정과 함께 로그아웃 )
			// ===============================================================================
			// step 2. Request - setting HashMap to make : Logout + Unlink
			HashMap<String, String> setting = new HashMap<String, String>();
			setting.put("Authorization", "Bearer " + responseTokenData.getAccess_token());

			// step 3. Request - GET method : Logout + Unlink
			HttpConnector connector_request_1 = new HttpConnector(
					HttpParameterData.mappingQueryStringLogoutUnlinkReceive(), RequestType.GET, "Json");
			HttpManager manager_request_1 = new HttpManager(connector_request_1);
			
			// step 4. Response : Logout + Unlink
			String result = manager_request_1.startManager();
			DeveloperManager.printDeveloperMessage("LogoutServlet : " + result);

			// step 5. Session remove
			request.getSession().removeAttribute("session_token");
			request.getSession().removeAttribute("session_user_info");

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
