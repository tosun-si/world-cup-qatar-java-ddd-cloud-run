package fr.groupbees.domain.service;

import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsDomainRepository;
import fr.groupbees.domain.TeamPlayerStatsMapper;
import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;

import java.util.List;

public class TeamPlayerStatsService {

    private final TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector;
    private final TeamPlayerStatsDomainRepository statsDomainRepository;

    public TeamPlayerStatsService(TeamPlayerStatsRawDatabaseConnector statsRawDatabaseConnector,
                                  TeamPlayerStatsDomainRepository statsDomainRepository) {
        this.statsRawDatabaseConnector = statsRawDatabaseConnector;
        this.statsDomainRepository = statsDomainRepository;
    }

    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        return statsRawDatabaseConnector.findTeamPlayersStatsRaw();
    }

    public List<TeamPlayerStats> findTeamPlayersStatsDomain() {
        final List<TeamPlayerStatsRaw> statsRaws = statsRawDatabaseConnector.findTeamPlayersStatsRaw();

        return TeamPlayerStatsMapper.toTeamPlayersStats(statsRaws);
    }

    public List<TeamPlayerStats> saveStatsDomain() {
        final List<TeamPlayerStatsRaw> statsRaw = findTeamPlayersStatsRaw();
        final List<TeamPlayerStats> statsDomain = TeamPlayerStatsMapper.toTeamPlayersStats(statsRaw);

        return statsDomainRepository.saveStats(statsDomain);
    }
}
