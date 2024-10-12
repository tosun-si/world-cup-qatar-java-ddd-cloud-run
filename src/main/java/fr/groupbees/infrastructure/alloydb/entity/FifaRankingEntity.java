package fr.groupbees.infrastructure.alloydb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "team_player_stats_raw")
public class FifaRankingEntity {

    @Id
    @Column(name = "fifa_ranking_id")
    private Integer id;

    private Integer ranking;
    private Integer team_id;

    public FifaRankingEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }
}
