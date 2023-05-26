package org.phoenix.paywall.service.impl;

import org.phoenix.paywall.model.Summary;
import org.phoenix.paywall.model.filter.SummaryFilter;
import org.phoenix.paywall.repository.SummaryRepository;
import org.phoenix.paywall.service.ISummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements ISummaryService {
	
	@Autowired
	SummaryRepository summaryRepository;

	@Override
	public List<Summary> getByFilter(SummaryFilter filter) {
		return summaryRepository.getByFilter(filter);
	}
	
}