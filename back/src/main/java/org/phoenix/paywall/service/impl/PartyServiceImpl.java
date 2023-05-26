package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.Party;
import org.phoenix.paywall.model.PartyType;
import org.phoenix.paywall.model.filter.PartyFilter;
import org.phoenix.paywall.repository.PartyRepository;
import org.phoenix.paywall.service.IPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyServiceImpl implements IPartyService {

	@Autowired
	PartyRepository partyRepository;
	
	@Override
	public List<Party> getAll() {
		return partyRepository.getAll();
	}

	@Override
	public List<Party> getByType(String type) {
		return partyRepository.getByType(type);
	}
	
	@Override
	public List<PartyType> getTypes() {
		return partyRepository.getTypes();
	}
	
	@Override
	public List<Party> getByFilter(PartyFilter filter) {
		return partyRepository.getByFilter(filter);
	}
}
