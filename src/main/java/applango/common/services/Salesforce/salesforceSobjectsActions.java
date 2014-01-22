package applango.common.services.Salesforce;

import applango.common.enums.*;
import applango.common.services.beans.SalesforceSobjects;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 02/12/13
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class    salesforceSobjectsActions  extends genericSalesforceWebsiteActions {
    public static SalesforceSobjects[] createNewSobject(FirefoxDriver driver, WebDriverWait wait, int numberOfAccounts) throws IOException {
        SalesforceSobjects[] newSobjects = new SalesforceSobjects[numberOfAccounts];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfAccounts; i++) {
                logger.info("Create new sObjects " + i + " out of " + numberOfAccounts);
                openTab(driver, salesforceTabs.SOBJECTS_DATA, wait);
                verifyRecentElementExist(wait, salesforceObjectMap, salesforceRecent.SOBJECTS);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                newSobjects[i-1] = fillNewSobjectDetailsAndSave(driver, wait);
            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("sObjects created successfully");
        return newSobjects;
    }

    public static void updateSObject(FirefoxDriver driver,  WebDriverWait wait, SalesforceSobjects sObject, SalesforceSobjects newSObject) throws IOException {
        logger.info("Updating sObject "+ sObject.getsObjectName() + " to "+ newSObject.getsObjectName());
        openPageWithIdInUrl(driver, wait, sObject.getsObjectId());

        clickOnEdit(driver, wait);
        fillSObjectDetails(driver, newSObject);
        clickOnSave(driver);
        waitForPageToLoad(wait);
        newSObject.setsObjectId(sObject.getsObjectId());
        newSObject.setSobjectDataCode(sObject.getsObjectDataCode());
    }

    public static boolean verifyObjectSaved(FirefoxDriver driver, String sObjectDataCode) {
        logger.info("Verify "+ sObjectDataCode + " object is saved ");
        try {
            Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.SOBJECT_SObjectNameInTitle.getValue())).getText().equals(sObjectDataCode));
            return true;
        }
        catch (Exception ex) {
            logger.error("Object not saved\n" + ex.getMessage());
            return false;
        }
    }

    private static String getSObjectDataCode(FirefoxDriver driver) {
        logger.info("Verify sObject is saved ");
        try {
            return driver.findElement(By.xpath(salesforceTextfields.SOBJECT_SObjectNameInTitle.getValue())).getText();
        }
        catch (Exception ex) {
            logger.error("Account not saved\n" + ex.getMessage());
            return null;
        }

    }

    private static SalesforceSobjects fillNewSobjectDetailsAndSave(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new sObject details and return object SalesforceAccounts with User, Movement and Sobject name");
        SalesforceSobjects newSobject;
        newSobject = fillNewSObjectDetails(driver);
        clickOnSave(driver);
        waitForPageToLoad(wait);
        if (getSObjectDataCode(driver) != null ) {
            newSobject.setSobjectDataCode(getSObjectDataCode(driver));
            newSobject.setsObjectId(getIdFromUrl(driver));
            return newSobject;

        }
        else {
            logger.error("Account not saved");
            return null;
        }

    }

    private static String getsObjectDataCodeFromPageTitle(WebDriver driver) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        return domain.getPath().substring(1);
    }

    private static void fillSObjectDetails(WebDriver driver, SalesforceSobjects salesforceSobjects) throws IOException {
        driver.findElement(By.id(salesforceTextfields.SOBJECT_USER.getValue())).sendKeys(salesforceSobjects.getUser());
        driver.findElement(By.id(salesforceTextfields.SOBJECT_MOVEMENT.getValue())).sendKeys(salesforceSobjects.getSalesforceSObjectMovement().getValue());
        driver.findElement(By.id(salesforceTextfields.SOBJECT_NAME.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.SOBJECT_NAME.getValue())).sendKeys(salesforceSobjects.getsObjectName());
        logger.info("New sObject name is " + salesforceSobjects.getsObjectName());
    }

    private static SalesforceSobjects fillNewSObjectDetails(WebDriver driver) throws IOException {
        return  fillNewSObjectDetails(driver, salesforceSObjectMovement.CREATE);
    }

    private static SalesforceSobjects fillNewSObjectDetails(WebDriver driver, salesforceSObjectMovement salesforceSObjectMovement) throws IOException {
        SalesforceSobjects sfObject = new SalesforceSobjects();
        sfObject = SalesforceSobjects.setSObjectValues(driver, salesforceSObjectMovement, sfObject);
        try {

            driver.findElement(By.id(salesforceTextfields.SOBJECT_NAME.getValue())).sendKeys(sfObject.getsObjectName());
            driver.findElement(By.id(salesforceTextfields.SOBJECT_USER.getValue())).sendKeys(sfObject.getUser());
            driver.findElement(By.id(salesforceTextfields.SOBJECT_MOVEMENT.getValue())).sendKeys(sfObject.getSalesforceSObjectMovement().getValue());
            logger.info("New sObject name is " + sfObject.getsObjectName());
            return sfObject;
        } catch (Exception ex) {
            logger.error("Field not exist \n" + ex);
        }
        return null;
    }

    public static SalesforceSobjects[] createNewSobject(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        return createNewSobject(driver, wait, 1);
    }
}
