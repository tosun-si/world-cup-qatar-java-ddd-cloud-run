CREATE TABLE "public".team (
   team_id INT PRIMARY KEY,
   team_name VARCHAR(100)
);

CREATE TABLE "public".fifa_ranking (
    fifa_ranking_id INT PRIMARY KEY,
    team_id INT,
    ranking INT,
    CONSTRAINT fk_team FOREIGN KEY(team_id) REFERENCES team(team_id)
);

CREATE TABLE "public".team_player_stats_raw (
    team_player_stats_id SERIAL PRIMARY KEY,
    team_id INT,
    fifa_ranking_id INT,
    nationalTeamKitSponsor VARCHAR(100),
    position VARCHAR(5),
    nationalTeamJerseyNumber INT,
    playerDob VARCHAR(50),
    club VARCHAR(100),
    playerName VARCHAR(200),
    appearances VARCHAR(4),
    goalsScored VARCHAR(4),
    assistsProvided VARCHAR(10),
    dribblesPerNinety VARCHAR(10),
    interceptionsPerNinety VARCHAR(10),
    tacklesPerNinety VARCHAR(10),
    totalDuelsWonPerNinety VARCHAR(10),
    savePercentage VARCHAR(10),
    cleanSheets VARCHAR(10),
    brandSponsorAndUsed VARCHAR(100),
    CONSTRAINT fk_team FOREIGN KEY(team_id) REFERENCES team(team_id),
    CONSTRAINT fk_fifa_ranking FOREIGN KEY(fifa_ranking_id) REFERENCES fifa_ranking(fifa_ranking_id)
);