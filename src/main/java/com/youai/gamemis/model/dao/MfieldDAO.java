package com.youai.gamemis.model.dao;

// Generated 2011-4-2 20:59:26 by Hibernate Tools 3.3.0.GA

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.annotation.Resource;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.Mfield;
/**
 * Home object for domain model class Mfield.
 * @see com.youai.gamemis.model.Mfield
 * @author Hibernate Tools
 */
@Transactional
@Repository("mfieldDAO")
public class MfieldDAO {

	private static final Log log = LogFactory.getLog(MfieldDAO.class);
	
	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;

	public List<Mfield> findMfieldsByMentityId( String id ){
		Session sess = sessionFactory.getCurrentSession();
		Query query = sess.createSQLQuery("SELECT * FROM mfield where mentity_id=? order by num").addEntity(Mfield.class);
		List<Mfield> mfields = query.setString(0, id).list();
		return mfields;
	}
	public void saveOrUpdate( Mfield mfield ){
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate( mfield );
	
	}
	public int deleteByMentityId( String id ){
		Session sess = sessionFactory.getCurrentSession();
		Query query  = sess.createSQLQuery( "delete from mfield where mentity_id=?" );
		query.setString( 0, id);
		return query.executeUpdate();
		
	}
	public void persist(Mfield transientInstance) {
		log.debug("persisting Mfield instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Mfield instance) {
		log.debug("attaching dirty Mfield instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Mfield instance) {
		log.debug("attaching clean Mfield instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Mfield persistentInstance) {
		log.debug("deleting Mfield instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mfield merge(Mfield detachedInstance) {
		log.debug("merging Mfield instance");
		try {
			Mfield result = (Mfield) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Mfield findById(java.lang.String id) {
		log.debug("getting Mfield instance with id: " + id);
		try {
			Mfield instance = (Mfield) sessionFactory.getCurrentSession().get(
					"com.youai.gamemis.model.Mfield", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Mfield> findByExample(Mfield instance) {
		log.debug("finding Mfield instance by example");
		try {
			List<Mfield> results = (List<Mfield>) sessionFactory
					.getCurrentSession().createCriteria(
							"com.youai.gamemis.model.Mfield").add(
							create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
