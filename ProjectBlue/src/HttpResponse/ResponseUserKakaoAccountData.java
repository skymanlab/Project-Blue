package HttpResponse;

import Developer.DeveloperManager;
import Enums.PrintType;

public class ResponseUserKakaoAccountData {
	
	/*
	 * ====================================================================================
	 * 사용자 관리 / REST API / 사용자 정보 가져오기 / Response / Json 객체
	 * - LoginServlet / step 9. 
	 * - Example
	 **		"kakao_account": {
	 **			"profile_needs_agreement": false,
	 **			"profile": {
	 **				"nickname": "홍길동",
	 **				"thumbnail_image_url": "http://yyy.kakao.com/.../img_110x110.jpg",
	 **				"profile_image_url": "http://yyy.kakao.com/dn/.../img_640x640.jpg"
	 * 			},
	 * 		}
	 * ====================================================================================
	 */
	// variable 
	private String profile_needs_agreement;
	private ResponseUserProfileData profile;

	// Constructor
	public ResponseUserKakaoAccountData(String profile_needs_agreement, ResponseUserProfileData profile) {
		super();
		this.profile_needs_agreement = profile_needs_agreement;
		this.profile = profile;
	}

	// method : getter, setter
	public String getProfile_needs_agreement() {
		return profile_needs_agreement;
	}

	public void setProfile_needs_agreement(String profile_needs_agreement) {
		this.profile_needs_agreement = profile_needs_agreement;
	}

	public ResponseUserProfileData getProfile() {
		return profile;
	}

	public void setProfile(ResponseUserProfileData profile) {
		this.profile = profile;
	}

}
