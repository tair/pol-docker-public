package org.phoenix.paywall.repository;

import java.util.List;

import org.phoenix.paywall.model.Partner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PartnerRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcPrimaryTemplate;
	
	public Partner getById(int id) {
		return null;
	}
	
	public List<Partner> getAll() {
		String sql = "SELECT * FROM Partner";
		List<Partner> partners = null;
		try {
			partners = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Partner.class));
		} catch (Exception e) {
			logger.error("Error while retrieving Partners:" + e);
		}
		return partners;
	}
}
