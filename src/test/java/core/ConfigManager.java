package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties =
            new Properties();

    static {

        try (InputStream inputStream =
                ConfigManager.class
                        .getClassLoader()
                        .getResourceAsStream(
                                "config/GlobalData.properties"
                        )) {

            if (inputStream == null) {

                throw new RuntimeException(
                        "GlobalData.properties not found"
                );
            }

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load GlobalData.properties",
                    e
            );
        }
    }

    public static String get(String key) {

        return properties.getProperty(key);
    }

    public static String get(
            String key,
            String defaultValue) {

        return properties.getProperty(
                key,
                defaultValue
        );
    }
}