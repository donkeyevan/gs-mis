package com.youai.gamemis.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.MisUsergroup;
import com.youai.gamemis.model.dao.MisUsergroupDAO;

@Service("misUsergroupService")
public class MisUsergroupService {
	@Autowired
	MisUsergroupDAO userGrpDao;
	
	public boolean addUser(String user_id, String[] grp_ids, String create_by){
		
		for( String grp_id : grp_ids ){
			MisUsergroup usergroup = new MisUsergroup();
			usergroup.setGroupId( grp_id );
			usergroup.setUserId( user_id );
			usergroup.setCreateAt(new Date() );
			usergroup.setCreateBy( create_by );
			usergroup.setUpdateAt( new Date() );
			usergroup.setUpdateBy( create_by );
			userGrpDao.save( usergroup );
			
		}
		return true;
	}
	
	public void deleteGrpIdsbyUserId( String userId ){
		 userGrpDao.deleteGrpIdsByUserId( userId );
	}
	
	public List<String> getGrpIdsByUserId(String userId ){
		return userGrpDao.getGrpIdsByUserId(userId);
	}
}
