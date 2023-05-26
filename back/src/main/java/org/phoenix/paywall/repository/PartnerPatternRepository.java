package org.phoenix.paywall.repository;

import java.util.List;

import org.phoenix.paywall.model.PartnerPattern;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PartnerPatternRepository extends JpaRepository<PartnerPattern, Long> {
	PartnerPattern findByPartnerPatternId(int partnerPatternId);

	List<PartnerPattern> findAll();
}