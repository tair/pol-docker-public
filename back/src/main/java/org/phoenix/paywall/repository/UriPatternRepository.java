package org.phoenix.paywall.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.phoenix.paywall.model.UriPattern;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UriPatternRepository extends JpaRepository<UriPattern, Long> {
	UriPattern findByPatternId(int patternId);

	List<UriPattern> findAll();
}