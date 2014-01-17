package com.youai.gamemis.model.dao;

// Generated 2011-4-21 19:39:09 by Hibernate Tools 3.3.0.GA

import java.util.List;

import javax.annotation.Resource;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.MisUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class MisUser.
 * @see com.youai.gamemis.model.MisUser
 * @author Hibernate Tools
 */
@Transactional
@Repository("misUserDAO")
public class MisUserDAO {

	private static final Log log = LogFactory.getLog(MisUserDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public List<MisUser> list(){
		List<MisUser> misusers = sessionFactory.getCurrentSession().createCriteria( MisUser.class ).list();
		return misusers;
	}
	
	public String add(MisUser user ){
		sessionFactory.getCurrentSession().save(user);
		return user.getId();
	}
	
	public MisUser findByName( String name ){
		Object user = sessionFactory.getCurrentSession().createSQLQuery("select * from mis_user where name=?").addEntity( MisUser.class ).setString(0, name).uniqueResult();
		return user == null ? null : (MisUser)user;
	}

	public void delete( String id ){
		sessionFactory.getCurrentSession().createSQLQuery("delete from mis_user where id=?").addEntity( MisUser.class ).setString(0, id).executeUpdate();
	}
	
	public MisUser find( String id ){
		return (MisUser)sessionFactory.getCurrentSession().get( MisUser.class, id );
	}
	
	public void updateUser(MisUser user){
		sessionFactory.getCurrentSession().update(user);
	}

}
