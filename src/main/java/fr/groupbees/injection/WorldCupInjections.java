package fr.groupbees.injection;

import fr.groupbees.application.TeamPlayerStatsController;
import fr.groupbees.domain.TeamPlayerStatsDomainRepository;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import fr.groupbees.infrastructure.InfraDomainDatabaseConfig;
import fr.groupbees.infrastructure.bigquery.TeamPlayerStatsDomainBigQueryRepository;
import fr.groupbees.infrastructure.inmemory.TeamPlayerStatsRawInMemoryAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfraDomainDatabaseConfig.class)
public class WorldCupInjections {

    @Bean
    public TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector() {
        return new TeamPlayerStatsRawInMemoryAdapter();
    }

    @Bean
    public TeamPlayerStatsDomainRepository statsDomainDatabaseConnector(InfraDomainDatabaseConfig infraConfig) {
        return new TeamPlayerStatsDomainBigQueryRepository(infraConfig);
    }

    @Bean
    public TeamPlayerStatsService teamPlayerStatsService(
            TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector,
            TeamPlayerStatsDomainRepository statsDomainDatabaseConnector) {
        return new TeamPlayerStatsService(statsRawDatabaseConnector, statsDomainDatabaseConnector);
    }

    @Bean
    public TeamPlayerStatsController teamPlayersStatsController(TeamPlayerStatsService teamPlayerStatsService) {
        return new TeamPlayerStatsController(teamPlayerStatsService);
    }
}
