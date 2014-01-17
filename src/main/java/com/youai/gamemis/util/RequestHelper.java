package com.youai.gamemis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.youai.gamemis.model.Mfield;



public class RequestHelper {
	private static Logger  logger = Logger.getLogger( RequestHelper.class );
	private static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static boolean isEmpty( String o ){
		if( o == null || o.trim().length() == 0 ){
			return true;
		}
		return false;
	}
	public static Class getFieldClass( String className ) throws ClassNotFoundException  {
		if( className.equalsIgnoreCase("int") ){
			return int.class;
		}
		if( className.equals("char") ){
			return char.class;
		}
		if( className.equals("byte") ){
			return byte.class;
		}
		if( className.equals("long") ){
			return long.class;
		}
		if( className.equals("float") ){
			return float.class;
		}
		return Class.forName( className );
	}
    public static Object getFieldValue(Mfield field, String value ){
        Object res_value = null;
        String typeName = field.getType();
        if( typeName.equals( String.class.getName() ) ){
        	 return value;
        }
        if( typeName.equals( Integer.class.getName() ) || typeName.equals( "int") ){
            res_value = isEmpty(value)  ? null: Integer.parseInt( value );
        }
        
        if( typeName.equals( Short.class.getName() ) || typeName.equals( "short" ) ){
            res_value = isEmpty(value)  ? null:Short.valueOf( value );
        }
        if( typeName.equals( Long.class.getName() ) || typeName.equals( "long" )  ){
            res_value = isEmpty(value)  ? null: Long.valueOf( value );
        }
       
        if( typeName.equals( Float.class.getName() ) || typeName.equals( "float" ) ){
            res_value = isEmpty(value)  ? null:Float.valueOf( value );
        }
        
        if( typeName.equals( Boolean.class.getName() ) || typeName.equals( "boolean") ){
            res_value = isEmpty(value)  ? null: Boolean.valueOf( value );
        }
        if( typeName.equals( Date.class.getName()) || typeName.equals("date") ){
        	if( field.getFieldValues().equals("1") ){
        		res_value = new Date();
        	}else{
        		try {
        			res_value = isEmpty( value) ? null:format1.parse( value );
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			logger.error(e.getMessage());
        		}
        	}
        }
        
        return res_value;
    }
    
    public static boolean valueEqual( Object oldValue, Object newValue, String typeName ){
    	logger.info("oldValue:"+oldValue + " newValue:"+newValue + " type:"+typeName );
    	if( typeName.equals( String.class.getName() ) ){
       	 	//TODO: 对于字符类型的不做记录
    		return true;
    		//return (oldValue == null && newValue == null) || ( oldValue != null && newValue != null && ((String)oldValue).equals( (String)newValue ));
       }
       if( typeName.equals( Integer.class.getName() ) || typeName.equals( "int") ){
           return (oldValue == null && newValue == null ) || ( oldValue != null && newValue != null && ((Integer)oldValue).intValue() == ( (Integer)newValue).intValue() );
       }
       
       if( typeName.equals( Short.class.getName() ) || typeName.equals( "short" ) ){
           return (oldValue == null && newValue == null ) ||  ( oldValue != null && newValue != null && ((Short)oldValue).shortValue() == ( (Short)newValue).shortValue() );
       }
       if( typeName.equals( Long.class.getName() ) || typeName.equals( "long" )  ){
    	  return (oldValue == null && newValue == null ) || ( oldValue != null && newValue != null && ((Long)oldValue).longValue() == ( (Long)newValue).longValue() );
       }
      
       if( typeName.equals( Float.class.getName() ) || typeName.equals( "float" ) ){
    	   return (oldValue == null && newValue == null ) || ( oldValue != null && newValue != null && ((Float)oldValue).floatValue() == ( (Float)newValue).floatValue() );
       }
       
       if( typeName.equals( Boolean.class.getName() ) || typeName.equals( "boolean") ){
    	   return (oldValue == null && newValue == null ) || ( oldValue != null && newValue != null && ((Boolean)oldValue).booleanValue() == ( (Boolean)newValue).booleanValue() );
       }
       if( typeName.equals( Date.class.getName()) || typeName.equals("date") ){
    	   //TODO: 日期类型暂时不处理
    	   return true;
       }
       return true;
    }
}
