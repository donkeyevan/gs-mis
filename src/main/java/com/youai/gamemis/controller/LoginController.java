package com.youai.gamemis.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.constants.AppConstant;
import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.service.MisUserService;
import com.youai.gamemis.util.MD5Util;
import com.youai.sysadmin.client.auth.AuthConstant;
import com.youai.sysadmin.client.auth.AuthResult;
import com.youai.sysadmin.client.auth.AuthResultValue;

@Controller
@RequestMapping("/userauth")
public class LoginController {

	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	AuthConstant authConstant;
	@Autowired
	GameConfig gameConfig;
	@Autowired
	MisUserService userService;

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String user_name = request.getParameter("user_name");
		String user_pass = request.getParameter("user_passwd");
		AuthResult authResult = null;
		if (gameConfig.getMode().equalsIgnoreCase("dev")) {
			// ////////////////for test//////////////////////
			authResult = new AuthResult();
			authResult.setResult(AuthResultValue.AUTH_SUCCESS);
			authResult.setUserId(1);
			// //////////////////////////////////////////////
		} else {
			String post_data = "user_name=" + user_name + "&user_passwd="
					+ user_pass;
			/*con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con
					.getOutputStream());
			writer.write(post_data);
			writer.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(con
					.getInputStream()));
			StringBuffer sb = new StringBuffer();

			// read answer
			String inputline;
			while ((inputline = in.readLine()) != null)
				sb.append(inputline);
			in.close();
			String recStr = sb.toString();
			
			authResult = gson.fromJson(recStr, AuthResult.class);*/
			if(user_name != null && !"".equals(user_name)){
				if((user_name.equals(GameConfig.SUPER_ADMIN_NAME) && MD5Util.toMD5(user_pass).equals("1e57dcda62a1f451483c0747ec1a6845"))){
					authResult = new AuthResult();
					authResult.setResult(AuthResultValue.AUTH_SUCCESS);
					authResult.setUserId(1);
				}else{
					MisUser user = userService.getUserByName(user_name);
					if(user != null && user.getPassword().equals(MD5Util.toMD5(user_pass.trim()))){
						authResult = new AuthResult();
						authResult.setResult(AuthResultValue.AUTH_SUCCESS);
					}else{
						authResult = new AuthResult();
						authResult.setResult(AuthResultValue.USER_PASSWORD_WRONG);
						authResult.setFailReason("wrong pwsscode");
					}
				}
			}else{
				authResult = new AuthResult();
				authResult.setResult(AuthResultValue.WRONG_FORMAT);
				authResult.setFailReason("wrong format");
			}

		}

		if (authResult.getResult() == AuthResultValue.AUTH_SUCCESS) {
			HttpSession sess = request.getSession();
			sess.setAttribute(AuthConstant.IS_LOGIN_KEY,
					AuthConstant.IS_LOGIN_FLAG);
			sess.setAttribute(AuthConstant.LOGIN_USER_ID, authResult
					.getUserId());
			sess.setAttribute(AuthConstant.LOGIN_USER_NAME, user_name);
			logger.info("User: " + user_name
					+ " has been logined in successfully!");
		}
		String json = gson.toJson(authResult);
		response.setContentType("text/html");
		response.getWriter().write(json);
		return null;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws MalformedURLException,
			IOException {
		HttpSession session = request.getSession();
		session.setAttribute(AuthConstant.IS_LOGIN_KEY, 0);
		session.setAttribute(AuthConstant.LOGIN_USER_ID, "");
		session.setAttribute(AuthConstant.LOGIN_USER_NAME, "");
		session.setAttribute(GameConfig.S_LOGIN_ADMIN_KEY, null);
		session.setAttribute(GameConfig.S_ADMIN_PRIV_KEY, null);
		session.setAttribute( AppConstant.SESSION_DEAULT_SERVER_IDX_KEY, -1 );
		session.setAttribute( AppConstant.SESSION_DEAULT_SERVER_NAME_KEY, "无" );
		session.setAttribute(AppConstant.SESSION_SERVERS_KEY, null );
		return "redirect:/jsp/user/login";
	}
}
