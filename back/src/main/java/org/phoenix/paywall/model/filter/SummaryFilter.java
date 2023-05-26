package org.phoenix.paywall.model.filter;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public class SummaryFilter {

	private String type;
	private String partner;
	private String year;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String toSqlWhereCondition() throws Exception {
		StringBuilder whereCond = new StringBuilder();
		Optional<SummaryFilter> filter = Optional.of(this);

		if (filter.isPresent()) {
			System.out.println(filter.get());
			if (filter.get().getType().equals("pageview")) {
				String sql2 = "SELECT childPartyId FROM PartyAffiliation WHERE parentPartyId="
						+ filter.get().getType();
				whereCond.append(" AND party_id IN ").append("(").append(sql2).append(")");
			} else {
//				if (Optional.ofNullable(filter.get().getPartyId()).isPresent()) {
//					whereCond.append(" AND party_id=").append(filter.get().getPartyId());
//				}
			}
		}
		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}
}
