package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.Partner;
import org.phoenix.paywall.repository.PartnerRepository;
import org.phoenix.paywall.service.IPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerServiceImpl implements IPartnerService {
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Override
	public Partner getById(int id) {
		return partnerRepository.getById(id);
	}

	@Override
	public List<Partner> getAll() {
		return partnerRepository.getAll();
	}
	
}