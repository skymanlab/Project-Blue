package HttpResponse;

import Developer.DeveloperManager;
import Enums.PrintType;

public class ResponseTokenData {
	
	/*
	 * ====================================================================================
	 * 카카오 로그인 / REST API / 사용자 토큰 받기 / Response / Json 객체
	 * - LoginServlet / step 4.
	 * - Example
	 *	{
	 *		"token_type":"bearer",
     *		"access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
     *		"expires_in":43199,
     *		"refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
     *		"refresh_token_expires_in":25184000,
     *		"scope":"account_email profile"
	 *	}
	 * ====================================================================================
	 */
	
	// variable
	private String token_type;
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private int refresh_token_expires_in;
	private String scope;
	
	// Constructor
	public ResponseTokenData(String token_type, String access_token, int expires_in, String refresh_token,
			int refresh_token_expires_in, String scope) {
		super();
		this.token_type = token_type;
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.refresh_token = refresh_token;
		this.refresh_token_expires_in = refresh_token_expires_in;
		this.scope = scope;
	}
	
	// method : getter, setter
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public int getRefresh_token_expires_in() {
		return refresh_token_expires_in;
	}
	public void setRefresh_token_expires_in(int refresh_token_expires_in) {
		this.refresh_token_expires_in = refresh_token_expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	// method : parameter print
	public void printParameter() {
		StringBuilder temp = new StringBuilder();
		
		temp.append("<<Json parsing data>>\n");
		temp.append("1. token_type : ");
		temp.append(token_type);
		temp.append("\n2. access_token : ");
		temp.append(access_token);
		temp.append("\n3. expires_in : ");
		temp.append(expires_in);
		temp.append("\n4. refresh_token : ");
		temp.append(refresh_token);
		temp.append("\n5. refresh_token_expires_in : ");
		temp.append(refresh_token_expires_in);
		temp.append("\n6. scope : ");
		temp.append(scope);
		temp.append("<<Json parsing data>>\n");
		temp.append("<<       end       >>");
		
		DeveloperManager.printDeveloperMessage(temp.toString());
	}
}
