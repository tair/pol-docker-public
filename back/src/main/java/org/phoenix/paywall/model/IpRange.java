package org.phoenix.paywall.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ActivationCode")
@NamedQuery(name = "find_all_activationcodes", query = "SELECT p FROM ActivationCode p")
public class IpRange {

	@Id
	private int ipRangeId;

	private String start;
	private String end;
	private Integer partyId;
	private Date createdAt;
	private Date expiredAt;
	private String label;
	private String partyName;

	public int getIpRangeId() {
		return ipRangeId;
	}

	public void setIpRangeId(int ipRangeId) {
		this.ipRangeId = ipRangeId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
}
