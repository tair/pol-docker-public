package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.PartnerPattern;
import org.phoenix.paywall.repository.PartnerPatternRepository;
import org.phoenix.paywall.service.IPartnerPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerPatternServiceImpl implements IPartnerPatternService {
	
	@Autowired
	PartnerPatternRepository partnerPatternRepository;
	
	@Override
	public PartnerPattern getById(int id) {
		return partnerPatternRepository.findByPartnerPatternId(id);
	}

	@Override
	public List<PartnerPattern> getAll() {
		return partnerPatternRepository.findAll();
	}
	
}