package org.phoenix.paywall.model.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubscriptionFilter {

	private List<String> partyType;
	private List<String> partner;
	private String partyEmail;
	private String partyName;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date endDate_From;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date startDate_From;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date endDate_To;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date startDate_To;

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

	public Date getEndDate_From() {
		return endDate_From;
	}

	public void setEndDate_From(Date endDate_From) {
		this.endDate_From = endDate_From;
	}

	public Date getStartDate_From() {
		return startDate_From;
	}

	public void setStartDate_From(Date startDate_From) {
		this.startDate_From = startDate_From;
	}

	public Date getEndDate_To() {
		return endDate_To;
	}

	public void setEndDate_To(Date endDate_To) {
		this.endDate_To = endDate_To;
	}

	public Date getStartDate_To() {
		return startDate_To;
	}

	public void setStartDate_To(Date startDate_To) {
		this.startDate_To = startDate_To;
	}

	public List<String> getPartner() {
		return partner;
	}

	public void setPartner(List<String> partner) {
		this.partner = partner;
	}

	public String toSqlWhereCondition() {

		StringBuilder whereCond = new StringBuilder();
		Optional<SubscriptionFilter> filter = Optional.of(this);

		if (filter.isPresent()) {

			String partyTypes = null;
			String partners = null;
			String startDate_From = null;
			String endDate_From = null;
			String startDate_To = null;
			String endDate_To = null;

			if (filter.map(SubscriptionFilter::getPartyType).isPresent()) {
				partyTypes = filter.get().getPartyType().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partyTypes != null && !partyTypes.isEmpty()) {
					whereCond.append(" AND partyType IN (").append(partyTypes).append(")");
				}
			}
			
			if (filter.map(SubscriptionFilter::getPartyEmail).isPresent()
					&& !filter.map(SubscriptionFilter::getPartyEmail).get().isEmpty()) {
				whereCond.append(" AND LOWER(email) like'%").append(filter.map(SubscriptionFilter::getPartyEmail).get().toLowerCase())
						.append("%'");
			}

			if (filter.map(SubscriptionFilter::getPartyName).isPresent()
					&& !filter.map(SubscriptionFilter::getPartyName).get().isEmpty()) {
				whereCond.append(" AND LOWER(p.name) like'%").append(filter.map(SubscriptionFilter::getPartyName).get().toLowerCase())
						.append("%'");
			}

			if (filter.map(SubscriptionFilter::getPartner).isPresent()) {
				partners = filter.get().getPartner().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partners != null && !partners.isEmpty()) {
					whereCond.append(" AND s.partnerId IN (").append(partners).append(")");
				}
			}

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			if (Optional.ofNullable(filter.get().getEndDate_From()).isPresent()
					&& Optional.ofNullable(filter.get().getEndDate_To()).isPresent()) {
				endDate_From = formatter.format(filter.get().getEndDate_From());
				endDate_To = formatter.format(filter.get().getEndDate_To());

				whereCond.append(" AND DATE(endDate) >= '").append(endDate_From).append("'");
				whereCond.append(" AND DATE(endDate) <= '").append(endDate_To).append("'");
			}

			if (Optional.ofNullable(filter.get().getStartDate_From()).isPresent()
					&& Optional.ofNullable(filter.get().getStartDate_To()).isPresent()) {
				startDate_From = formatter.format(filter.get().getStartDate_From());
				startDate_To = formatter.format(filter.get().getStartDate_To());

				whereCond.append(" AND DATE(startDate) >= '").append(startDate_From).append("'");
				whereCond.append(" AND DATE(startDate) <= '").append(startDate_To).append("'");
			}
		}

		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}

}
