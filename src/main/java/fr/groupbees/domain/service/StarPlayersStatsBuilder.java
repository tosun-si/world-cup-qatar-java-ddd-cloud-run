package fr.groupbees.domain.service;

import fr.groupbees.domain.model.StarPlayerDetail;
import fr.groupbees.domain.model.StarPlayersStats;
import fr.groupbees.domain.model.StarPlayersStats.StarPlayerCategory;
import fr.groupbees.domain.model.TeamPlayerStats.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarPlayersStatsBuilder {

    private final Map<String, List<StarPlayerCategory>> playerCategories;

    private StarPlayersStatsBuilder(final Map<String, List<StarPlayerCategory>> playerCategories) {
        this.playerCategories = playerCategories;
    }

    public static StarPlayersStatsBuilder newInstance() {
        return new StarPlayersStatsBuilder(new HashMap<>());
    }

    public StarPlayersStatsBuilder withCategory(StarPlayerCategory category, List<Player> players) {
        if (players == null) return this;

        for (Player player : players) {
            playerCategories.computeIfAbsent(player.playerName(), _ -> new ArrayList<>()).add(category);
        }

        return this;
    }

    public StarPlayersStats build() {
        List<StarPlayerDetail> starPlayers = playerCategories.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(entry -> new StarPlayerDetail(entry.getKey(), entry.getValue()))
                .toList();

        return new StarPlayersStats(starPlayers);
    }
}
