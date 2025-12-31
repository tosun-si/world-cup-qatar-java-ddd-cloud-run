package fr.groupbees.application.usecase;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.application.port.TeamPlayerStatsRepository;
import fr.groupbees.domain.model.TeamPlayerStats;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.domain.service.TeamPlayerStatsService;

import java.util.List;

public class SaveTeamPlayerStatsDomainUseCase {

    private final TeamPlayerStatsRawRepository statsRawRepository;
    private final TeamPlayerStatsRepository statsRepository;
    private final TeamPlayerStatsService statsService;

    public SaveTeamPlayerStatsDomainUseCase(TeamPlayerStatsRawRepository statsRawRepository,
                                            TeamPlayerStatsRepository statsRepository,
                                            TeamPlayerStatsService statsService) {
        this.statsRawRepository = statsRawRepository;
        this.statsRepository = statsRepository;
        this.statsService = statsService;
    }

    public List<TeamPlayerStats> execute() {
        final List<TeamPlayerStatsRaw> statsRaw = statsRawRepository.find();
        final List<TeamPlayerStats> statsDomain = statsService.toTeamPlayerStats(statsRaw);

        return statsRepository.save(statsDomain);
    }
}
