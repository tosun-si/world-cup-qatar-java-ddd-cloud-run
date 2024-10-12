package fr.groupbees.infrastructure.alloydb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "team_player_stats_raw")
public class TeamStatsRawEntity {

    @Id
    @Column(name = "team_player_stat_id")
    private Integer id;

    private Integer team_id;

    private Integer fifa_ranking_id;

    private String nationalTeamKitSponsor;

    private String position;

    private Integer nationalTeamJerseyNumber;
    private String playerDob;
    private String club;
    private String playerName;
    private String appearances;
    private String goalsScored;
    private String assistsProvided;
    private String dribblesPerNinety;
    private String interceptionsPerNinety;
    private String tacklesPerNinety;
    private String totalDuelsWonPerNinety;
    private String savePercentage;
    private String cleanSheets;
    private String brandSponsorAndUsed;

    public TeamStatsRawEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public Integer getFifa_ranking_id() {
        return fifa_ranking_id;
    }

    public void setFifa_ranking_id(Integer fifa_ranking_id) {
        this.fifa_ranking_id = fifa_ranking_id;
    }

    public String getNationalTeamKitSponsor() {
        return nationalTeamKitSponsor;
    }

    public void setNationalTeamKitSponsor(String nationalTeamKitSponsor) {
        this.nationalTeamKitSponsor = nationalTeamKitSponsor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNationalTeamJerseyNumber() {
        return nationalTeamJerseyNumber;
    }

    public void setNationalTeamJerseyNumber(Integer nationalTeamJerseyNumber) {
        this.nationalTeamJerseyNumber = nationalTeamJerseyNumber;
    }

    public String getPlayerDob() {
        return playerDob;
    }

    public void setPlayerDob(String playerDob) {
        this.playerDob = playerDob;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAppearances() {
        return appearances;
    }

    public void setAppearances(String appearances) {
        this.appearances = appearances;
    }

    public String getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(String goalsScored) {
        this.goalsScored = goalsScored;
    }

    public String getAssistsProvided() {
        return assistsProvided;
    }

    public void setAssistsProvided(String assistsProvided) {
        this.assistsProvided = assistsProvided;
    }

    public String getDribblesPerNinety() {
        return dribblesPerNinety;
    }

    public void setDribblesPerNinety(String dribblesPerNinety) {
        this.dribblesPerNinety = dribblesPerNinety;
    }

    public String getInterceptionsPerNinety() {
        return interceptionsPerNinety;
    }

    public void setInterceptionsPerNinety(String interceptionsPerNinety) {
        this.interceptionsPerNinety = interceptionsPerNinety;
    }

    public String getTacklesPerNinety() {
        return tacklesPerNinety;
    }

    public void setTacklesPerNinety(String tacklesPerNinety) {
        this.tacklesPerNinety = tacklesPerNinety;
    }

    public String getTotalDuelsWonPerNinety() {
        return totalDuelsWonPerNinety;
    }

    public void setTotalDuelsWonPerNinety(String totalDuelsWonPerNinety) {
        this.totalDuelsWonPerNinety = totalDuelsWonPerNinety;
    }

    public String getSavePercentage() {
        return savePercentage;
    }

    public void setSavePercentage(String savePercentage) {
        this.savePercentage = savePercentage;
    }

    public String getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(String cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public String getBrandSponsorAndUsed() {
        return brandSponsorAndUsed;
    }

    public void setBrandSponsorAndUsed(String brandSponsorAndUsed) {
        this.brandSponsorAndUsed = brandSponsorAndUsed;
    }
}
