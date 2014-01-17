package com.youai.gamemis.service;

import java.beans.PropertyVetoException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import playercenter.service.GameServerInfoService;

import com.youai.gamemis.constants.PlayerServerInfoConstant;
import com.youai.gamemis.model.dao.MyRoutingDataSource;

@Component
public class BeanFactoryAwareBean implements BeanFactoryAware
//, ApplicationListener<ContextRefreshedEvent> 
{  
    private DefaultListableBeanFactory beanFactory;  
    
    @Autowired
	private GameServerInfoService gameServerService;
    
    @Resource(name = "playerServerInfoConstant")
	private PlayerServerInfoConstant playerServerInfoConstant;

    @Resource(name = "gameServerDataSource")
    private MyRoutingDataSource gameServerDataSource;
  
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {  
        System.out.println("setBeanFactory.........................");  
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;  
    }  
  
    //public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {  
    public void onApplicationEvent() throws PropertyVetoException {  
        System.out.println("ContextRefreshed...................");  
        ConcurrentHashMap<Object, DataSource> newDataSource = new ConcurrentHashMap<Object, DataSource>(playerServerInfoConstant.getServerMap(true));
        
        //modify dataSource at dataSourceConstant
        
        BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder 
                .genericBeanDefinition(PlayerServerInfoConstant.class);  
        userBeanDefinitionBuilder.addPropertyValue("gameServerService", gameServerService);  
        userBeanDefinitionBuilder.addPropertyValue("allDataSources", newDataSource);  
        BeanDefinition bd = userBeanDefinitionBuilder.getRawBeanDefinition();
        bd.setScope("singleton");
        beanFactory.registerBeanDefinition("playerServerInfoConstant", bd);
        
        //refresh data at router
        for (Entry<Object, DataSource> element : newDataSource.entrySet()) {
        	gameServerDataSource.putNewDataSource(element.getKey(), element.getValue());
		}
    }  
}