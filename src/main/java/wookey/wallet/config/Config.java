package wookey.wallet.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * node config
 */
public class Config {
    private Properties properties;

    private static Config instance;

    private Config() {
        loadProperties();
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (final InputStream stream =
                     this.getClass()
                             .getClassLoader()
                             .getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Integer getIntProperty(String key) {
        return Integer.valueOf(properties.getProperty(key));
    }

}
