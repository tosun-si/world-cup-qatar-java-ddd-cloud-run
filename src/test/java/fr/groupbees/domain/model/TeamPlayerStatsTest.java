package fr.groupbees.domain.model;

import fr.groupbees.domain.model.StarPlayersStats.StarPlayerCategory;
import fr.groupbees.domain.model.TeamPlayerStats.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamPlayerStatsTest {

    // ==================== Performance Rating Tests ====================

    @Test
    void givenTeamWithOffensiveAndDefensiveStats_whenGetPerformanceRating_thenReturnsWeightedScore() {
        TeamPlayerStats stats = createTeamPlayerStats(
                10,   // teamTotalGoals
                5,    // topScorerGoals
                3,    // bestPasserAssists
                2.0f, // interceptions
                1.5f  // tackles
        );

        Integer rating = stats.getPerformanceRating();

        // Expected: (10*5) + (5*3) + (3*2) + 2 + 1 = 50 + 15 + 6 + 2 + 1 = 74
        assertThat(rating).isEqualTo(74);
    }

    @Test
    void givenTeamWithVeryHighStats_whenGetPerformanceRating_thenReturnsCappedAt100() {
        TeamPlayerStats stats = createTeamPlayerStats(
                20,   // teamTotalGoals (20*5 = 100 alone)
                10,   // topScorerGoals
                5,    // bestPasserAssists
                5.0f, // interceptions
                5.0f  // tackles
        );

        Integer rating = stats.getPerformanceRating();

        assertThat(rating).isEqualTo(100);
    }

    // ==================== Team Style Tests ====================

    @Test
    void givenHighOffensiveStats_whenGetTeamStyle_thenReturnsOffensive() {
        TeamPlayerStats stats = createTeamPlayerStatsWithStyle(
                15,   // teamTotalGoals
                8,    // bestPasserAssists
                5.0f, // dribbles
                1.0f, // interceptions
                1.0f, // tackles
                1.0f  // duels
        );

        TeamStyle style = stats.getTeamStyle();

        assertThat(style).isEqualTo(TeamStyle.OFFENSIVE);
    }

    @Test
    void givenHighDefensiveStats_whenGetTeamStyle_thenReturnsDefensive() {
        TeamPlayerStats stats = createTeamPlayerStatsWithStyle(
                2,    // teamTotalGoals
                1,    // bestPasserAssists
                0.5f, // dribbles
                8.0f, // interceptions
                7.0f, // tackles
                6.0f  // duels
        );

        TeamStyle style = stats.getTeamStyle();

        assertThat(style).isEqualTo(TeamStyle.DEFENSIVE);
    }

    @Test
    void givenSimilarOffensiveAndDefensiveStats_whenGetTeamStyle_thenReturnsBalanced() {
        TeamPlayerStats stats = createTeamPlayerStatsWithStyle(
                5,    // teamTotalGoals
                3,    // bestPasserAssists
                2.0f, // dribbles
                3.0f, // interceptions
                3.0f, // tackles
                4.0f  // duels
        );

        TeamStyle style = stats.getTeamStyle();

        assertThat(style).isEqualTo(TeamStyle.BALANCED);
    }

    // ==================== Goalkeeper Reliability Tests ====================

    @Test
    void givenGoalkeeperWithSavePercentageAbove80_whenGetGoalkeeperReliability_thenReturnsElite() {
        TeamPlayerStats stats = createTeamPlayerStatsWithGoalkeeper("85%");

        GoalkeeperLevel level = stats.getGoalkeeperReliability();

        assertThat(level).isEqualTo(GoalkeeperLevel.ELITE);
    }

    @Test
    void givenGoalkeeperWithSavePercentageBetween70And80_whenGetGoalkeeperReliability_thenReturnsReliable() {
        TeamPlayerStats stats = createTeamPlayerStatsWithGoalkeeper("75%");

        GoalkeeperLevel level = stats.getGoalkeeperReliability();

        assertThat(level).isEqualTo(GoalkeeperLevel.RELIABLE);
    }

    @Test
    void givenGoalkeeperWithSavePercentageBelow70_whenGetGoalkeeperReliability_thenReturnsAverage() {
        TeamPlayerStats stats = createTeamPlayerStatsWithGoalkeeper("65%");

        GoalkeeperLevel level = stats.getGoalkeeperReliability();

        assertThat(level).isEqualTo(GoalkeeperLevel.AVERAGE);
    }

    @Test
    void givenGoalkeeperWithInvalidSavePercentage_whenGetGoalkeeperReliability_thenReturnsAverage() {
        TeamPlayerStats stats = createTeamPlayerStatsWithGoalkeeper("-");

        GoalkeeperLevel level = stats.getGoalkeeperReliability();

        assertThat(level).isEqualTo(GoalkeeperLevel.AVERAGE);
    }

    // ==================== Star Players Stats Tests ====================

    @Test
    void givenPlayersAppearingInMultipleCategories_whenGetStarPlayersStats_thenReturnsPlayersWithCategories() {
        Player messi = createPlayer("Lionel Messi");
        Player mbappe = createPlayer("Kylian Mbappe");
        Player griezmann = createPlayer("Antoine Griezmann");
        Player kante = createPlayer("N'Golo Kante");

        var stats = new TeamPlayerStats(
                "France",
                10,
                "Nike",
                new TopScorersStats(List.of(messi, mbappe), 5),
                new BestPassersStats(List.of(messi, griezmann), 3),
                new BestDribblersStats(List.of(mbappe), 4.0f),
                createGoalkeeperStats("80%"),
                new PlayersMostAppearancesStats(List.of(kante), 7),
                new PlayersMostDuelsWonStats(List.of(kante), 5.0f),
                new PlayersMostInterceptionStats(List.of(griezmann), 3.0f),
                new PlayersMostSuccessfulTacklesStats(List.of(kante), 4.0f)
        );

        StarPlayersStats starPlayersStats = stats.getStarPlayersStats();

        // Messi (2), Mbappe (2), Griezmann (2), Kante (3) = 4 star players
        assertThat(starPlayersStats.totalCount()).isEqualTo(4);
        assertThat(starPlayersStats.starPlayers()).hasSize(4);

        // Verify Messi appears in Top Scorers and Best Passers
        StarPlayerDetail messiDetail = starPlayersStats.starPlayers().stream()
                .filter(p -> p.playerName().equals("Lionel Messi"))
                .findFirst()
                .orElseThrow();
        assertThat(messiDetail.categories()).containsExactlyInAnyOrder(
                StarPlayerCategory.TOP_SCORERS,
                StarPlayerCategory.BEST_PASSERS
        );
        assertThat(messiDetail.categoryCount()).isEqualTo(2);

        // Verify Kante appears in 3 categories
        StarPlayerDetail kanteDetail = starPlayersStats.starPlayers().stream()
                .filter(p -> p.playerName().equals("N'Golo Kante"))
                .findFirst()
                .orElseThrow();
        assertThat(kanteDetail.categories()).containsExactlyInAnyOrder(
                StarPlayerCategory.MOST_APPEARANCES,
                StarPlayerCategory.MOST_DUELS_WON,
                StarPlayerCategory.MOST_SUCCESSFUL_TACKLES
        );
        assertThat(kanteDetail.categoryCount()).isEqualTo(3);
    }

    @Test
    void givenNoPlayerAppearingInMultipleCategories_whenGetStarPlayersStats_thenReturnsEmptyList() {
        var stats = new TeamPlayerStats(
                "France",
                10,
                "Nike",
                new TopScorersStats(List.of(createPlayer("Player1")), 5),
                new BestPassersStats(List.of(createPlayer("Player2")), 3),
                new BestDribblersStats(List.of(createPlayer("Player3")), 4.0f),
                createGoalkeeperStats("80%"),
                new PlayersMostAppearancesStats(List.of(createPlayer("Player4")), 7),
                new PlayersMostDuelsWonStats(List.of(createPlayer("Player5")), 5.0f),
                new PlayersMostInterceptionStats(List.of(createPlayer("Player6")), 3.0f),
                new PlayersMostSuccessfulTacklesStats(List.of(createPlayer("Player7")), 4.0f)
        );

        StarPlayersStats starPlayersStats = stats.getStarPlayersStats();

        assertThat(starPlayersStats.totalCount()).isEqualTo(0);
        assertThat(starPlayersStats.starPlayers()).isEmpty();
    }

    // ==================== Helper Methods ====================

    private TeamPlayerStats createTeamPlayerStats(int totalGoals,
                                                  int topScorerGoals,
                                                  int assists,
                                                  float interceptions,
                                                  float tackles) {
        return new TeamPlayerStats(
                "TestTeam",
                totalGoals,
                "Nike",
                new TopScorersStats(List.of(), topScorerGoals),
                new BestPassersStats(List.of(), assists),
                new BestDribblersStats(List.of(), 0f),
                createGoalkeeperStats("75%"),
                new PlayersMostAppearancesStats(List.of(), 0),
                new PlayersMostDuelsWonStats(List.of(), 0f),
                new PlayersMostInterceptionStats(List.of(), interceptions),
                new PlayersMostSuccessfulTacklesStats(List.of(), tackles)
        );
    }

    private TeamPlayerStats createTeamPlayerStatsWithStyle(int totalGoals,
                                                           int assists,
                                                           float dribbles,
                                                           float interceptions,
                                                           float tackles,
                                                           float duels) {
        return new TeamPlayerStats(
                "TestTeam",
                totalGoals,
                "Nike",
                new TopScorersStats(List.of(), 0),
                new BestPassersStats(List.of(), assists),
                new BestDribblersStats(List.of(), dribbles),
                createGoalkeeperStats("75%"),
                new PlayersMostAppearancesStats(List.of(), 0),
                new PlayersMostDuelsWonStats(List.of(), duels),
                new PlayersMostInterceptionStats(List.of(), interceptions),
                new PlayersMostSuccessfulTacklesStats(List.of(), tackles)
        );
    }

    private TeamPlayerStats createTeamPlayerStatsWithGoalkeeper(String savePercentage) {
        return new TeamPlayerStats(
                "TestTeam",
                0,
                "Nike",
                new TopScorersStats(List.of(), 0),
                new BestPassersStats(List.of(), 0),
                new BestDribblersStats(List.of(), 0f),
                createGoalkeeperStats(savePercentage),
                new PlayersMostAppearancesStats(List.of(), 0),
                new PlayersMostDuelsWonStats(List.of(), 0f),
                new PlayersMostInterceptionStats(List.of(), 0f),
                new PlayersMostSuccessfulTacklesStats(List.of(), 0f)
        );
    }

    private GoalkeeperStats createGoalkeeperStats(String savePercentage) {
        return new GoalkeeperStats(
                "Test Keeper",
                "10",
                savePercentage,
                "5",
                "Test Club"
        );
    }

    private Player createPlayer(String name) {
        return new Player(
                name,
                "Jan 1, 1990",
                "FW",
                "Test Club",
                "Nike",
                10
        );
    }
}
