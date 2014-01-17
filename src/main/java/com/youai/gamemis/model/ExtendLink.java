package com.youai.gamemis.model;

public class ExtendLink implements Cloneable, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String link;
	private String word;
	/*
	 * link的类型：本服的，跨服的
	 */
	private Integer type;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public Object clone() {
		ExtendLink o = null;
		try {
			o = (ExtendLink) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
