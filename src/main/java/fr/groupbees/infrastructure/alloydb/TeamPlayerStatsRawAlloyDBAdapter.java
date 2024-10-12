package fr.groupbees.infrastructure.alloydb;

import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
import fr.groupbees.infrastructure.alloydb.entity.TeamStatsRawEntity;

import java.util.List;

public class TeamPlayerStatsRawAlloyDBAdapter implements TeamPlayerStatsRawDatabaseConnector {

    private final TeamPlayerStatsRawRepository statsRawRepository;

    public TeamPlayerStatsRawAlloyDBAdapter(TeamPlayerStatsRawRepository statsRawRepository) {
        this.statsRawRepository = statsRawRepository;
    }

    @Override
    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        final List<TeamStatsRawEntity> teamStatsRawEntities = statsRawRepository.findTeamWithPlayersById();

        return null;
    }
}
