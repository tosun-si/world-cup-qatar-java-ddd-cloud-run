package fr.groupbees.infrastructure.alloydb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "team_player_stats_raw")
public class TeamStatsRawEntity {

    @Id
    @Column(name = "team_player_stats_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamEntity team;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fifa_ranking_id", referencedColumnName = "fifa_ranking_id")
    private FifaRankingEntity fifaRanking;

    @Column(name = "national_team_kit_sponsor")
    private String nationalTeamKitSponsor;

    private String position;

    @Column(name = "national_team_jersey_number")
    private Integer nationalTeamJerseyNumber;

    @Column(name = "player_dob")
    private String playerDob;

    private String club;

    @Column(name = "player_name")
    private String playerName;

    private String appearances;

    @Column(name = "goals_scored")
    private String goalsScored;

    @Column(name = "assists_provided")
    private String assistsProvided;

    @Column(name = "dribbles_per_ninety")
    private String dribblesPerNinety;

    @Column(name = "interceptions_per_ninety")
    private String interceptionsPerNinety;

    @Column(name = "tackles_per_ninety")
    private String tacklesPerNinety;

    @Column(name = "total_duels_won_per_ninety")
    private String totalDuelsWonPerNinety;

    @Column(name = "save_percentage")
    private String savePercentage;

    @Column(name = "clean_sheets")
    private String cleanSheets;

    @Column(name = "brand_sponsor_and_used")
    private String brandSponsorAndUsed;

    public TeamStatsRawEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public FifaRankingEntity getFifaRanking() {
        return fifaRanking;
    }

    public void setFifaRanking(FifaRankingEntity fifaRanking) {
        this.fifaRanking = fifaRanking;
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
