//package com.youai.gamemis.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.data.redis.connection.DataType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.danga.MemCached.MemCachedClient;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.youai.gamemis.constants.GameConfig;
//import com.youai.gamemis.exception.MisException;
//import com.youai.gamemis.service.RedisService;
//import com.youai.gs.common.db.redis.RedisProxy;
//
//
//
//@Controller
//@RequestMapping("/redis")
//public class RedisController {
//	private static enum COMMAND{
//		zscore, zrank, zrem, zrevrank, zadd, hset, lrange, llen, set, hget, hdel
//	}
//	protected static Logger logger = Logger
//			.getLogger(RedisController.class);
//	@Autowired
//	RedisService redisService;
//	@Resource(name="redisProxy")
//	RedisProxy redisProxy;
//	
//	private Gson gson = new GsonBuilder().serializeNulls().create();
//	
//
//	
//	@RequestMapping(value = "/delete/key")
//	public String delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String key = request.getParameter("key");
//		redisService.delete( key ) ;
//		response.setContentType("text/html");
//		response.getWriter().write("Delete cache key:"+key+" successfully!");
//		return null;	
//	}
//	
//	/**
//	@RequestMapping(value = "/delete/all")
//	public String deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
//
//		if( cacheClient.flushAll() ){
//			response.setContentType("text/html");
//			response.getWriter().write("Delete all cache  successfully!");
//			return null;
//		}else{
//			response.setContentType("text/html");
//			response.getWriter().write("Delete all cache failed!");
//			return null;
//		}
//	}
//	@RequestMapping(value = "/add/key")
//	public String add(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String key = request.getParameter("cache_key");
//		String value = request.getParameter("cache_value");
//		if( cacheClient.add( key, value ) ){
//			response.setContentType("text/html");
//			response.getWriter().write("Add cache key:"+key+" successfully!");
//			return null;
//		}else{
//			response.setContentType("text/html");
//			response.getWriter().write("Add cache key:"+key+" failed!");
//			return null;
//		}
//	}
//**/
//	@RequestMapping(value = "/query/key")
//	public String query(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String key = request.getParameter("query_key");
//		
//		if( redisService.hasKey( key ) ){
//			response.setContentType("text/html");
//			Object value;
//			try {
//				value = redisService.get( key );
//				logger.info("value for key["+ key+"] : "+ value );
//			} catch (MisException e) {
//				response.setContentType("text/html");
//				response.getWriter().write(e.getMessage());
//				return null;
//			}
//			response.getWriter().write( gson.toJson(value) );
//			return null;
//		}else{
//			response.setContentType("text/html");
//			response.getWriter().write("The key is not exsited in redis!");
//			return null;
//		}
//		
//	}
//	
//	@RequestMapping(value = "/query/keyjson")
//	public String queryJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String key = request.getParameter("query_key");
//		
//		if( redisService.hasKey( key ) ){
//			response.setContentType("text/html");
//			Object value;
//			try {
//				value = redisService.getJson( key );
//				logger.info("value for key["+ key+"] : "+ value );
//			} catch (MisException e) {
//				response.setContentType("text/html");
//				response.getWriter().write(e.getMessage());
//				return null;
//			}
//			response.getWriter().write( gson.toJson(value) );
//			return null;
//		}else{
//			response.setContentType("text/html");
//			response.getWriter().write("The key is not exsited in redis!");
//			return null;
//		}
//		
//	}
//	
//	@RequestMapping(value = "/command")
//	public String command(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String scommand = request.getParameter("command");
//		logger.info("Execute redis command:"+scommand );
//		String[] args = scommand.split("[ ]+");
//		if( args == null || args.length == 0 ){
//			response.setContentType("text/html");
//			response.getWriter().write("The commond can not be empty!");
//			return null;
//		}
//		COMMAND command  = null;
//		try{
//			command = COMMAND.valueOf( args[0] );
//		}catch(Exception e){
//			response.setContentType("text/html");
//			response.getWriter().write("The command "+args[0]+" is not be suppurted right now!");
//			return null;
//		}
//		switch( command ){
//		case set:
//			if( args.length != 3 ){
//				response.setContentType("text/html");
//				response.getWriter().write("The set command must have 3 args!");
//				return null;
//			}
//			redisProxy.set(args[1], RedisService.parseArg( args[2])  );
//			response.setContentType("text/html");
//			response.getWriter().write( "set command completed!" );
//			return null;
//		case llen:
//			if( args.length != 2 ){
//				response.setContentType("text/html");
//				response.getWriter().write("The llen command must have 2 args!");
//				return null;
//			}
//			long size = redisProxy.llen( args[1] );
//			response.setContentType("text/html");
//			response.getWriter().write( "" + size );
//			return null;
//		case lrange:
//			if( args.length != 4 ){
//				response.setContentType("text/html");
//				response.getWriter().write("The lrange command must have 4 args!");
//				return null;
//			}
//			List<Object> items = redisProxy.lrange( args[1] , Integer.parseInt(args[2]),  Integer.parseInt(args[3]) );
//			response.setContentType("text/html");
//			response.getWriter().write( gson.toJson(items) );
//			return null;
//		case hset:
//			if( args.length != 4){
//				response.setContentType("text/html");
//				response.getWriter().write("The hset command must have 3 args!");
//				return null;
//			}
//			
//			redisProxy.hashPut( args[1] , args[2],  redisService.parseArg( args[3]) );
//			response.setContentType("text/html");
//			response.getWriter().write( "Update successfully!" );
//			return null;
//		case hget:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The hset command must have 2 args!");
//				return null;
//			}
//			Object value = redisProxy.hashGet( args[1] ,   args[2] );
//			response.setContentType("text/html");
//			response.getWriter().write( gson.toJson( value ) );
//			return null;
//		case hdel:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The hdel command must have 2 args!");
//				return null;
//			}
//			redisProxy.hashDelete( args[1] , args[2] );
//			response.setContentType("text/html");
//			response.getWriter().write( "del hash key successfully" );
//			return null;
//		case zscore:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The zscore command must have 3 args!");
//				return null;
//			}
//			
//			Object zscore = redisProxy.zscore( args[1], redisService.parseArg(args[2]) );
//			response.setContentType("text/html");
//			response.getWriter().write( gson.toJson(zscore) );
//			return null;
//		
//		case zrank:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The zrank command must have 3 args!");
//				return null;
//			}
//			Object rank = redisProxy.zrank( args[1], redisService.parseArg(args[2]) );
//			response.setContentType("text/html");
//			response.getWriter().write( gson.toJson(rank) );
//			return null;
//		case zrevrank:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The zrevrank command must have 3 args!");
//				return null;
//			}
//			Object zrank = redisProxy.zrevrank( args[1], redisService.parseArg(args[2]) );
//			response.setContentType("text/html");
//			response.getWriter().write( gson.toJson(zrank) );
//			return null;
//		case zrem:
//			if( args.length != 3){
//				response.setContentType("text/html");
//				response.getWriter().write("The zrem command must have 3 args!");
//				
//				return null;
//			}
//			redisProxy.zrem( args[1], redisService.parseArg(args[2]) );
//			response.setContentType("text/html");
//			response.getWriter().write( "remove item:"+args[2]+" sccessfully!" );
//			return null;
//		case zadd:
//			if( args.length != 4){
//				response.setContentType("text/html");
//				response.getWriter().write("The zrem command must have 4 args!");
//				
//				return null;
//			}
//			redisProxy.zadd( args[1], redisService.parseArg(args[2]), Double.parseDouble(args[3]) );
//			response.setContentType("text/html");
//			response.getWriter().write( "remove item:"+args[2]+" sccessfully!" );
//			return null;
//		default:
//			response.setContentType("text/html");
//			response.getWriter().write("The command "+args[0]+" is not be suppurted right now!");
//			return null;
//			
//		}
//		
//		
//	}
//}
