package fr.groupbees.domain.model;

import java.util.List;

public record TeamPlayerStats(
        String teamName,
        Integer teamTotalGoals,
        String nationalTeamKitSponsor,
        TopScorersStats topScorers,
        BestPassersStats bestPassers,
        BestDribblersStats bestDribblers,
        GoalkeeperStats goalKeeper,
        PlayersMostAppearancesStats playersMostAppearances,
        PlayersMostDuelsWonStats playersMostDuelsWon,
        PlayersMostInterceptionStats playersMostInterception,
        PlayersMostSuccessfulTacklesStats playersMostSuccessfulTackles
) {

    public record Player(
            String playerName,
            String playerDob,
            String position,
            String club,
            String brandSponsorAndUsed,
            Integer appearances
    ) {
    }

    public record BestPassersStats(
            List<Player> players,
            Integer goalAssists
    ) {
    }

    public record TopScorersStats(
            List<Player> players,
            Integer goals
    ) {
    }

    public record BestDribblersStats(
            List<Player> players,
            Float dribbles
    ) {
    }

    public record GoalkeeperStats(
            String playerName,
            String appearances,
            String savePercentage,
            String cleanSheets,
            String club
    ) {
    }

    public record PlayersMostAppearancesStats(
            List<Player> players,
            Integer appearances
    ) {
    }

    public record PlayersMostDuelsWonStats(
            List<Player> players,
            Float duels
    ) {
    }

    public record PlayersMostInterceptionStats(
            List<Player> players,
            Float interceptions
    ) {
    }

    public record PlayersMostSuccessfulTacklesStats(
            List<Player> players,
            Float successfulTackles
    ) {
    }
}
