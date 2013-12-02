package applango.common.services.Salesforce;

import applango.common.enums.salesforceButtons;
import applango.common.enums.salesforceRecent;
import applango.common.enums.salesforceTabs;
import applango.common.enums.salesforceTextfields;
import applango.common.services.beans.SalesforceAccounts;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 02/12/13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class salesforceAccountActions extends genericSalesforceWebsiteActions {
    public static void openAccount(WebDriver driver, WebDriverWait wait, SalesforceAccounts account) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + account.getAccountId();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }

    static boolean verifyAccountSaved(WebDriver driver, String newAccountName) {
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

    static SalesforceAccounts fillNewAccountDetailsAndSave(WebDriver driver, WebDriverWait wait) throws IOException {
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

    private static String fillNewAccountDetails(WebDriver driver) throws IOException {
        CharSequence accountName = "testAccount"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
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

    public static void deleteAccounts(WebDriver driver,  WebDriverWait wait, SalesforceAccounts[] accounts) throws IOException {

        for (SalesforceAccounts account : accounts) {
            deleteAccount(driver, wait, account);
        }

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

    private static String fillAccountDetails(WebDriver driver, String accountName) throws IOException {
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
    }

}
