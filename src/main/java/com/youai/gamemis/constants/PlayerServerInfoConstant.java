package com.youai.gamemis.constants;

import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import playercenter.model.GameServerInfo;
import playercenter.service.GameServerInfoService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SuppressWarnings("rawtypes")
@Scope("singleton")
@Component("playerServerInfoConstant")
public class PlayerServerInfoConstant  implements ApplicationListener{
	
	@Autowired
	private GameServerInfoService gameServerService;
	
	private ConcurrentHashMap<Object, DataSource> allDataSources = null;
	
	private Logger logger = Logger.getLogger(PlayerServerInfoConstant.class);
	
	private PlayerServerInfoConstant(){}
	
	public Map<Object, DataSource> getServerMap(boolean refreshflag) throws PropertyVetoException{
		 if(allDataSources == null || refreshflag == true){  
	            //synchronized(serverMap){
	                //if(serverMap == null){  
			 allDataSources = new ConcurrentHashMap<Object, DataSource>();
			 for (GameServerInfo info : gameServerService.getGameServers()) {
        		ComboPooledDataSource cpds = new ComboPooledDataSource();
        		cpds.setDriverClass("com.mysql.jdbc.Driver");
        		cpds.setJdbcUrl("jdbc:mysql://" + info.getIp() + ":" + info.getPort() + "/" + info.getDb() + "?useUnicode=true&characterEncoding=UTF-8");
        		cpds.setUser(info.getUsername());
        		cpds.setPassword(info.getPassword());
        		cpds.setIdleConnectionTestPeriod(60);
        		cpds.setAcquireRetryAttempts(10);
        		cpds.setAcquireRetryDelay(1000);
        		cpds.setTestConnectionOnCheckout(true);
        		cpds.setMaxIdleTime(60);
        		cpds.setMaxStatements(0);
        		if(info.getIdx() == 2){
        			cpds.setMaxPoolSize(50);
        		}
        		logger.info("init dataSouce:" + info.getIp() + "   " + info.getPort());
        		allDataSources.put("dataSource" + info.getIdx(), cpds);
			 }
	               // }  
	            //}  
	      }
	      return allDataSources;  
	}
	
	public void empytDataSources(){
		
	}

	public void onApplicationEvent(ApplicationEvent arg0) {
		try {
			getServerMap(false);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public GameServerInfoService getGameServerService() {
		return gameServerService;
	}

	public void setGameServerService(GameServerInfoService gameServerService) {
		this.gameServerService = gameServerService;
	}

	public ConcurrentHashMap<Object, DataSource> getAllDataSources() {
		return allDataSources;
	}

	public void setAllDataSources(ConcurrentHashMap<Object, DataSource> allDataSources) {
		this.allDataSources = allDataSources;
	}
	

}
