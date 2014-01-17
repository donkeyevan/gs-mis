package com.youai.gamemis.util;

import java.lang.Character.UnicodeBlock;
import java.util.List;


public class StringUtil {
	public static String trimSpecialChar(String content ){
		if( content == null ) return "";
		content =  content.replaceAll("\f", "\n");
		return  content.replaceAll("[\n]+", "\n");
	}
	
	public static boolean isEmpty(String string){
		return string == null || "".equals(string.trim());
	}
	
	public static String implde( List params,String splitChar ){
			StringBuffer sb = new StringBuffer();
			if( params == null || params.size() == 0 ){
				return "";
			}
			for( Object param: params ){
				sb.append( param ).append( splitChar );
			}
			return sb.subSequence( 0 , sb.length()  - 1 ).toString();
			
	}
	public static boolean containsEmoji(String name)  
    {     
          

        try{  
            char[] myBuffer = name.toCharArray();  
            for (int i = 0; i < name.length(); i++) {  
             UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);  
                if(ub == UnicodeBlock.BASIC_LATIN){  
                 continue;
                }else if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
                	     || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
                	     || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
                	     || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION 
                	     || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ){  
                 continue;
                }else{  
                 //汉字  
                 String temp = String.valueOf(myBuffer[i]);  
                 if(judgeIfSpecial(temp.getBytes("unicode")))  
                 {                     
                  
                     return true;  
                 }else  
                 {  
                    continue;
                 }  
                }  
            }  
             
            return false;                
        }catch(Exception e)  
        {  
        	e.printStackTrace();
        	return false;  
        }   
       
    }
	//emoji: http://en.wikipedia.org/wiki/Emoji
	public static boolean judgeIfSpecial(byte[] c)  
    {  
        if(c.length != 2 && c.length != 4) return false;  
        byte[] b = new byte[2];       
        if(c.length == 4)   
        {  
            b[0] = c[2];  
            b[1] = c[3];  
        }else  
        {  
            b[0] = c[0];  
            b[1] = c[1];  
        }  
        if(b[0] != -32 && b[0] != -31 && b[0] != -30 && b[0] != -28 && b[0] != -27 )  
        {  
            return false;  
        }  
        switch(b[0])  
        {  
        case -32: //0xe0   0xE001,0xE05A  
             if(b[1] >= 1 && b[1] <= 90 )   //0x01, 0x5A  
             {  
                 return true;  
             }  
            break;  
        case -31: //0xe1   0xE101,0xE15A  
            if(b[1] >= 1 && b[1] <= 90 )   //0x01, 0x5A  
             {  
                 return true;  
             }  
            break;  
        case -30: //0xe2   0xE201,0xE253  
            if(b[1] >= 1 && b[1] <= 83 )   //0x01, 0x53  
             {  
                 return true;  
             }  
            break;  
        case -28: //0xe4   0xE401,0xE44C  
            if(b[1] >= 1 && b[1] <= 76 )   //0x01, 0x4c  
             {  
                 return true;  
             }  
            break;  
        case -27: //0xe5  0xE501,0xE537  
            if(b[1] >= 1 && b[1] <= 55 )   //0x01, 0x37  
             {  
                 return true;  
             }  
            break;  
        default:  
            break;  
        }  
        return false;  
    } 
	
	/*
	 * judge a string is a digit
	 */
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){   
			if (!Character.isDigit(str.charAt(i))){
					return false;
			}
		}
		return true;
	}
}

