package com.youai.gs.common.message.model.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gs.common.message.model.Message;
@Transactional
@Repository("MessageDAO")
public class MessageDAO {
	private static final Log logger = LogFactory.getLog(MessageDAO.class);
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	public void addMessage(Message message){
		Session session = sessionFactory.getCurrentSession();
		session.save( message );
	}
}
