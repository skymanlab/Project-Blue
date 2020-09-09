package HttpResponse;

import Developer.DeveloperManager;
import Enums.PrintType;

public class ResponseUserProfileData {

	/*
	 * ====================================================================================
	 * 사용자 관리 / REST API / 사용자 정보 가져오기 / Response / Json 객체
	 * - LoginServlet / step 9. 
	 * - Example.
	 **			"profile": {
	 **				"nickname": "홍길동",
	 **				"thumbnail_image_url": "http://yyy.kakao.com/.../img_110x110.jpg",
	 **				"profile_image_url": "http://yyy.kakao.com/dn/.../img_640x640.jpg"
	 * 			},
	 * ====================================================================================
	 */
	// variable
	private String nickname;
	private String thumbnail_image_url;
	private String profile_image_url;

	// Constructor
	public ResponseUserProfileData(String nickname, String thumbnail_image, String thumbnail_image_url,
			String profile_image_url) {
		super();
		this.nickname = nickname;
		this.thumbnail_image_url = thumbnail_image_url;
		this.profile_image_url = profile_image_url;
	}

	// method : getter, setter
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getThumbnail_image() {
		return thumbnail_image_url;
	}

	public void setThumbnail_image(String thumbnail_image) {
		this.thumbnail_image_url = thumbnail_image;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

}
