package by.epam.airport_system.dao.connectionpool;

import java.util.ResourceBundle;

public class ResourceManager {
    private static ResourceManager instance;
    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
