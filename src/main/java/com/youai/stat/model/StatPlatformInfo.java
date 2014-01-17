package com.youai.stat.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the stat_platform_info database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name="stat_platform_info")
public class StatPlatformInfo implements Serializable {

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	private byte idx;

	@Column(length=32)
	private String name;

	private int platform;

	@Column(length=8)
	private String prefix;

	@Column(length=124)
	private String zhname;

	public StatPlatformInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIdx() {
		return this.idx;
	}

	public void setIdx(byte idx) {
		this.idx = idx;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlatform() {
		return this.platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getZhname() {
		return this.zhname;
	}

	public void setZhname(String zhname) {
		this.zhname = zhname;
	}

}