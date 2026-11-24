package com.medical.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username:}")
    private String username;

    @Value("${spring.datasource.password:}")
    private String password;

    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);

        String jdbcUrl = databaseUrl;
        String dbUser = username;
        String dbPass = password;

        // Automatically convert cloud URI format (postgres:// or postgresql://) to JDBC format
        if (databaseUrl != null && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            try {
                URI uri = new URI(databaseUrl);
                String host = uri.getHost();
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String path = uri.getPath();

                if (uri.getUserInfo() != null) {
                    String[] userInfo = uri.getUserInfo().split(":", 2);
                    dbUser = userInfo[0];
                    if (userInfo.length > 1) {
                        dbPass = userInfo[1];
                    }
                }

                jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", host, port, path);
                log.info("Converted cloud DATABASE_URL to JDBC: jdbc:postgresql://{}:{}{}", host, port, path);
            } catch (Exception e) {
                log.warn("Failed to parse DATABASE_URL as URI, using as-is: {}", e.getMessage());
            }
        }

        config.setJdbcUrl(jdbcUrl);
        if (dbUser != null && !dbUser.isBlank()) {
            config.setUsername(dbUser);
        }
        if (dbPass != null && !dbPass.isBlank()) {
            config.setPassword(dbPass);
        }

        // Production connection pool configuration
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setMaxLifetime(1800000);

        return new HikariDataSource(config);
    }
}
