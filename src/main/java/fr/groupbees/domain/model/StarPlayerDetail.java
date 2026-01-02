package fr.groupbees.domain.model;

import fr.groupbees.domain.model.StarPlayersStats.StarPlayerCategory;

import java.util.List;

public record StarPlayerDetail(String playerName, List<StarPlayerCategory> categories) {

    public int categoryCount() {
        return categories.size();
    }
}
