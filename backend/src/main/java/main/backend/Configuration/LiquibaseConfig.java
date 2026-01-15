package main.backend.Configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class LiquibaseConfig {

    @Value("${POSTGRESQL_URL}")
    private String url;

    @Value("${POSTGRESQL_USERNAME}")
    private String username;

    @Value("${POSTGRESQL_PASSWORD}")
    private String password;

    @Bean
    @LiquibaseDataSource
    public DataSource liquibaseDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
