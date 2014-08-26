package applango.common.enums.generic;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum months {

    JANUARY("DJ"),
    FEBRUARY("DF"),
    MARCH("DM"),
    APRIL("DA"),
    MAY("DAM"),
    JUNE("DAJ"),
    JULY("DAJJ"),
    AUGUST("DAA"),
    SEPTEMBER("S"),
    OCTOBER("O"),
    NOVEMBER("N"),
    DECEMBER("D");

    private final String value;

    private months(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}