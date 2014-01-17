package com.youai.gamemis.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.dao.MisGroupprivDAO;

@Service("misGroupPrivService")
public class MisGroupPrivService {
	
	@Autowired
	MisGroupprivDAO grpPrivDao;
	public void add(String grpId, String[] privIds, String createBy ){
		for(String privId : privIds ){
			MisGrouppriv grpPriv = new MisGrouppriv();
			grpPriv.setCreateAt(new Date());
			grpPriv.setCreateBy( createBy );
			grpPriv.setGroupId( grpId );
			grpPriv.setPrivId( privId );
			grpPrivDao.save( grpPriv );
		}
	}
	
	public void add(String grpId, Collection<MisGrouppriv> gprivs, String createBy ){
		for( MisGrouppriv gpriv : gprivs ){
			gpriv.setGroupId( grpId );
			gpriv.setCreateAt( new Date() );
			gpriv.setCreateBy( createBy );
			grpPrivDao.save( gpriv );
		}
	}
	public void deleteByGrpId( String grpId ){
		grpPrivDao.deleteGrpPriv( grpId );
	}
	
	public void deleteByPrivId( String privId ){
		grpPrivDao.deleteByPrivId( privId );
	}
	
	public Collection<MisGrouppriv> parseMisGroupprivs(String[] gprivs ){
		Map<String,MisGrouppriv> groupPrivMap = new HashMap<String,MisGrouppriv>();
		for(String sgpriv : gprivs ){
			String[] values = sgpriv.split("[_]+");
			if( values == null || values.length < 2 ) continue;
			String privId = values[0];
			String privFlag = values[1];
			MisGrouppriv gpriv = null;
			if( groupPrivMap.containsKey( privId ) ){
				 gpriv = groupPrivMap.get( privId );
				
			}else{
				gpriv = new MisGrouppriv();
				gpriv.setPrivId( privId );
				groupPrivMap.put( gpriv.getPrivId(), gpriv );
			}
			if( gpriv != null ){
				if( privFlag.equalsIgnoreCase("add") ){
					gpriv.setAddPriv(1);
				}else if( privFlag.equalsIgnoreCase("delete")){
					gpriv.setDeletePriv(1);
				}else if( privFlag.equalsIgnoreCase("query")){
					gpriv.setQueryPriv(1);
				}else if( privFlag.equalsIgnoreCase("modify")){
					gpriv.setModifyPriv(1);
				}
			}
		}
		return groupPrivMap.values();
	}
}
