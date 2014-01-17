package com.youai.gamemis.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.service.MisGroupService;
import com.youai.gamemis.service.MisUserService;
import com.youai.gamemis.service.MisUsergroupService;
import com.youai.gamemis.util.MD5Util;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/passwd")
public class ModityPasswdController {
	Logger logger = Logger.getLogger(ModityPasswdController.class);
	@Autowired
	MisUserService userService;

	@Autowired
	MisUsergroupService userGrpService;

	@Autowired
	MisGroupService groupService;

	@RequestMapping(value = "/modify")
	public String modifyPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userName = (String)request.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		String oldPasswd = request.getParameter("oldPasswd");
		String passwd = request.getParameter("passwd");
		MisUser user = userService.getUserByName(userName);
		response.setContentType("text/html");
		if(user == null){
			response.getWriter().write("用户不存在，请联系管理员！");
			return null; 
		}
		if(!MD5Util.toMD5(oldPasswd.trim()).equals(user.getPassword())){
			response.getWriter().write("原始密码错误，请重新输入！");
			return null; 
		}
		user.setPassword(MD5Util.toMD5(passwd.trim()));
		userService.update(user);
		response.getWriter().write("用户" + user.getName() + "密码修改成功！");
		return null; 
	}
}
