package com.youai.gamemis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PlayerEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date createAt;
	private int type;
	private Map<Object,Object> params;
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Map<Object, Object> getParams() {
		return params;
	}
	public void setParams(Map<Object, Object> params) {
		this.params = params;
	}
	
	
	
}
