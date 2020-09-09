package HttpResponse;

public class ResponseUserKakaoAccountData {

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
