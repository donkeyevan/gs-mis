package com.youai.gamemis.model;

/**
 * @author mini5
 *
 */
public class MyEntry {
	
	private String key;
	
	private Object value;

	public MyEntry(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
