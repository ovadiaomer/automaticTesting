package applango.common.services.beans;

import applango.common.enums.salesforceSObjectMovement;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 26/11/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class SalesforceSobjects {
    String user;
    String sObjectName;

    public static SalesforceSobjects setSObjectValues(WebDriver driver, salesforceSObjectMovement salesforceSObjectMovement, SalesforceSobjects sfObject) {
        CharSequence sObjectName = "testsObject"+ Calendar.getInstance().getTimeInMillis();
        sfObject.setsObjectName(sObjectName.toString());
        sfObject.setUser(genericSalesforceWebsiteActions.getUserLabel((FirefoxDriver) driver));
        sfObject.setSalesforceSObjectMovement(salesforceSObjectMovement);
        return sfObject;
    }

    public String getsObjectId() {
        return sObjectId;
    }

    public void setsObjectId(String sObjectId) {
        this.sObjectId = sObjectId;
    }

    public String getsObjectDataCode() {
        return sObjectDataCode;
    }

    String sObjectId;
    salesforceSObjectMovement salesforceSObjectMovement;
    String sObjectDataCode;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getsObjectName() {
        return sObjectName;
    }

    public void setsObjectName(String sObjectName) {
        this.sObjectName = sObjectName;
    }

    public salesforceSObjectMovement getSalesforceSObjectMovement() {
        return salesforceSObjectMovement;
    }

    public void setSalesforceSObjectMovement(salesforceSObjectMovement salesforceSObjectMovement) {
        this.salesforceSObjectMovement = salesforceSObjectMovement;
    }

    public void SalesforceAccounts() {

    }

    public void setSobjectDataCode(String sObjectDataCode) {
        this.sObjectDataCode = sObjectDataCode;
    }
}
