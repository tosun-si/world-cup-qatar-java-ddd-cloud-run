package fr.groupbees.infrastructure.inmemory;

import fr.groupbees.application.port.TeamPlayerStatsRepository;
import fr.groupbees.domain.model.TeamPlayerStats;

import java.util.List;

public class TeamPlayerStatsDomainInMemoryAdapter implements TeamPlayerStatsRepository {

    @Override
    public List<TeamPlayerStats> save(List<TeamPlayerStats> teamPlayersStats) {
        return teamPlayersStats;
    }
}
