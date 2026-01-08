package fr.groupbees.domain.service;

import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static fr.groupbees.testutils.JsonTestDataLoader.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

class TeamPlayerStatsServiceTest {

    private static final String TEST_RESOURCES_PATH = "/usecases/team-player-stats/";

    private TeamPlayerStatsService service;

    @BeforeEach
    void setUp() {
        service = new TeamPlayerStatsService();
    }

    @ParameterizedTest(name = "Given {0} raw stats, when transforming to domain, then returns expected stats")
    @MethodSource("teamTestCases")
    void givenRawStats_whenToTeamPlayerStats_thenMatchesExpectedJson(
            String teamName,
            String inputFile,
            String expectedFile) throws IOException {

        // Given.
        List<TeamPlayerStatsRaw> inputRawStats = loadTeamPlayerStatsRawInput(TEST_RESOURCES_PATH + inputFile);

        // When.
        List<TeamPlayerStats> actualStats = service.toTeamPlayerStats(inputRawStats);

        // Then.
        assertThat(actualStats).hasSize(1);

        String expectedJson = loadExpectedJson(TEST_RESOURCES_PATH + expectedFile);
        String actualJson = toJson(actualStats.getFirst());

        assertThatJson(actualJson)
                .when(IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedJson);
    }

    private static Stream<Arguments> teamTestCases() {
        return Stream.of(
                // Standard cases.
                Arguments.of("France", "france_team_input.json", "france_team_expected.json"),
                Arguments.of("Argentina", "argentina_team_input.json", "argentina_team_expected.json"),
                // Edge cases.
                Arguments.of("Portugal (single player)", "edge_single_player_input.json", "edge_single_player_expected.json"),
                Arguments.of("Brazil (tied scorers)", "edge_tied_scorers_input.json", "edge_tied_scorers_expected.json"),
                Arguments.of("Morocco (no goals)", "edge_no_goals_input.json", "edge_no_goals_expected.json")
        );
    }
}
