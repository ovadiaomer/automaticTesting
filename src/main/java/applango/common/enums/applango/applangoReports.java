package applango.common.enums.applango;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum applangoReports {



    TotalObjectActivityForCompany("Total Object Activity for Company"),

    GroupActivityObjects("Group Activity (Objects)"),
    ObjectActivityGroups("Object Activity (Groups)"),


    ;

    private final String value;

    private applangoReports(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}