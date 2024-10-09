package fr.groupbees.injection;

import fr.groupbees.application.TeamPlayerStatsController;
import fr.groupbees.domain.TeamPlayerStatsDatabaseConnector;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import fr.groupbees.infrastructure.inmemory.TeamPlayerStatsInMemoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorldCupInjections {

    @Bean
    public TeamPlayerStatsDatabaseConnector teamPlayersStatsDatabaseConnector() {
        return new TeamPlayerStatsInMemoryAdapter();
    }

    @Bean
    public TeamPlayerStatsService teamPlayerStatsService(TeamPlayerStatsDatabaseConnector teamPlayerStatsDatabaseConnector) {
        return new TeamPlayerStatsService(teamPlayerStatsDatabaseConnector);
    }

    @Bean
    public TeamPlayerStatsController teamPlayersStatsController(TeamPlayerStatsService teamPlayerStatsService) {
        return new TeamPlayerStatsController(teamPlayerStatsService);
    }
}
