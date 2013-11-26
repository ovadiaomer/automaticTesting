package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import applango.common.enums.*;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceAccounts;
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

    public static SalesforceAccounts[] createNewAccounts(WebDriver driver, WebDriverWait wait, int numberOfAccounts) throws IOException {
        SalesforceAccounts[] newAccountName = new SalesforceAccounts[numberOfAccounts];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfAccounts; i++) {
                logger.info("Create new account " + i + " out of " + numberOfAccounts);
                openTab(driver, salesforceTabs.ACCOUNT);
                verifyRecentElementExist(wait, salesforceObjectMap, salesforceRecent.ACCOUNTS);
                clickOnButton(driver, salesforceButtons.NEW);
                newAccountName[i-1] = fillNewAccountDetailsAndSave(driver, wait);
            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        return newAccountName;
    }


    public static void deleteAccounts(WebDriver driver,  WebDriverWait wait, SalesforceAccounts[] accounts) throws IOException {

        for (SalesforceAccounts account : accounts) {
            deleteAccount(driver, wait, account);
        }

    }

    public static void deleteAccount(WebDriver driver,  WebDriverWait wait, SalesforceAccounts account) throws IOException {
        logger.info("Deleting account "+ account.getAccountName());
        openAccount(driver, wait, account);
        if (verifyAccountSaved(driver, account.getAccountName())) {
            clickOnDelete(driver);
            if(driver.switchTo().alert().getText().equals("Are you sure?")) {
                acceptAlertPopup(driver);

            }
            checkAccountDeleted(driver, wait, account);
        }
    }

    private static void clickOnDelete(WebDriver driver) throws IOException {
        clickOnButton(driver, salesforceButtons.DELETE);
    }

    private static void openAccount(WebDriver driver, WebDriverWait wait, SalesforceAccounts account) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + account.getAccountId();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }

    private static void checkAccountDeleted(WebDriver driver,  WebDriverWait wait, SalesforceAccounts account) throws IOException {
        logger.info("Verify account deleted");
        openAccount(driver, wait, account);
        Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.ACCOUNT_AccountStatus.getValue())).getText().contains("Record deleted"));
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
    //Fill new account and return object SalesforceAccounts with accountName and accountId
    private static SalesforceAccounts fillNewAccountDetailsAndSave(WebDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new account details and return object SalesforceAccounts with accountName and accountId");
        SalesforceAccounts newAccount = new SalesforceAccounts();
        newAccount.setAccountName(fillNewAccountDetails(driver));
        clickOnButton(driver, salesforceButtons.SAVE);
        waitForPageToLoad(wait);
        if (verifyAccountSaved(driver, newAccount.getAccountName())) {
            newAccount.setAccountId(getAccountIdFromUrl(driver));
            return newAccount;

        }
        else {
            logger.error("Account not saved");
            return null;
        }

    }

    private static String getAccountIdFromUrl(WebDriver driver) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        return domain.getPath().substring(1);
    }

    private static String fillNewAccountDetails(WebDriver driver) throws IOException {
        CharSequence accountName = "testAccount"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
    }

    private static void clickOnButton(WebDriver driver, salesforceButtons sfButton) throws IOException {
        logger.info("Click on button " + sfButton);
        driver.findElement(By.xpath(sfButton.getValue())).click();
    }


    private static void openTab(WebDriver driver, salesforceTabs sfTab) throws IOException {
        logger.info("Open tab " + sfTab);
        driver.findElement(By.xpath(sfTab.getValue())).click();
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
