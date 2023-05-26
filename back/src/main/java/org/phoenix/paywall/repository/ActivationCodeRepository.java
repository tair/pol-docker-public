package org.phoenix.paywall.repository;

import java.util.List;
import java.util.Optional;

import org.phoenix.paywall.model.ActivationCode;
import org.phoenix.paywall.model.filter.ActivationCodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActivationCodeRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcPrimaryTemplate;
    
	public List<ActivationCode> getByFilter(ActivationCodeFilter filter) {

		String sql = "select activationCodeId, activationCode, period, purchaseDate, a.partnerId, partyId_id as partyId, transactionType, a.deleteMarker,"
				+
				"	p.name as partyName, partyType, email as partyEmail FROM ActivationCode a LEFT JOIN Party p LEFT JOIN Credential c ON (p.partyId = c.partyId) ON (a.partyId_id = p.partyId)";

		if (Optional.ofNullable(filter).isPresent()) {
			String whereCond = filter.toSqlWhereCondition();
			if (Optional.ofNullable(whereCond).isPresent()) {
				sql += " WHERE " + whereCond;
			}
		}
		sql += " LIMIT 100000";
		System.out.println(sql);

		List<ActivationCode> codes = null;
		try {
			codes = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(ActivationCode.class));
		} catch (Exception e) {
			logger.error("Error while retrieving Activation Codes:" + e);
		}
		return codes;
	}

	public boolean deleteByActivationId(int activationCodeId) {
		String sql = "UPDATE ActivationCode SET deleteMarker = true WHERE activationCodeId = ?";
		int result = jdbcPrimaryTemplate.update(sql, activationCodeId);
		return result > 0;
	}

	public boolean createActivationCode(ActivationCode activationCode) {
		String sql = "INSERT INTO ActivationCode (activationCode, period, purchaseDate, partnerId, partyId_id, transactionType, deleteMarker) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			int result = jdbcPrimaryTemplate.update(sql, activationCode.getActivationCode(), activationCode.getPeriod(),
					activationCode.getPurchaseDate(), activationCode.getPartnerId(), activationCode.getPartyId(),
					activationCode.getTransactionType(), activationCode.isDeleteMarker());
			return result > 0;
		} catch (Exception e) {
			logger.error("Error while creating Activation Code:" + e);
			return false;
		}
	}
}
