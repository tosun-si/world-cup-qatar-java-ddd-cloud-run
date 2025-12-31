package fr.groupbees.domain.model;

import fr.groupbees.domain.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public record TeamPlayerStatsRaw(
        String nationality,
        int fifaRanking,
        String nationalTeamKitSponsor,
        String position,
        int nationalTeamJerseyNumber,
        String playerDob,
        String club,
        String playerName,
        String appearances,
        String goalsScored,
        String assistsProvided,
        String dribblesPerNinety,
        String interceptionsPerNinety,
        String tacklesPerNinety,
        String totalDuelsWonPerNinety,
        String savePercentage,
        String cleanSheets,
        String brandSponsorAndUsed

) {
    public TeamPlayerStatsRaw {
        Validator.of(this)
                .validate(_ -> nationality, Objects::nonNull, "Nationality should not be null")
                .validate(_ -> nationality, StringUtils::isNotEmpty, "Nationality should not be empty")
                .validate(_ -> fifaRanking, p -> p != 0, "Fifa Ranking should not different than 0")
                .validate(_ -> playerName, Objects::nonNull, "Player name should not be null")
                .validate(_ -> playerName, StringUtils::isNotEmpty, "Player name should not be empty")
                .getOrElseThrow();
    }
}
