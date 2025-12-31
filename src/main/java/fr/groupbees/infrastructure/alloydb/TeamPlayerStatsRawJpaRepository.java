package fr.groupbees.infrastructure.alloydb;

import fr.groupbees.infrastructure.alloydb.entity.TeamStatsRawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayerStatsRawJpaRepository extends JpaRepository<TeamStatsRawEntity, Integer> {

    // @Query(name = "TeamStatsRawEntity.findTeamPlayersStatsRaw")

    @Query("SELECT t FROM TeamStatsRawEntity t JOIN FETCH t.team JOIN FETCH t.fifaRanking")
    List<TeamStatsRawEntity> findTeamWithPlayersById();
}
