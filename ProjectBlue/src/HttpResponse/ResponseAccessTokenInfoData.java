package HttpResponse;

import Enums.PrintType;

public class ResponseAccessTokenInfoData {
	
	// constant
	private final PrintType PRINT_TYPE = PrintType.ON;
	// variable
	private long id;
	private int expires_in;
	private int app_id;
	
	// getter, setter method
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	
}
