package org.phoenix.paywall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class PaywallOutlookServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(PaywallOutlookServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaywallOutlookServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner logJdbcUrl(DataSource dataSource) {
        return args -> {
            try {
                String jdbcUrl = dataSource.getConnection().getMetaData().getURL();
                logger.info("Connected to JDBC MySQL URL: {}", jdbcUrl);
            } catch (SQLException e) {
                logger.error("Error while retrieving JDBC MySQL URL", e);
            }
        };
    }
}


