package applango.common.services.beans;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 21/01/14
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class SalesforceOpportunities {
    String opportunityName;
    String opportunityId;
    String accountName;
    String closedDate;
    String stage;
    String probability;

    public SalesforceOpportunities(String opportunityName, String opportunityId, String accountName, String closedDate, String stage, String probability, String type) {
        this.opportunityName = opportunityName;
        this.opportunityId = opportunityId;
        this.accountName = accountName;
        this.closedDate = closedDate;
        this.stage = stage;
        this.probability = probability;
        this.type = type;
    }
    public SalesforceOpportunities() {
        this.opportunityName = null;
        this.opportunityId = null;
        this.accountName = "danielt";
        this.closedDate = "1/1/2019";
        this.stage = "Prospecting";
        this.probability = "0";
        this.type = "Product";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type = "Product";

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
