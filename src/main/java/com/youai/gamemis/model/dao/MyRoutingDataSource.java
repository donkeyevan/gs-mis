package com.youai.gamemis.model.dao;

import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.youai.gamemis.constants.PlayerServerInfoConstant;
import com.youai.gamemis.filter.SpObserver;

@Component
public class MyRoutingDataSource extends MyAbstractRoutingDataSource implements InitializingBean, ApplicationContextAware{
	
	private static final Logger logger = Logger.getLogger(MyRoutingDataSource.class);
	
	@Resource(name = "playerServerInfoConstant")
	private PlayerServerInfoConstant playerServerInfoConstant;
	
	private ApplicationContext applicationContext = null;

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceName = SpObserver.getSp();
		logger.info("dynamic data source name:" + dataSourceName);
		if(dataSourceName == null){
			dataSourceName = "dataSource2";
		}
		return dataSourceName;
	}
	
	public DataSource getDataSource() throws PropertyVetoException{
		String sp = SpObserver.getSp();
		return getDataSource(sp);
	}
	
	private DataSource getDataSource(String datasourceName) throws PropertyVetoException {
		Map<Object, DataSource> resolvedDataSources = this.getResolvedDataSources();
		if(resolvedDataSources == null){
			resolvedDataSources =  playerServerInfoConstant.getServerMap(false);
		}
		
		if (datasourceName == null || "".equals(datasourceName)) {
			return getResolvedDefaultDataSource();
		}
		DataSource source = resolvedDataSources.get(datasourceName);
		if (source == null) {
			return getResolvedDefaultDataSource();
		}
		return source;
	}

	public void afterPropertiesSet() throws Exception {
		Map<Object, DataSource> resolvedDataSources = playerServerInfoConstant.getServerMap(false);;
		int i =0;
		for (Entry<Object, DataSource> element : resolvedDataSources.entrySet()) {
			logger.debug("myAbstractDS, index:" + i + ", key:" + element.getKey() + ", value:" + element.getValue().toString());
			i++;
		}
		this.setResolvedDataSources(resolvedDataSources);
		this.setResolvedDefaultDataSource((DataSource)this.applicationContext.getBean("dataSource"));
		
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}

}
