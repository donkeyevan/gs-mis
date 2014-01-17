package com.youai.gamemis.controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.service.BeanFactoryAwareBean;



@Controller
@RequestMapping("/debris")
public class DebrisController {
	
	protected static Logger logger = Logger
			.getLogger(DebrisController.class);
	
	/*@Autowired
	ReloadDataSourceService reloadDataSourceService;*/
	
	@Autowired
	private BeanFactoryAwareBean awareBean;
	
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	@RequestMapping(value = "/refreshdatasource")
	public String refreshDataSource(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, PropertyVetoException {
		logger.info("get request refreshdatasource");
		//reloadDataSourceService.refreshDataSource();
		awareBean.onApplicationEvent();
		logger.info("refresh success");
		response.setContentType("text/html");
		response.getWriter().write("success");
		return null;
	}
	
	
}
