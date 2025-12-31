package fr.groupbees.infrastructure.config;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.application.port.TeamPlayerStatsRepository;
import fr.groupbees.application.usecase.GetTeamPlayerStatsDomainUseCase;
import fr.groupbees.application.usecase.GetTeamPlayerStatsRawUseCase;
import fr.groupbees.application.usecase.SaveTeamPlayerStatsDomainUseCase;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import fr.groupbees.infrastructure.inmemory.TeamPlayerStatsDomainInMemoryAdapter;
import fr.groupbees.infrastructure.inmemory.TeamPlayerStatsRawInMemoryAdapter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class WorldCupTestInjections {

    @Bean
    public TeamPlayerStatsRawRepository statsRawRepository() {
        return new TeamPlayerStatsRawInMemoryAdapter();
    }

    @Bean
    public TeamPlayerStatsRepository statsRepository() {
        return new TeamPlayerStatsDomainInMemoryAdapter();
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
}
