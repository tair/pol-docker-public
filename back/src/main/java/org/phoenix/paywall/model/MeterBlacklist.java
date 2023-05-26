package org.phoenix.paywall.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MeterBlacklist")
@NamedQuery(name = "find_all_meterblacklists", query = "SELECT p FROM MeterBlacklist p")
public class MeterBlacklist {

	@Id
	private String meterBlackListId;

	private String partnerId;
	private String pattern;

	public String getMeterBlackListId() {
		return meterBlackListId;
	}

	public void setMeterBlackListId(String meterBlackListId) {
		this.meterBlackListId = meterBlackListId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
