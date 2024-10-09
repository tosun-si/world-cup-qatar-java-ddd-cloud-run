package fr.groupbees.application;

import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsRaw;
import fr.groupbees.domain.service.TeamPlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamPlayerStatsController {

    private final TeamPlayerStatsService teamPlayerStatsService;

    @Autowired
    public TeamPlayerStatsController(final TeamPlayerStatsService teamPlayerStatsService) {
        this.teamPlayerStatsService = teamPlayerStatsService;
    }

    @GetMapping("/teams/players/stats/raw")
    public List<TeamPlayerStatsRaw> getTeamPlayerStatsRaw() {
        return teamPlayerStatsService.findTeamPlayersStatsRaw();
    }

    @GetMapping("/teams/players/stats")
    public List<TeamPlayerStats> getTeamPlayerStats() {
        return teamPlayerStatsService.findTeamPlayersStats();
    }
}
