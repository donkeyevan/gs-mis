package com.youai.gamemis.model;

// Generated 2011-6-9 12:23:00 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * MisGroup generated by hbm2java
 */
@Entity
@Table(name = "mis_group")
public class MisGroup implements java.io.Serializable {

	private String id;
	private String name;
	private Date createAt;
	private Date updateAt;
	private String comment;
	@Transient
	private int belongTo=0;
	@Transient
	public int getBelongTo() {
		return belongTo;
	}
	@Transient
	public void setBelongTo(int belongTo) {
		this.belongTo = belongTo;
	}

	public MisGroup() {
	}

	public MisGroup(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public MisGroup(String id, String name, Date createAt, Date updateAt,
			String comment) {
		this.id = id;
		this.name = name;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.comment = comment;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at", length = 19)
	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_at", length = 19)
	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@Column(name = "comment")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
