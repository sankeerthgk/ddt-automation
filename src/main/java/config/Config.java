package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public Properties loadConfig() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties");

        properties.load(fileInputStream);
        return properties;
    }
}
