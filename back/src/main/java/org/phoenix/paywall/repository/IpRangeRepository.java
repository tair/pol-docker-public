package org.phoenix.paywall.repository;

import java.util.List;
import java.util.Optional;

import org.phoenix.paywall.model.ActivationCode;
import org.phoenix.paywall.model.IpRange;
import org.phoenix.paywall.model.filter.ActivationCodeFilter;
import org.phoenix.paywall.model.filter.IpRangeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IpRangeRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcPrimaryTemplate;
    
	public List<IpRange> getByFilter(IpRangeFilter filter) {

		String sql = "select ipRangeId, start, end, createdAt, a.partyId," +
				"	p.name as partyName, partyType, email as partyEmail FROM IpRange a LEFT JOIN Party p LEFT JOIN Credential c ON (p.partyId = c.partyId) ON (a.partyId = p.partyId)";
		
		if(Optional.ofNullable(filter).isPresent()) {
			String whereCond = filter.toSqlWhereCondition();
			System.out.println(whereCond);
			if(Optional.ofNullable(whereCond).isPresent()) {
				sql += " WHERE " + whereCond;
			}
		}
		sql += " LIMIT 100000";
		System.out.println(sql);
		
        List<IpRange> ranges = null;
        try {
        	ranges = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(IpRange.class));
        } catch (Exception e) {
            logger.error("Error while retrieving IP Ranges:" + e);
        }
        return ranges;
    }
}
