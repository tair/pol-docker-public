package org.phoenix.paywall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "UriPattern")
@NamedQuery(name = "find_all_uripatterns", query = "SELECT p FROM UriPattern p")
public class UriPattern {

	@Id
	private int patternId;
	private String pattern;

	public int getPatternId() {
		return patternId;
	}

	public void setPatternId(int patternId) {
		this.patternId = patternId;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
