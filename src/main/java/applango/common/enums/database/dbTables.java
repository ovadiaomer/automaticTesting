package applango.common.enums.database;

public enum dbTables {

    applangoUserLoginHistory("applangoUserLoginHistory"),
    appInfo("appInfo"),
    applangoUser("applangoUser"),
    customer("customer"),
    customerAppRankWeightSet("customerAppRankWeightSet"),
    customerPreferencesSfManagedObjects("customerPreferencesSfManagedObjects"),
    emailToken("emailToken"),
    externalObjectActivity("externalObjectActivity"),
    excludedUser("excludedUser"),
    groupInfo("groupInfo"),
    loginInfo("loginInfo"),
    licenseApp("licenseApp"),
    rolledUpUserAppRankInfo("rolledUpUserAppRankInfo"),
    syncProcessProgress("syncProcessProgress"),
    user("user"),
    OAuth2Credentials("oAuth2Credentials");


    private final String value;

    private dbTables(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}