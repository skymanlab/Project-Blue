package HttpResponse;

public class ResponseUserProfileData {

	//variable
	private String nickname;
	private String thumbnail_image_url;
	private String profile_image_url;
	
	// Constructor
	public ResponseUserProfileData(String nickname, String thumbnail_image, String thumbnail_image_url, String profile_image_url) {
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
