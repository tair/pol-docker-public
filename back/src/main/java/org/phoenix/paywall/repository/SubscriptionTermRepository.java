package org.phoenix.paywall.repository;

import java.util.List;

import org.phoenix.paywall.model.SubscriptionTerm;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubscriptionTermRepository extends JpaRepository<SubscriptionTerm, Long> {
	SubscriptionTerm findBySubscriptionTermId(int subscriptionTermId);

	List<SubscriptionTerm> findAll();
}
