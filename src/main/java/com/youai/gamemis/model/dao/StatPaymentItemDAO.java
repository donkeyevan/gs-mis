package com.youai.gamemis.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.StatPaymentItem;
@Transactional
@Repository("StatPaymentItemDAO")
public class StatPaymentItemDAO {
	private static final Log log = LogFactory.getLog(StatPaymentItemDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory ;
	public List<StatPaymentItem> getStatPaymentItems(){
			return sessionFactory.getCurrentSession().createCriteria( StatPaymentItem.class ).list();
		
	}
}
