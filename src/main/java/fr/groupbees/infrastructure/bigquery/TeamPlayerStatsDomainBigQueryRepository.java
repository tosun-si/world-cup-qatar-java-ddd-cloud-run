package fr.groupbees.infrastructure.bigquery;

import com.google.cloud.bigquery.*;
import fr.groupbees.domain.TeamPlayerStatsDomainObjects.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsDomainRepository;
import fr.groupbees.infrastructure.InfraDomainDatabaseConfig;
import fr.groupbees.infrastructure.JsonUtil;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class TeamPlayerStatsDomainBigQueryRepository implements TeamPlayerStatsDomainRepository {

    private final InfraDomainDatabaseConfig infraConfig;

    public TeamPlayerStatsDomainBigQueryRepository(InfraDomainDatabaseConfig infraConfig) {
        this.infraConfig = infraConfig;
    }

    @Override
    public List<TeamPlayerStats> saveStats(List<TeamPlayerStats> teamPlayersStats) {
        List<Map<String, Object>> rows = JsonUtil.convertListToMap(teamPlayersStats);

        var currentTimestamp = Instant.now().toString();

        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
        TableId tableId = TableId.of(infraConfig.dataset(), infraConfig.table());

        rows.forEach(r -> tableInsertRows(bigquery, tableId, currentTimestamp, r));

        return teamPlayersStats;
    }

    public void tableInsertRows(
            BigQuery bigquery,
            TableId tableId,
            String currentTimestamp,
            Map<String, Object> rowContent) {

        rowContent.put("ingestionDate", currentTimestamp);

        try {
            InsertAllResponse response = bigquery.insertAll(
                    InsertAllRequest.newBuilder(tableId)
                            .addRow(rowContent.get("teamName").toString(), rowContent)
                            .build()
            );

            if (response.hasErrors()) {
                for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                    System.out.println("Response error: \n" + entry.getValue());
                }
            }
            System.out.println("Rows successfully inserted into table");
        } catch (BigQueryException e) {
            System.out.println("Insert operation not performed \n" + e.toString());
        }
    }
}
