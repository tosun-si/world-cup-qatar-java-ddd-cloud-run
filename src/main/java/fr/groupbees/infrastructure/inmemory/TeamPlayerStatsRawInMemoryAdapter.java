package fr.groupbees.infrastructure.inmemory;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.infrastructure.serialization.JsonUtil;

import java.util.List;

public class TeamPlayerStatsRawInMemoryAdapter implements TeamPlayerStatsRawRepository {

    @Override
    public List<TeamPlayerStatsRaw> find() {
        var ref = new TypeReference<List<TeamPlayerStatsRaw>>() {
        };

        return JsonUtil.deserializeFromResourcePath(
                "files/world_cup_team_players_stats_raw.json", ref
        );
    }
}
