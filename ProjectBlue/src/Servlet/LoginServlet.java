package Servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Developer.DeveloperManager;
import Enums.PrintType;
import Enums.RequestType;
import HttpNetwork.HttpConnector;
import HttpNetwork.HttpManager;
import HttpRequest.HttpParameterData;
import HttpResponse.ResponseTokenData;
import HttpResponse.ResponseUserInfoData;
import Session.SessionManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// step 0. html encoding setting / request, response : UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// step 1. Query String parsing - Response
		String code = request.getParameter("code");
		String error_description = request.getParameter("error_description");
		String error = request.getParameter("error");

		// step 2. session check : SessionManager object
		if (request.getSession().getAttribute("session_manager") == null) {
			DeveloperManager.printDeveloperMessage("LoginServlet / F / session not exist");

			// Access Token Request ( 카카오 로그인 / REST API / 인증 코드 받기 )
			// ===============================================================================
			// step 3. Request - Post Method : Access Token
			HttpConnector connector_request_1 = new HttpConnector(
					HttpParameterData.mappingQueryStringAccessTokenReceive(code), RequestType.POST, "Json");
			HttpManager manager_request_1 = new HttpManager(connector_request_1);

			// step 4. Json Object Receive - Response : Access Token
			String access_token_data = manager_request_1.startManager();

			// step 5. Json Object parsing : Access Token
			Gson gson = new Gson();
			ResponseTokenData responseTokenData = gson.fromJson(access_token_data, ResponseTokenData.class);
			responseTokenData.printParameter();

			// step 6. session - ResponseTokenData object : SessionManager set
			SessionManager sessionManager = new SessionManager();
			sessionManager.setToken(responseTokenData);

			// User Info Request ( 카카오 로그인 / REST API / 사용자 관리 )
			// ===============================================================================
			// step 7. Request - setting HashMap to make : User Info
			HashMap<String, String> setting = new HashMap<String, String>();
			setting.put("Authorization", "Bearer " + responseTokenData.getAccess_token());
			setting.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

			// step 8. Request - GET of POST method :User Info
			HttpConnector connector_request_2 = new HttpConnector(HttpParameterData.mappingQueryStringUserInfoReceive(),
					RequestType.GET, "Json", setting);
			HttpManager manager_request_2 = new HttpManager(connector_request_2);

			// step 9. Json Object Receive - Response : User Info
			String user_info_data = manager_request_2.startManager();

			// step 10. Json Object parsing : User Info
			ResponseUserInfoData responseUserInfoData = gson.fromJson(user_info_data, ResponseUserInfoData.class);
			responseUserInfoData.printParameter();

			// step 11. session - ResponseUserInfoData object : SessionManager set
			sessionManager.setUserInfo(responseUserInfoData);

			// step 12. session : request add
			request.getSession().setAttribute("session_manager", sessionManager);

			// step 13. page move
			// ===============================================================================
			response.sendRedirect("index.jsp");
			
		} else {
			DeveloperManager.printDeveloperMessage("LoginServlet / F / session exist");

			// step 3. get session
			SessionManager sessionManager = (SessionManager) request.getSession().getAttribute("session_manager");
			DeveloperManager.printDeveloperMessage("LoginServlet / E / access token : " + sessionManager.getToken().getAccess_token());
			
			// step 4. page move
			// ===============================================================================
			response.sendRedirect("index.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
