package org.phoenix.paywall.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ActivationCode")
@NamedQuery(name = "find_all_activationcodes", query = "SELECT p FROM ActivationCode p")
@SQLDelete(sql = "UPDATE activation_code SET deleteMarker = true WHERE activationCodeId = ?")
@Where(clause = "deleteMarker = false")
public class ActivationCode {

	@Id
	private int activationCodeId;

	private String activationCode;
	private int period;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date purchaseDate;
	private String partnerId;
	private Integer partyId;
	private String transactionType;
	private String partyName;
	private String partyEmail;
	private String partyType;

	private boolean deleteMarker;

	public int getActivationCodeId() {
		return activationCodeId;
	}

	public void setActivationCodeId(int activationCodeId) {
		this.activationCodeId = activationCodeId;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getPartyEmail() {
		return partyEmail;
	}

	public void setPartyEmail(String partyEmail) {
		this.partyEmail = partyEmail;
	}

	public boolean isDeleteMarker() {
		return deleteMarker;
	}

	public void setDeleteMarker(boolean deleteMarker) {
		this.deleteMarker = deleteMarker;
	}
}
