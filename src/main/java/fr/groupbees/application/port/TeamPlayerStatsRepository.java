package fr.groupbees.application.port;

import fr.groupbees.domain.model.TeamPlayerStats;

import java.util.List;

public interface TeamPlayerStatsRepository {

    List<TeamPlayerStats> save(List<TeamPlayerStats> teamPlayersStats);
}
