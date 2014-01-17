package playercenter.model;

// Generated 2013-4-11 14:51:08 by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * GameServerInfo generated by hbm2java
 */
@Entity
@Table(name = "game_server_info", catalog = "dzm_mis")
public class GameServerInfo implements java.io.Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idx;
	private String name;
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private Integer status;
	private String comment;
	private String db;
	private Integer osType;
	private Integer mixType;
	private Date stamp;

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Column(name = "os_type")
	public Integer getOsType() {
		return osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public GameServerInfo() {
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

	@Column(name = "idx")
	public Integer getIdx() {
		return this.idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "comment", length = 64)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "ip", length=32)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username", length = 32)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "db", length = 16)
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	@Column(name = "mix_type")
	public Integer getMixType() {
		return mixType;
	}

	public void setMixType(Integer mixType) {
		this.mixType = mixType;
	}
	
}
