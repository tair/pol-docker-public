package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.SubscriptionTerm;
import org.phoenix.paywall.repository.SubscriptionTermRepository;
import org.phoenix.paywall.service.ISubscriptionTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionTermServiceImpl implements ISubscriptionTermService {
	
	@Autowired
	private SubscriptionTermRepository subscriptionTermRepository;
	
	@Override
	public SubscriptionTerm getById(int id) {
		return subscriptionTermRepository.findBySubscriptionTermId(id);
	}

	@Override
	public List<SubscriptionTerm> getAll() {
		return subscriptionTermRepository.findAll();
	}
	
}