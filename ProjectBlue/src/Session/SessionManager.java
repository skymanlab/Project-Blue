package Session;

import javax.servlet.http.HttpServletRequest;

import Developer.DeveloperManager;
import HttpResponse.ResponseTokenData;
import HttpResponse.ResponseUserInfoData;

public class SessionManager {

	/*
	 * ====================================================================================
	 * SessionManager에는 로그인을 했을 때 받은 객체를 각각 저장하고, 
	 * request 객체를 통해서 session에 SessionManager 객체를 저장한다.
	 * ====================================================================================
	 */
	// variable
	private ResponseTokenData token;
	private ResponseUserInfoData userInfo;

	// Constructor
	public SessionManager() {
		this.token = null;
		this.userInfo = null;
	}

	// method : getter, setter
	public ResponseTokenData getToken() {
		return token;
	}

	public void setToken(ResponseTokenData token) {
		this.token = token;
	}

	public ResponseUserInfoData getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ResponseUserInfoData userInfo) {
		this.userInfo = userInfo;
	}

	// method : session check
	public void checkSession() {
		
	}

}
