package org.phoenix.paywall.model;

public class Turnaways {

	private String ip;
	private Long ipLong;
	private int turnaway_count;
	private int party_id;
	private int month;
	private int year;
	private int date;

	public Turnaways() {
	}

	public Turnaways(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
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

	public int getTurnaways_count() {
		return turnaway_count;
	}

	public void setTurnaways_count(int turnaway_count) {
		this.turnaway_count = turnaway_count;
	}

	public int getParty_id() {
		return party_id;
	}

	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}

}
