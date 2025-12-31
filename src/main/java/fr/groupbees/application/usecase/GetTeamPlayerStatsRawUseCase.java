package fr.groupbees.application.usecase;

import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;

import java.util.List;

public class GetTeamPlayerStatsRawUseCase {

    private final TeamPlayerStatsRawRepository statsRawRepository;

    public GetTeamPlayerStatsRawUseCase(TeamPlayerStatsRawRepository statsRawRepository) {
        this.statsRawRepository = statsRawRepository;
    }

    public List<TeamPlayerStatsRaw> execute() {
        return statsRawRepository.find();
    }
}
