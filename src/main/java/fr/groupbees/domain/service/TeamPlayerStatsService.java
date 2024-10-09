package fr.groupbees.domain.service;

import fr.groupbees.domain.TeamPlayerStatsDatabaseConnector;
import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsMapper;
import fr.groupbees.domain.TeamPlayerStatsRaw;

import java.util.List;

public class TeamPlayerStatsService {

    private final TeamPlayerStatsDatabaseConnector teamPlayerStatsDatabaseConnector;

    public TeamPlayerStatsService(TeamPlayerStatsDatabaseConnector teamPlayerStatsDatabaseConnector) {
        this.teamPlayerStatsDatabaseConnector = teamPlayerStatsDatabaseConnector;
    }

    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        return teamPlayerStatsDatabaseConnector.findTeamPlayersStatsRaw();
    }

    public List<TeamPlayerStats> findTeamPlayersStats() {
        final List<TeamPlayerStatsRaw> statsRaws = teamPlayerStatsDatabaseConnector.findTeamPlayersStatsRaw();

        return TeamPlayerStatsMapper.toTeamPlayersStats(statsRaws);
    }
}
