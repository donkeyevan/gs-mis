package com.youai.gamemis.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danga.MemCached.MemCachedClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.model.MisGroup;
import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.service.MisGroupPrivService;
import com.youai.gamemis.service.MisGroupService;
import com.youai.gamemis.service.MisPrivService;
import com.youai.sysadmin.client.auth.AuthConstant;

@Controller
@RequestMapping("/misgroup")
public class MisGroupController {
	@Autowired
	MisGroupService groupService;
	@Autowired
	MisPrivService privService;
	@Autowired
	MisGroupPrivService grpPrivService; 

	@Autowired
	private MemCachedClient memcachedClient;
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@RequestMapping(value = "/add")
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException {
		String groupName = request.getParameter("name");
		String comment = request.getParameter("comment");
		String groupid = groupService.add(groupName, comment);
		response.setContentType("text/html");
		response.getWriter().write(
				"Add group successfully! group id:" + groupid);
		return null;
	}

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException {
		List<MisGroup> groups = groupService.list();
		request.setAttribute("groups", groups);
		return "misgroup/list";

	}

	@RequestMapping(value = "/preeditpriv")
	public String preeditpriv(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {
		String grpId = request.getParameter("id");
		MisGroup group = groupService.find( grpId );
		List<MisPrivilege> privs = privService.list();
		List<MisGrouppriv> grpPrivs = privService.getGroupPrivsByGrpId(grpId);
		Map<String,MisGrouppriv> grpPrivMap = new HashMap<String,MisGrouppriv>();
		for(MisGrouppriv grpPriv : grpPrivs ){
			grpPrivMap.put( grpPriv.getPrivId(), grpPriv );
		}
		if (grpPrivs != null) {
			for (MisPrivilege priv : privs) {
				if (grpPrivMap.containsKey(priv.getId())) {
					priv.setOwned(1);
					MisGrouppriv gpriv = grpPrivMap.get( priv.getId() );
					priv.setAddPriv( gpriv.getAddPriv() );
					priv.setDeletePriv( gpriv.getDeletePriv() );
					priv.setModifyPriv( gpriv.getModifyPriv() );
					priv.setQueryPriv( gpriv.getQueryPriv() );
				}
			}
		}
		request.setAttribute("privs", privs);
		request.setAttribute("group", group );
		return "misgroup/privedit";
	}
	
	@RequestMapping(value = "/privedit")
	public String privedit(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {
		String grpId = request.getParameter("group_id");
		String[] privIds = request.getParameterValues("priv_ids");
		
		String login_user_name = request.getSession().getAttribute(
				AuthConstant.LOGIN_USER_NAME) == null ? "" : (String) request
				.getSession().getAttribute(AuthConstant.LOGIN_USER_NAME);
		Collection<MisGrouppriv> grpPrivs = grpPrivService.parseMisGroupprivs( privIds );
		grpPrivService.deleteByGrpId(grpId);
		grpPrivService.add(grpId, grpPrivs, login_user_name);
		response.setContentType("text/html");
		response.getWriter().write(
				"Modify group priv successfully! group id:" + grpId);
		return null;
	}
	
	@RequestMapping(value = "/listuser")
	public String listuser(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {
		String grpId = request.getParameter("id");
		List<MisUser> users = groupService.getUsersByGrpId( grpId );
		request.setAttribute("users", users);
		return "misgroup/userlist";
	}

}
