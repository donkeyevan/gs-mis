package com.youai.gamemis.model.dao;

// Generated 2011-4-22 11:53:30 by Hibernate Tools 3.3.0.GA

import java.util.List;

import javax.annotation.Resource;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.MisUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class MisGrouppriv.
 * @see com.youai.gamemis.model.MisGrouppriv
 * @author Hibernate Tools
 */
@Transactional
@Repository("MisGroupprivDAO")
public class MisGroupprivDAO {

	private static final Log log = LogFactory.getLog(MisGroupprivDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public List<MisGrouppriv> list(String grpId){
		return (List<MisGrouppriv>)sessionFactory.getCurrentSession().createCriteria( MisGrouppriv.class ).list();
	}
	
	public String save(MisGrouppriv grpPriv ){
		return (String)sessionFactory.getCurrentSession().save( grpPriv );
	}
	
	public void deleteGrpPriv( String grpId ){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_grouppriv where group_id=?").setString(0, grpId ).executeUpdate();
	}
	
	public void deleteByPrivId(String privId){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_grouppriv where priv_id=?").setString(0, privId ).executeUpdate();
	}

	
}
