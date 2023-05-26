package org.phoenix.paywall.model.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IpRangeFilter {
	private List<String> partyType;
	private List<String> partner;
	private String partyEmail;
	private String partyName;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date created_From;

	@JsonFormat(pattern = "MM/dd/yyyy", timezone = "PST")
	private Date created_To;


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

	public Date getCreated_From() {
		return created_From;
	}

	public void setCreated_From(Date created_From) {
		this.created_From = created_From;
	}

	public Date getCreated_To() {
		return created_To;
	}

	public void setCreated_To(Date created_To) {
		this.created_To = created_To;
	}

	public String toSqlWhereCondition() {

		StringBuilder whereCond = new StringBuilder();
		Optional<IpRangeFilter> filter = Optional.of(this);

		if (filter.isPresent()) {

			String partyTypes = null;
			String partners = null;
			String createdDate_From = null;
			String createdDate_To = null;

			if (filter.map(IpRangeFilter::getPartyType).isPresent()) {
				partyTypes = filter.get().getPartyType().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partyTypes != null && !partyTypes.isEmpty()) {
					whereCond.append(" AND partyType IN (").append(partyTypes).append(")");
				}
			}

			if (filter.map(IpRangeFilter::getPartyEmail).isPresent()
					&& !filter.map(IpRangeFilter::getPartyEmail).get().isEmpty()) {
				whereCond.append(" AND LOWER(email) like'%").append(filter.map(IpRangeFilter::getPartyEmail).get().toLowerCase())
						.append("%'");
			}

			if (filter.map(IpRangeFilter::getPartyName).isPresent()
					&& !filter.map(IpRangeFilter::getPartyName).get().isEmpty()) {
				whereCond.append(" AND LOWER(p.name) like'%")
						.append(filter.map(IpRangeFilter::getPartyName).get().toLowerCase()).append("%'");
			}

			if (filter.map(IpRangeFilter::getPartner).isPresent()) {
				partners = filter.get().getPartner().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partners != null && !partners.isEmpty()) {
					whereCond.append(" AND a.partnerId IN (").append(partners).append(")");
				}
			}


			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			if (Optional.ofNullable(filter.get().getCreated_From()).isPresent()
					&& Optional.ofNullable(filter.get().getCreated_To()).isPresent()) {
				createdDate_From = formatter.format(filter.get().getCreated_From());
				createdDate_To = formatter.format(filter.get().getCreated_To());

				whereCond.append(" AND DATE(purchaseDate) >= '").append(createdDate_From).append("'");
				whereCond.append(" AND DATE(purchaseDate) <= '").append(createdDate_To).append("'");
			}

		}

		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}

}
