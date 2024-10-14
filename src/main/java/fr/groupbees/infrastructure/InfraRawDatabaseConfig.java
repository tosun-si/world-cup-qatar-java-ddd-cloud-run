package fr.groupbees.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.infra.stats.raw.database")
public record InfraRawDatabaseConfig(String firestoreCollectionName) {
}
