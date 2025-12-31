package fr.groupbees.infrastructure.alloydb;

import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.infrastructure.alloydb.entity.TeamStatsRawEntity;

import java.util.List;

public class TeamPlayerStatsRawAlloyDBAdapter implements TeamPlayerStatsRawRepository {

    private final TeamPlayerStatsRawJpaRepository statsRawJpaRepository;

    public TeamPlayerStatsRawAlloyDBAdapter(TeamPlayerStatsRawJpaRepository statsRawJpaRepository) {
        this.statsRawJpaRepository = statsRawJpaRepository;
    }

    @Override
    public List<TeamPlayerStatsRaw> find() {
        return statsRawJpaRepository.findTeamWithPlayersById()
                .stream()
                .map(this::toTeamStatsRaw)
                .toList();
    }

    private TeamPlayerStatsRaw toTeamStatsRaw(TeamStatsRawEntity teamStatsRawEntity) {
        return new TeamPlayerStatsRaw(
                teamStatsRawEntity.getTeam().getTeam_name(),
                teamStatsRawEntity.getFifaRanking().getRanking(),
                teamStatsRawEntity.getNationalTeamKitSponsor(),
                teamStatsRawEntity.getPosition(),
                teamStatsRawEntity.getNationalTeamJerseyNumber(),
                teamStatsRawEntity.getPlayerDob(),
                teamStatsRawEntity.getClub(),
                teamStatsRawEntity.getPlayerName(),
                teamStatsRawEntity.getAppearances(),
                teamStatsRawEntity.getGoalsScored(),
                teamStatsRawEntity.getAssistsProvided(),
                teamStatsRawEntity.getDribblesPerNinety(),
                teamStatsRawEntity.getInterceptionsPerNinety(),
                teamStatsRawEntity.getTacklesPerNinety(),
                teamStatsRawEntity.getTotalDuelsWonPerNinety(),
                teamStatsRawEntity.getSavePercentage(),
                teamStatsRawEntity.getCleanSheets(),
                teamStatsRawEntity.getBrandSponsorAndUsed()
        );
    }
}
