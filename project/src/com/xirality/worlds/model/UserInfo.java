package com.xirality.worlds.model;

public class UserInfo {
	private static final String DEFAULT_DEVICE_TYPE = String.format("%s %s", android.os.Build.MODEL, android.os.Build.VERSION.RELEASE);
	
	private String login;
	private String password;
	private String deviceType = DEFAULT_DEVICE_TYPE;
	private String deviceId;
	
	public UserInfo (String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
}
