package fr.groupbees.domain;

import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;

import java.util.List;

public interface TeamPlayerStatsDomainRepository {

    List<TeamPlayerStats> saveStats(final List<TeamPlayerStats> teamPlayersStats);
}
