package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.Subscription;
import org.phoenix.paywall.model.filter.SubscriptionFilter;
import org.phoenix.paywall.repository.SubscriptionRepository;
import org.phoenix.paywall.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;

	@Override
	public List<Subscription> getByFilter(SubscriptionFilter filter) {
		return subscriptionRepository.getByFilter(filter);
	}
	
}