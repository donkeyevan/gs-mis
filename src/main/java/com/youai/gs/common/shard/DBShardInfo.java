package com.youai.gs.common.shard;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "dbshard_info", catalog = "parkingshard")
public class DBShardInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idGenerator", strategy="assigned") 
	@GeneratedValue(generator="idGenerator")
	@Column(name = "player_id", unique = true, nullable = false)
	private Integer playerId;
	
	@Column(name = "db_id")
	private Integer dbId;
	
	@Column(name = "player_name", nullable = false, length = 255)
	private String playerName;
	
	@Column(name = "player_key" )
	private Integer playerKey;
	
	public Integer getPlayerKey() {
		return playerKey;
	}

	public void setPlayerKey(Integer playerKey) {
		this.playerKey = playerKey;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	public Integer getDbId() {
		return dbId;
	}
	
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
}
