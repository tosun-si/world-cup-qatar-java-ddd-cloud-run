package fr.groupbees.domain;

import java.util.List;

public interface TeamPlayerStatsRawDatabaseConnector {

    List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw();
}
