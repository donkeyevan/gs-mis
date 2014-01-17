package com.youai.gamemis.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danga.MemCached.MemCachedClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.constants.GameConfig;


@Controller
@RequestMapping("/mcache")
public class McacheController {

	protected static Logger logger = Logger
			.getLogger(McacheController.class);
	@Resource(name="memcachedClient")
	MemCachedClient cacheClient;
	@Autowired
	GameConfig gameConfig;
	private Gson gson = new GsonBuilder().serializeNulls().create();
	@RequestMapping(value = "/delete/key")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key = request.getParameter("cache_key");
		if( cacheClient.delete( key ) ){
			response.setContentType("text/html");
			response.getWriter().write("Delete cache key:"+key+" successfully!");
			return null;
		}else{
			response.setContentType("text/html");
			response.getWriter().write("Delete cache key:"+key+" failed!");
			return null;
		}
	}
	@RequestMapping(value = "/delete/all")
	public String deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException{

		if( cacheClient.flushAll() ){
			response.setContentType("text/html");
			response.getWriter().write("Delete all cache  successfully!");
			return null;
		}else{
			response.setContentType("text/html");
			response.getWriter().write("Delete all cache failed!");
			return null;
		}
	}
	@RequestMapping(value = "/add/key")
	public String add(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key = request.getParameter("cache_key");
		String value = request.getParameter("cache_value");
		if( cacheClient.add( key, value ) ){
			response.setContentType("text/html");
			response.getWriter().write("Add cache key:"+key+" successfully!");
			return null;
		}else{
			response.setContentType("text/html");
			response.getWriter().write("Add cache key:"+key+" failed!");
			return null;
		}
	}
	
	@RequestMapping(value = "/query/key")
	public String query(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key = request.getParameter("query_key");
		logger.info( gson.toJson( cacheClient.get( key ) ) );
		if( cacheClient.keyExists( key ) ){
			response.setContentType("text/html");
			Object value = cacheClient.get( key );
			response.getWriter().write( gson.toJson(value) );
			return null;
		}else{
			response.setContentType("text/html");
			response.getWriter().write("The key is not exsited in cache!");
			return null;
		}
	}
}
