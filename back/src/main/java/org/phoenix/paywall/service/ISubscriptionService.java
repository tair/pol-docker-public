package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.Subscription;
import org.phoenix.paywall.model.filter.SubscriptionFilter;

public interface ISubscriptionService {
	
	List<Subscription> getByFilter(SubscriptionFilter filter);
}