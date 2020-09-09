package HttpResponse;

import Enums.PrintType;

public class ResponseTokenData {
	
	// constant
	private final PrintType PRINT_TYPE = PrintType.ON;
	// 변수
	private String token_type;
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private int refresh_token_expires_in;
	private String scope;
	
	// getter, setter method
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
	
	// method : print
	public void printParameter() {
		StringBuilder jsonParsingData = new StringBuilder();
		
		jsonParsingData.append("<<Json parsing data>>\n");
		jsonParsingData.append("1. token_type : ");
		jsonParsingData.append(token_type);
		jsonParsingData.append("\n2. access_token : ");
		jsonParsingData.append(access_token);
		jsonParsingData.append("\n3. expires_in : ");
		jsonParsingData.append(expires_in);
		jsonParsingData.append("\n4. refresh_token : ");
		jsonParsingData.append(refresh_token);
		jsonParsingData.append("\n5. refresh_token_expires_in : ");
		jsonParsingData.append(refresh_token_expires_in);
		jsonParsingData.append("\n6. scope : ");
		jsonParsingData.append(scope);
		jsonParsingData.append("<<Json parsing data>>\n");
		jsonParsingData.append("<<       end       >>\n");
		
		printDeveloperMessage(jsonParsingData.toString());
	}
	
	// method : developer message
	private void printDeveloperMessage(String message) {
		if (PRINT_TYPE == PrintType.ON)
			System.out.println(message);
	}
}
