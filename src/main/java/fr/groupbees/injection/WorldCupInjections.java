package fr.groupbees.injection;

import fr.groupbees.application.TeamPlayerStatsController;
import fr.groupbees.domain.TeamPlayerStatsDomainRepository;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import fr.groupbees.infrastructure.InfraDomainDatabaseConfig;
import fr.groupbees.infrastructure.InfraRawDatabaseConfig;
import fr.groupbees.infrastructure.bigquery.TeamPlayerStatsDomainBigQueryRepository;
import fr.groupbees.infrastructure.firestore.TeamPlayerStatsRawFirestoreAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableConfigurationProperties({InfraDomainDatabaseConfig.class, InfraRawDatabaseConfig.class})
@ComponentScan(basePackages = "fr.groupbees.infrastructure.alloydb")
@EntityScan(basePackages = "fr.groupbees.infrastructure.alloydb")
@EnableJpaRepositories(basePackages = "fr.groupbees.infrastructure.alloydb")
public class WorldCupInjections {

//    @Bean
//    public TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector(TeamPlayerStatsRawRepository statsRawRepository) {
//        return new TeamPlayerStatsRawAlloyDBAdapter(statsRawRepository);
//    }

    @Bean
    public TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector(InfraRawDatabaseConfig infraConfig) {
        return new TeamPlayerStatsRawFirestoreAdapter(infraConfig);
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
