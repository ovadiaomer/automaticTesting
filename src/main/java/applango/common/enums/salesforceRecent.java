package applango.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceRecent {

    ACCOUNTS("Recent Accounts"),
    CONTACTS("Recent Contacts");

    private final String value;

    private salesforceRecent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}