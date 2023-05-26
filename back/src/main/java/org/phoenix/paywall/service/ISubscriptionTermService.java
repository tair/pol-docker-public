package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.SubscriptionTerm;

public interface ISubscriptionTermService {
	
	SubscriptionTerm getById(int id);
	
	List<SubscriptionTerm> getAll();
}