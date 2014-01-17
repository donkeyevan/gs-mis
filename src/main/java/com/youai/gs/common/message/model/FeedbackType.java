package com.youai.gs.common.message.model;

// Generated 2011-5-13 11:23:27 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * FeedbackType generated by hbm2java
 */
@Entity
@Table(name = "feedback_type")
public class FeedbackType implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	private String title;
	private Date createAt;
	private String keyWords;
	private Integer status;
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "key_words", length = 255)
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Transient
	private int selected=0;
	@Transient
	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	
	public FeedbackType() {
	}

	public FeedbackType(String title, Date createAt) {
		this.title = title;
		this.createAt = createAt;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at", nullable = false, length = 19)
	public Date getCreateAt() {
		return this.createAt;
	}
	
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
}