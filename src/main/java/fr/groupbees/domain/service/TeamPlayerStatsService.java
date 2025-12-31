package fr.groupbees.domain.service;

import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStats.*;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

public class TeamPlayerStatsService {

    public List<TeamPlayerStats> toTeamPlayerStats(List<TeamPlayerStatsRaw> teamPlayersStatsRaw) {
        return teamPlayersStatsRaw
                .stream()
                .collect(groupingBy(TeamPlayerStatsRaw::nationality))
                .entrySet()
                .stream()
                .map(this::toTeamPlayerStats)
                .toList();
    }

    private TeamPlayerStats toTeamPlayerStats(Entry<String, List<TeamPlayerStatsRaw>> statsRawWithTeam) {
        String nationality = statsRawWithTeam.getKey();
        List<TeamPlayerStatsRaw> teamPlayersStatsRaw = statsRawWithTeam.getValue();

        final Tuple2<String, List<Player>> topScorersWithStat = getPlayersWithMaxStatsInt(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::goalsScored
        );

        var bestPassersWithStat = getPlayersWithMaxStatsInt(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::assistsProvided
        );

        var bestDribblersWithStat = getPlayersWithMaxStatsFloat(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::dribblesPerNinety
        );

        var appearancesWithStat = getPlayersWithMaxStatsInt(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::appearances
        );

        var playersMostDuelsWonWithStat = getPlayersWithMaxStatsFloat(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::totalDuelsWonPerNinety
        );

        var playersMostInterceptionsWithStat = getPlayersWithMaxStatsFloat(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::interceptionsPerNinety
        );

        var playersMostSuccessfulTacklesWithStat = getPlayersWithMaxStatsFloat(
                teamPlayersStatsRaw,
                TeamPlayerStatsRaw::tacklesPerNinety
        );

        final TeamPlayerStatsRaw currentGoalKeeperStats = teamPlayersStatsRaw
                .stream()
                .filter(r -> !r.savePercentage().equals("-"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Goalkeeper stats for the team: " + nationality));

        final int teamTotalGoals = teamPlayersStatsRaw
                .stream()
                .map(TeamPlayerStatsRaw::goalsScored)
                .filter(this::isInteger)
                .mapToInt(Integer::parseInt)
                .sum();

        var topScorersStats = new TopScorersStats(
                topScorersWithStat._2,
                toIntegerOrNull(topScorersWithStat._1)
        );

        var bestPassersStats = new BestPassersStats(
                bestPassersWithStat._2,
                toIntegerOrNull(bestPassersWithStat._1)
        );

        var bestDribblersStats = new BestDribblersStats(
                bestDribblersWithStat._2,
                toFloatOrNull(bestDribblersWithStat._1)
        );

        var playersMostAppearancesStats = new PlayersMostAppearancesStats(
                appearancesWithStat._2,
                toIntegerOrNull(appearancesWithStat._1)
        );

        var playersMostDuelsWonStats = new PlayersMostDuelsWonStats(
                playersMostDuelsWonWithStat._2,
                toFloatOrNull(playersMostDuelsWonWithStat._1)
        );

        var playersMostInterceptionStats = new PlayersMostInterceptionStats(
                playersMostInterceptionsWithStat._2,
                toFloatOrNull(playersMostInterceptionsWithStat._1)
        );

        var playersMostSuccessfulTacklesStats = new PlayersMostSuccessfulTacklesStats(
                playersMostSuccessfulTacklesWithStat._2,
                toFloatOrNull(playersMostSuccessfulTacklesWithStat._1)
        );

        var goalKeeperStats = new GoalkeeperStats(
                currentGoalKeeperStats.playerName(),
                currentGoalKeeperStats.club(),
                currentGoalKeeperStats.appearances(),
                currentGoalKeeperStats.savePercentage(),
                currentGoalKeeperStats.cleanSheets()
        );

        var currentTeam = teamPlayersStatsRaw
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No team found in a the list of players grouped by team: " + nationality));

        return new TeamPlayerStats(
                nationality,
                teamTotalGoals,
                currentTeam.nationalTeamKitSponsor(),
                topScorersStats,
                bestPassersStats,
                bestDribblersStats,
                goalKeeperStats,
                playersMostAppearancesStats,
                playersMostDuelsWonStats,
                playersMostInterceptionStats,
                playersMostSuccessfulTacklesStats
        );
    }

    private Tuple2<String, List<Player>> getPlayersWithMaxStatsInt(final List<TeamPlayerStatsRaw> teamPlayersStatsRaw,
                                                                    final Function<TeamPlayerStatsRaw, String> projectionOnStatField) {
        var maxStatValue = teamPlayersStatsRaw
                .stream()
                .filter(r -> isInteger(projectionOnStatField.apply(r)))
                .max(comparing(r -> Integer.parseInt(projectionOnStatField.apply(r))))
                .map(projectionOnStatField)
                .orElse("");

        final List<Player> playersMaxStat = teamPlayersStatsRaw
                .stream()
                .filter(r -> projectionOnStatField.apply(r).equals(maxStatValue))
                .filter(r -> !projectionOnStatField.apply(r).isEmpty())
                .filter(r -> !projectionOnStatField.apply(r).equals("0"))
                .map(this::toPlayer)
                .toList();

        return Tuple.of(maxStatValue, playersMaxStat);
    }

    private Tuple2<String, List<Player>> getPlayersWithMaxStatsFloat(final List<TeamPlayerStatsRaw> teamPlayersStatsRaw,
                                                                      final Function<TeamPlayerStatsRaw, String> projectionOnStatField) {
        var maxStatValue = teamPlayersStatsRaw
                .stream()
                .filter(r -> isFloat(projectionOnStatField.apply(r)))
                .max(comparing(r -> Float.parseFloat(projectionOnStatField.apply(r))))
                .map(projectionOnStatField)
                .orElse("");

        final List<Player> playersMaxStat = teamPlayersStatsRaw
                .stream()
                .filter(r -> projectionOnStatField.apply(r).equals(maxStatValue))
                .filter(r -> !projectionOnStatField.apply(r).isEmpty())
                .filter(r -> !projectionOnStatField.apply(r).equals("0.0"))
                .map(this::toPlayer)
                .toList();

        return Tuple.of(maxStatValue, playersMaxStat);
    }

    private Player toPlayer(TeamPlayerStatsRaw teamPlayerStats) {
        return new Player(
                teamPlayerStats.playerName(),
                teamPlayerStats.playerDob(),
                teamPlayerStats.position(),
                teamPlayerStats.club(),
                teamPlayerStats.brandSponsorAndUsed(),
                toIntegerOrNull(teamPlayerStats.appearances())
        );
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Integer toIntegerOrNull(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Float toFloatOrNull(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
