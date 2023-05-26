package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.Party;
import org.phoenix.paywall.model.PartyType;
import org.phoenix.paywall.model.filter.PartyFilter;

public interface IPartyService {
	List<Party> getAll();
	List<Party> getByType(String type);
	List<PartyType> getTypes();
	List<Party> getByFilter(PartyFilter filter);
}