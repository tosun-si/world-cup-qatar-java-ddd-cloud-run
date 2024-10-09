package fr.groupbees.domain;

import java.util.List;

public interface TeamPlayerStatsDomainObjects {

    record Player(
            String playerName,
            String playerDob,
            String position,
            String club,
            String brandSponsorAndUsed,
            Integer appearances
    ) {
    }

    record BestPassersStats(
            List<Player> players,
            Integer goalAssists
    ) {
    }

    record TopScorersStats(
            List<Player> players,
            Integer goals
    ) {
    }

    record BestDribblersStats(
            List<Player> players,
            Float dribbles
    ) {
    }

    record GoalkeeperStats(
            String playerName,
            String appearances,
            String savePercentage,
            String cleanSheets,
            String club
    ) {
    }

    record PlayersMostAppearancesStats(
            List<Player> players,
            Integer appearances
    ) {
    }


    record PlayersMostDuelsWonStats(
            List<Player> players,
            Float duels
    ) {
    }

    record PlayersMostInterceptionStats(
            List<Player> players,
            Float interceptions
    ) {
    }

    record PlayersMostSuccessfulTacklesStats(
            List<Player> players,
            Float successfulTackles
    ) {
    }

    record TeamPlayerStats(
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
    }
}
