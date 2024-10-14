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
    national_team_kit_sponsor VARCHAR(100),
    position VARCHAR(5),
    national_team_jersey_number INT,
    player_dob VARCHAR(50),
    club VARCHAR(100),
    player_name VARCHAR(200),
    appearances VARCHAR(4),
    goals_scored VARCHAR(4),
    assists_provided VARCHAR(10),
    dribbles_per_ninety VARCHAR(10),
    interceptions_per_ninety VARCHAR(10),
    tackles_per_ninety VARCHAR(10),
    total_duels_won_per_ninety VARCHAR(10),
    save_percentage VARCHAR(10),
    clean_sheets VARCHAR(10),
    brand_sponsor_and_used VARCHAR(100),
    CONSTRAINT fk_team FOREIGN KEY(team_id) REFERENCES team(team_id),
    CONSTRAINT fk_fifa_ranking FOREIGN KEY(fifa_ranking_id) REFERENCES fifa_ranking(fifa_ranking_id)
);