package org.phoenix.paywall.model.filter;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public class UsageFilter {

	private String dateFrom; // mm/yyyy string format
	private String dateTo;
	private String partyId;
	private String partyType;
	private String partner;
	private List<String> ipRange;
	private List<String> paidType;

	private int pageHitMin;
	private int pageHitMax;

	public int getPageHitMin() {
		return pageHitMin;
	}

	public void setPageHitMin(int pageHitMin) {
		this.pageHitMin = pageHitMin;
	}

	public int getPageHitMax() {
		return pageHitMax;
	}

	public void setPageHitMax(int pageHitMax) {
		this.pageHitMax = pageHitMax;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public List<String> getIpRange() {
		return ipRange;
	}

	public void setIpRange(List<String> ipRange) {
		this.ipRange = ipRange;
	}

	public List<String> getPaidType() {
		return paidType;
	}

	public void setPaidType(List<String> paidType) {
		this.paidType = paidType;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String toSqlWhereCondition() throws Exception {
		StringBuilder whereCond = new StringBuilder();
		Optional<UsageFilter> filter = Optional.of(this);

		if (filter.isPresent()) {
			if (Optional.ofNullable(filter.get().getPartner()).isPresent()) {
				whereCond.append("AND partner_id=").append("'").append(filter.get().getPartner()).append("'");
			} else {
				throw new Exception("No partner_id found in the query");
			}
			
			if(filter.get().getPageHitMin() > 0) {
				whereCond.append(" AND s.total_views>=").append(filter.get().getPageHitMin());
			}
			if(filter.get().getPageHitMax() > 0) {
				whereCond.append(" AND s.total_views<=").append(filter.get().getPageHitMax());
			}

			if (Optional.ofNullable(filter.get().getDateFrom()).isPresent()) {
				whereCond.append(getDateCondition(filter.get()));
			}
			if (Optional.ofNullable(filter.get().getPartyType()).isPresent()) {
				if (Optional.ofNullable(filter.get().getPartyId()).isPresent()) {
					if (filter.get().getPartyType().equals("consortium")) {
						String sql2 = "SELECT childPartyId FROM PartyAffiliation WHERE parentPartyId="
								+ filter.get().getPartyId();
						whereCond.append(" AND party_id IN ").append("(").append(sql2).append(")");
					} else {
						whereCond.append(" AND party_id=").append(filter.get().getPartyId());
					}
				}
			} else {
				if (Optional.ofNullable(filter.get().getIpRange()).isPresent()) {
					whereCond.append(getIpRangeCondition(filter.get())).append(")");
				}
			}
			if (Optional.ofNullable(filter.get().getPaidType()).isPresent()) {
				if(filter.get().getPaidType().size() == 1) {
					if(filter.get().getPaidType().get(0).equals("paid")) {
						System.out.println("Paid");
						whereCond.append(" AND s.paid_content_count > 0");
					} else if(filter.get().getPaidType().get(0).equals("unpaid")) {
						whereCond.append(" AND s.paid_content_count = 0");
					}

				}
			}

		}
		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}

	private String getDateCondition(UsageFilter filter) {
		StringBuilder dateCondition = new StringBuilder();
		String[] splitData = filter.getDateFrom().split("/");
		String monthFrom = splitData[0].trim();
		String yearFrom = splitData[1].trim();
		if (filter.getDateFrom().equals(filter.getDateTo())) {
			dateCondition.append(" AND month=").append(monthFrom);
			dateCondition.append(" AND year=").append(yearFrom);
		} else {
			String timeStampDateFrom = yearFrom + "-" + monthFrom + "-01";
			String[] splitDataTo = filter.getDateTo().split("/");
			String monthTo = splitDataTo[0].trim();
			String yearTo = splitDataTo[1].trim();
			String timeStampDateTo = yearTo + "-" + monthTo + "-01";
			dateCondition.append(" AND DATE(date_time) >=").append("'").append(timeStampDateFrom).append("'");
			dateCondition.append(" AND DATE(date_time) <=").append("'").append(timeStampDateTo).append("'");
		}
		return dateCondition.toString();
	}

	private String getIpRangeCondition(UsageFilter filter) {
		StringBuilder ipRangeCondition = new StringBuilder();
		List<String> ipRangeList = filter.getIpRange();
		if (ipRangeList.size() > 0) {
			ipRangeCondition.append(" AND");
			ipRangeCondition.append(" (");
			int countIpRangeList = 0;
			for (String ipRange : ipRangeList) {
				if (countIpRangeList > 0) {
					ipRangeCondition.append(" OR");
				}
				if (ipRange.contains("-")) {
					String[] ips = ipRange.split("-");
					if (ips[0].contains("*")) {
						ips[0] = ips[0].replaceAll("\\*", "0");
					}
					if (ips[1].contains("*")) {
						ips[1] = ips[1].replaceAll("\\*", "255");
					}
					Long ipFrom = convertIpToLong(ips[0].trim());
					Long ipTo = convertIpToLong(ips[1].trim());
					ipRangeCondition.append(" ip_long >= ").append(ipFrom);
					ipRangeCondition.append(" AND ip_long <= ").append(ipTo);
				} else {
					String ipRangeFrom = ipRange.trim();
					String ipRangeTo = ipRange.trim();
					if (ipRange.contains("*")) {
						ipRangeFrom = ipRange.replaceAll("\\*", "0");
						ipRangeTo = ipRange.replaceAll("\\*", "255");
					}
					Long ipFrom = convertIpToLong(ipRangeFrom);
					Long ipTo = convertIpToLong(ipRangeTo);
					ipRangeCondition.append(" ip_long >= ").append(ipFrom);
					ipRangeCondition.append(" AND ip_long <= ").append(ipTo);
				}
				countIpRangeList++;
			}
		}

		return ipRangeCondition.toString();
	}

	public long convertIpToLong(String ipAddress) {
		long result = 0;
		try {
			InetAddress address = InetAddress.getByName(ipAddress);
			if (address instanceof Inet6Address) {
				// It's ipv6
			} else if (address instanceof Inet4Address) {
				String[] ipAddressInArray = ipAddress.split("\\.");

				for (int i = 0; i < ipAddressInArray.length; i++) {
					int power = 3 - i;
					int ip = Integer.parseInt(ipAddressInArray[i]);
					result += ip * Math.pow(256, power);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return result;
	}
}
