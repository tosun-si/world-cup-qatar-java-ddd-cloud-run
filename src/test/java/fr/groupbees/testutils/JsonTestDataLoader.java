package fr.groupbees.testutils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.groupbees.domain.model.TeamPlayerStatsRaw;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class JsonTestDataLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonTestDataLoader() {
    }

    public static List<TeamPlayerStatsRaw> loadTeamPlayerStatsRawInput(String filePath) throws IOException {
        return loadJson(filePath, new TypeReference<>() {});
    }

    public static String loadExpectedJson(String filePath) throws IOException {
        try (InputStream is = JsonTestDataLoader.class.getResourceAsStream(filePath)) {
            assertThat(is).withFailMessage("Expected file not found: " + filePath).isNotNull();
            return new String(is.readAllBytes());
        }
    }

    public static <T> T loadJson(String filePath, TypeReference<T> typeReference) throws IOException {
        try (InputStream is = JsonTestDataLoader.class.getResourceAsStream(filePath)) {
            assertThat(is).withFailMessage("File not found: " + filePath).isNotNull();
            return OBJECT_MAPPER.readValue(is, typeReference);
        }
    }

    public static String toJson(Object object) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}
