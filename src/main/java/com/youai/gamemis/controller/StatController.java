package com.youai.gamemis.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.StatPlayerResult;
import com.youai.gamemis.model.StatUIPayment;
import com.youai.gamemis.service.StatService;

@Controller
@RequestMapping("/stat")
public class StatController {
	public static enum STAT_TYPE { all, payuser };
	protected static Logger logger = Logger
	.getLogger(StatController.class);

	@Autowired
	StatService statService;
	@Autowired
	GameConfig gameConfig;
	
	@RequestMapping(value = "/player")
	public String statPayment(HttpServletRequest request,
			HttpServletResponse response ) throws JRException, IOException{
		String begin_date = request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		List<StatPlayerResult> results = statService.getStatPlayers(begin_date, end_date);
		InputStream reportStream = this.getClass().getResourceAsStream("/reports/stat-player.jasper");
		JasperReport jasReport = (JasperReport) JRLoader.loadObject(reportStream);
		// Creates the JasperPrint object
		// It needs a JasperReport layout and a datasource
		// params is used for passing extra parameters 
		HashMap params = new HashMap(); 
		Gson gson = new  GsonBuilder().serializeNulls().create();
		JRDataSource ds = new JRBeanCollectionDataSource(results);
		JasperPrint jp = JasperFillManager.fillReport(jasReport, params, ds);
		// Write to response stream
		JRHtmlExporter exporter = new JRHtmlExporter();		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		//exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		//exporter.setParameter( JRHtmlExporterParameter.IMAGES_URI, "../../image?image=");
		exporter.setParameter( JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);

		exporter.exportReport();
		
		// Retrieve the output stream
		ServletOutputStream outputStream = response.getOutputStream();
		response.setContentType("text/html");
		response.setContentLength( baos.size() );
		System.out.print("bao length:"+ baos.size()  );
		// Write to the output stream
		baos.writeTo(outputStream);
		// Flush the stream
		outputStream.flush();
		return null;
	}
	
	
	@RequestMapping(value = "/payment")
	public String statPlayer(HttpServletRequest request,
			HttpServletResponse response ) throws JRException, IOException,
			IllegalAccessException, InvocationTargetException, InstantiationException{
		String begin_date = request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		String stat_type = request.getParameter("stat_type");
		STAT_TYPE statType = STAT_TYPE.valueOf( stat_type );
		switch( statType ){
			case all:{
				List<StatUIPayment> results = statService.getStatPayments(begin_date, end_date);
				InputStream reportStream = this.getClass().getResourceAsStream("/reports/stat-payment-us.jasper");
				JasperReport jasReport = (JasperReport) JRLoader.loadObject(reportStream);
				HashMap params = new HashMap(); 
				Gson gson = new  GsonBuilder().serializeNulls().create();
				JRDataSource ds = new JRBeanCollectionDataSource(results);
				JasperPrint jp = JasperFillManager.fillReport(jasReport, params, ds);
				// Write to response stream
				JRHtmlExporter exporter = new JRHtmlExporter();		
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				//exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				//exporter.setParameter( JRHtmlExporterParameter.IMAGES_URI, "../../image?image=");
				exporter.setParameter( JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);		
				exporter.exportReport();
				
				// Retrieve the output stream
				ServletOutputStream outputStream = response.getOutputStream();
				response.setContentType("text/html");
				response.setContentLength( baos.size() );
				System.out.print("bao length:"+ baos.size()  );
				// Write to the output stream
				baos.writeTo(outputStream);
				// Flush the stream
				outputStream.flush();
				return null;
				
			}
			case payuser:{
				
				int payuserNum = statService.getPayuserNum(begin_date, end_date);
				request.setAttribute("result", "从"+begin_date+"到"+end_date+"共计"+payuserNum+"个不同付费用户");
				return "common/success";
			}
			
		
		}
		return null;
	}
	
}
