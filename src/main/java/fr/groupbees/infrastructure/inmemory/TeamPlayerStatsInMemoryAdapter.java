package fr.groupbees.infrastructure.inmemory;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.groupbees.domain.TeamPlayerStatsDatabaseConnector;
import fr.groupbees.domain.TeamPlayerStatsRaw;

import java.util.List;

public class TeamPlayerStatsInMemoryAdapter implements TeamPlayerStatsDatabaseConnector {

    @Override
    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        var ref = new TypeReference<List<TeamPlayerStatsRaw>>() {
        };

        return JsonUtil.deserializeFromResourcePath(
                "files/world_cup_team_players_stats_raw.json", ref
        );

//        var goalKeeperPlayer = new TeamPlayerStatsRaw(
//                "France",
//                3,
//                "Nike",
//                "GK",
//                1,
//                "Dec 26, 1986",
//                "Tottenham",
//                "Hugo Lloris",
//                "6",
//                "-",
//                "-",
//                "-",
//                "-",
//                "-",
//                "1.10",
//                "70.83%",
//                "17%",
//                "Nike"
//        );
//
//        var mbappePlayer = new TeamPlayerStatsRaw(
//                "France",
//                3,
//                "Nike",
//                "FW",
//                10,
//                "Dec 20, 1998",
//                "PSG",
//                "Kylian Mbappe",
//                "7",
//                "8",
//                "2",
//                "7.24",
//                "0.00",
//                "0.00",
//                "5.12",
//                "-",
//                "-",
//                "Nike"
//        );
//
//        return List.of(
//                goalKeeperPlayer,
//                mbappePlayer
//        );
    }
}
