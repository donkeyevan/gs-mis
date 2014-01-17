package com.youai.gamemis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import playercenter.model.GameServerInfo;
import playercenter.service.GameServerInfoService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.cache.CacheClientProxy;
import com.youai.gamemis.constants.AppConstant;
import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.ParentNav;
import com.youai.gamemis.model.dao.ParentNavDAO;
import com.youai.gamemis.service.MisPrivService;
import com.youai.gamemis.service.MisUserService;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/index")
public class IndexController {
	Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	MisPrivService privService;
	@Autowired
	MisUserService userService;
	@Autowired
	GameServerInfoService gameServerInfoService;
	@Autowired
	ParentNavDAO parentNavDAO;
	@Autowired
	CacheClientProxy cacheClient;
	private Gson gson = new GsonBuilder().serializeNulls().create();
	@RequestMapping(value = "/show")	
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//cacheClient.get( GameConfig.C_ADMIN_PRIVIDS_KEY );
		List<MisPrivilege> privs = null;
		List<String> privIds = null;
		HttpSession session = request.getSession();
		//privs = (List<MisPrivilege>)session.getAttribute( GameConfig.S_ADMIN_PRIV_KEY );
		MisUser user = null;
		user = (MisUser)session.getAttribute( GameConfig.S_LOGIN_ADMIN_KEY );
		List<ParentNav> parentNavs = parentNavDAO.getAll();
		session.setAttribute(AppConstant.SESSION_PARENT_NAV_KEY, parentNavs );
		if( user == null ){
			//获取所有权限的map
			Map<String,MisPrivilege> privMap = null;
			if( cacheClient.keyExists( GameConfig.C_PRIVS_MAP_KEY ) ){
				logger.info("Get priv map from cache!");
				privMap = (Map<String,MisPrivilege>)cacheClient.get( GameConfig.C_PRIVS_MAP_KEY );
			}
			if( privMap == null ){
				logger.info("Get priv map from db!");
				privMap = privService.getPrivsMap();
				cacheClient.set( GameConfig.C_PRIVS_MAP_KEY, privMap );
			}
			
			privs = new ArrayList<MisPrivilege>();
			//获取用户信息
			String userName = (String)request.getSession().getAttribute( AuthConstant.LOGIN_USER_NAME );
			if( userName != null && userName.equalsIgnoreCase(GameConfig.SUPER_ADMIN_NAME) ){
				logger.info(">>>>super admin logined!<<<<");
				user = new MisUser();
				user.setIsSuperAdmin( 1 );
				user.setName( userName );
				user.setId( "superadmin" );	
				//privIds = privService.getPrivIds();
				//获得所有权限
				Collection<MisPrivilege> allPrivs = privMap.values();
				for( MisPrivilege priv : allPrivs ){
					priv.setRealUrl( request.getContextPath() + priv.getUrl() );
					priv.setAddPriv(1);
					priv.setDeletePriv(1);
					priv.setModifyPriv(1);
					priv.setQueryPriv(1);
					privs.add( priv );
				}
			}else{
				//如果用户没有在改系统中注册为管理员，则直接提示并清除session
				user = userService.getUserByName( userName );
				if( user == null ){
					session.setAttribute( AuthConstant.IS_LOGIN_KEY, 0);
					session.setAttribute( AuthConstant.LOGIN_USER_ID, "" );
					session.setAttribute( AuthConstant.LOGIN_USER_NAME, "" );
					session.setAttribute( GameConfig.S_LOGIN_ADMIN_KEY, null );
					session.setAttribute( GameConfig.S_ADMIN_PRIV_KEY, null );
					response.setContentType("text/html");				
					response.getWriter().write("User "+userName+" has no privilege to manage this game mis! Please contact the manager to assign the privilege!<a href='"+
							request.getContextPath()+"/userauth/logout'>click here to login once again</a>");
					return null;
				}
				String userId = user.getId();
				Collection<MisGrouppriv> grpPrivs = privService.getGroupPrivsByUserId(userId);
				//获取用户的权限
				
				for(MisGrouppriv groupPriv : grpPrivs ){
						if( privMap.containsKey( groupPriv.getPrivId() ) ){
							MisPrivilege misPriv = privMap.get(  groupPriv.getPrivId() );
							misPriv.setRealUrl( request.getContextPath() + misPriv.getUrl() );
							if( groupPriv.getAddPriv() == 1 ) { misPriv.setAddPriv(1); } else{ misPriv.setAddPriv(0); }
							if( groupPriv.getDeletePriv() == 1) { misPriv.setDeletePriv(1); }else{ misPriv.setDeletePriv(0); }
							if( groupPriv.getModifyPriv() == 1) { misPriv.setModifyPriv(1); }else{ misPriv.setModifyPriv(0); }
							if( groupPriv.getQueryPriv() == 1) { misPriv.setQueryPriv(1); }else{ misPriv.setQueryPriv(0); }
							privs.add( misPriv );
						
						}
				}
			}
			
			
			
			session.setAttribute( GameConfig.S_LOGIN_ADMIN_KEY, user );	
			
			this.typePriv( privs, parentNavs, session );
			//session.setAttribute(  GameConfig.S_ADMIN_PRIV_KEY, privs);
			//logger.info( "xxxxx" + gson.toJson( privs ) );
			//logger.info( "yyyyy" + gson.toJson( user ) );
		}else{
		
			if( user.getIsSuperAdmin() == 1){
				privs = privService.list();
				for(MisPrivilege priv:privs ){
					priv.setRealUrl( request.getContextPath() + priv.getUrl() );
				}
				this.typePriv( privs, parentNavs, session );
				session.setAttribute( GameConfig.S_LOGIN_ADMIN_KEY, user );	
				
				//session.setAttribute(GameConfig.S_ADMIN_PRIV_KEY,  privs);
				
			}
		}
		//初始化所有的gameServer服务器
		if(session.getAttribute(AppConstant.SESSION_SERVERS_KEY) == null){
			List<GameServerInfo> servers = gameServerInfoService.getGameServers();
			session.setAttribute(AppConstant.SESSION_SERVERS_KEY, servers );
			//the test server's id is 2
			session.setAttribute(AppConstant.SESSION_DEAULT_SERVER_IDX_KEY, 2 );
		}
		
		return "common/index";
			
		
	}
	
	private  void typePriv(List<MisPrivilege> privs, List<ParentNav> parentNavs, HttpSession session ){
		Map<Integer,MisPrivilege> navMap = new HashMap<Integer,MisPrivilege>();
		if( parentNavs != null ){
			for(ParentNav nav : parentNavs ){
				MisPrivilege parentPriv = new MisPrivilege();
				parentPriv.setPrivs(  new ArrayList<MisPrivilege>() );
				parentPriv.setPosition( nav.getPosition() );
				parentPriv.setName( nav.getName() );
				parentPriv.setNavType(1);
				navMap.put( nav.getIdx(),  parentPriv );
			}
		}
		
		List<MisPrivilege> resultPrivs = new ArrayList<MisPrivilege>();
		for(MisPrivilege priv : privs ){
			if( priv.getParentNavId()  != null && priv.getParentNavId() > 0 ){
				if( navMap.containsKey( priv.getParentNavId() ) ){
					navMap.get( priv.getParentNavId() ).getPrivs().add( priv );
				}
			}else{
				resultPrivs.add( priv );
			}
		}
		resultPrivs.addAll( navMap.values() );
		Collections.sort( resultPrivs, new Comparator<MisPrivilege>(){

			public int compare(MisPrivilege arg0, MisPrivilege arg1) {
				if( arg0.getPosition() == null )  arg0.setPosition(0);
				if( arg1.getPosition() == null )  arg1.setPosition(0);
				if( arg0.getPosition() > arg1.getPosition() ) return -1;
				if( arg0.getPosition() < arg1.getPosition() ) return 1;
				return 0;
				
			}
			
		});
		session.setAttribute( GameConfig.S_ADMIN_PRIV_KEY, resultPrivs );
		
	}
}
