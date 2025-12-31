package fr.groupbees.infrastructure.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;
import fr.groupbees.application.port.TeamPlayerStatsRawRepository;
import fr.groupbees.infrastructure.config.InfraRawDatabaseConfig;
import io.vavr.control.Try;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class TeamPlayerStatsRawFirestoreAdapter implements TeamPlayerStatsRawRepository {

    private static final String ERROR_MESSAGE_GET_STATS_FIRESTORE = "Error when trying to retrieve the collection of team player stats raw from Firestore";

    private final InfraRawDatabaseConfig infraRawDatabaseConfig;

    public TeamPlayerStatsRawFirestoreAdapter(InfraRawDatabaseConfig infraRawDatabaseConfig) {
        this.infraRawDatabaseConfig = infraRawDatabaseConfig;
    }

    @Override
    public List<TeamPlayerStatsRaw> find() {
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        ApiFuture<QuerySnapshot> query = db.collection(infraRawDatabaseConfig.firestoreCollectionName()).get();

        QuerySnapshot querySnapshot = Try.of(query::get)
                .getOrElseThrow(() -> new IllegalStateException(ERROR_MESSAGE_GET_STATS_FIRESTORE));

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        return documents
                .stream()
                .map(this::toTeamStatsRaw)
                .toList();
    }

    private TeamPlayerStatsRaw toTeamStatsRaw(QueryDocumentSnapshot teamStatsRawDocument) {
        var nationalTeamJerseyNumber = Optional.ofNullable(teamStatsRawDocument.getLong("nationalTeamJerseyNumber"))
                .map(Long::intValue)
                .orElse(0);

        return new TeamPlayerStatsRaw(
                teamStatsRawDocument.getString("nationality"),
                requireNonNull(teamStatsRawDocument.getLong("fifaRanking")).intValue(),
                teamStatsRawDocument.getString("nationalTeamKitSponsor"),
                teamStatsRawDocument.getString("position"),
                nationalTeamJerseyNumber,
                teamStatsRawDocument.getString("playerDob"),
                teamStatsRawDocument.getString("club"),
                teamStatsRawDocument.getString("playerName"),
                teamStatsRawDocument.getString("appearances"),
                teamStatsRawDocument.getString("goalsScored"),
                teamStatsRawDocument.getString("assistsProvided"),
                teamStatsRawDocument.getString("dribblesPerNinety"),
                teamStatsRawDocument.getString("interceptionsPerNinety"),
                teamStatsRawDocument.getString("tacklesPerNinety"),
                teamStatsRawDocument.getString("totalDuelsWonPerNinety"),
                teamStatsRawDocument.getString("savePercentage"),
                teamStatsRawDocument.getString("cleanSheets"),
                teamStatsRawDocument.getString("brandSponsorAndUsed")
        );
    }
}
