package com.youai.gamemis.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.MisGroup;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.dao.MisGroupDAO;

@Service("misGroupService")
public class MisGroupService {
	@Autowired
	private MisGroupDAO groupDao;

	public List<MisGroup> list(){
		return groupDao.list();
	}
	
	public String add( String name, String comment ){
		MisGroup group = new MisGroup();
		group.setComment( comment );
		group.setCreateAt( new Date() );
		group.setName( name );
		group.setUpdateAt( new Date() );
		groupDao.save( group );
		return group.getId();
	}
	
	public MisGroup find(String id){
		return groupDao.find( id );
	}
	
	public List<MisUser> getUsersByGrpId( String grpId ){
		return groupDao.getUsersByGrpId(grpId);
	}
	
}
