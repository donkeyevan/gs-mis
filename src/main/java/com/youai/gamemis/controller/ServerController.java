package com.youai.gamemis.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import playercenter.model.GameServerInfo;
import playercenter.service.GameServerInfoService;

import com.youai.gamemis.constants.AppConstant;



@Controller
@RequestMapping("/server")
public class ServerController {
	
	protected static Logger logger = Logger
			.getLogger(ServerController.class);
	
	@Autowired
	GameServerInfoService gameServerInfoService;
	
	//private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	@RequestMapping(value = "/select")
	public String select(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		Integer serverIdx = Integer.parseInt(request.getParameter("serverIdx"));
		GameServerInfo server = gameServerInfoService.getByIdx( serverIdx );
		if( server != null ){
			request.getSession().setAttribute( AppConstant.SESSION_DEAULT_SERVER_IDX_KEY, serverIdx );
			request.getSession().setAttribute( AppConstant.SESSION_DEAULT_SERVER_NAME_KEY, server.getName() );
			response.setContentType("text/html");
			response.getWriter().write("1" );
			return null;
		}else{
			request.getSession().setAttribute( AppConstant.SESSION_DEAULT_SERVER_IDX_KEY, -1 );
			request.getSession().setAttribute( AppConstant.SESSION_DEAULT_SERVER_NAME_KEY, "无" );
			response.setContentType("text/html");
			response.getWriter().write("0" );
			return null;
		}
		
	}
	
}
