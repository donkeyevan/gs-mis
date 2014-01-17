package com.youai.gamemis.model.dao;

// Generated 2011-4-22 11:53:30 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.MisUsergroup;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class MisUsergroup.
 * @see com.youai.gamemis.model.MisUsergroup
 * @author Hibernate Tools
 */
@Transactional
@Repository("MisUsergroupDAO")
public class MisUsergroupDAO {

	private static final Log log = LogFactory.getLog(MisUsergroupDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public String save( MisUsergroup usergroup ){
			String key = (String)this.sessionFactory.getCurrentSession().save(usergroup);
			return key;
	}
	
	public List<String> getGrpIdsByUserId( String userId ){
		List<String> grpIds = (List<String>)sessionFactory.getCurrentSession().createSQLQuery("select group_id from mis_usergroup where user_id=?").addScalar("group_id", Hibernate.STRING ).setString(0, userId).list();
		return grpIds;
	}
	
	public void deleteGrpIdsByUserId( String userId ){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_usergroup where user_id=?").setString(0, userId).executeUpdate();
	}
	
}
