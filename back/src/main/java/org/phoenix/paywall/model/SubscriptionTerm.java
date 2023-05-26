package org.phoenix.paywall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="SubscriptionTerm")
public class SubscriptionTerm {
	@Id
	private int subscriptionTermId;
	
	private int period;
	private long price;
	private long groupDiscountPercentage;
	private String partnerId;
	private String description;
	
	public int getSubscriptionTermId() {
		return subscriptionTermId;
	}
	public void setSubscriptionTermId(int subscriptionTermId) {
		this.subscriptionTermId = subscriptionTermId;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getGroupDiscountPercentage() {
		return groupDiscountPercentage;
	}
	public void setGroupDiscountPercentage(long groupDiscountPercentage) {
		this.groupDiscountPercentage = groupDiscountPercentage;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
