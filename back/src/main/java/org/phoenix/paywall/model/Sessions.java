package org.phoenix.paywall.model;

public class Sessions {

	private String ip;
	private Long ipLong;
	private int session_count;
	private int party_id;
	private int month;
	private int year;
	private int date;
	private int last_view_time;

	public Sessions() {
	}

	public Sessions(String ip) {
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

	public int getSession_count() {
		return session_count;
	}

	public void setSession_count(int session_count) {
		this.session_count = session_count;
	}

	public int getParty_id() {return party_id;}

	public void setParty_id(int party_id) {this.party_id = party_id;}

	public int getLast_view_time() {return last_view_time;}

	public void setLast_view_time(int last_view_time) {this.last_view_time = last_view_time;}

}
