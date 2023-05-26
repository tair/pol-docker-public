package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.MeterBlacklist;
import org.phoenix.paywall.repository.MeterBlacklistRepository;
import org.phoenix.paywall.service.IMeterBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterBlacklistServiceImpl implements IMeterBlacklistService {
	
	@Autowired
	MeterBlacklistRepository MeterBlacklistRepository;
	
	@Override
	public MeterBlacklist getById(int id) {
		return MeterBlacklistRepository.getById(id);
	}

	@Override
	public List<MeterBlacklist> getAll() {
		return MeterBlacklistRepository.getAll();
	}
	
}