package com.youai.gamemis.model.dao;

import static org.hibernate.criterion.Example.create;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.Mentity;
import com.youai.gamemis.model.OptionValue;
import com.youai.gamemis.util.CommonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Repository("MentityDAO")
public class MentityDAO {
	private static final Log log = LogFactory.getLog(MentityDAO.class);

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public List<Mentity> list(){
		List<Mentity> mentities = sessionFactory.getCurrentSession().createCriteria( Mentity.class ).list();
		return mentities;
	}
	public List<OptionValue> getOptionValues( Class clazz, String value_field, String text_field ) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Session session = sessionFactory.getCurrentSession();
		
		//通过hibernate拿到某类型的所有对象
		List objs = session.createCriteria( clazz ).list();
		Table table = (Table)clazz.getAnnotation(Table.class);
		String catalog = table.catalog();
		Method keyGetterMethod = clazz.getMethod( CommonUtil.getGetterMethod( value_field, catalog ) );
		Method textGetterMethod = clazz.getMethod( CommonUtil.getGetterMethod( text_field, catalog ) );
		List<OptionValue> optionValues = new ArrayList<OptionValue>();
		OptionValue optionValue = null;
		boolean hasZoreValue = false;
		for( Object obj : objs ){
			//通过反射拿到某对象中某列的值
			Object key = keyGetterMethod.invoke( obj );
			Object text = textGetterMethod.invoke(obj );
			if( key.toString().equals("0")){
				hasZoreValue = true; 
			}
			optionValue = new OptionValue();
			optionValue.setKey( key.toString() );
			optionValue.setValue( text.toString() );
			optionValues.add( optionValue );
		}
		if( !hasZoreValue ){
			optionValue = new OptionValue();
			optionValue.setKey("0");
			optionValue.setValue("无");
			optionValues.add( optionValue );
		}
		return optionValues;
	}
	public void saveOrUpdate( Mentity item ){
		sessionFactory.getCurrentSession().saveOrUpdate( item );
	}
	public void persist(Mentity transientInstance) {
		log.debug("persisting Mentity instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Mentity instance) {
		log.debug("attaching dirty Mentity instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Mentity instance) {
		log.debug("attaching clean Mentity instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Mentity persistentInstance) {
		log.debug("deleting Mentity instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mentity merge(Mentity detachedInstance) {
		log.debug("merging Mentity instance");
		try {
			Mentity result = (Mentity) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Mentity findById(java.lang.String id) {
		log.debug("getting Mentity instance with id: " + id);
		try {
			Mentity instance = (Mentity) sessionFactory.getCurrentSession()
					.get("com.youai.gamemis.model.Mentity", id);
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

	public List<Mentity> findByExample(Mentity instance) {
		log.debug("finding Mentity instance by example");
		try {
			List<Mentity> results = (List<Mentity>) sessionFactory
					.getCurrentSession().createCriteria(
							"com.youai.gamemis.model.Mentity").add(
							create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	
	public void deleteMentity( String id ){
		Session session = sessionFactory.getCurrentSession();
	    Mentity mentity = new Mentity();
	    mentity.setId( id );
		session.delete( mentity );
		
	}
}
