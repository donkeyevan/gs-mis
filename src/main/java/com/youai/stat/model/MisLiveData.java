package com.youai.stat.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the mis_live_data database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name="mis_live_data")
public class MisLiveData implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private int payers;
	
	private int money;

	@Column(name="new_player")
	private int newPlayer;

	@Column(name="platform_id")
	private byte platformId;

	@Column(name="server_id")
	private Integer serverId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date stamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateAt;

	public int getPayers() {
		return payers;
	}

	public void setPayers(int payers) {
		this.payers = payers;
	}

	public MisLiveData() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getNewPlayer() {
		return this.newPlayer;
	}

	public void setNewPlayer(int newPlayer) {
		this.newPlayer = newPlayer;
	}

	public byte getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(byte platformId) {
		this.platformId = platformId;
	}

	public Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}