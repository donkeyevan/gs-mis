//package com.youai.gamemis.service;
//
//import javax.annotation.Resource;
//
//import org.springframework.data.redis.connection.DataType;
//import org.springframework.stereotype.Service;
//
//import com.youai.gamemis.exception.MisException;
//import com.youai.gs.common.db.redis.RedisProxy;
//
//@Service("RedisService")
//public class RedisService {
//	@Resource(name="redisProxy")
//	RedisProxy redisProxy;
//	public Object get( String key) throws MisException{
//		DataType dataType = redisProxy.type( key );
//		if( dataType == DataType.STRING ){
//			Object value = redisProxy.get( key );
//			return value;
//		}
//		if( dataType == DataType.HASH ){
//			return redisProxy.hgetall( key );
//		}
//		if( dataType == DataType.ZSET ){
//			return redisProxy.zrange( key, 0, -1 );
//		}
//		if( dataType == DataType.SET ){
//			return redisProxy.smembers( key );
//		}
//		if( dataType == DataType.LIST ){
//			return redisProxy.lrange(key, 0, -1 );
//		}
//		throw new MisException("Unknow redis data type!");
//	}
//	public Object getJson( String key) throws MisException{
//		DataType dataType = redisProxy.type( key );
//		if( dataType == DataType.STRING ){
//			return "current not support string type query";
//		}
//		if( dataType == DataType.SET ){
//			return "current not support set type query";
//		}
//		if( dataType == DataType.ZSET ){
//			return "current not support zset type query";
//		}
//		if( dataType == DataType.HASH ){
//			return redisProxy.hgetallJson( key, Object.class );
//		}
//		if( dataType == DataType.LIST ){
//			return redisProxy.lrangeJson(key, 0, -1, Object.class );
//		}
//		throw new MisException("Unknow redis data type!");
//	}
//	public void delete(String key){
//		redisProxy.delete( key );
//	}
//	public boolean hasKey(String key){
//		return redisProxy.hasKey( key );
//	}
//	public static Object parseArg( String key ){
//		if( key.charAt(0) == 'I' ){
//			key = key.substring(1);
//			return  Integer.parseInt( key );
//		}
//		if( key.charAt(0) == 'L'){
//			key = key.substring(1);
//			return Long.parseLong( key );
//		}
//		if( key.charAt(0) == 'D'){
//			key = key.substring(1);
//			return Double.parseDouble( key );	
//		}
//		if( key.charAt(0) == 'B'){
//			key = key.substring(1);
//			return Boolean.parseBoolean( key );
//		}
//		if( key.charAt(0) == 'F') {
//			key = key.substring(1);
//			return Float.parseFloat(key);
//		}
//		key = key.substring(1);
//		return key;
//	}
//}
