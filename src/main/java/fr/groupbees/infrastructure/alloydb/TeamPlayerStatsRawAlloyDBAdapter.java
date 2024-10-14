package fr.groupbees.infrastructure.alloydb;

import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.TeamPlayerStatsRawDatabaseConnector;
import fr.groupbees.infrastructure.alloydb.entity.TeamStatsRawEntity;

import java.util.List;

public class TeamPlayerStatsRawAlloyDBAdapter implements TeamPlayerStatsRawDatabaseConnector {

    private final TeamPlayerStatsRawRepository statsRawRepository;

    public TeamPlayerStatsRawAlloyDBAdapter(TeamPlayerStatsRawRepository statsRawRepository) {
        this.statsRawRepository = statsRawRepository;
    }

    @Override
    public List<TeamPlayerStatsRaw> findTeamPlayersStatsRaw() {
        return statsRawRepository.findTeamWithPlayersById()
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
