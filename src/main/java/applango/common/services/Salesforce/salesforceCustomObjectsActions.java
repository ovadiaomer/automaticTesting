package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import applango.common.enums.salesforceButtons;
import applango.common.enums.salesforceTextfields;
import applango.common.enums.salesforceUrls;
import applango.common.services.beans.SalesforceCustomObject;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 11/12/13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class salesforceCustomObjectsActions {
    public static void deleteCustomObject(WebDriver driver,  WebDriverWait wait, SalesforceCustomObject customObject, salesforceButtons deleteButtonId) throws Exception {

        SeleniumTestBase.logger.info("Deleting record " + customObject.getObjectName());

        genericSalesforceWebsiteActions.openPageWithIdInUrl(driver, wait, customObject.getObjectId());
        driver.findElement(By.xpath(salesforceButtons.CUSTOM_OBJECT_DELETE.getValue())).click();
//        Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
//        Iterator<String> itererator = windowId.iterator();
//
//        String mainWinID = itererator.next();
//        String  newAdwinID = itererator.next();
//
//        driver.switchTo().window(newAdwinID);
//        System.out.println(driver.getTitle());

        confirmCustomObjectDeleteInPopup(driver);
        checkCustomObjectDeleted(driver, wait, customObject.getObjectId());

    }

    private static void confirmCustomObjectDeleteInPopup(WebDriver driver) throws IOException {

        String parent = driver.getWindowHandle();
        Set<String> pops = driver.getWindowHandles();
        {
            Iterator<String> it = pops.iterator();
            while (it.hasNext()) {

                String popupHandle=it.next().toString();
                if(!popupHandle.contains(parent))
                {
                    driver = driver.switchTo().window(popupHandle);
                    if (driver.getTitle().equals("Confirm Custom Object Delete")) {
                        //Check checkbox - Yes, I want to delete the custom object
                        genericSalesforceWebsiteActions.markCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_POPUP_DELETE_CHECKBOX);
                        //Click on delete
                        genericSalesforceWebsiteActions.clickOnButton(driver, salesforceButtons.CUSTOM_OBJECT_POPUP_DELETE_BUTTON);
                    }
                }
            }
        }
    }

    protected static void checkCustomObjectDeleted(WebDriver driver, WebDriverWait wait, String recordId) throws IOException {
        SeleniumTestBase.logger.info("Verify custom object deleted");
        genericSalesforceWebsiteActions.openPageWithIdInUrl(driver, wait, recordId);
        Assert.assertTrue(driver.findElement(By.xpath(salesforceButtons.CUSTOM_OBJECT_UNDELETE.getValue())).isDisplayed());
    }

    public static SalesforceCustomObject[] createNewCustomObject(WebDriver driver, WebDriverWait wait) throws IOException {
        return createNewCustomObject(driver, wait, 1);
    }

    public static SalesforceCustomObject[] createNewCustomObject(WebDriver driver, WebDriverWait wait, int numOfCustomObjects) throws IOException {
        SalesforceCustomObject[] newCustomObject = new SalesforceCustomObject[numOfCustomObjects];

        Map salesforceObjectMap = genericSalesforceWebsiteActions.getMap();
        try {
            for (int i=1; i<=numOfCustomObjects; i++) {
                SeleniumTestBase.logger.info("Create new custom Objects " + i + " out of " + numOfCustomObjects);
                genericSalesforceWebsiteActions.openUrl(driver, wait, salesforceUrls.CUSTOM_OBJECTS);
                clickOnNewCustomObject(driver, wait);
                newCustomObject[i-1] = fillCustomObjectDetailsAnsSave(driver, wait);
            }

        }
        catch (Exception ex) {
            SeleniumTestBase.logger.error("Error- " + ex.getCause());
        }
        SeleniumTestBase.logger.info("custom Object created successfully");
        genericSalesforceWebsiteActions.waitForPageToLoad(wait);
        return newCustomObject;

    }

    private static SalesforceCustomObject fillCustomObjectDetailsAnsSave(WebDriver driver, WebDriverWait wait) throws IOException {
        SeleniumTestBase.logger.info("Create customObject object");
        SalesforceCustomObject sfCustomObject = new SalesforceCustomObject();
        SalesforceCustomObject.setCustomObjectValues(driver, sfCustomObject);
        fillCustomObjectDetails(driver, sfCustomObject);
        genericSalesforceWebsiteActions.clickOnSave(driver);
        genericSalesforceWebsiteActions.waitForPageToLoad(wait);
        salesforceSobjectsActions.verifyObjectSaved(driver, sfCustomObject.getCustomObjectLabel());
        sfCustomObject.setObjectId(genericSalesforceWebsiteActions.getIdFromUrl(driver));
        return sfCustomObject;

    }

    public static void updateCustomObject(WebDriver driver,  WebDriverWait wait, SalesforceCustomObject customObject, SalesforceCustomObject newCustomObject) throws IOException {
        SeleniumTestBase.logger.info("Updating customObject " + customObject.getObjectName() + " to " + newCustomObject.getObjectName());
        genericSalesforceWebsiteActions.openPageWithIdInUrl(driver, wait, customObject.getObjectId());

        genericSalesforceWebsiteActions.clickOnButton(driver, salesforceButtons.CUSTOM_OBJECT_EDIT);
        fillCustomObjectDetails(driver, newCustomObject);
        genericSalesforceWebsiteActions.clickOnButton(driver, salesforceButtons.CUSTOM_OBJECT_SAVE);
        genericSalesforceWebsiteActions.waitForPageToLoad(wait);
        newCustomObject.setObjectId(customObject.getObjectId());
    }

    private static void fillCustomObjectDetails(WebDriver driver, SalesforceCustomObject sfCustomObject) throws IOException {
        enterCustomObjectLabel(driver, sfCustomObject);
        markOptionalFeatures(driver, sfCustomObject);
        setDepolymentStatusDeployed(driver, sfCustomObject);
    }

    protected static void setDepolymentStatusDeployed(WebDriver driver, SalesforceCustomObject sfCustomObject) throws IOException {
        SeleniumTestBase.logger.info("Mark Custom Object Deploy");
        if (sfCustomObject.isCustomObjectDeployed()) {
            genericSalesforceWebsiteActions.markCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_DEPLOY);
        }
    }

    protected static void markOptionalFeatures(WebDriver driver, SalesforceCustomObject sfCustomObject) throws IOException {
        SeleniumTestBase.logger.info("Check,  un-check Allow Reports, Allow Activities, Track Field History");
        if (sfCustomObject.isAllowReport()) {
            genericSalesforceWebsiteActions.markCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_ALLOW_REPORTS);
        }
        else {
            genericSalesforceWebsiteActions.unmarkCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_ALLOW_REPORTS);
        }
        if (sfCustomObject.isAllowActivities()) {
            genericSalesforceWebsiteActions.markCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_ALLOW_ACTIVITIES);
        }
        else {
            genericSalesforceWebsiteActions.unmarkCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_ALLOW_ACTIVITIES);
        }
        if (sfCustomObject.isCustomObjectDeployed()) {
            genericSalesforceWebsiteActions.markCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_TRACK_FIELD_HISTORY);
        }
        else {
            genericSalesforceWebsiteActions.unmarkCheckBox(driver, salesforceButtons.CUSTOM_OBJECT_TRACK_FIELD_HISTORY);
        }
    }

    protected static void enterCustomObjectLabel(WebDriver driver, SalesforceCustomObject customObjectName) throws IOException {
        SeleniumTestBase.logger.info("Setting customObject Label " + customObjectName.getCustomObjectLabel() + " and plural label " + customObjectName.getCustomObjectPluralLabel());
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_Label.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_PluralLabel.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_ObjectName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_RecordName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_Label.getValue())).sendKeys(customObjectName.getCustomObjectLabel());
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_PluralLabel.getValue())).sendKeys(customObjectName.getCustomObjectPluralLabel());
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_ObjectName.getValue())).sendKeys(customObjectName.getObjectName());
        driver.findElement(By.id(salesforceTextfields.CustomOBJECT_RecordName.getValue())).sendKeys(customObjectName.getRecordName());
    }

    protected static void clickOnNewCustomObject(WebDriver driver, WebDriverWait wait) throws IOException {
        genericSalesforceWebsiteActions.clickOnButton(driver, salesforceButtons.CUSTOM_OBJECT_NEW);
        genericSalesforceWebsiteActions.waitForPageToLoad(wait);

    }
}
