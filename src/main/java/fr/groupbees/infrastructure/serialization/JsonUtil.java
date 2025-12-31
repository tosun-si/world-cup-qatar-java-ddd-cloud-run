package fr.groupbees.infrastructure.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

        OBJECT_MAPPER = mapper;
    }

    public static <T> List<Map<String, Object>> convertListToMap(List<T> objects) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Object object : objects) {
            Map<String, Object> objectMap = OBJECT_MAPPER.convertValue(object, Map.class);
            resultList.add(objectMap);
        }

        return resultList;
    }

    public static <T> List<T> deserializeFromResourcePath(final String resourcePath, final TypeReference<List<T>> reference) {
        final InputStream stream = JsonUtil.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        return deserializeToList(stream, reference);
    }

    public static <T> List<T> deserializeToList(final InputStream stream, final TypeReference<List<T>> reference) {
        try {
            return OBJECT_MAPPER.readValue(stream, reference);
        } catch (Exception e) {
            throw new IllegalStateException("The deserialization of inputStream for the following reference type " + reference);
        }
    }
}
