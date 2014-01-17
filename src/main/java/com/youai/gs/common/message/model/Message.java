package com.youai.gs.common.message.model;

// Generated 2011-5-4 10:49:59 by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * Message generated by hbm2java
 */
@Entity
@Table(name = "message")
public class Message implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	private Date createdAt;
	private Integer type;
	private Integer status;
	private Integer senderKey;
	private Integer receiverKey;
	private String content;
	private String senderName;
	private String receiverName;
	private Integer threadId=0;
 
	public Message() {
	}

	public Message(Date createdAt, Integer type, Integer status,
			Integer senderKey, Integer receiverKey, String content,
			String senderName, String receiverName, Integer threadId) {
		this.createdAt = createdAt;
		this.type = type;
		this.status = status;
		this.senderKey = senderKey;
		this.receiverKey = receiverKey;
		this.content = content;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.threadId = threadId;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "sender_key")
	public Integer getSenderKey() {
		return this.senderKey;
	}

	public void setSenderKey(Integer senderKey) {
		this.senderKey = senderKey;
	}

	@Column(name = "receiver_key")
	public Integer getReceiverKey() {
		return this.receiverKey;
	}

	public void setReceiverKey(Integer receiverKey) {
		this.receiverKey = receiverKey;
	}

	@Column(name = "content", length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "sender_name", length = 64)
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Column(name = "receiver_name", length = 64)
	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Column(name = "thread_id")
	public Integer getThreadId() {
		return this.threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}

}