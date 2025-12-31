package fr.groupbees.application.usecase;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.domain.service.TeamPlayerStatsService;

import java.util.List;

public class GetTeamPlayerStatsDomainUseCase {

    private final TeamPlayerStatsRawRepository statsRawRepository;
    private final TeamPlayerStatsService statsService;

    public GetTeamPlayerStatsDomainUseCase(TeamPlayerStatsRawRepository statsRawRepository,
                                           TeamPlayerStatsService statsService) {
        this.statsRawRepository = statsRawRepository;
        this.statsService = statsService;
    }

    public List<TeamPlayerStats> execute() {
        final List<TeamPlayerStatsRaw> statsRaws = statsRawRepository.find();

        return statsService.toTeamPlayerStats(statsRaws);
    }
}
