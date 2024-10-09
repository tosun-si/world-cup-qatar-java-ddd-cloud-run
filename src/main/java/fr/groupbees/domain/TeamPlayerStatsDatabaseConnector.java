package fr.groupbees.domain;

import java.util.List;

public interface TeamPlayerStatsDatabaseConnector {

    List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw();
}
