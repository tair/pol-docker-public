package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.IpRange;
import org.phoenix.paywall.model.filter.IpRangeFilter;

public interface IIPRangeService {
	
	List<IpRange> getByFilter(IpRangeFilter filter);
}