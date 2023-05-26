package org.phoenix.paywall.service.impl;

import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.repository.SessionsRepository;
import org.phoenix.paywall.service.ISessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionsServiceImpl implements ISessionsService {
	
	@Autowired
	SessionsRepository sessionsRepository;

	@Override
	public List<Sessions> getByFilter(SessionsFilter filter) {
		return sessionsRepository.getByFilter(filter);
	}
	
}