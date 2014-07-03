package applango.common.enums.generic;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum applications {

    BOX("boxmenuitem"),
    SALESFORCE("salesforcemenuitem")
    ;

    private final String value;

    private applications(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}