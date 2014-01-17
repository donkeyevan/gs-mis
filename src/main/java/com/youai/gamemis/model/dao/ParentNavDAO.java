package com.youai.gamemis.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.ParentNav;

@Transactional
@Repository("parentNavDAO")
public class ParentNavDAO {
	private static final Logger logger = Logger.getLogger(ParentNavDAO.class);
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	public List<ParentNav> getAll(){
		return sessionFactory.getCurrentSession().createCriteria(ParentNav.class).list();
	}
}
