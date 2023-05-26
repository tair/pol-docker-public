package org.phoenix.paywall.repository;

import java.util.List;
import java.util.Optional;

import org.phoenix.paywall.model.Usage;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsageRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcSecondaryTemplate;


	public List<Usage> getByFilter(UsageFilter filter) {
		List<Usage> usages = null;
		String table_name = "page_view_stats_" + filter.getPartner();
		String sql = "SELECT s.ip, s.ip_long, s.total_views, s.paid_content_count, s.total_turnaways, s.party_name, s.month, s.year "
				+
				"FROM " + table_name + " s";
		if (Optional.ofNullable(filter).isPresent()) {
			try {
				String whereCond = filter.toSqlWhereCondition();
				if (Optional.ofNullable(whereCond).isPresent()) {
					sql += " WHERE " + whereCond;
				}
				sql += " LIMIT 100000";
				System.out.println(sql);
				usages = jdbcSecondaryTemplate.query(sql, new BeanPropertyRowMapper<>(Usage.class));
			} catch (Exception e) {
				logger.error("Error while retrieving Usage:" + e);
			}
		}
		return usages;
	}
}
