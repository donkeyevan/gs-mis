package com.youai.gamemis.util;

import java.sql.Date;

import com.youai.gamemis.model.DBCatalogType;
import com.youai.gamemis.model.StatDataType;


public class CommonUtil {
	public static String getGetterMethod( String fieldName, String s_catalog ){
		return "get" + firstLetterToUpper( fieldName );
	}
	public static String getSetterMethod( String fieldName, String s_catalog ){
	
		return "set" + firstLetterToUpper( fieldName );
	}
	public static String firstLetterToUpper(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public static Class getStatDataTypeClazz( String dataType){
		StatDataType sdType = StatDataType.valueOf( dataType );
		Class sdType_clazz = null;
		if( StatDataType.t_int == sdType){
			sdType_clazz = Integer.class;
		}
		if( StatDataType.t_string == sdType ){
			sdType_clazz = String.class;
		}
		if( StatDataType.t_date == sdType ){
			sdType_clazz = Date.class;
		}
		return sdType_clazz;
	}
}
