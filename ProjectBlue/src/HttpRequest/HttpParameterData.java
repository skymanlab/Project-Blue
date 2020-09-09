package HttpRequest;

import Developer.DeveloperManager;
import Enums.PrintType;

public class HttpParameterData {
	/*
	 * ====================================================================================
	 * Kakao API를 사용할 때, url 에 실어보 낼 Query String 만들고 관리하는 클래스이다.
	 * ====================================================================================
	 */

	/*
	 * ==================== 1. 인증 코드 받기  ====================
	 * Request Parameter	
	 * 1. client_id
	 * 2. redirect_uri
	 * 3. resonse_type
	 * 4. state
	 */
	public static final String AUTHORIZE_HOST = "https://kauth.kakao.com/";
	public static final String CLIENT_ID = "20ddb7c5e99abf058668abe5798816db";
	public static final String REDIRECT_URI = "http://localhost:8080/ProjectBlue/LoginServlet";
	public static final String RESPONSE_TYPE = "code";
	public static final String STATE = "";

	// authorized code 
	public static String mappingQueryStringAuthoCodeReceive() {
		/*
		 * Kakao REST API 
		 * - Query String to make : authorize code
		 * - Method : GET 
		 * - HOST : https://kauth.kakao.com/oauth/authorize?
		 * - Parameter 
		 * 		1. client_id / String /
		 * 		2. redirect_uri / String / 
		 * 		3. response_type / String / 
		 * 		4. state / String /
		 */
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(AUTHORIZE_HOST);
		// get ++
		domain.append("oauth/authorize?");
		// get ++ 1. client_id
		domain.append("client_id=");
		domain.append(CLIENT_ID);
		// get ++ 2. redirect_uri
		domain.append("&redirect_uri=");
		domain.append(REDIRECT_URI);
		// get ++ 3. response_type
		domain.append("&response_type=");
		domain.append("code");

		return domain.toString();
	}

	/*
	 * ==================== 2. 사용자 토큰 받기  ====================
	 * Request Parameter
	 * 1. grant_type
	 * 2. client_id
	 * 3. redirect_uri
	 * 4. code
	 * 5. client_secret
	 */
	public static final String TOKEN_HOST = "https://kauth.kakao.com/";
	public static final String CONETENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
	public static final String GRANT_TYPE = "authorization_code";
	public static final String CODE = "";
	public static final String CLIENT_SECRET = "";

	// access token 
	public static String mappingQueryStringAccessTokenReceive(String code) {

		/*
		 * Kakao REST API
		 * - Query String to make : Access token
		 * - Method : POST
		 * - Host : https://kauth.kakao.com/oauth/token?
		 * - Prameter 
		 * 		1. grant_type
		 * 		2. client_id
		 * 		3. redirect_uri
		 * 		4. code
		 * 		5. client_secret
		 */
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(TOKEN_HOST);
		// post ++
		domain.append("oauth/token?");
		// post ++ 1. grant_type
		domain.append("grant_type=");
		domain.append(GRANT_TYPE);
		// post ++ 2. client_id
		domain.append("&client_id=");
		domain.append(CLIENT_ID);
		// post ++ 3. redirect_uri
		domain.append("&redirect_uri=");
		domain.append(REDIRECT_URI);
		// post ++ 4. code
		domain.append("&code=");
		domain.append(code);
		// post ++ 5. client_secret
		domain.append("&client_secret=");

		return domain.toString();
	}

	/*
	 * ==================== 3. 사용자 정보 받기  ====================
	 * Request Header
	 * 1. Authorization: Bearer {USER_ACCESS_TOKEN}			/			헤더 포맷 -> Authorization: Bearer {USER_ACCESS_TOKEN}
	 * 2. Content-type: application/x-www-form-urlencoded;charset=utf-8
	 */
	public static final String USER_INFO_HOST = "https://kapi.kakao.com/";

	// user information 
	public static String mappingQueryStringUserInfoReceive() {
		
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(USER_INFO_HOST);
		// get or post ++ 
		domain.append("v2/user/me");
		
		return domain.toString();
	}

	/*
	 * ==================== 4.  로그아웃  ====================
	 * - 사용자 액세스 토큰과 리프레시 토큰을 모두 만료시킵니다.
	 * - 로그아웃 시에도 웹 브라우저의 카카오계정 세션은 만료되지 않고, 로그아웃을 호출한 앱의 사용자 토큰만 만료됩니다. 따라서 웹 브라우저의 카카오계정 로그인 상태는 로그아웃 API를 호출해도 유지됩니다.
	 * Request Header
	 * 1. Authorization: Bearer {USER_ACCESS_TOKEN}
	 */
	public static final String LOGOUT_HOST = "https://kapi.kakao.com/";

	// logout 
	public static String mappingQueryStringLogoutReceive() {
		
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(LOGOUT_HOST);
		// post ++
		domain.append("v1/user/logout");

		return domain.toString();
	}

	/* 
	 * ==================== 5. 연결 끊기  ====================
	 * Request Header 
	 * 1. Authorization: Bearer {USER_ACCESS_TOKEN}
	 */
	public static final String UNLINK_HOST = "https://kapi.kakao.com/";

	// unlink 
	public static String mappingQueryStringUnlinkReceive() {
		
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(UNLINK_HOST);
		// post ++
		domain.append("v1/user/unlink");

		return domain.toString();
	}

	/* 
	 * ==================== 6. 카카오 계정과 함께 로그아웃 하기  ====================
	 */
	public static final String LOGOUT_UNLINK_HOST = "https://kauth.kakao.com/";
	public static final String LOGOUT_REDIRECT_URI = "http://192.168.0.6:80/ProjectBlueKakaoLogin";
	public static final String LOGOUT_UNLINK_STATE = "";

	// logout + unlink
	public static String mappingQueryStringLogoutUnlinkReceive() {
		
		StringBuilder domain = new StringBuilder();
		// Host
		domain.append(LOGOUT_UNLINK_HOST);
		// get ++
		domain.append("oauth/logout?");
		// get ++ 1. client_id
		domain.append("client_id=");
		domain.append(CLIENT_ID);
		// get ++ 2. logout_redirect_uri
		domain.append("&logout_redirect_uri=");
		domain.append(LOGOUT_REDIRECT_URI);
		// get ++ 3. state
		domain.append("&state=");
		domain.append(LOGOUT_UNLINK_STATE);

		return domain.toString();
	}
}
