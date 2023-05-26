package org.phoenix.paywall.repository;

import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.Summary;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.SummaryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SummaryRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcSecondaryTemplate;

	public List<Summary> getByFilter(SummaryFilter filter) {
		List<Summary> summaryRecords = null;
		String table_name = "page_view_session_count_" + filter.getPartner();
		String sql = "SELECT SUM(s.session_count) as total_count, s.month, s.year " +
				"FROM "+ table_name + " s where year=" + filter.getYear() + " group by s.month, s.year";
//		System.out.println(filter.getType());
		if(filter.getType().equals("pv_count")) {
			table_name = "page_view_stats_" + filter.getPartner();
			sql = "SELECT SUM(s.total_views) as total_count, s.month, s.year " +
					"FROM "+ table_name + " s where year=" + filter.getYear() + " group by s.month, s.year";
		}
		try{
//			System.out.println(sql);
			summaryRecords = jdbcSecondaryTemplate.query(sql, new BeanPropertyRowMapper<>(Summary.class));
		} catch (Exception e) {
			logger.error("Error while retrieving Usage:" + e);
		}
//		if (Optional.ofNullable(filter).isPresent()) {
//			try {
//				String whereCond = filter.toSqlWhereCondition();
//				if (Optional.ofNullable(whereCond).isPresent()) {
//					sql += " WHERE " + whereCond;
//				}
//				sql += " group by s.month, s.ip";
//				sql += " order by session_count desc";
//				sql += " LIMIT 100000";
//				System.out.println(sql);
//				summaryRecords = jdbcSecondaryTemplate.query(sql, new BeanPropertyRowMapper<>(Summary.class));
//			} catch (Exception e) {
//				logger.error("Error while retrieving Usage:" + e);
//			}
//		}
		return summaryRecords;
	}
}
