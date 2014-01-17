package com.youai.gamemis.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youai.gamemis.model.ServerConfig;

@Transactional
@Repository("ServerConfigDAO")
public class ServerConfigDAO {
	private static final Logger logger = Logger.getLogger(ServerConfigDAO.class);
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	
	public ServerConfig getByIdx( int idx){
		return (ServerConfig)sessionFactory.getCurrentSession().createSQLQuery("select * from server_config where server_idx=?")
		.addEntity( ServerConfig.class ).setInteger(0, idx).uniqueResult();
		 
	}
	public List<ServerConfig> getConfigs(){
		return sessionFactory.getCurrentSession().createSQLQuery("select * from server_config ").addEntity( ServerConfig.class ).list();
	}
}
