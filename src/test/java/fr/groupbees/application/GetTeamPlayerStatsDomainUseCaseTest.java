package fr.groupbees.application;

import fr.groupbees.application.usecase.SaveTeamPlayerStatsDomainUseCase;
import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.infrastructure.config.WorldCupTestInjections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WorldCupTestInjections.class)
class GetTeamPlayerStatsDomainUseCaseTest {

    @Autowired
    private SaveTeamPlayerStatsDomainUseCase saveTeamPlayerStatsDomainUseCase;

    @Test
    void whenSaveTeamPlayersStatsDomain_thenHaveExpectedStatsAsResult() {
        final List<TeamPlayerStats> teamPlayersStats = saveTeamPlayerStatsDomainUseCase.execute();

        assertThat(teamPlayersStats).isNotNull();
        assertThat(teamPlayersStats).isNotEmpty();
    }
}
