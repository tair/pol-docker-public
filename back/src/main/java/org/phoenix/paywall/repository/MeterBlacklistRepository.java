package org.phoenix.paywall.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.phoenix.paywall.model.MeterBlacklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MeterBlacklistRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public MeterBlacklist getById(int id) {
		return entityManager.find(MeterBlacklist.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<MeterBlacklist> getAll() {
		Query query = entityManager.createNamedQuery(
				"find_all_meterblacklists", MeterBlacklist.class);
		return query.getResultList();
	}
}
