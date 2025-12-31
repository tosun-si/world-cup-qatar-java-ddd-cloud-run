package fr.groupbees.infrastructure.config;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.infrastructure.alloydb.TeamPlayerStatsRawAlloyDBAdapter;
import fr.groupbees.infrastructure.alloydb.TeamPlayerStatsRawJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("alloydb")
@ComponentScan(basePackages = "fr.groupbees.infrastructure.alloydb")
@EntityScan(basePackages = "fr.groupbees.infrastructure.alloydb")
@EnableJpaRepositories(basePackages = "fr.groupbees.infrastructure.alloydb")
public class AlloyDbAdapterConfig {

    @Bean
    public TeamPlayerStatsRawRepository statsRawRepository(TeamPlayerStatsRawJpaRepository statsRawJpaRepository) {
        return new TeamPlayerStatsRawAlloyDBAdapter(statsRawJpaRepository);
    }
}
