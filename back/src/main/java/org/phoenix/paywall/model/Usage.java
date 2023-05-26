package org.phoenix.paywall.model;

public class Usage {

	private String ip;
	private Long ipLong;
	private int total_views;
	private int paid_content_count;
	private int total_turnaways;
	private String partyName;
	private int month;
	private int year;

	public Usage() {
	}

	public Usage(String ip, int cnt, String pname) {
		this.ip = ip;
		this.total_views = cnt;
		this.partyName = pname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Long getIpLong() {
		return ipLong;
	}

	public void setIpLong(Long ipLong) {
		this.ipLong = ipLong;
	}

	public int getTotal_views() {
		return total_views;
	}

	public void setTotal_views(int total_views) {
		this.total_views = total_views;
	}

	public int getTotal_turnaways() {
		return total_turnaways;
	}

	public void setTotal_turnaways(int total_turnaways) {
		this.total_turnaways = total_turnaways;
	}

	public int getPaid_content_count() {
		return paid_content_count;
	}

	public void setPaid_content_count(int paid_content_count) {
		this.paid_content_count = paid_content_count;
	}

}
