package fr.groupbees.infrastructure.alloydb;

import fr.groupbees.infrastructure.alloydb.entity.TeamStatsRawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayerStatsRawRepository extends JpaRepository<TeamStatsRawEntity, Integer> {

    @Query(name = "TeamStatsRawEntity.findTeamPlayersStatsRaw")
    List<TeamStatsRawEntity> findTeamWithPlayersById();
}
