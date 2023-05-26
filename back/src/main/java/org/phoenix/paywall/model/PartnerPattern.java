package org.phoenix.paywall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "PartnerPattern")
@NamedQuery(name = "find_all_partnerpatterns", query = "SELECT p FROM PartnerPattern p")
public class PartnerPattern {

	@Id
	private int partnerPatternId;

	private String sourceUri;
	private String targetUri;
	private String partnerId;

	public int getPartnerPatternId() {
		return partnerPatternId;
	}

	public void setPartnerPatternId(int partnerPatternId) {
		this.partnerPatternId = partnerPatternId;
	}

	public String getSourceUri() {
		return sourceUri;
	}

	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}

	public String getTargetUri() {
		return targetUri;
	}

	public void setTargetUri(String targetUri) {
		this.targetUri = targetUri;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

}
