package com.youai.gamemis.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.cache.CacheClientProxy;
import com.youai.gamemis.cache.CommonCacheConstant;



@Controller
@RequestMapping("/bulletin")
public class BulletinController {
	
	protected static Logger logger = Logger
			.getLogger(BulletinController.class);
	
	@Autowired
	CacheClientProxy cacheClientProxy;
	
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	/**
	 * 当添加新系统消息时，清除memcache，在server中完成下发操作
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/createbulletin")
	public String createBulletin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//String amountStr = request.getParameter("amount");
		
		boolean result = cacheClientProxy.delete(CommonCacheConstant.BULLETIN);
		
		String optionsJson = gson.toJson(result );
		response.setContentType("text/json");
		response.getWriter().write(optionsJson);
		return null;
	}
	
	
}
