package fr.groupbees.application;

import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TeamPlayerStatsServiceTest {

    @Autowired
    private TeamPlayerStatsService teamPlayerStatsService;

    @Test
    void whenSaveStatsDomain_thenHaveExpectedStatsAsResult() {
        final List<TeamPlayerStats> teamStats = teamPlayerStatsService.saveStatsDomain();

        assertThat(teamStats).isNotNull();
        assertThat(teamStats).isNotEmpty();
    }
}
