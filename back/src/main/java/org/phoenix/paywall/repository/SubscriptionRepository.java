package org.phoenix.paywall.repository;

import java.util.List;
import java.util.Optional;

import org.phoenix.paywall.model.Subscription;
import org.phoenix.paywall.model.filter.SubscriptionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcPrimaryTemplate;

	public List<Subscription> getByFilter(SubscriptionFilter filter) {

		String sql = "SELECT s.subscriptionId, s.startDate, s.endDate, s.partnerId, s.partyId, p.name as partyName, p.partyType, email as partyEmail "
				+ "FROM Subscription s LEFT JOIN Party p LEFT JOIN Credential c ON (p.partyId = c.partyId) ON (s.partyId = p.partyId)";
		
		if(Optional.ofNullable(filter).isPresent()) {
			String whereCond = filter.toSqlWhereCondition(); 
			if(Optional.ofNullable(whereCond).isPresent()) {
				sql += " WHERE " + whereCond;
			}
		}
		sql += " LIMIT 100000";
		System.out.println(sql);
		
        List<Subscription> subsciptions = null;
        try {
        	subsciptions = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class));
        } catch (Exception e) {
            logger.error("Error while retrieving Parties:" + e);
        }
        return subsciptions;
    }
}
