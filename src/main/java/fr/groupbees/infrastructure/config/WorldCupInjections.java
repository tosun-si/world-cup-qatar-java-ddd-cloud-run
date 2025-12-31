package fr.groupbees.infrastructure.config;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.application.port.TeamPlayerStatsRepository;
import fr.groupbees.application.usecase.GetTeamPlayerStatsDomainUseCase;
import fr.groupbees.application.usecase.GetTeamPlayerStatsRawUseCase;
import fr.groupbees.application.usecase.SaveTeamPlayerStatsDomainUseCase;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import fr.groupbees.infrastructure.bigquery.TeamPlayerStatsDomainBigQueryAdapter;
import fr.groupbees.infrastructure.web.TeamPlayerStatsController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({InfraDomainDatabaseConfig.class, InfraRawDatabaseConfig.class})
public class WorldCupInjections {

    @Bean
    public TeamPlayerStatsRepository statsRepository(InfraDomainDatabaseConfig infraConfig) {
        return new TeamPlayerStatsDomainBigQueryAdapter(infraConfig);
    }

    @Bean
    public TeamPlayerStatsService teamPlayerStatsService() {
        return new TeamPlayerStatsService();
    }

    @Bean
    public GetTeamPlayerStatsRawUseCase getTeamPlayerStatsRawUseCase(
            TeamPlayerStatsRawRepository statsRawRepository) {
        return new GetTeamPlayerStatsRawUseCase(statsRawRepository);
    }

    @Bean
    public GetTeamPlayerStatsDomainUseCase getTeamPlayerStatsDomainUseCase(
            TeamPlayerStatsRawRepository statsRawRepository,
            TeamPlayerStatsService statsService) {
        return new GetTeamPlayerStatsDomainUseCase(statsRawRepository, statsService);
    }

    @Bean
    public SaveTeamPlayerStatsDomainUseCase saveTeamPlayerStatsDomainUseCase(
            TeamPlayerStatsRawRepository statsRawRepository,
            TeamPlayerStatsRepository statsRepository,
            TeamPlayerStatsService statsService) {
        return new SaveTeamPlayerStatsDomainUseCase(statsRawRepository, statsRepository, statsService);
    }

    @Bean
    public TeamPlayerStatsController teamPlayersStatsController(
            GetTeamPlayerStatsRawUseCase getTeamPlayerStatsRawUseCase,
            GetTeamPlayerStatsDomainUseCase getTeamPlayerStatsDomainUseCase) {
        return new TeamPlayerStatsController(getTeamPlayerStatsRawUseCase, getTeamPlayerStatsDomainUseCase);
    }
}
