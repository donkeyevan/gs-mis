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
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.MisGrouppriv;
import com.youai.gamemis.model.MisPrivilege;
import com.youai.gamemis.model.MisUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class MisPrivilege.
 * @see com.youai.gamemis.model.MisPrivilege
 * @author Hibernate Tools
 */
@Transactional
@Repository("MisPrivilegeDAO")
public class MisPrivilegeDAO {

	private static final Log log = LogFactory.getLog(MisPrivilegeDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public List<MisPrivilege> list(){
		List<MisPrivilege> privs = (List<MisPrivilege>)sessionFactory.getCurrentSession().createCriteria( MisPrivilege.class ).addOrder( Property.forName("position").desc() ).list();
		return privs;
	}
	
	public String save(MisPrivilege priv){
		sessionFactory.getCurrentSession().save( priv );
		return priv.getId();
	}
	
	public MisPrivilege find( String privId ){
		return (MisPrivilege)sessionFactory.getCurrentSession().get( MisPrivilege.class,  privId );
	}
	
	public void delete( String privId ){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_privilege where id=?").setString(0, privId).executeUpdate();
	}
	
	public void update( MisPrivilege priv ){
		sessionFactory.getCurrentSession().update( priv );
	}
	

	public List<String> getPrivIdsByGrpId( String grpId ){
		return (List<String>)sessionFactory.getCurrentSession().createSQLQuery("select priv_id from mis_grouppriv where group_id=?").addScalar( "priv_id", Hibernate.STRING ).setString(0, grpId).list();
	}
	
	public List<MisGrouppriv> getGroupPrivsByGrpId( String grpId ){
		return (List<MisGrouppriv>)sessionFactory.getCurrentSession().createSQLQuery("select * from mis_grouppriv where group_id=?").addEntity(MisGrouppriv.class).setString(0, grpId).list();
	}
	
	public List<String> getPrivIds(){
		return (List<String>)sessionFactory.getCurrentSession().createSQLQuery("select id from mis_privilege ").addScalar( "id", Hibernate.STRING ).list();
	}
	
	public void deleteByMentityId(String mentityId){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_privilege where mentity_id=?").setString(0, mentityId).executeUpdate();
	}
	
	public MisPrivilege getByMentityId( String mentityId ){
		return (MisPrivilege)sessionFactory.getCurrentSession().createSQLQuery("select * from mis_privilege where mentity_id=?").addEntity(MisPrivilege.class).setString(0, mentityId).uniqueResult();
	}
}
