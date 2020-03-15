package by.epam.tr.dao.connectionpool;

public enum DBConnectionParameter {
    DRIVER("db.driver"), URL("db.url"), USER("db.user"), PASSWORD("db.password"), POOL_SIZE("db.pool_size");

    private String key;

    DBConnectionParameter(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
