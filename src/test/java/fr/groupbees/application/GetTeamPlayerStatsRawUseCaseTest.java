package fr.groupbees.application;

import fr.groupbees.application.usecase.GetTeamPlayerStatsRawUseCase;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.infrastructure.config.WorldCupTestInjections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WorldCupTestInjections.class)
class GetTeamPlayerStatsRawUseCaseTest {

    @Autowired
    private GetTeamPlayerStatsRawUseCase getTeamPlayerStatsRawUseCase;

    @Test
    void whenFindTeamPlayersStatsRaw_thenHaveExpectedStatsAsResult() {
        final List<TeamPlayerStatsRaw> teamPlayersStatsRaw = getTeamPlayerStatsRawUseCase.execute();

        assertThat(teamPlayersStatsRaw).isNotNull();
        assertThat(teamPlayersStatsRaw).isNotEmpty();
    }
}
