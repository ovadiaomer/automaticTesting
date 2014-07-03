package applango.common.enums.salesforce;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 01/12/13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceLicenses {
    SALESFORCE("SALESFORCE"),
    SYSTEM_ADMINISTRATOR("System administrator"),
    SALESFORCE_PLATFORM("SALESFORCE PLATFORM"),
    FORCE("Force.com - Free User"),
    CHATTER_FREE("CHATTER FREE USER"),
    CHATTER_ONLY("CHATTER_ONLY"),
    CHATTER_EXTERNAL("CHATTER_EXTERNAL"),
    SUPPORT("SUPPORT"),
    MARKETING("MARKETING");


    private final String value;

    private salesforceLicenses(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
