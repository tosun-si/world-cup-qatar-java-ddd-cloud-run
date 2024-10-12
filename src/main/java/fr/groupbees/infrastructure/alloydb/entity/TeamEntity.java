package fr.groupbees.infrastructure.alloydb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "team")
public class TeamEntity {

    @Id
    @Column(name = "team_id")
    private Integer id;

    private Integer team_name;


    public TeamEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeam_name() {
        return team_name;
    }

    public void setTeam_name(Integer team_name) {
        this.team_name = team_name;
    }
}
