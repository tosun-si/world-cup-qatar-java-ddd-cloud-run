package fr.groupbees.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.infra.stats.domain.database")
public record InfraDomainDatabaseConfig(String dataset, String table) {
}
