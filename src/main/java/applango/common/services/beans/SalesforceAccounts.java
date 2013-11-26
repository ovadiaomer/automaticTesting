package applango.common.services.beans;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 26/11/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class SalesforceAccounts {
    String accountName;
    String accountId;

    public void SalesforceAccounts() {

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
