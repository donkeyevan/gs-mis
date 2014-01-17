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
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.service.MisGroupService;
import com.youai.gamemis.service.MisUserService;
import com.youai.gamemis.service.MisUsergroupService;
import com.youai.gamemis.util.MD5Util;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/misuser")
public class MisUserController {
	Logger logger = Logger.getLogger(MisUserController.class);
	@Autowired
	MisUserService userService;

	@Autowired
	MisUsergroupService userGrpService;

	@Autowired
	MisGroupService groupService;

	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException {
		List<MisUser> users = userService.list();
		request.setAttribute("users", users);
		return "misuser/list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException {
		String user_name = request.getParameter("name");
		String[] group_ids = request.getParameterValues("group_ids");
		if (group_ids.length == 0) {
			response.setContentType("text/html");
			response.getWriter().write("请选择至少一个群组！");
			return null;
		}
		String login_user_name = request.getSession().getAttribute(
				AuthConstant.LOGIN_USER_NAME) == null ? "" : (String) request
				.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		String userId = null;
		try {
			userId = userService.add(user_name, login_user_name);
		} catch (MisException e) {
			logger.error("", e);
			response.setContentType("text/html");
			response.getWriter().write(
					"用户名为：" + user_name + "的用户已经存在，请直接选择该用户进行编辑！");
			return null;
		}
		if( userId == null ){
			response.setContentType("text/html");
			response.getWriter().write("分配用户ID失败！");
			return null;
		}
		userGrpService.addUser( userId, group_ids, login_user_name);
		response.setContentType("text/html");
		response.getWriter().write("添加用户" + user_name + "成功！");
		return null;
	}

	@RequestMapping(value = "/preadd")
	public String preadd(HttpServletRequest request,
			HttpServletResponse response) throws MalformedURLException,
			IOException {
		List<MisGroup> groups = groupService.list();
		
		request.setAttribute("groups", groups);
		return "/misuser/add";
	}

	@RequestMapping(value = "/preedit")
	public String preedit(HttpServletRequest request,
			HttpServletResponse response) throws MalformedURLException,
			IOException {
		String userId = request.getParameter("id");
		MisUser user = userService.get(userId);
		List<MisGroup> misgroups = groupService.list();
		List<String> userGroupIds = userGrpService.getGrpIdsByUserId(userId);
		if (userGroupIds != null) {
			for (MisGroup misgroup : misgroups) {
				if (userGroupIds.contains(misgroup.getId())) {
					misgroup.setBelongTo(1);
				}
			}
		}
		logger.info("userId:"+userId);
		logger.info("misgroups:"+ gson.toJson( misgroups ) );
		logger.info( "userGroupIds" +gson.toJson( userGroupIds ) );
		request.setAttribute("groups", misgroups);
		request.setAttribute("user", user);
		return "misuser/edit";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String userId = request.getParameter("user_id");
		String[] group_ids = request.getParameterValues("group_ids");
		if (group_ids.length == 0) {
			response.setContentType("text/html");
			response.getWriter().write("请选择至少一个群组！");
			return null;
		}
		String login_user_name = request.getSession().getAttribute(
				AuthConstant.LOGIN_USER_NAME) == null ? "" : (String) request
				.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		
		userGrpService.deleteGrpIdsbyUserId( userId );
		userGrpService.addUser(userId, group_ids, login_user_name);
		response.setContentType("text/html");
		response.getWriter().write("修改用户所在群组成功！");
		return null; 
	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userId = request.getParameter("id");
		userService.delete( userId );
		userGrpService.deleteGrpIdsbyUserId( userId );
		response.setContentType("text/html");
		response.getWriter().write("删除id为"+userId+"的用户成功！");
		return null; 
	}

}
