package enums;

/**
 * User: sergiich
 * Date: 11/14/13
 */

public enum LogCategory{

	SetPlayerInfoRequest("SetPlayerInfoRequest"),
	SetPlayerInfoResponse("SetPlayerInfoResponse"),
	CheckUsernameRequest ("CheckUsernameAvailabilityRequest"),
	CheckUsernameResponse ("CheckUsernameAvailabilityResponse"),
	LogoutRequest ("LogoutNotificationRequest"),
	LoginRequest ("LoginRequest"),
	LoginResponse ("LoginResponse"),
	StartWindowSessionRequest ("StartWindowSessionRequest"),
	StartWindowSessionResponse ("StartWindowSessionResponse"),
	GetPlayerInfoRequest2 ("GetPlayerInfoRequest2"),
	GetPlayerInfoResponse2 ("GetPlayerInfoResponse2"),
	ForgotPasswordRequest ("ForgotPasswordRequest"),
	ForgotPasswordResponse ("ForgotPasswordResponse");

	private final String name;

	private LogCategory(String s) {
		name = s;
	}

	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equals(otherName);
	}

	public String toString(){
		return name;
	}
}
