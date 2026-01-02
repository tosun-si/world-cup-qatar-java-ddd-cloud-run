package fr.groupbees.domain.model;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record StarPlayersStats(List<StarPlayerDetail> starPlayers) {

    public StarPlayersStats {
        requireNonNull(starPlayers, "starPlayers should not be null");
    }

    public enum StarPlayerCategory {
        TOP_SCORERS,
        BEST_PASSERS,
        BEST_DRIBBLERS,
        MOST_APPEARANCES,
        MOST_DUELS_WON,
        MOST_INTERCEPTIONS,
        MOST_SUCCESSFUL_TACKLES
    }

    public int totalCount() {
        return starPlayers.size();
    }
}
