package fr.groupbees.application;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.application.usecase.GetTeamPlayerStatsDomainUseCase;
import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static fr.groupbees.testutils.JsonTestDataLoader.loadTeamPlayerStatsRawInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTeamPlayerStatsDomainUseCaseTest {

    private static final String TEST_RESOURCES_PATH = "/usecases/team-player-stats/";
    private static final String FRANCE_COUNTRY = "France";
    private static final String ARGENTINA_COUNTRY = "Argentina";

    @Mock
    private TeamPlayerStatsRawRepository repository;

    private GetTeamPlayerStatsDomainUseCase useCase;

    @BeforeEach
    void setUp() {
        var service = new TeamPlayerStatsService();
        useCase = new GetTeamPlayerStatsDomainUseCase(repository, service);
    }

    @Test
    void givenRawStatsInRepository_whenExecute_thenCallsRepositoryAndReturnsTransformedStats() throws IOException {
        // Given.
        List<TeamPlayerStatsRaw> rawStats = loadTeamPlayerStatsRawInput(TEST_RESOURCES_PATH + "france_team_input.json");
        when(repository.find()).thenReturn(rawStats);

        // When.
        List<TeamPlayerStats> result = useCase.execute();

        // Then.
        verify(repository).find();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().teamName()).isEqualTo(FRANCE_COUNTRY);
        assertThat(result.getFirst().teamTotalGoals()).isEqualTo(6);
    }

    @Test
    void givenMultipleTeamsInRepository_whenExecute_thenReturnsAllTeamsTransformed() throws IOException {
        // Given.
        List<TeamPlayerStatsRaw> franceStats = loadTeamPlayerStatsRawInput(TEST_RESOURCES_PATH + "france_team_input.json");
        List<TeamPlayerStatsRaw> argentinaStats = loadTeamPlayerStatsRawInput(TEST_RESOURCES_PATH + "argentina_team_input.json");
        List<TeamPlayerStatsRaw> allStats = Stream.concat(franceStats.stream(), argentinaStats.stream()).toList();
        when(repository.find()).thenReturn(allStats);

        // When.
        List<TeamPlayerStats> result = useCase.execute();

        // Then.
        verify(repository).find();
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(TeamPlayerStats::teamName)
                .containsExactlyInAnyOrder(FRANCE_COUNTRY, ARGENTINA_COUNTRY);
    }

    @Test
    void givenEmptyRepository_whenExecute_thenReturnsEmptyList() {
        // Given.
        when(repository.find()).thenReturn(Collections.emptyList());

        // When.
        List<TeamPlayerStats> result = useCase.execute();

        // Then.
        verify(repository).find();
        assertThat(result).isEmpty();
    }
}
