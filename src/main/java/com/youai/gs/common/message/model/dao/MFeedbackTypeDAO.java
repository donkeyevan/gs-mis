package com.youai.gs.common.message.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.dao.MentityDAO;
import com.youai.gs.common.message.model.FeedbackType;



@Transactional
@Repository("MFeedbackTypeDAO")
public class MFeedbackTypeDAO {
	private static final Log logger = LogFactory.getLog(MentityDAO.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	public List<FeedbackType> getFeedbackTypes(){
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria( FeedbackType.class ).list();
	}
	public Map<Integer,String> getFeedbackTypeMap(){
		List<FeedbackType> types = this.getFeedbackTypes();
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		for( FeedbackType type: types){
			typeMap.put( type.getId(), type.getTitle() );
		}
		return typeMap;
	}
	
	public List<FeedbackType> getFeedbackTypesByStatus(Integer status){
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria( FeedbackType.class ).add( Restrictions.eq( "status", status ) ).list();
	}
	public FeedbackType get(Integer id){
		Session session = sessionFactory.getCurrentSession();
		return (FeedbackType)session.get( FeedbackType.class, id);
	}
}
