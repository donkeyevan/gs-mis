package com.youai.gamemis.constants;

public class GameConfig {
   
   public static String SERVICE_HOME = "com.youai.gamemis.service.";
   public static int RN=100;
   public static int PN=1;
   
   //cache key
   public static String C_FEEDBACK_SAMPLE_KEY = "mis_feedback_sample_key";
   public static String C_FEEDBACK_TYPE_KEY = "mis_feedback_type_key";   
   public static String C_PRIVS_MAP_KEY = "mis_privs_map_key";
   
   //session key
   public static String S_ADMIN_PRIV_KEY = "mis_admin_priv";
   public static String S_ADMIN_NO_NAV_PRIV_KEY = "mis_no_nav_privs";
   public static String S_ADMIN_NAV_KEY = "mis_parent_navs";
   public static String S_LOGIN_ADMIN_KEY = "login_game_admin";
   
   //other constants
   public static String GAME_KEY = "parking";
   public static String SUPER_ADMIN_NAME="youaiadmin";
   
   //deploy enviroments
   public static enum ModeType{
	   dev, product
   }
   private String mode; 

	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

	
}
