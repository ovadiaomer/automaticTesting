package applango.common.enums.salesforce;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 01/12/13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceRanks {
    LOGIN(1),
    CREATE(3),
    UPDATE(2),
    DELETE(1),
    ACCOUNT(2),
    LEAD(2),
    OPPORTUNITY(3),
    CONTACT(1),
    CAMPAIGN(1),

    CREATE_IN_customerAppRankWeightSet(1),
    UPDATE_IN_customerAppRankWeightSet(1),
    DELETE_IN_customerAppRankWeightSet(1),
    ACCOUNT_IN_customerAppRankWeightSet(4),
    LEAD_IN_customerAppRankWeightSet(5),
    OPPORTUNITY_IN_customerAppRankWeightSet(7),
    CONTACT_IN_customerAppRankWeightSet(18),
    CAMPAIGN_IN_customerAppRankWeightSet(1)


    ;


    private final int value;

    private salesforceRanks(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
