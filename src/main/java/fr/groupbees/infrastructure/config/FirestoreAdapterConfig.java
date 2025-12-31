package fr.groupbees.infrastructure.config;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.infrastructure.firestore.TeamPlayerStatsRawFirestoreAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("firestore")
public class FirestoreAdapterConfig {

    @Bean
    public TeamPlayerStatsRawRepository statsRawRepository(InfraRawDatabaseConfig infraConfig) {
        return new TeamPlayerStatsRawFirestoreAdapter(infraConfig);
    }
}
