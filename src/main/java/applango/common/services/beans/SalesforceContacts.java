package applango.common.services.beans;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 26/11/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class SalesforceContacts {
    String contactName;
    String contactId;
    String accountName;
    String country;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void SalesforceContacts() {

    }

}
