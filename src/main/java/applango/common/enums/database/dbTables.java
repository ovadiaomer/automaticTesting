package applango.common.enums.database;

public enum dbTables {

    appInfo("appInfo"),
    syncProcessProgress("syncProcessProgress"),
    externalObjectActivity("externalObjectActivity"),
    applangoUser("applangoUser"),
    OAuth2Credentials("oAuth2Credentials"),
    CUSTOMER("customer");

    private final String value;

    private dbTables(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}