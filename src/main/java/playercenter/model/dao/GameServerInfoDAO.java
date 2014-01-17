package playercenter.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import playercenter.model.GameServerInfo;
@Transactional
@Repository("GameServerInfoDAO")
public class GameServerInfoDAO {
	//private static final Logger logger = Logger.getLogger(GameServerInfoDAO.class);

	@Resource(name="sessionFactory")
	private  SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<GameServerInfo> getValidGameServerInfos(){
		return sessionFactory.getCurrentSession().createSQLQuery("select * from game_server_info where status=1 order by mix_type, idx")
		.addEntity( GameServerInfo.class ).list();
	}

	public GameServerInfo getByIdx(int idx) {
		return (GameServerInfo) sessionFactory.getCurrentSession().createSQLQuery("select * from game_server_info where status=1 and idx=?")
				.addEntity( GameServerInfo.class ).setInteger(0, idx).uniqueResult();
	}
}
