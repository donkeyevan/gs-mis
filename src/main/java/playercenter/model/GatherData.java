package playercenter.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the gather_data database table.
 * 
 */
@Entity
@Table(name="gather_data", catalog = "dzm_mis")
public class GatherData {
	private int id;
	private BigInteger data;
	private int extra;
	private int serverIdx;
	private Date stamp;
	private int type;

	public GatherData() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public BigInteger getData() {
		return this.data;
	}

	public void setData(BigInteger data) {
		this.data = data;
	}


	public int getExtra() {
		return this.extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}


	@Column(name="server_idx")
	public Integer getServerIdx() {
		return this.serverIdx;
	}

	public void setServerIdx(Integer serverIdx) {
		this.serverIdx = serverIdx;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getStamp() {
		return this.stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}


	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

}