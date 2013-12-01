package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import applango.common.enums.*;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceAccounts;
import applango.common.services.beans.SalesforceSobjects;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 17/11/13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class genericSalesforceWebsiteActions extends SeleniumTestBase{
    private static final String LOG_IN_UI_OBJECT = "login.button.id";
    private static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.id";
    private static final String RECENT_XPATH= "recent.xpath";
    private static final String SALESFORCE_ENVIRONMENT = "salesfoceEnvironment";
    private static Map configPropertiesMapper;


    public static Salesforce getSalesforceConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        String salesfoceEnvironment = configPropertiesMapper.get(SALESFORCE_ENVIRONMENT).toString();
        Salesforce salesforce = readFromConfigurationFile.getSalesfoceConfigurationFileByenvironmentId(salesfoceEnvironment);
        return salesforce;
    }

    public static void clickOnLoginButton(WebDriver driver, WebDriverWait wait) throws IOException {

        Map salesforceObjectMap = getMap();
        String loginButton = salesforceObjectMap.get(LOG_IN_UI_OBJECT).toString();
        SeleniumTestBase.logger.info("Clicking on login button (by xpath)" + loginButton);
        driver.findElement(By.id(loginButton)).click();
    }

    public static void clickOnSubmitCredentials(WebDriver driver, WebDriverWait wait) throws IOException {
        Map salesforceObjectMap = getMap();
        String loginSubmit = salesforceObjectMap.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.id(loginSubmit)).click();
        waitForPageToLoad(wait);

    }

    private static void waitForPageToLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bodyCell")));
    }

    public static void createNewAccount(WebDriver driver, WebDriverWait wait) throws IOException {
        createNewAccounts(driver, wait, 1);
    }

    public static String getUserLabel(WebDriver driver) {
        return driver.findElement(By.id("userNavLabel")).getText();
    }

    public static SalesforceAccounts[] createNewAccounts(WebDriver driver, WebDriverWait wait, int numberOfAccounts) throws IOException {
        SalesforceAccounts[] newAccountName = new SalesforceAccounts[numberOfAccounts];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfAccounts; i++) {
                logger.info("Create new account " + i + " out of " + numberOfAccounts);
                openTab(driver, salesforceTabs.ACCOUNT, wait);
                verifyRecentElementExist(wait, salesforceObjectMap, salesforceRecent.ACCOUNTS);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                newAccountName[i-1] = fillNewAccountDetailsAndSave(driver, wait);
            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("Accounts created successfully");
        return newAccountName;
    }

    public static SalesforceSobjects[] createNewSobject(WebDriver driver, WebDriverWait wait) throws IOException {
        return createNewSobject(driver, wait, 1);
    }

    public static SalesforceSobjects[] createNewSobject(WebDriver driver, WebDriverWait wait, int numberOfAccounts) throws IOException {
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


    public static void deleteAccounts(WebDriver driver,  WebDriverWait wait, SalesforceAccounts[] accounts) throws IOException {

        for (SalesforceAccounts account : accounts) {
            deleteAccount(driver, wait, account);
        }

    }

    public static void deleteRecordById(WebDriver driver,  WebDriverWait wait, String Id) throws IOException {
        logger.info("Deleting record "+ Id);
        openPageWithIdInUrl(driver, wait, Id);
        clickOnDelete(driver);
        if(driver.switchTo().alert().getText().equals("Are you sure?")) {
            acceptAlertPopup(driver);
            waitForPageToLoad(wait);

        }
        checkRecordDeleted(driver, wait, Id);
    }



    public static void deleteAccount(WebDriver driver,  WebDriverWait wait, SalesforceAccounts account) throws IOException {
        logger.info("Deleting account "+ account.getAccountName());
        openPageWithIdInUrl(driver, wait, account.getAccountId());
        if (verifyAccountSaved(driver, account.getAccountName())) {
            clickOnDelete(driver);
            if(driver.switchTo().alert().getText().equals("Are you sure?")) {
                acceptAlertPopup(driver);

            }
            checkRecordDeleted(driver, wait, account.getAccountId());
        }
    }


    public static void updateAccounts(WebDriver driver,  WebDriverWait wait, SalesforceAccounts[] accounts, String newAccountName) throws IOException {
        for (SalesforceAccounts account : accounts) {
            updateAccount(driver, wait, account, newAccountName);
        }
    }


    public static void updateAccount(WebDriver driver,  WebDriverWait wait, SalesforceAccounts account, String newAccountName) throws IOException {
        logger.info("Updating account name of "+ account.getAccountName() + " to "+ newAccountName);
        openPageWithIdInUrl(driver, wait, account.getAccountId());
        if (verifyAccountSaved(driver, account.getAccountName())) {
            clickOnEdit(driver, wait);
            account.setAccountName(fillAccountDetails(driver, newAccountName));
            clickOnSave(driver);
            waitForPageToLoad(wait);
            verifyAccountSaved(driver, newAccountName);
        }
        else {
            logger.info("Failed to update account " + account.getAccountName() + " since account not found");
        }
    }

    public static void updateSObject(WebDriver driver,  WebDriverWait wait, SalesforceSobjects sObject, SalesforceSobjects newSObject) throws IOException {
        logger.info("Updating sObject "+ sObject.getsObjectName() + " to "+ newSObject.getsObjectName());
        openPageWithIdInUrl(driver, wait, sObject.getsObjectId());

        clickOnEdit(driver, wait);
        fillSObjectDetails(driver, newSObject);
        clickOnSave(driver);
        waitForPageToLoad(wait);
        newSObject.setsObjectId(sObject.getsObjectId());
        newSObject.setSobjectDataCode(sObject.getsObjectDataCode());
    }

    private static void clickOnDelete(WebDriver driver) throws IOException {
        clickOnButton(driver, salesforceButtons.DELETE);
    }
    private static void clickOnSave(WebDriver driver) throws IOException {
        clickOnButton(driver, salesforceButtons.SAVE);
    }
    private static void clickOnEdit(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnButton(driver, salesforceButtons.EDIT);
        waitForPageToLoad(wait);
    }

    public static void openPageWithIdInUrl(WebDriver driver, WebDriverWait wait, String id) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String url = domain.getHost().toString() + "/" + id;
        driver.navigate().to("https://" + url);
        waitForPageToLoad(wait);
    }

    public static void openAccount(WebDriver driver, WebDriverWait wait, SalesforceAccounts account) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + account.getAccountId();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }
    public static void openSetup(WebDriver driver, WebDriverWait wait) throws IOException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + salesforceTabs.SETUP.getValue();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }
//public static void openSetup(WebDriver driver, WebDriverWait wait) throws MalformedURLException {
//        URL domain = new URL(driver.getCurrentUrl().toString());
//        String accountUrl = domain.getHost().toString() + "/setup/forcecomHomepage.apexp";
//        driver.navigate().to("https://" + accountUrl);
//        waitForPageToLoad(wait);
//    }

    private static void checkRecordDeleted(WebDriver driver, WebDriverWait wait, String recordId) throws IOException {
        logger.info("Verify record deleted");
        openPageWithIdInUrl(driver, wait, recordId);
        Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.RECORD_STATUS.getValue())).getText().contains("Record deleted"));
    }

    private static void acceptAlertPopup(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    private static void dismissAlertPopup(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }


    private static boolean verifyAccountSaved(WebDriver driver, String newAccountName) {
        logger.info("Verify account is saved ");
        try {
            Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.ACCOUNT_AccountNameInTitle.getValue())).getText().equals(newAccountName));
            return true;
        }
        catch (Exception ex) {
            logger.error("Account not saved\n" + ex.getMessage());
            return false;
        }
    }
    private static boolean verifyObjectSaved(WebDriver driver, String sObjectDataCode) {
        logger.info("Verify account is saved ");
        try {
            Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.SOBJECT_SObjectNameInTitle.getValue())).getText().equals(sObjectDataCode));
            return true;
        }
        catch (Exception ex) {
            logger.error("Account not saved\n" + ex.getMessage());
            return false;
        }
    }

    private static String getSObjectDataCode(WebDriver driver) {
        logger.info("Verify sObject is saved ");
        try {
            return driver.findElement(By.xpath(salesforceTextfields.SOBJECT_SObjectNameInTitle.getValue())).getText();
        }
        catch (Exception ex) {
            logger.error("Account not saved\n" + ex.getMessage());
            return null;
        }

    }
    private static SalesforceAccounts fillNewAccountDetailsAndSave(WebDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new account details and return object SalesforceAccounts with accountName and accountId");
        SalesforceAccounts newAccount = new SalesforceAccounts();
        newAccount.setAccountName(fillNewAccountDetails(driver));
        clickOnSave(driver);
        waitForPageToLoad(wait);
        if (verifyAccountSaved(driver, newAccount.getAccountName())) {
            newAccount.setAccountId(getIdFromUrl(driver));
            return newAccount;

        }
        else {
            logger.error("Account not saved");
            return null;
        }

    }
    private static SalesforceSobjects fillNewSobjectDetailsAndSave(WebDriver driver, WebDriverWait wait) throws IOException {
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


    private static String getIdFromUrl(WebDriver driver) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        return domain.getPath().substring(1);
    }
    private static String getsObjectDataCodeFromPageTitle(WebDriver driver) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        return domain.getPath().substring(1);
    }

    private static String fillNewAccountDetails(WebDriver driver) throws IOException {
        CharSequence accountName = "testAccount"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
    }
    private static String fillAccountDetails(WebDriver driver, String accountName) throws IOException {
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
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
        sfObject = setSObjectValues(driver, salesforceSObjectMovement, sfObject);
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

    private static SalesforceSobjects setSObjectValues(WebDriver driver, salesforceSObjectMovement salesforceSObjectMovement, SalesforceSobjects sfObject) {
        CharSequence sObjectName = "testsObject"+ Calendar.getInstance().getTimeInMillis();
        sfObject.setsObjectName(sObjectName.toString());
        sfObject.setUser(getUserLabel(driver));
        sfObject.setSalesforceSObjectMovement(salesforceSObjectMovement);
        return sfObject;
    }

    private static void clickOnButton(WebDriver driver, salesforceButtons sfButton) throws IOException {
        logger.info("Click on button " + sfButton);
        driver.findElement(By.xpath(sfButton.getValue())).click();
    }


    public static void openTab(WebDriver driver, salesforceTabs sfTab, WebDriverWait wait) throws IOException {
        logger.info("Open tab " + sfTab);
        driver.findElement(By.xpath(sfTab.getValue())).click();
        waitForPageToLoad(wait);
    }

    private static void verifyRecentElementExist(WebDriverWait wait, Map salesforceObjectMap, salesforceRecent sfRecent) {
        logger.info("Verify page load by searching for " + sfRecent.getValue());
        String recentXpath = salesforceObjectMap.get(RECENT_XPATH).toString();
        try {
            Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(recentXpath))).getText().equals(sfRecent.getValue()));
        }
        catch (AssertionError error) {
            System.out.println("Element: " + sfRecent.getValue() + " not found\n" + error);
            logger.error("Element: " + sfRecent.getValue() + " not found\n" + error);
        }
    }

    private static Map getMap() throws IOException {
        return objectMapper.getObjectMap(jsonMaps.SALESFORCE);
    }


}
