package com.youai.gs.common.message.model.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.Pagenator;
import com.youai.gs.common.message.model.FeedbackMessage;
import com.youai.gs.common.message.model.FeedbackSample;
import com.youai.gs.common.message.model.FeedbackThread;
import com.youai.gs.common.message.model.FeedbackUIThread;
@Transactional
@Repository("MfeedbackDAO")
public class MfeedbackDAO {
	private static final Log logger = LogFactory.getLog(MfeedbackDAO.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	public List<FeedbackMessage> queryMsgs(Integer threadId){
		Session session = sessionFactory.getCurrentSession();
		List<FeedbackMessage> rows = (List<FeedbackMessage>)session.createCriteria( FeedbackMessage.class ).add( Restrictions.eq("threadId", threadId) ).addOrder( Property.forName("createdAt").desc() ).list();
		return rows;
	}
	public FeedbackThread getThreadById( int id ){
		Session session = sessionFactory.getCurrentSession();
		Object thread = session.get(FeedbackThread.class, id );
		if( thread == null ){
			return null;
		}else{
			return (FeedbackThread)thread;
		}
	}
	
	public void saveThread( FeedbackThread thread ){
		Session session = sessionFactory.getCurrentSession();
		session.save( thread );
	}
	public void updateThread( FeedbackThread thread ){
		Session session = sessionFactory.getCurrentSession();
		session.update( thread );
		
	}
	public Pagenator queryBySql(String countSql, String sql, String[] keywords, String query_value,
			Pagenator pager) {
		Session session = sessionFactory.getCurrentSession();
		//get the total number
		Query countQuery = session.createSQLQuery( countSql );
		if( keywords != null ){
			for( int i=0 ; i< keywords.length ; i++ ){
				countQuery.setString("keywords"+i, "%"+keywords[i]+"%");
			}
		}
		if( query_value != null && !query_value.isEmpty() ){
			countQuery.setString( "query_value", query_value );
		}
		int totalCount = ((BigInteger)countQuery.uniqueResult()).intValue();
		int pn = pager.pn;
		int rn = pager.rn;
		int pageSum = totalCount % rn == 0 ? totalCount / rn : totalCount / rn + 1;
		int startNum = (pn - 1) * rn;
		logger.info( ">>>>>>>>>sql:"+ sql);
		
		//get the records of the specified page
		Query sqlQuery = session.createSQLQuery( sql ).addEntity("feedback_message", FeedbackMessage.class).
		addEntity("feedback_thread", FeedbackThread.class).setFirstResult(startNum).setMaxResults(rn);
		if( keywords != null ){
			for( int i=0 ; i< keywords.length ; i++ ){
				sqlQuery.setString("keywords"+i, "%"+keywords[i]+"%");
			}
		}
		if( query_value != null && !query_value.isEmpty() ){
			sqlQuery.setString( "query_value", query_value );
		}
		List<Object> rows = sqlQuery.list();
		Iterator iter = rows.iterator();
		List<FeedbackUIThread> threads = new ArrayList<FeedbackUIThread>();
		Map<Integer,Date> threadMap = new HashMap<Integer, Date>();
		while( iter.hasNext() ){
			
			Object[] tuple = (Object[])iter.next();
			FeedbackMessage feedbackMessage = (FeedbackMessage)tuple[0] ;
			FeedbackThread feedbackThread = (FeedbackThread)tuple[1];
			if( threadMap.containsKey( feedbackThread.getPlayerKey() ) ){
				Date lastThreadDate = threadMap.get( feedbackThread.getPlayerKey() );
				
				//如果同一个人两次反馈的时间间隔小于10秒，则忽略此反馈				
				if(  lastThreadDate.getTime() - feedbackThread.getCreatedAt().getTime() < 10000 ){
					totalCount = totalCount - 1;
					if(feedbackThread.getIgnored() == 0 ){
						feedbackThread.setIgnored(1);
						feedbackThread.setLastOpAdmin("system");
						this.updateThread(feedbackThread);
						
					}
					continue;
				}
				
			}
			
			String content = feedbackMessage.getContent().trim();
			if( content.isEmpty() ){
				totalCount = totalCount - 1;
				if(feedbackThread.getIgnored() == 0 ){
					feedbackThread.setIgnored(1);
					feedbackThread.setLastOpAdmin("system");
					this.updateThread(feedbackThread);
				}
				continue;
			}
			
		
			//只有当反馈没有被忽略时，才能被加入判断时间的条件，后面出现的时间间隔太小的反馈被认为是重复反馈，
			//确保剩下的唯一一条没有被忽略的反馈不会因为改变排序后落到已忽略反馈后面而再次被忽略
			if( feedbackThread.getIgnored() != 1){
				threadMap.put( feedbackThread.getPlayerKey(),feedbackThread.getCreatedAt());
			}
	        FeedbackUIThread uithread = new FeedbackUIThread();
	       
	        uithread.setThread( feedbackThread );
	        uithread.setOrigMsg( feedbackMessage );
			threads.add( uithread );
		}
		pager.setRowCount(totalCount);
		pager.setPageSum( pageSum );
		pager.setRows( threads );
		return pager;
		
	}
	public Pagenator query(DetachedCriteria dc,
			Pagenator pager) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		int totalCount = ((Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		int pn = pager.pn;
		int rn = pager.rn;
		int pageSum = totalCount % rn == 0 ? totalCount / rn : totalCount / rn
				+ 1;
		int startNum = (pn - 1) * rn;
		criteria.setProjection(null);
		List<FeedbackThread> feedbackThreads = criteria
				.setFirstResult(startNum).setMaxResults(rn).list();
		Map<String, Object> result = new HashMap<String, Object>();
		pager.setRows(feedbackThreads);
		pager.setRowCount(totalCount);
		pager.setPageSum( pageSum );
		return pager;
	}
	
	public void addFeedbackMsg( FeedbackMessage fdMsg ){
		Session session = sessionFactory.getCurrentSession();
		session.save( fdMsg );
	}
	public int changeThreadStatus( int threadId, String last_op_name, int status ){
		Session session = sessionFactory.getCurrentSession();
		
		return session.createSQLQuery("update feedback_thread set ignored=?, last_op_admin=? where id=? ")
			.setInteger(0, status).setString(1, last_op_name).setInteger(2, threadId).executeUpdate();
		
	}
	
	public void deleteFeedbackMsg( int id ){
		Session session = sessionFactory.getCurrentSession();
		Object obj = session.get(FeedbackMessage.class, id );
		if( obj == null ){
			logger.warn("The feedback msg is not exsited! id:"+id);
			return ;
		}
		session.delete(obj);
	}
	
	
	public List<FeedbackSample> getFeedbackSamples( ){
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria( FeedbackSample.class).list();
	}
	
	public List<FeedbackSample> getFeedbackSamplesByStatus(Integer status ){
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria( FeedbackSample.class).add( Restrictions.eq( "status", status ) ).list();
	}
	

}
