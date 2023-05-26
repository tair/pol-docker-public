package org.phoenix.paywall.model.filter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PartyFilter {

	private List<String> partyTypes;
	private String name;

	private String username;
	private String email;
	private String institution;
	private List<String> partners;
	private String userIdentifier;
	private String firstName;
	private String lastName;

	public List<String> getPartyTypes() {
		return partyTypes;
	}

	public void setPartyTypes(List<String> partyTypes) {
		this.partyTypes = partyTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public List<String> getPartners() {
		return partners;
	}

	public void setPartners(List<String> partners) {
		this.partners = partners;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toSqlWhereCondition() {
		StringBuilder whereCond = new StringBuilder();
		Optional<PartyFilter> filter = Optional.of(this);

		if (filter.isPresent()) {

			if (filter.map(PartyFilter::getPartyTypes).isPresent()) {
				String partyTypes = filter.get().getPartyTypes().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partyTypes != null && !partyTypes.isEmpty()) {
					whereCond.append(" AND partyType IN (").append(partyTypes).append(")");
				}
			}

			if (filter.map(PartyFilter::getPartners).isPresent()) {
				String partners = filter.get().getPartners().stream()
						.map(item -> new StringBuilder().append("'").append(item).append("'"))
						.collect(Collectors.joining(","));

				if (partners != null && !partners.isEmpty()) {
					whereCond.append(" AND partnerId IN (").append(partners).append(")");
				}
			}

			if (filter.map(PartyFilter::getName).isPresent()
					&& !filter.map(PartyFilter::getName).get().isEmpty()) {
				whereCond.append(" AND LOWER(name) like'%")
						.append(filter.map(PartyFilter::getName).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getUsername).isPresent()
					&& !filter.map(PartyFilter::getUsername).get().isEmpty()) {
				whereCond.append(" AND LOWER(username) like'%")
						.append(filter.map(PartyFilter::getUsername).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getEmail).isPresent()
					&& !filter.map(PartyFilter::getEmail).get().isEmpty()) {
				whereCond.append(" AND LOWER(email) like'%")
						.append(filter.map(PartyFilter::getEmail).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getInstitution).isPresent()
					&& !filter.map(PartyFilter::getInstitution).get().isEmpty()) {
				whereCond.append(" AND LOWER(institution) like'%")
						.append(filter.map(PartyFilter::getInstitution).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getUserIdentifier).isPresent()
					&& !filter.map(PartyFilter::getUserIdentifier).get().isEmpty()) {
				whereCond.append(" AND LOWER(userIdentifier) like'%")
						.append(filter.map(PartyFilter::getUserIdentifier).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getFirstName).isPresent()
					&& !filter.map(PartyFilter::getFirstName).get().isEmpty()) {
				whereCond.append(" AND LOWER(firstName) like'%")
						.append(filter.map(PartyFilter::getFirstName).get().toLowerCase()).append("%'");
			}

			if (filter.map(PartyFilter::getLastName).isPresent()
					&& !filter.map(PartyFilter::getLastName).get().isEmpty()) {
				whereCond.append(" AND LOWER(lastName) like'%")
						.append(filter.map(PartyFilter::getLastName).get().toLowerCase()).append("%'");
			}

		}

		if (whereCond.length() > 0)
			return whereCond.toString().trim().substring(4);

		return null;
	}

}
