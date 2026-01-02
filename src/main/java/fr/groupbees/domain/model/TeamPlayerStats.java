package fr.groupbees.domain.model;

import fr.groupbees.domain.model.StarPlayersStats.StarPlayerCategory;
import fr.groupbees.domain.service.StarPlayersStatsBuilder;

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

    private static final float MIN_SCORE_ADVANTAGE_RATIO = 1.5f;
    private static final int GOAL_POINTS = 5;
    private static final int TOP_SCORER_GOAL_POINTS = 3;
    private static final int ASSIST_POINTS = 2;
    private static final float ELITE_SAVE_PERCENTAGE_THRESHOLD = 80.0f;
    private static final float RELIABLE_SAVE_PERCENTAGE_THRESHOLD = 70.0f;

    /**
     * Calculates team performance rating (0-100) based on weighted offensive and defensive stats.
     */
    public Integer getPerformanceRating() {
        int score = 0;

        score += safeInt(teamTotalGoals) * GOAL_POINTS;
        score += safeInt(topScorers.goals()) * TOP_SCORER_GOAL_POINTS;
        score += safeInt(bestPassers.goalAssists()) * ASSIST_POINTS;
        score += (int) safeFloat(playersMostInterception.interceptions());
        score += (int) safeFloat(playersMostSuccessfulTackles.successfulTackles());

        return Math.min(100, score);
    }

    /**
     * Classifies team playing style based on offensive vs defensive stats ratio.
     */
    public TeamStyle getTeamStyle() {
        float offensiveScore = safeInt(teamTotalGoals)
                + safeInt(bestPassers.goalAssists())
                + safeFloat(bestDribblers.dribbles());
        float defensiveScore = safeFloat(playersMostInterception.interceptions())
                + safeFloat(playersMostSuccessfulTackles.successfulTackles())
                + safeFloat(playersMostDuelsWon.duels());

        if (offensiveScore > defensiveScore * MIN_SCORE_ADVANTAGE_RATIO) return TeamStyle.OFFENSIVE;
        if (defensiveScore > offensiveScore * MIN_SCORE_ADVANTAGE_RATIO) return TeamStyle.DEFENSIVE;
        return TeamStyle.BALANCED;
    }

    /**
     * Evaluates goalkeeper reliability based on save percentage.
     */
    public GoalkeeperLevel getGoalkeeperReliability() {
        try {
            String savePercentageStr = goalKeeper.savePercentage().replace("%", "").trim();
            float savePercentage = Float.parseFloat(savePercentageStr);

            if (savePercentage >= ELITE_SAVE_PERCENTAGE_THRESHOLD) return GoalkeeperLevel.ELITE;
            if (savePercentage >= RELIABLE_SAVE_PERCENTAGE_THRESHOLD) return GoalkeeperLevel.RELIABLE;
            return GoalkeeperLevel.AVERAGE;
        } catch (NumberFormatException e) {
            return GoalkeeperLevel.AVERAGE;
        }
    }

    /**
     * Returns star players (appearing in multiple "best" stat categories) with their category details.
     */
    public StarPlayersStats getStarPlayersStats() {
        return StarPlayersStatsBuilder.newInstance()
                .withCategory(StarPlayerCategory.TOP_SCORERS, topScorers.players())
                .withCategory(StarPlayerCategory.BEST_PASSERS, bestPassers.players())
                .withCategory(StarPlayerCategory.BEST_DRIBBLERS, bestDribblers.players())
                .withCategory(StarPlayerCategory.MOST_APPEARANCES, playersMostAppearances.players())
                .withCategory(StarPlayerCategory.MOST_DUELS_WON, playersMostDuelsWon.players())
                .withCategory(StarPlayerCategory.MOST_INTERCEPTIONS, playersMostInterception.players())
                .withCategory(StarPlayerCategory.MOST_SUCCESSFUL_TACKLES, playersMostSuccessfulTackles.players())
                .build();
    }

    private int safeInt(Integer value) {
        return value != null ? value : 0;
    }

    private float safeFloat(Float value) {
        return value != null ? value : 0f;
    }

    public enum TeamStyle {
        OFFENSIVE,
        BALANCED,
        DEFENSIVE
    }

    public enum GoalkeeperLevel {
        ELITE,
        RELIABLE,
        AVERAGE
    }

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
