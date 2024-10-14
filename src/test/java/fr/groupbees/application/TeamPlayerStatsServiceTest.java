package fr.groupbees.application;

import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
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

    @Autowired
    private TeamPlayerStatsRawDatabaseConnector teamPlayerStatsRawDatabaseConnector;

    @Test
    void whenFindTeamPlayersStatsRaw_thenHaveExpectedStatsAsResult() {
        final List<TeamPlayerStatsRaw> teamPlayersStatsRaw = teamPlayerStatsService.findTeamPlayersStatsRaw();

        assertThat(teamPlayersStatsRaw).isNotNull();
        assertThat(teamPlayersStatsRaw).isNotEmpty();
    }

    @Test
    void whenSaveTeamPlayersStatsDomain_thenHaveExpectedStatsAsResult() {
        final List<TeamPlayerStats> teamPlayersStats = teamPlayerStatsService.saveStatsDomain();

        assertThat(teamPlayersStats).isNotNull();
        assertThat(teamPlayersStats).isNotEmpty();
    }
}
