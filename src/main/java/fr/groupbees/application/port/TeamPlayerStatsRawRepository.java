package fr.groupbees.application.port;

import fr.groupbees.domain.model.TeamPlayerStatsRaw;

import java.util.List;

public interface TeamPlayerStatsRawRepository {

    List<TeamPlayerStatsRaw> find();
}
