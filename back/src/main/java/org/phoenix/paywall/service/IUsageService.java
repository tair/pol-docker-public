package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.Usage;
import org.phoenix.paywall.model.filter.UsageFilter;

public interface IUsageService {
	
	List<Usage> getByFilter(UsageFilter filter);
}