package com.youai.gamemis.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.ExtendLink;
import com.youai.gamemis.model.ExtendLinkType;
import com.youai.gamemis.model.FieldValueType;
import com.youai.gamemis.model.Mentity;
import com.youai.gamemis.model.Mfield;
import com.youai.gamemis.model.PageQueryInfo;
import com.youai.gamemis.service.GameEntityService;
import com.youai.gamemis.service.ManagedEntityService;
import com.youai.gamemis.util.RequestHelper;
import com.youai.sysadmin.client.auth.AuthConstant;

/**
 * Handles and retrieves person request
 */
@Controller
@RequestMapping("/gameentity")
public class GameEntityController {

	protected static Logger logger = Logger
			.getLogger(GameEntityController.class);

	@Autowired
	private GameEntityService gentityService;
	@Autowired
	private ManagedEntityService mentityService;
	//@Autowired
	//private AjaxService ajaxService;
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	/**
	 * Handles and retrieves all persons and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 * @throws Exception 
	 */
	@RequestMapping(value = "/query")
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		String mentity_id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityWithRealValues(mentity_id);
		//deal with the link
		logger.info("extendLink:"+ mentity.getExtendLink()  );
		if( mentity.getExtendLink() != null && !mentity.getExtendLink().trim().isEmpty() ){
			ExtendLinkType extendLinkType = gson.fromJson( mentity.getExtendLink(), ExtendLinkType.class );
			for( ExtendLink elink : extendLinkType.getExtendLinks() ){
				//判断链接是否为本服的
				if(elink.getType() == null){
					elink.setLink( request.getContextPath() + elink.getLink() );
				}else if(elink.getType() == 1){
					elink.setLink( elink.getLink() );
				}
			}
			mentity.setExtendLinkType( extendLinkType );
		}else{
			mentity.setExtendLinkType( null );
			mentity.setExtendLink( null );
		}
		
		String modelClass = mentity.getEntityClass();
		Class clazz = Class.forName(modelClass);
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		int serverIdx = request.getParameter("selectedServerIdx") == null ?
				0 : Integer.parseInt( request.getParameter("selectedServerIdx") );
	
		String queryField = request.getParameter("query_field") == null ? ""
				: request.getParameter("query_field").trim();
		Integer pn = request.getParameter("pn") == null ? GameConfig.PN
				: Integer.valueOf(request.getParameter("pn"));
		Integer rn = request.getParameter("rn") == null ? GameConfig.RN
				: Integer.valueOf(request.getParameter("rn"));
		
		String queryValue =request.getParameter("query_value") != null? 
				new String(request.getParameter("query_value").getBytes("iso-8859-1"), "utf-8") : null;
				
		String queryField2 = request.getParameter("query_field2") == null ? ""
				: request.getParameter("query_field2").trim();
		
		String queryValue2 =request.getParameter("query_value2") != null? 
				new String(request.getParameter("query_value2").getBytes("iso-8859-1"), "utf-8") : null;
				
		String compareField1 = request.getParameter("compare_field1") == null ? ""
				: request.getParameter("compare_field1").trim();

		String compareType1 = request.getParameter("compare_type1");
		
		String compareValue1 =request.getParameter("compare_value1") != null? 
				new String(request.getParameter("compare_value1").getBytes("iso-8859-1"), "utf-8") : null;

		String compareField2 = request.getParameter("compare_field2") == null ? ""
				: request.getParameter("compare_field2").trim();
		
		String compareType2 = request.getParameter("compare_type2");
		
		String compareValue2 =request.getParameter("compare_value2") != null? 
				new String(request.getParameter("compare_value2").getBytes("iso-8859-1"), "utf-8") : null;
				
		String sortField1 = request.getParameter("sort_field1");
		String sortType1 = request.getParameter("sort_type1");
		logger.info("Begin to query entity! Params[queryField:" + queryField
				+ " queryValue:" + queryValue + "]"+"[queryField2:"+queryField2+" queryValue2:"+ queryValue2 +"]");
		Object oqValue = null;
		List<Mfield> queryFields = mentity.getAllfields();
		if (queryField.isEmpty() == false) {
			for (Mfield mfield : queryFields) {
				if (mfield.getName().equals(queryField)) {
					oqValue = RequestHelper.getFieldValue(mfield,
							queryValue);
				}
			}

			dc.add(Restrictions.eq(queryField, oqValue));
			
		}
		Object oqValue2 = null;
		if( queryField2.isEmpty() == false && queryValue2.isEmpty() == false ){
			for (Mfield mfield : queryFields) {
				if (mfield.getName().equals(queryField2)) {
					oqValue2 = RequestHelper.getFieldValue(mfield,
							queryValue2);
				}
			}
			dc.add(Restrictions.eq(queryField2, oqValue2));
		}
		
		//compare factor1
		Object coValue1 = null;
		if( compareField1.isEmpty() == false && compareValue1.isEmpty() == false ){
			for (Mfield mfield : queryFields) {
				if (mfield.getName().equals(compareField1)) {
					coValue1 = RequestHelper.getFieldValue(mfield, compareValue1);
				}
			}
			int compareType1Int = Integer.parseInt(compareType1);
			if(compareType1Int == 0){
				dc.add(Restrictions.gt(compareField1, coValue1));
			}else if(compareType1Int == 1){
				dc.add(Restrictions.ge(compareField1, coValue1));
			}else if(compareType1Int == 2){
				dc.add(Restrictions.lt(compareField1, coValue1));
			}else if(compareType1Int == 3){
				dc.add(Restrictions.le(compareField1, coValue1));
			}
		}
		//compare factor2
		Object coValue2 = null;
		if( compareField2.isEmpty() == false && compareValue2.isEmpty() == false ){
			for (Mfield mfield : queryFields) {
				if (mfield.getName().equals(compareField2)) {
					coValue2 = RequestHelper.getFieldValue(mfield, compareValue2);
				}
			}
			int compareType2Int = Integer.parseInt(compareType2);
			if(compareType2Int == 0){
				dc.add(Restrictions.gt(compareField2, coValue2));
			}else if(compareType2Int == 1){
				dc.add(Restrictions.ge(compareField2, coValue2));
			}else if(compareType2Int == 2){
				dc.add(Restrictions.lt(compareField2, coValue2));
			}else if(compareType2Int == 3){
				dc.add(Restrictions.le(compareField2, coValue2));
			}
		}
		
		if( sortField1.isEmpty() == false ){
			if( Integer.parseInt(sortType1) == 0 ){
				dc.addOrder( Property.forName(sortField1).asc() );
			}
			else{
				dc.addOrder( Property.forName(sortField1).desc() );
			}
		}
		if( modelClass.equalsIgnoreCase("com.youai.gs.dzm.model.DailyTaskItem")){
			dc.addOrder( Property.forName("status").desc() );
			dc.addOrder( Property.forName("position").asc() );
		}
		
		PageQueryInfo queryInfo = new PageQueryInfo();
		queryInfo.setPn(pn);
		queryInfo.setRn(rn);

		PageQueryInfo pqInfo = gentityService.getGameEntites(dc, queryInfo, mentity);
		//System.out.println( "pgInfo:" + gson.toJson( pqInfo ) );
		pqInfo.setMentity(mentity);
		request.setAttribute("pqinfo", pqInfo);
		request.setAttribute("query_field", queryField);
		request.setAttribute("query_value", queryValue);
		request.setAttribute("mentity_id", mentity_id);
		request.setAttribute("sort_field1", sortField1);
		request.setAttribute("sort_type1", sortType1);
		return ("entity/list");

	}

	@RequestMapping(value = "/prequery")
	public String prequery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityWithRealValues(id);
		request.setAttribute("mentity", mentity);
		request.setAttribute("mentity_id", id);
		return "/entity/query";
	}

	@RequestMapping(value = "/preadd")
	public String preadd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityWithRealValues(id);
		request.setAttribute("mentity", mentity);
		request.setAttribute("mentity_id", id);
		return "/entity/add";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, IOException {
		String mentity_id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityById(mentity_id);
		Mentity addData = (Mentity) mentity.clone();
		List<Mfield> fields = addData.getFields();
		for (Mfield field : fields) {
			String value = "";
			//对multi_select的处理：将字符数组，转化为字符串
			if(field.getValueUitype() != FieldValueType.multi_select.ordinal()){
				value = request.getParameter(field.getName());
			}else{
				String[] values = request.getParameterValues(field.getName())==null? new String[]{} : request.getParameterValues(field.getName());
				StringBuilder sb = new StringBuilder();
				for (String string : values) {
					sb.append(string + ",");
				}
				if(sb.length() > 1){
					value = sb.substring(0, sb.length() - 1);
				}
			}
			Object res_value = RequestHelper.getFieldValue(field,
					value);
			field.setValue(res_value);
		}
		logger.info(">>>>>>>>>>>>Begin to add new entity of class:"+mentity.getEntityClass() );
		logger.info( "Mentity data:" + gson.toJson( fields) );
		HttpSession session = request.getSession();
		String adminName = (String)session.getAttribute( AuthConstant.LOGIN_USER_NAME );
		Mentity savedEntity = gentityService.addGameEntity(addData, adminName);

		if (savedEntity.getIdField().getValue() != null) {
			response.setContentType("text/html");
			response.getWriter().write(
					"Add enti ty succcessfully! entity key:"
							+ savedEntity.getIdField().getValue());
			return null;
		} else {
			response.setContentType("text/html");
			response.getWriter().write("Add entity failed!");
			return null;
		}
	}

	@RequestMapping(value = "/preedit")
	public String preedit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_gentity_id = request.getParameter("entity_id");
		String mentity_id = request.getParameter("mentity_id");
		//提取出entity类本生的信息和其field的信息
		Mentity mentity = mentityService.getMentityWithRealValues(mentity_id);
		
		//找出entity的主键相关信息
		Object gentity_id = RequestHelper.getFieldValue( mentity.getIdField(), s_gentity_id );
		mentity.getIdField().setValue(gentity_id);
		
		//给field中填充值
		Mentity mdata = gentityService.getGameEntity(mentity);
		//加入ajax_input操作
		//ajaxService.addAjaxElement(mentity);
		
		request.setAttribute("mentity_id", mentity_id);
		request.setAttribute("gameentity", mdata);
		return "entity/edit";
	}
	@RequestMapping(value = "/next")
	public String getNext(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_gentity_id = request.getParameter("entity_id");
		String mentity_id = request.getParameter("mentity_id");
		
		Mentity mentity = mentityService.getMentityWithRealValues(mentity_id);
		Object gentity_id = RequestHelper.getFieldValue( mentity.getIdField(), s_gentity_id );
		String modelClass = mentity.getEntityClass();
		Class clazz = Class.forName(modelClass);
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		List<Mfield> queryFields = mentity.getAllfields();
		dc.add(Restrictions.gt(mentity.getIdField().getName(), gentity_id));
		Mentity mdata = gentityService.getNextGameEntity(dc, mentity);	
		
		request.setAttribute("mentity_id", mentity_id);
		request.setAttribute("gameentity", mdata);
		return "entity/edit";
	}
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, IOException {
		String gentity_id = request.getParameter("gameentity_id");
		String mentity_id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityById(mentity_id);

		List<Mfield> fields = mentity.getFields();
		logger.info("Begin to edit entity! game entity_id:" + gentity_id
				+ " class:" + mentity.getEntityClass());

		HashMap<String, Object> updatePropMap = new HashMap<String, Object>();
		for (Mfield field : fields) {
			String value = "";
			//对multi_select的处理：将字符数组，转化为字符串
			if(field.getValueUitype() != FieldValueType.multi_select.ordinal()){
				value = request.getParameter(field.getName());
			}else{
				String[] values = request.getParameterValues(field.getName())==null? new String[]{} : request.getParameterValues(field.getName());
				StringBuilder sb = new StringBuilder();
				for (String string : values) {
					sb.append(string + ",");
				}
				if(sb.length() > 1){
					value = sb.substring(0, sb.length() - 1);
				}
			}
			Object res_value = RequestHelper.getFieldValue(field,
					value);
			field.setValue(res_value);
		}
		Object res_value = RequestHelper.getFieldValue(mentity.getIdField()
				, gentity_id);
		mentity.getIdField().setValue(res_value);
		HttpSession session = request.getSession();
		String adminName = (String)session.getAttribute( AuthConstant.LOGIN_USER_NAME );
		gentityService.saveGameEntity(mentity, adminName);
		//logger.info( "Edit game entity, new properties:"+gson.toJson( mentity) );
		response.setContentType("text/html");
		response.getWriter().write("Update successfully!");
		return null;
	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, IOException {
		String mentity_id = request.getParameter("mentity_id");
		String gentity_id = request.getParameter("gameentity_id");
		Mentity mentity = mentityService.getMentityById(mentity_id);
		Object idValue = RequestHelper.getFieldValue(mentity.getIdField()
				, gentity_id);
		mentity.getIdField().setValue(idValue);
		logger.info("Begin to delete the game entity: entity_id" + gentity_id
				+ " class_id:" + mentity.getEntityClass());
		gentityService.deleteGameEntity(mentity);
		response.setContentType("text/html");

		response.getWriter().write("{\"result\":1 ,\"desc\": \"\"}");
		return null;
	}
	@RequestMapping(value = "/sysentity/preedit")
	public String sysentityPreEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mentity_id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityWithRealValues(mentity_id);
		Mentity sysdata = gentityService.getSysEntity(mentity);
		request.setAttribute("mentity_id", mentity_id);
		request.setAttribute("gameentity", sysdata);
		return "entity/sysedit";
	}
	/**
	 * 修改系统实体方法，逻辑和修改普通实体方法一样，分开只是为了方便权限控制
	 * @param request
	 * @param response
	 * @return

	 */
	@RequestMapping(value = "/sysentity/edit")
	public String sysentityEdit(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, IOException {
		String gentity_id = request.getParameter("gameentity_id");
		String mentity_id = request.getParameter("mentity_id");
		Mentity mentity = mentityService.getMentityById(mentity_id);

		List<Mfield> fields = mentity.getFields();
		logger.info("Begin to edit entity! game entity_id:" + gentity_id
				+ " class:" + mentity.getEntityClass());

		HashMap<String, Object> updatePropMap = new HashMap<String, Object>();
		for (Mfield field : fields) {
			String value = request.getParameter(field.getName());
			
			Object res_value = RequestHelper.getFieldValue(field,
					value);
			field.setValue(res_value);
		}
		Object res_value = RequestHelper.getFieldValue(mentity.getIdField()
				, gentity_id);
		mentity.getIdField().setValue(res_value);
		HttpSession session = request.getSession();
		String adminName = (String)session.getAttribute( AuthConstant.LOGIN_USER_NAME );
		gentityService.saveGameEntity(mentity, adminName);
		logger.info( "Edit game entity, new properties:"+gson.toJson( mentity) );
		response.setContentType("text/html");
		response.getWriter().write("Update successfully!");
		return null;
	}
}
