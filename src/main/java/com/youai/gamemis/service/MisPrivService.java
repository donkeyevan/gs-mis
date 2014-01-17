package com.youai.gamemis.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.exception.MisException;
import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.dao.MisPrivilegeDAO;
import com.youai.gamemis.model.dao.MisUserDAO;
import com.youai.gamemis.model.dao.MisUsergroupDAO;

@Service("misPrivService")
public class MisPrivService {
	
	@Autowired
	MisPrivilegeDAO privDao;
	@Autowired
	MisUsergroupDAO userGrpDao;
	
	public List<MisPrivilege> list(){
		List<MisPrivilege> privs = privDao.list();
		return privs;
	}
	
	public String add(String name, String url, int position, String create_by, String mentity_id, int parentNavId ) throws MisException{
		MisPrivilege priv = new MisPrivilege();
		priv.setCreateAt(new Date());
		priv.setCreateBy( create_by );
		priv.setUrl( url );
		priv.setName( name );
		priv.setPosition( position );
		priv.setMentityId( mentity_id );
		priv.setParentNavId(parentNavId );
		return privDao.save( priv );
		
	}
	
	public MisPrivilege get( String privId ){
		return privDao.find( privId );
	}
	
	public void delete( String privId ){
		privDao.delete( privId );
	}
	public void update( MisPrivilege priv ){
		privDao.update( priv );
	}
	
	public List<String> getPrivIdsByGrpId(String grpId ){
		return privDao.getPrivIdsByGrpId( grpId );
	}
	
	
	public List<String> getPrivIdsByUserId( String userId ){
		List<String> grpIds = userGrpDao.getGrpIdsByUserId( userId );
		List<String> privIds = new ArrayList<String>();
		for(String grpId : grpIds ){
			List<String> grpPrivIds = privDao.getPrivIdsByGrpId( grpId );
			privIds.addAll( grpPrivIds );
		}
		return privIds;
	}
	public Collection<MisGrouppriv> getGroupPrivsByUserId( String userId ){
		List<String> grpIds = userGrpDao.getGrpIdsByUserId( userId );
		HashMap<String,MisGrouppriv> privMap = new HashMap<String,MisGrouppriv>();
		for(String grpId : grpIds ){
			List<MisGrouppriv> grpPrivs = this.getGroupPrivsByGrpId( grpId );
			if( grpPrivs != null && grpPrivs.size() > 0 ){
				for(MisGrouppriv grpPriv : grpPrivs ){
					if( privMap.containsKey( grpPriv.getPrivId() ) ){
						MisGrouppriv item = privMap.get( grpPriv.getPrivId() );
						if( grpPriv.getAddPriv() == 1 ){ item.setAddPriv(1);}
						if( grpPriv.getDeletePriv() == 1 ){ item.setDeletePriv(1); }
						if( grpPriv.getModifyPriv() == 1){ item.setModifyPriv(1); }
						if( grpPriv.getQueryPriv() == 1){ item.setQueryPriv( 1) ; }
						
						
					}else{
						privMap.put( grpPriv.getId(), grpPriv );
					}
				}
			}
		}
		return privMap.values();
		
	}
	
	public List<MisGrouppriv> getGroupPrivsByGrpId(String grpId ){
		return privDao.getGroupPrivsByGrpId( grpId );
	}
	
	public List<String> getPrivIds(){
		return privDao.getPrivIds();
	}
	
	
	public Map<String,MisPrivilege> getPrivsMap(){
		Map<String,MisPrivilege> privMap = new HashMap<String,MisPrivilege>();
		List<MisPrivilege> privs = privDao.list();
		for( MisPrivilege priv : privs ){
			privMap.put( priv.getId(), priv);
		}
		return privMap;
	}
	
	public void deleteByMentityId( String mentityId ){
		privDao.deleteByMentityId(mentityId );
	}
	
	public MisPrivilege getByMentityId( String mentityId ){
		return privDao.getByMentityId( mentityId );
	}
	
}
