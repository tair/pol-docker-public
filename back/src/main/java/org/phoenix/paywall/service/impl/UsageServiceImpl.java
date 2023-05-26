package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.Usage;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.repository.UsageRepository;
import org.phoenix.paywall.service.IUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements IUsageService {
	
	@Autowired
	UsageRepository usageRepository;

	@Override
	public List<Usage> getByFilter(UsageFilter filter) {
		return usageRepository.getByFilter(filter);
	}
	
}