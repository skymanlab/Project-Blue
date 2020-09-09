package HttpResponse;

import Developer.DeveloperManager;
import Enums.PrintType;

public class ResponseUserInfoData {

	/*
	 * ====================================================================================
	 * 사용자 관리 / REST API / 사용자 정보 가져오기 / Response / Json 객체
	 * - LoginServlet / step 9.
	 * - Example : 이 것들중에 취사선택 한 데이터 / 추가 사항은 각 클래스에 추가한다.
	 * 	{
	 ** 	"id":123456789,
	 ** 	"properties":{
	 **			"nickname":"홍길동카톡",
	 **			"thumbnail_image":"http://xxx.kakao.co.kr/.../aaa.jpg",
	 **			"profile_image":"http://xxx.kakao.co.kr/.../bbb.jpg",
	 * 			"custom_field1":"23",
	 * 			"custom_field2":"여"
	 * 			...
	 * 		},
	 **		"kakao_account": {
	 **			"profile_needs_agreement": false,
	 **			"profile": {
	 **				"nickname": "홍길동",
	 **				"thumbnail_image_url": "http://yyy.kakao.com/.../img_110x110.jpg",
	 **				"profile_image_url": "http://yyy.kakao.com/dn/.../img_640x640.jpg"
	 * 			},
	 * 			"email_needs_agreement":false,
	 * 			"is_email_valid": true,
	 * 			"is_email_verified": true,
	 * 			"email": "xxxxxxx@xxxxx.com"
	 * 			"age_range_needs_agreement":false,
	 * 			"age_range":"20~29",
	 * 			"birthday_needs_agreement":false,
	 * 			"birthday":"1130",
	 * 			"gender_needs_agreement":false,
	 * 			"gender":"female"
	 * 		}
	 * }
	 * 
	 * ====================================================================================
	 */
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

	// method : parameter print
	public void printParameter() {
		StringBuilder temp = new StringBuilder();
		temp.append("<< json parsing data >>");
		temp.append("\n");
		temp.append("== id : " + id);
		temp.append("\n");
		temp.append("== connected_at : " + connected_at);
		temp.append("\n");
		temp.append("== properties");
		temp.append("\n");
		temp.append("======= nickname : " + properties.getNickname());
		temp.append("\n");
		temp.append("======= profile_image : " + properties.getProfile_image());
		temp.append("\n");
		temp.append("======= thumbnail_image : " + properties.getThumbnail_image());
		temp.append("\n");
		temp.append("== kakao_accuount");
		temp.append("\n");
		temp.append("======= profile_needs_agreement : " + kakao_account.getProfile_needs_agreement());
		temp.append("\n");
		temp.append("======= profile");
		temp.append("\n");
		temp.append("============ nickname : " + kakao_account.getProfile().getNickname());
		temp.append("\n");
		temp.append("============ thumbnail_image_url : " + kakao_account.getProfile().getThumbnail_image());
		temp.append("\n");
		temp.append("============ profile_image_url : " + kakao_account.getProfile().getProfile_image_url());
		temp.append("\n");
		temp.append("<<        end        >>");
		
		DeveloperManager.printDeveloperMessage(temp.toString());
	}
}
