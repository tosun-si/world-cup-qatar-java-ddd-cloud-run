package fr.groupbees.infrastructure.web;

import fr.groupbees.application.usecase.GetTeamPlayerStatsDomainUseCase;
import fr.groupbees.application.usecase.GetTeamPlayerStatsRawUseCase;
import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamPlayerStatsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamPlayerStatsController.class);

    private final GetTeamPlayerStatsRawUseCase getTeamPlayerStatsRawUseCase;
    private final GetTeamPlayerStatsDomainUseCase getTeamPlayerStatsDomainUseCase;

    @Autowired
    public TeamPlayerStatsController(final GetTeamPlayerStatsRawUseCase getTeamPlayerStatsRawUseCase,
                                     final GetTeamPlayerStatsDomainUseCase getTeamPlayerStatsDomainUseCase) {
        this.getTeamPlayerStatsRawUseCase = getTeamPlayerStatsRawUseCase;
        this.getTeamPlayerStatsDomainUseCase = getTeamPlayerStatsDomainUseCase;
    }

    @GetMapping("/teams/players/stats/raw")
    public List<TeamPlayerStatsRaw> getTeamPlayerStatsRaw() {
        LOGGER.info("Received request for raw team player stats");
        List<TeamPlayerStatsRaw> result = getTeamPlayerStatsRawUseCase.execute();
        LOGGER.info("Returning {} raw team player stats", result.size());
        return result;
    }

    @GetMapping("/teams/players/stats")
    public List<TeamPlayerStats> getTeamPlayerStats() {
        LOGGER.info("Received request for domain team player stats");
        List<TeamPlayerStats> result = getTeamPlayerStatsDomainUseCase.execute();
        LOGGER.info("Returning {} domain team player stats", result.size());
        return result;
    }
}
