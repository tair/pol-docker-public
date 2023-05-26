package org.phoenix.paywall.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Partner")
@NamedQuery(name = "find_all_partners", query = "SELECT p FROM Partner p")
public class Partner {

	@Id
	private String partnerId;

	private String name;
	private String description;
	private String subscriptionListDesc;
	private String uiMeterUri;
	private String loginRedirectErrorText;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubscriptionListDesc() {
		return subscriptionListDesc;
	}

	public void setSubscriptionListDesc(String subscriptionListDesc) {
		this.subscriptionListDesc = subscriptionListDesc;
	}

	public String getUiMeterUri() {
		return uiMeterUri;
	}

	public void setUiMeterUri(String uiMeterUri) {
		this.uiMeterUri = uiMeterUri;
	}

	public String getLoginRedirectErrorText() {
		return loginRedirectErrorText;
	}

	public void setLoginRedirectErrorText(String loginRedirectErrorText) {
		this.loginRedirectErrorText = loginRedirectErrorText;
	}

}
