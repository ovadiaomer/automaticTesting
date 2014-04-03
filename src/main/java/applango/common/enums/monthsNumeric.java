package applango.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum monthsNumeric {

    M1("JANUARY"),
    M2("FEBRUARY"),
    M3("MARCH"),
    M4("APRIL"),
    M5("MAY"),
    M6("JUNE"),
    M7("JULY"),
    M8("AUGUST"),
    M9("SEPTEMBER"),
    M10("OCTOBER"),
    M11("NOVEMBER"),
    M12("DECEMBER");

    private final String value;

    private monthsNumeric(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}