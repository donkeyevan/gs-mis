//package com.youai.gamemis.controller;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.youai.gamemis.model.FieldValueType;
//import com.youai.gamemis.model.Mentity;
//import com.youai.gamemis.model.Mfield;
//import com.youai.gamemis.model.OptionValue;
//import com.youai.gamemis.model.OptionValueAjax;
//import com.youai.gamemis.service.AjaxService;
//import com.youai.gamemis.service.GameEntityService;
//import com.youai.gamemis.service.ManagedEntityService;
//
///**
// * Handles and retrieves person request
// */
//@Controller
//@RequestMapping("/ajax")
//public class AjaxController {
//
//	protected static Logger logger = Logger.getLogger(AjaxController.class);
//
//	@Autowired
//	private GameEntityService gentityService;
//	@Autowired
//	private ManagedEntityService mentityService;
//	@Autowired
//	private AjaxService ajaxService;
//	private static Gson gson = new GsonBuilder().serializeNulls().create();
//
//	/**
//	 * Handles and retrieves all persons and show it in a JSP page
//	 * 
//	 * @return the name of the JSP page
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/query")
//	public String query(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String entityStr = request.getParameter("entity") == null ? "" : request.getParameter("entity").trim();
//
//		String fieldStr = request.getParameter("field") != null ? new String(request.getParameter("field").getBytes(
//				"iso-8859-1"), "utf-8") : null;
//
//		Integer param = Integer.parseInt(request.getParameter("param") == null ? "" : request.getParameter("param")
//				.trim());
//
//		OptionValueAjax ova = ajaxService.entrance(entityStr, fieldStr, param);
//		if (ova != null) {
//			String optionsJson = gson.toJson(ova);
//			response.setContentType("text/html");
//			response.getWriter().write(optionsJson);
//		}
//		return null;
//
//	}
//
//}
