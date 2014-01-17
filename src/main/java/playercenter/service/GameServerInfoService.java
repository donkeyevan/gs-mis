package playercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.ServerConfig;

import playercenter.model.GameServerInfo;
import playercenter.model.dao.GameServerInfoDAO;

@Service("gameServerInfoService")
public class GameServerInfoService {
	@Autowired
	GameServerInfoDAO gameServerInfoDAO;
	
	public List<GameServerInfo> getGameServers(){
		//TODO add the cache
		return gameServerInfoDAO.getValidGameServerInfos();
	}

	public GameServerInfo getByIdx(Integer serverIdx) {
		return gameServerInfoDAO.getByIdx(serverIdx);
	}
}
