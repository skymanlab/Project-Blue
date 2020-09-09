package HttpResponse;

import Enums.PrintType;

public class ResponseUserInfoData {

	// constant
	private final PrintType PRINT_TYPE = PrintType.ON;

	// variable
	private int id;
	private String connected_at;
	private ResponseUserPropertiesData properties;
	private ResponseUserKakaoAccountData kakao_account;

	// Constructor
	public ResponseUserInfoData(int id, String connected_at, ResponseUserPropertiesData properties,
			ResponseUserKakaoAccountData kakao_account) {
		super();
		this.id = id;
		this.connected_at = connected_at;
		this.properties = properties;
		this.kakao_account = kakao_account;
	}

	// method : getter, setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnected_at() {
		return connected_at;
	}

	public void setConnected_at(String connected_at) {
		this.connected_at = connected_at;
	}

	public ResponseUserPropertiesData getProperties() {
		return properties;
	}

	public void setProperties(ResponseUserPropertiesData properties) {
		this.properties = properties;
	}

	public ResponseUserKakaoAccountData getKakao_accout() {
		return kakao_account;
	}

	public void setKakao_accout(ResponseUserKakaoAccountData kakao_accout) {
		this.kakao_account = kakao_accout;
	}

	// method : print
	public void printParameter() {
		
		System.out.println("<< json parsing data >>");
		System.out.println("== id : " + id);
		System.out.println("== connected_at : " + connected_at);
		System.out.println("== properties");
		System.out.println("======= nickname : " + properties.getNickname());
		System.out.println("======= profile_image : " + properties.getProfile_image());
		System.out.println("======= thumbnail_image : " + properties.getThumbnail_image());
		System.out.println("== kakao_accuount");
		System.out.println("======= profile_needs_agreement : " + kakao_account.getProfile_needs_agreement());
		System.out.println("======= profile");
		System.out.println("============ nickname : " + kakao_account.getProfile().getNickname());
		System.out.println("============ thumbnail_image_url : " + kakao_account.getProfile().getThumbnail_image());
		System.out.println("============ profile_image_url : " + kakao_account.getProfile().getProfile_image_url());
		System.out.println("<< 		  end  		 >>");
	
	}

	//method : developer message
	private void printDeveloperMessage(String message) {
		if (PRINT_TYPE == PrintType.ON)
			System.out.println(message);
	}
}
