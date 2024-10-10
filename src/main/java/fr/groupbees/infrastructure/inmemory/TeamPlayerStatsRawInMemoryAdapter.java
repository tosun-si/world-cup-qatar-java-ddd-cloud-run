package fr.groupbees.infrastructure.inmemory;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
import fr.groupbees.infrastructure.JsonUtil;

import java.util.List;

public class TeamPlayerStatsRawInMemoryAdapter implements TeamPlayerStatsRawDatabaseConnector {

    @Override
    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        var ref = new TypeReference<List<TeamPlayerStatsRaw>>() {
        };

        return JsonUtil.deserializeFromResourcePath(
                "files/world_cup_team_players_stats_raw.json", ref
        );
    }
}
