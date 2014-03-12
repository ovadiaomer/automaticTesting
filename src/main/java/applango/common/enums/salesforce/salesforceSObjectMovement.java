package applango.common.enums.salesforce;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 01/12/13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceSObjectMovement {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    UNDELETE("UNDELETE"),
    VIEW("VIEW"),
    LINK("LINK"),
    UNLINK("UNLINK");


    private final String value;

    private salesforceSObjectMovement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
