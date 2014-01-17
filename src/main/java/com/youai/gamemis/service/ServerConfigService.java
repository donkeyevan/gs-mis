package com.youai.gamemis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.ServerConfig;
import com.youai.gamemis.model.dao.ServerConfigDAO;
@Service("serverConfigService")
public class ServerConfigService {
	@Autowired
	private ServerConfigDAO serverConfigDAO;
	public List<ServerConfig> getServerConfigs(){
		return serverConfigDAO.getConfigs();
	}
	public ServerConfig getByIdx(int idx){
		return serverConfigDAO.getByIdx( idx );
	}
}
