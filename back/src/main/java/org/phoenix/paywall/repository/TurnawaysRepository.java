package org.phoenix.paywall.repository;

import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.Turnaways;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TurnawaysRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcSecondaryTemplate;

	public List<Turnaways> getByFilter(UsageFilter filter) {
		List<Turnaways> turnaways = null;
		String table_name = "page_view_stats_" + filter.getPartner();
		String sql = "SELECT s.ip, s.ip_long, s.total_turnaways as turnaway_count, s.party_id, s.month, s.year "
				+
				"FROM " + table_name + " s";
		if (Optional.ofNullable(filter).isPresent()) {
			try {
				String whereCond = filter.toSqlWhereCondition();
				if (Optional.ofNullable(whereCond).isPresent()) {
					sql += " WHERE " + whereCond;
				}
				sql += " group by s.month, s.ip";
				sql += " order by turnaway_count desc";
				sql += " LIMIT 100000";
				System.out.println(sql);
				turnaways = jdbcSecondaryTemplate.query(sql, new BeanPropertyRowMapper<>(Turnaways.class));
			} catch (Exception e) {
				logger.error("Error while retrieving Turnaways:" + e);
				return null;
			}
		}
		return turnaways;
	}
}
