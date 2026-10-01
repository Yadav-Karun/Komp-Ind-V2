package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Data;

public class JsonDataReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Data> getJsonData(String filePath) throws Exception {

        String jsonData = Files.readString(Paths.get(filePath));

        return mapper.readValue(
            jsonData,
            new TypeReference<List<Data>>() {}
        );
    }
}