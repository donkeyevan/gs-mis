package com.youai.gamemis.model.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.StatPaymentResult;
import com.youai.gamemis.model.StatPlayerResult;

@Transactional
@Repository("StatDAO")
public class StatDAO {
	private static final Logger logger = Logger.getLogger(StatDAO.class);
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;

	
	public List<StatPlayerResult> getPlayerStat( String begin_date, String end_date ){
		logger.info( "Begin date:" +begin_date +" End date:" +end_date );
		List<StatPlayerResult> playerResults = new ArrayList<StatPlayerResult>();
		Map<Date, StatPlayerResult>  statMap = new HashMap<Date,StatPlayerResult>();
		
	
		Session session = sessionFactory.getCurrentSession();
		List<StatPlayerResult> statItems  = session.createSQLQuery("select * from stat_player_result where stat_date>= ? and stat_date <= ? order by stat_date asc").addEntity( StatPlayerResult.class )
				.setString(0, begin_date).setString(1, end_date).list();
			if( statItems != null && statItems.size() > 0 ){
				for( StatPlayerResult statItem : statItems ){
					if( !statMap.containsKey( statItem.getStatDate() ) ){
						statMap.put( statItem.getStatDate(), statItem);
					}else{
						StatPlayerResult statItem2 = statMap.get( statItem.getStatDate() );
						statItem2.add( statItem );
					}
				}
			}
		
			
		return new ArrayList(statMap.values());
	}
	
	public List<StatPaymentResult> getStatPayments( String begin_date, String end_date ){
		logger.info( "Begin date:" +begin_date +" End date:" +end_date );
		Session session = sessionFactory.getCurrentSession();
		List<StatPaymentResult> results = session.createSQLQuery("select * from stat_payment_result where stat_date>= ? and stat_date <= ? order by stat_date").addEntity( StatPaymentResult.class )
			.setString(0, begin_date).setString(1, end_date).list();		
		return results;
	}
	public int getPayuserNum( String begin_date, String end_date ){
		Session session = sessionFactory.getCurrentSession();
		BigInteger payuserNum = (BigInteger)session.createSQLQuery("select count(distinct(player_key)) from apple_payment where created_at>= ? and created_at <= ?").setString(0, begin_date).setString(1, end_date).uniqueResult();
		return payuserNum.intValue();
	}
}

