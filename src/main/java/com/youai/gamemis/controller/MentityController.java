package com.youai.gamemis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.exception.MisException;
import com.youai.gamemis.model.FieldValueType;
import com.youai.gamemis.model.Mentity;
import com.youai.gamemis.model.Mfield;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.ParentNav;
import com.youai.gamemis.model.dao.ParentNavDAO;
import com.youai.gamemis.service.ManagedEntityService;
import com.youai.gamemis.service.MisGroupPrivService;
import com.youai.gamemis.service.MisPrivService;
import com.youai.gamemis.util.RequestHelper;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/mentity")
public class MentityController {
	protected static Logger logger = Logger
			.getLogger(GameEntityController.class);

	@Resource(name = "managedEntityService")
	private ManagedEntityService mentityService;
	@Autowired
	private MisPrivService privService;
	@Autowired
	private MisGroupPrivService grpPrivService;
	@Autowired
	private ParentNavDAO parentNavDAO;
	/**
	 * Handles and retrieves all persons and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listMEntities(HttpSession httpSession, Model model) {
		List<Mentity> entities = mentityService.list();
		model.addAttribute("mentities", entities);
		httpSession.setAttribute("mentities", "entities");
		return "mentity/list";
	}

	@RequestMapping(value = "/nav", method = RequestMethod.GET)
	public String getNavEntities(HttpSession httpSession, Model model) {
		logger.info(">>>>>Begin to fetch nav info!");
		List<Mentity> entities = mentityService.list();		
		model.addAttribute("mentities", entities);
		return "common/left";
	}

	@RequestMapping(value = "/preedit", method = RequestMethod.GET)
	public String preEdit(@RequestParam("mentity_id") String mentityId,
			Model model) {
		
		Mentity mentity = mentityService.getMentityById(mentityId);
 		List<Mfield> lastFields = mentityService.getMfields( mentity.getEntityClass() );	
		List<Mfield> oldFields = mentity.getFields();
		List<Mfield> resFields = new ArrayList<Mfield>();
		Map<String,Mfield> mfmap = new HashMap<String,Mfield>();
		for( Mfield field : oldFields ){
			mfmap.put( field.getName(), field );
		}
		mfmap.put( mentity.getIdField().getName(), mentity.getIdField() );
		for(Mfield lastfield : lastFields ){
			if( mfmap.containsKey( lastfield.getName()) ){
				Mfield oldField = mfmap.get( lastfield.getName());
				oldField.setFieldValues( HtmlUtils.htmlEscape( oldField.getFieldValues()));
				resFields.add( oldField );
			}else{
				lastfield.setNickname( lastfield.getName() );
				lastfield.setValueUitype(0 );
				resFields.add( lastfield );
			}
			
		}
		mentity.setExtendLink(  mentity.getExtendLink()  );
		Collections.sort( resFields );
		
		
		mentity.setFields( resFields );
		List<ParentNav> parentNavs = parentNavDAO.getAll();
		model.addAttribute("parentNavs", parentNavs);
		model.addAttribute("mentity", mentity);
		return "mentity/edit";
	}

	@RequestMapping(value = "/getfields", method = RequestMethod.GET)
	public String getFieldsByClassPath(
			@RequestParam("model_class") String modelClassPath,
			HttpServletResponse response) throws IOException {

		logger.info("model_class_path:" + modelClassPath);
		List<Mfield> mfields = mentityService.getMfields(modelClassPath);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String fieldsJson = gson.toJson(mfields);
		response.setContentType("text/html");
		response.getWriter().write(fieldsJson);
		return null;
	}
	@RequestMapping(value = "/preadd")
	public String preadd(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, MisException {
		List<ParentNav> parentNavs = parentNavDAO.getAll();
		request.setAttribute("parentNavs", parentNavs );
		return "/mentity/add";
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, MisException {

		Gson gson = new GsonBuilder().serializeNulls().create();
		logger.info("entityName:" + request.getParameter("entityName"));
		String modelClassPath = (String) request.getParameter("entityClass");
		logger.info("Add entity into management, model_class:"+ modelClassPath);
		List<Mfield> mfields = mentityService.getMfields(modelClassPath);
		Mentity mentity = new Mentity();
		for (Mfield mfield : mfields) {
			
			if( mfield.getIsKey() == 1 ){
				mentity.setIdField( mfield );
			}
			String nickName = (String) request.getParameter(mfield.getName());
			if (nickName == null) {
				nickName = mfield.getName();
			}
			mfield.setNickname(nickName);
			String value_type = (String) request.getParameter(mfield.getName()
					+ "_type");
			mfield.setValueUitype( Integer.valueOf( value_type) );
			FieldValueType valueType = FieldValueType.values()[ Integer.valueOf( value_type )];
			if( valueType == FieldValueType.select ){
				String values = (String) request.getParameter(mfield.getName()
						+ "_values");
				mfield.setFieldValues(values);

			}else if( valueType == FieldValueType.custom_select ){
				String values = (String) request.getParameter(mfield.getName()
						+ "_values");
				mfield.setFieldValues(values);
			}else if( valueType == FieldValueType.ajax_select ){
				String values = (String) (request.getParameter(mfield.getName()
						+ "_values")==null? "" : request.getParameter(mfield.getName()
								+ "_values"));
				mfield.setFieldValues(values);
				String relatedInput = request.getParameter(mfield.getName()+ "_ajax_related_input");
				mfield.setAjaxRelatedInput(relatedInput);
				String ajaxUrl = request.getParameter(mfield.getName()+ "_ajax_url")==null? "":request.getParameter(mfield.getName() + "_ajax_url");
				mfield.setAjaxUrl(ajaxUrl);
				
			}
			else if( valueType == FieldValueType.datetime ){
				if( !RequestHelper.isEmpty(request.getParameter(mfield.getName() + "_takecurtime") ) ){
					mfield.setFieldValues("1");
				}else{
					mfield.setFieldValues("0");
				}
				
			} else {
				mfield.setFieldValues("");
			}
			
			String unmodified = request.getParameter( mfield.getName() + "_unmodified");
			if( RequestHelper.isEmpty(unmodified) ){
				mfield.setUnmodified( 0 );
			}else{
				mfield.setUnmodified( 1 );
			}
			
			//show order number
			String sNum = request.getParameter( mfield.getName() + "_num");
			int num = 0;
			if( sNum != null ){
				num = Integer.parseInt( sNum );
			}
			mfield.setNum( num );
		}
		if( RequestHelper.isEmpty( request.getParameter("isSystemConfig") ) ){
			mentity.setIsSysconfig( 0 );
		}else{
			mentity.setIsSysconfig( 1 );
		}
		int serverShard = RequestHelper.isEmpty( request.getParameter("serverShard") ) ? 0 : 1;
		Class clazz = Class.forName( modelClassPath );
		Table table =(Table)clazz.getAnnotation( Table.class );
		mentity.setCatalog( table.catalog() );
		mentity.setComment((String) request.getParameter("comment"));
		mentity.setEntityClass(modelClassPath);
		mentity.setEntityName((String) request.getParameter("entityName"));
		mentity.setExtendLink( request.getParameter("extendLink") );
		mentity.setFields(mfields);
		logger.info(gson.toJson(mentity));
		Mentity savedMentity = mentityService.addMentity(mentity);
		String mentity_id = savedMentity.getId();
		if (mentity_id != null) {
			//add the mis priv for this mentity manage
			String url  = null;
			if( mentity.getIsSysconfig() == 1){
				url = "/gameentity/sysentity/preedit?mentity_id="+mentity_id;
			}else{
				url = "/gameentity/prequery?mentity_id="+mentity_id;
			}
			String userName = (String)request.getSession().getAttribute( AuthConstant.LOGIN_USER_NAME );
			privService.add( mentity.getEntityName(), url, 0, userName, mentity_id, serverShard);
			
			response.setContentType("text/html");
			response.getWriter().write(
					"Add managed entity successfully! id:" + mentity_id);
			return null;
		}
		response.setContentType("text/html");
		response.getWriter().write("Failed to add amanged entity!");
		return null;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("mentity_id");
		mentityService.deleteMentity(id);
		//删除实体关联的权限数据
		privService.deleteByMentityId( id );
		grpPrivService.deleteByPrivId( id );
		request.setAttribute("result",
				"Delete managed entity difination successfully! key:" + id);
		return "common/success";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws IOException, MisException, ClassNotFoundException {
		String id = (String) request.getParameter("entity_id");
		Mentity mentity = mentityService.getMentityById(id);
		int parentNavId = Integer.parseInt( request.getParameter("parentNavId") );
		String modelClassPath = (String) request.getParameter("entityClass");
		logger.info("Edit managed entity model_class_path:" + modelClassPath);
		List<Mfield> mfields = mentityService.getMfields(modelClassPath);
		for (Mfield mfield : mfields) {
			mfield.setMentityId( id );
			String nickName = (String) request.getParameter(mfield.getName());
			if (nickName == null) {
				nickName = mfield.getName();
			}
			mfield.setNickname(nickName);
			String value_type = (String) request.getParameter(mfield.getName() + "_type");
			logger.info(">>>>>>value ui type:"+value_type );
			mfield.setValueUitype( Integer.valueOf( value_type) );
			FieldValueType valueType = FieldValueType.values()[ Integer.valueOf( value_type )];
			if( valueType == FieldValueType.select || valueType == FieldValueType.multi_select){				
				String values = (String) request.getParameter(mfield.getName() + "_values");
				mfield.setFieldValues(values);

			}  
			else if( valueType == FieldValueType.custom_select ){
				String values = (String) request.getParameter(mfield.getName()
						+ "_values");
				mfield.setFieldValues(values);
			}else if( valueType == FieldValueType.ajax_select ){
				String values = (String) (request.getParameter(mfield.getName()
						+ "_values")==null? "" : request.getParameter(mfield.getName()
								+ "_values"));
				mfield.setFieldValues(values);
				String relatedInput = request.getParameter(mfield.getName()+ "_ajax_related_input");
				mfield.setAjaxRelatedInput(relatedInput);
				String ajaxUrl = request.getParameter(mfield.getName()+ "_ajax_url")==null? "":request.getParameter(mfield.getName() + "_ajax_url");
				mfield.setAjaxUrl(ajaxUrl);
				
			}else if( valueType == FieldValueType.datetime ){
				if( !RequestHelper.isEmpty(request.getParameter(mfield.getName() + "_takecurtime") ) ){
					mfield.setFieldValues("1");
				}else{
					mfield.setFieldValues("0");
				}
				
			} else {
				mfield.setFieldValues("");
			}
			String unmodified = request.getParameter(  mfield.getName()+"_unmodified" );
			
			if( RequestHelper.isEmpty(unmodified)  ){
				mfield.setUnmodified( 0 );
			}else{
				mfield.setUnmodified( 1 );
			}
			//show order number
			String sNum = request.getParameter( mfield.getName() + "_num");
			int num = 0;
			if( sNum != null ){
				num = Integer.parseInt( sNum );
			}
			mfield.setNum( num );
		}
		Class clazz = Class.forName( modelClassPath );
		Table table =(Table)clazz.getAnnotation( Table.class );
		mentity.setCatalog( table.catalog() );
		mentity.setComment(request.getParameter("comment"));
		mentity.setEntityClass(modelClassPath);
		mentity.setEntityName(request.getParameter("entityName"));
		mentity.setExtendLink( request.getParameter("extendLink") );
		mentity.setParentNavId( parentNavId );
		mentity.setFields(mfields);
		mentityService.updateMentity(mentity);
		
		//如果实体对应的管理权限不存在，则新建一个权限
		MisPrivilege priv = privService.getByMentityId( id );
		if( priv == null ){
			String url  = null;
			if( mentity.getIsSysconfig() == 1){
				url = "/gameentity/sysentity/preedit?mentity_id="+id;
			}else{
				url = "/gameentity/prequery?mentity_id="+id;
			}
			String userName = (String)request.getSession().getAttribute( AuthConstant.LOGIN_USER_NAME );
			privService.add(mentity.getEntityName(), url, 0, userName, id, parentNavId);
		}else{//修改对应的权限
			String url  = null;
			priv.setName( mentity.getEntityName() );
			priv.setParentNavId( parentNavId );
			if( mentity.getIsSysconfig() == 1){
				url = "/gameentity/sysentity/preedit?mentity_id="+id;
			}else{
				url = "/gameentity/prequery?mentity_id="+id;
			}
			priv.setUrl(url);
			privService.update( priv );
		}
		response.setContentType("text/html");
		response.getWriter().write("Update successfully!");
		return null;
		
	}
	

}
