package org.phoenix.paywall.model.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivationCodeFilter {

	private List<String> partyType;
	private List<String> partner;
	private List<String> transactionType;
	private String partyEmail;
	private String partyName;
	private String code;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date purchaseDate_From;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date purchaseDate_To;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyEmail() {
		return partyEmail;
	}

	public void setPartyEmail(String partyEmail) {
		this.partyEmail = partyEmail;
	}

	public List<String> getPartyType() {
		return partyType;
	}

	public void setPartyType(List<String> partyType) {
		this.partyType = partyType;
	}

	public List<String> getPartner() {
		return partner;
	}

	public void setPartner(List<String> partner) {
		this.partner = partner;
	}

	public List<String> getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(List<String> transactionType) {
		this.transactionType = transactionType;
	}

	public Date getPurchaseDate_From() {
		return purchaseDate_From;
	}

	public void setPurchaseDate_From(Date purchaseDate_From) {
		this.purchaseDate_From = purchaseDate_From;
	}

	public Date getPurchaseDate_To() {
		return purchaseDate_To;
	}

	public void setPurchaseDate_To(Date purchaseDate_To) {
		this.purchaseDate_To = purchaseDate_To;
	}

	public String toSqlWhereCondition() {

		StringBuilder whereCond = new StringBuilder();
		Optional<ActivationCodeFilter> filter = Optional.of(this);

		whereCond.append(" AND deleteMarker=false");
		if (filter.isPresent()) {

			String partyTypes = null;
			String partners = null;
			String purchaseDate_From = null;
			String purchaseDate_To = null;
			String transactionType = null;

			if (filter.map(ActivationCodeFilter::getPartyType).isPresent()) {
				partyTypes = filter.get().getPartyType().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partyTypes != null && !partyTypes.isEmpty()) {
					whereCond.append(" AND partyType IN (").append(partyTypes).append(")");
				}
			}

			if (filter.map(ActivationCodeFilter::getPartyEmail).isPresent()
					&& !filter.map(ActivationCodeFilter::getPartyEmail).get().isEmpty()) {
				whereCond.append(" AND LOWER(email) like'%").append(filter.map(ActivationCodeFilter::getPartyEmail).get().toLowerCase())
						.append("%'");
			}

			if (filter.map(ActivationCodeFilter::getCode).isPresent()
					&& !filter.map(ActivationCodeFilter::getCode).get().isEmpty()) {
				whereCond.append(" AND activationCode like'%").append(filter.map(ActivationCodeFilter::getCode).get())
						.append("%'");
			}

			if (filter.map(ActivationCodeFilter::getPartyName).isPresent()
					&& !filter.map(ActivationCodeFilter::getPartyName).get().isEmpty()) {
				whereCond.append(" AND LOWER(p.name) like'%")
						.append(filter.map(ActivationCodeFilter::getPartyName).get().toLowerCase()).append("%'");
			}

			if (filter.map(ActivationCodeFilter::getPartner).isPresent()) {
				partners = filter.get().getPartner().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partners != null && !partners.isEmpty()) {
					whereCond.append(" AND a.partnerId IN (").append(partners).append(")");
				}
			}

			if (filter.map(ActivationCodeFilter::getTransactionType).isPresent()) {
				transactionType = filter.get().getTransactionType().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (transactionType != null && !transactionType.isEmpty()) {
					whereCond.append(" AND transactionType IN (").append(transactionType).append(")");
				}
			}

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			if (Optional.ofNullable(filter.get().getPurchaseDate_From()).isPresent()
					&& Optional.ofNullable(filter.get().getPurchaseDate_To()).isPresent()) {
				purchaseDate_From = formatter.format(filter.get().getPurchaseDate_From());
				purchaseDate_To = formatter.format(filter.get().getPurchaseDate_To());

				whereCond.append(" AND DATE(purchaseDate) >= '").append(purchaseDate_From).append("'");
				whereCond.append(" AND DATE(purchaseDate) <= '").append(purchaseDate_To).append("'");
			}

		}

		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}

}
