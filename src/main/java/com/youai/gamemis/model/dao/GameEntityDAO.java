package com.youai.gamemis.model.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.constants.GameConfig;
import com.youai.gamemis.model.DBCatalogType;
import com.youai.gamemis.model.MyEntry;
import com.youai.gamemis.model.PageQueryInfo;

@Transactional
@Repository("GameEntityDAO")
public class GameEntityDAO {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Resource(name = "gsSessionFactory")
	private SessionFactory gsSessionFactory;
	
	private DBCatalogType catalog = DBCatalogType.game_server;

	private static Logger logger = Logger.getLogger( GameEntityDAO.class );
	public DBCatalogType getCatalog(){
		return this.catalog;
	}
	public void setCatalog( String s_catalog ){

		if( s_catalog.toLowerCase().contains("mis")){
			this.catalog = DBCatalogType.mis; 
			return;
		}
		this.catalog = DBCatalogType.game_server;	
	}
	
	public Map<String,Object> findGentitiesByClass(DetachedCriteria detachedCriteria, PageQueryInfo queryInfo ) {
		SessionFactory sessionFactory = this.getSessionFactory();
		//sessionFactory.get
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		Integer iTotalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		int totalCount = iTotalCount == null ? 0 : iTotalCount.intValue(); 
        int pn = queryInfo.pn;
        int rn = queryInfo.rn;
		if( pn == 0 ) pn = GameConfig.PN;
        if( rn == 0 ) rn = GameConfig.RN;
        int pageSum = totalCount % rn == 0 ? totalCount / rn :  totalCount / rn+1;
        int startNum = (pn-1)*rn;
        criteria.setProjection(null); 
		List<Object> entities = criteria.setFirstResult(startNum).setMaxResults(rn).list();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put( "pagesum", pageSum );
		result.put( "entities", entities );
		result.put( "totalcount", totalCount );
		return result;
		
	}
	public Object save( Object o ){
		
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.save( o );
		return o;
	}
	public Object getById( Serializable id, String modelClass) throws ClassNotFoundException{
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session  = sessionFactory.getCurrentSession();
		Class clazz = Class.forName( modelClass );
		Object entity = session.get( clazz, id );
		return entity;
	}
	public Object getNext( DetachedCriteria detachedCriteria) throws ClassNotFoundException{
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);

		
        criteria.setProjection(null); 
		List<Object> entities = criteria.setFirstResult(0).setMaxResults(1).list();
		
		return entities != null && entities.size() > 0 ? entities.get(0) : null;
	}
	public void update( Object o ){
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session  = sessionFactory.getCurrentSession();
		session.update( o );
		
	}
	
	public void delete( Object o ){
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Object merged_obj = session.merge( o );
		session.delete( merged_obj );
	}
	
	public Object getUniqueEntity( Class clazz ){
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria( clazz ).uniqueResult();
	}
	
	/**
	 * 通过类的名字和field来查询
	 * @param className
	 * @param entry
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Object> getEntityByClazzAndField(String className, MyEntry ...entry ) throws ClassNotFoundException{
		Class clazz = Class.forName(className);
		return getEntityByClazzAndField(clazz, entry);
	}

	/**
	 * 通过类的名字和field来查询
	 * @param className
	 * @param entry
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Object> getEntityByClazzAndField(Class clazz, MyEntry ...entry ) throws ClassNotFoundException{
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		for (MyEntry myEntry : entry) {
			dc.add(Restrictions.eq(myEntry.getKey(), myEntry.getValue()));
		}
		SessionFactory sessionFactory = this.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		return criteria.list();
	}
	
	public SessionFactory getSessionFactory(){
		
		if( this.catalog == DBCatalogType.mis ){
			return sessionFactory;
		}
		if( this.catalog == DBCatalogType.game_server ){
			return gsSessionFactory;
		}
		return null;
	}
}
