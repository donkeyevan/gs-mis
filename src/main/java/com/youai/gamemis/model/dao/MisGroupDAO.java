package com.youai.gamemis.model.dao;

// Generated 2011-4-22 11:53:30 by Hibernate Tools 3.3.0.GA

import java.util.List;

import javax.annotation.Resource;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.MisGroup;
import com.youai.gamemis.model.MisUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class MisGroup.
 * @see com.youai.gamemis.model.MisGroup
 * @author Hibernate Tools
 */
@Transactional
@Repository("MisGroupDAO")
public class MisGroupDAO {

	private static final Log log = LogFactory.getLog(MisGroupDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public List<MisGroup> list(){
		List<MisGroup> misgroups = sessionFactory.getCurrentSession().createCriteria( MisGroup.class ).list();
		return misgroups;
	}
	
	public void save(MisGroup group ){
		sessionFactory.getCurrentSession().save( group );
	}
	
	public MisGroup find(String id){
		return (MisGroup)sessionFactory.getCurrentSession().get( MisGroup.class, id);
	}
	
	public List<MisUser> getUsersByGrpId( String grpId) {
		return (List<MisUser>)sessionFactory.getCurrentSession().createSQLQuery("select a.* from mis_user a, mis_usergroup b where a.id=b.user_id and b.group_id=?")
			.addEntity( MisUser.class ).setString(0, grpId).list();
		
	}
	
}
