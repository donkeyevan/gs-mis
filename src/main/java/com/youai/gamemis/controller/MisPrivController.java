package com.youai.gamemis.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.exception.MisException;
import com.youai.gamemis.model.MisGroup;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.ParentNav;
import com.youai.gamemis.model.dao.ParentNavDAO;
import com.youai.gamemis.service.MisGroupPrivService;
import com.youai.gamemis.service.MisGroupService;
import com.youai.gamemis.service.MisPrivService;
import com.youai.gamemis.service.MisUserService;
import com.youai.gamemis.service.MisUsergroupService;
import com.youai.gamemis.util.RequestHelper;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/mispriv")
public class MisPrivController {
	Logger logger = Logger.getLogger(MisPrivController.class);
	@Autowired
	MisPrivService privService;
	@Autowired
	MisGroupPrivService grpPrivService;
	@Autowired
	private ParentNavDAO parentNavDAO;
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException {
		List<MisPrivilege> privs = privService.list();
		request.setAttribute("privs", privs);
		return "mispriv/list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException, MisException {
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		int position = RequestHelper.isEmpty(request.getParameter("position")) ? 0 : Integer.parseInt(request.getParameter("position") );
		int parentNavId = Integer.parseInt( request.getParameter("parentNavId") ) ;
		String mentityId = request.getParameter("mentity_id");
		String login_user_name = request.getSession().getAttribute(
				AuthConstant.LOGIN_USER_NAME) == null ? "" : (String) request
				.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		
		privService.add(name, url, position, login_user_name, mentityId, parentNavId);
		response.setContentType("text/html");
		response.getWriter().write("添加权限成功！");
		return null;
	}

	

	@RequestMapping(value = "/preedit")
	public String preedit(HttpServletRequest request,
			HttpServletResponse response) throws MalformedURLException,
			IOException {
		String privId = request.getParameter("id");
		MisPrivilege priv = privService.get( privId );
		
		request.setAttribute("priv", priv);
		return "mispriv/edit";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String privId = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String mentityId = request.getParameter("mentity_id");
		int parentNavId = Integer.parseInt( request.getParameter("parentNavId") ) ;
		int position = RequestHelper.isEmpty(request.getParameter("position")) ? 0 : Integer.parseInt(request.getParameter("position") );
		String login_user_name = request.getSession().getAttribute(
				AuthConstant.LOGIN_USER_NAME) == null ? "" : (String) request
				.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		MisPrivilege priv = privService.get( privId );
		if(priv == null ){
			response.setContentType("text/html");
			response.getWriter().write("ID为："+privId+"的权限不存在！");
			return null; 
		}
		priv.setPosition( position );
		priv.setUrl( url );
		priv.setName( name );
		priv.setMentityId(mentityId);
		priv.setParentNavId( parentNavId );
		privService.update( priv );
		
		response.setContentType("text/html");
		response.getWriter().write("修改权限成功！");
		return null; 
	}
	
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String privId = request.getParameter("id");
		privService.delete(privId);
		grpPrivService.deleteByPrivId( privId );
		response.setContentType("text/html");
		response.getWriter().write("删除权限成功！");
		return null;
	}
}
