package com.youai.sysadmin.client.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.MisPrivilege;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = Logger.getLogger( AuthInterceptor.class );
	private String loginPage;
	public String getLoginPage() {
		return loginPage;
	}
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession sess = request.getSession();
		
		Gson gson = new GsonBuilder().create();
		logger.info( "userid:"+ request.getSession().getAttribute(AuthConstant.LOGIN_USER_ID) +" user:" + 
				request.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME) +" request url:"+ request.getRequestURL() +
				" context path:" + request.getContextPath() + "requestUri:"+request.getRequestURI()+" query string:"+request.getQueryString()  );
		logger.info( "============begin parameters:============="  );
		logger.info( gson.toJson( request.getParameterMap()) );
		logger.info( "============end parameters:============="  );
		Integer is_login = sess.getAttribute(AuthConstant.IS_LOGIN_KEY) == null ? 0
					: (Integer) sess.getAttribute(AuthConstant.IS_LOGIN_KEY);
		if (is_login != AuthConstant.IS_LOGIN_FLAG) {
			response.sendRedirect( this.loginPage);			
			return false;
		}
		List<MisPrivilege> navPrivs = (List<MisPrivilege>)sess.getAttribute(  GameConfig.S_ADMIN_PRIV_KEY );
		List<MisPrivilege> privs = new ArrayList<MisPrivilege>();
		for( MisPrivilege entry : navPrivs  ){
			if( entry.getPrivs() != null  && entry.getPrivs().size() > 0 ){
				privs.addAll( entry.getPrivs() );
			}else{
				privs.add( entry );
			}
		}
		String privUri = request.getRequestURI();
		String queryString = request.getQueryString();
		String userName = (String)request.getSession().getAttribute( AuthConstant.LOGIN_USER_NAME );
		logger.info("checked Url "+ privUri );
		boolean found = this.checkActionPriv( privUri,queryString, userName, privs,request);
		
		
		if( !found ){
			logger.warn("Not found the access priv for u:"+ request.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME) + 
					" for limit access uri:"+ privUri );
			response.sendRedirect("/resources/html/noprivilege.html"); 
			return false;
		}
		return true;
	}
	
	private boolean checkActionPriv(String privUri, String queryString, String userName, List<MisPrivilege> privs,  
			HttpServletRequest request){
		Map<String,MisPrivilege> privMap = new HashMap<String,MisPrivilege>();
		for(MisPrivilege priv : privs ){
			logger.info( "owned priv url:" + priv.getUrl() );
			privMap.put( priv.getUrl(), priv );
		}
		MisPrivilege priv  = null;
		String comparedPrivUrl = "";
		if(userName == null ){
			logger.warn( "user name is null!");
			return false;
		}
		if( userName.endsWith(GameConfig.SUPER_ADMIN_NAME)){
			priv = new MisPrivilege();
			priv.setAddPriv(1);
			priv.setDeletePriv(1);
			priv.setModifyPriv(1);
			priv.setDeletePriv(1);
			priv.setQueryPriv(1);
			request.getSession().setAttribute("currentPriv", priv );
			return true;
		}
		
		if(privUri.contains("/gameentity") ){
			if( privUri.contains("/gameentity/sysentity") ){
				String mentityId = request.getParameter("mentity_id");
				comparedPrivUrl = "/gameentity/sysentity/preedit?mentity_id="+mentityId;
			}else{
				String mentityId = request.getParameter("mentity_id");
				comparedPrivUrl = "/gameentity/prequery?mentity_id="+mentityId;
			}
		}else if( privUri.contains("/redis") ){
			comparedPrivUrl = "/jsp/redis/query";
		}else if( privUri.contains("/mcache") ){
			comparedPrivUrl = "/jsp/mcache/mcache";
		}
		else if( privUri.contains("/feedback")){
			comparedPrivUrl = "/feedback/thread/prequery";
		}
		else if( privUri.contains("/achievement")){
			comparedPrivUrl = "/achievement/main";
		}
		else if( privUri.contains("/passwd/")){
			comparedPrivUrl = "/jsp/passwd/main";
		}
		else{
			comparedPrivUrl = privUri;
		}
		
		priv = privMap.get( comparedPrivUrl );
		if(priv == null ) {
			logger.warn("Not found priv for uri:"+comparedPrivUrl );
			return false;
		}
		request.getSession().setAttribute("currentPriv", priv );
		
		
		logger.info( "found matched priv url:" + priv.getUrl() );
		if( priv.getUrl().startsWith("/gameentity") ){
			if( privUri.startsWith("/gameentity/sysentity/preedit") ){
				if(  priv.getQueryPriv() != null && priv.getQueryPriv() == 1){
					return true;
				}
				return false;
			}
			if( privUri.startsWith("/gameentity/sysentity/edit") ){
				if(  priv.getModifyPriv() != null && priv.getModifyPriv() == 1){
					return true;
				}
				return false;
			}
			if( privUri.startsWith("/gameentity/query") ){
				if( priv.getQueryPriv() != null && priv.getQueryPriv() == 1 ){
					return true;
				}else{
					return false;
				}
			}
			if( privUri.startsWith("/gameentity/add") ){
				if( priv.getAddPriv() != null && priv.getAddPriv() == 1 ){
					return true;
				}else{
					return false;
				}
			}
			if( privUri.startsWith("/gameentity/delete") ){
				if( priv.getDeletePriv() != null && priv.getDeletePriv() == 1 ){
					return true;
				}else{
					return false;
				}
			}
			if( privUri.startsWith("/gameentity/edit") ){
				if( priv.getModifyPriv() != null && priv.getModifyPriv() == 1 ){
					return true;
				}else{
					return false;
				}
			}
			
			
		}else{
			//redis action
			if( priv.getUrl().startsWith("/jsp/redis/query") ){
				if( privUri.startsWith("/redis/query/key") || privUri.startsWith("/redis/query/keyjson") ){
					if( priv.getQueryPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/redis/delete/key") ){
					if( priv.getDeletePriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/redis/command") ){
					if( priv.getModifyPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
			}
			//redis action
			if( priv.getUrl().startsWith("/jsp/mcache/mcache") ){
				if( privUri.startsWith("/mcache/query/key") ){
					if( priv.getQueryPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/mcache/delete/key") || privUri.startsWith("/mcache/delete/all") ){
					if( priv.getDeletePriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/mcache/add/key") ){
					if( priv.getAddPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
			}
			//mis group
			if(priv.getUrl().startsWith("/misgroup")){
				if( privUri.startsWith("/misgroup/add") ){
					if( priv.getAddPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/misgroup/privedit") ){
					if( priv.getModifyPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/redis/list") ){
					if( priv.getQueryPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				
			}
			//mis user
			if(priv.getUrl().startsWith("/misuser")){
				if( privUri.startsWith("/misuser/add") ){
					if( priv.getAddPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/misuser/edit") ){
					if( priv.getModifyPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/misuser/delete") ){
					if( priv.getDeletePriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				if( privUri.startsWith("/misuser/list") ){
					if( priv.getQueryPriv() == 1 ){
						return true;
					}else{
						return false;
					}
				}
				
			}
		}
		
		//feedback 
		if( priv.getUrl().startsWith("/feedback")){
			if( privUri.startsWith("/feedback/msg/delete") ){
				if( priv.getDeletePriv() == 1){
					return true;
				}else{
					return false;
				}
			}
	
			if( privUri.startsWith("/feedback/thread/batchtype") || privUri.startsWith("/feedback/thread/batchanswer") ||
					privUri.startsWith("/feedback/thread/status") || privUri.startsWith("/feedback/thread/answer") ||
					privUri.startsWith("/feedback/message/send") || privUri.startsWith("/feedback/thread/batchstatus") ){
				if( priv.getModifyPriv() == 1){
					return true;
				}else{
					return false;
				}
			}
			
			if( privUri.startsWith("/feedback/msg/list") || privUri.startsWith("/feedback/thread/query") ||
					privUri.startsWith("/feedback//thread/preanswer") || privUri.startsWith("/feedback/player/info")){
				if( priv.getQueryPriv() == 1){
					return true;
				}else{
					return false;
				}
			}
		}
		
		if( priv.getUrl().startsWith("/achievement")){
			if( privUri.startsWith("/achievement/query") ){
				if( priv.getQueryPriv() == 1){
					return true;
				}else{
					return false;
				}
			}
		}
		if( priv.getUrl().startsWith("/jsp/passwd/")){
			if( privUri.startsWith("/jsp/passwd/modify") ){
				if( priv.getModifyPriv() == 1){
					return true;
				}else{
					return false;
				}
			}
		}
		logger.info("DO not match any limit uri, then allow access uri:"+privUri );
		return true;
			
		
	}
}
