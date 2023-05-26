package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.IpRange;
import org.phoenix.paywall.model.filter.IpRangeFilter;
import org.phoenix.paywall.repository.IpRangeRepository;
import org.phoenix.paywall.service.IIPRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPRangeServiceImpl implements IIPRangeService {
	
	@Autowired
	IpRangeRepository ipRangeRepository;
	
	@Override
	public List<IpRange> getByFilter(IpRangeFilter filter) {
		return ipRangeRepository.getByFilter(filter);
	}
	
}