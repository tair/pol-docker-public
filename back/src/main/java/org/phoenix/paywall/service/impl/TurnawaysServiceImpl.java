package org.phoenix.paywall.service.impl;

import org.phoenix.paywall.model.Turnaways;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.repository.TurnawaysRepository;
import org.phoenix.paywall.service.ITurnawaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnawaysServiceImpl implements ITurnawaysService {

	@Autowired
	TurnawaysRepository turnawaysRepository;

	@Override
	public List<Turnaways> getByFilter(UsageFilter filter) {
		return turnawaysRepository.getByFilter(filter);
	}
}