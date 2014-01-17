package com.youai.sysadmin.client.auth;

public class AuthConstant {
	public static String IS_LOGIN_KEY = "is_login_key";
	   public static String LOGIN_USER_NAME = "login_user_name";
	   public static String LOGIN_USER_ID = "login_user_id";
	   public static int IS_LOGIN_FLAG = 1;
	   
	    private String authAddress;

		public String getAuthAddress() {
			return authAddress;
		}

		public void setAuthAddress(String authAddress) {
			this.authAddress = authAddress;
		}
}
