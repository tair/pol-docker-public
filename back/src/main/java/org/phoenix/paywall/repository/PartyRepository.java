package org.phoenix.paywall.repository;

import java.util.List;
import java.util.Optional;

import org.phoenix.paywall.model.Party;
import org.phoenix.paywall.model.PartyType;
import org.phoenix.paywall.model.filter.PartyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PartyRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private JdbcTemplate jdbcPrimaryTemplate;
    
    public List<Party> getAll() {
        return getByType(null);
    }
    
    public List<Party> getAllOrgs() {
        String sql = "SELECT " + "p.partyId, p.partyType, p.name " + "FROM Party p"
                + " WHERE p.partyType='organization'";
        sql += " LIMIT 100000";
        List<Party> parties = null;
        try {
            parties = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Party.class));
        } catch (Exception e) {
            logger.error("Error while retrieving Parties:" + e);
        }
        return parties;
    }
	
	public List<Party> getByType(String type) {

        String sql = "SELECT " + 
		        		"p.partyId, p.partyType, p.name, username, email, institution, partnerId, userIdentifier, firstName, lastName " + 
		        		"FROM Party p LEFT JOIN Credential c ON (p.partyId = c.partyId) WHERE p.name != ''";
		
        if(type != null && type.length() > 0) {
        		sql += " AND partyType='" + type + "'";
		}
		sql += " LIMIT 100000";

        List<Party> parties = null;
        try {
            parties = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Party.class));
        } catch (Exception e) {
            logger.error("Error while retrieving Parties:" + e);
        }
        return parties;
    }
	
	public List<Party> getByFilter(PartyFilter filter) {

		String sql = "SELECT " + 
        		"p.partyId, p.partyType, p.name, username, email, institution, partnerId, userIdentifier, firstName, lastName " + 
        		"FROM Party p LEFT JOIN Credential c ON (p.partyId = c.partyId)";

		if(Optional.ofNullable(filter).isPresent()) {
			String whereCond = filter.toSqlWhereCondition(); 
			if(Optional.ofNullable(whereCond).isPresent()) {
				sql += " WHERE " + whereCond;
			}
		}
		sql += " LIMIT 100000";
		System.out.println(sql);
		
        List<Party> codes = null;
        try {
        	codes = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Party.class));
        } catch (Exception e) {
            logger.error("Error while retrieving Party:" + e);
        }
        return codes;
    }
	
	public List<PartyType> getTypes() {
		
		String sql = "select partyType as type, count(*) as count from Party group by partyType";

        List<PartyType> types = null;
        try {
            types = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(PartyType.class));
        } catch (Exception e) {
            logger.error("Error while retrieving Parties:" + e);
        }
        return types;
	}
	
}
