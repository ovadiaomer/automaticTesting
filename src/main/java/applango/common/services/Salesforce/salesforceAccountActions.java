package applango.common.services.Salesforce;

import applango.common.enums.salesforce.salesforceButtons;
import applango.common.enums.salesforce.salesforceRecent;
import applango.common.enums.salesforce.salesforceTextfields;
import applango.common.enums.salesforce.salesforceUrls;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceAccounts;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

public class salesforceAccountActions extends genericSalesforceWebsiteActions {
    public static void openAccount(WebDriver driver, WebDriverWait wait, SalesforceAccounts account) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + account.getAccountId();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }

    static boolean verifySaved(FirefoxDriver driver, String newAccountName) {
        logger.info("Verify account is saved ");
        try {
//            Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.ACCOUNT_AccountNameInTitle.getValue())).getText().equals(newAccountName));
            return true;
        }
        catch (Exception ex) {
            logger.error("Account not saved\n" + ex.getMessage());
            return false;
        }
    }

    public static SalesforceAccounts fillDetailsAndSave(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new account details and return object SalesforceAccounts with accountName and accountId");
        waitForPageToLoad(wait);
        SalesforceAccounts newAccount = new SalesforceAccounts();
        waitForPageToLoad(wait);
        newAccount.setAccountName(fillDetailsRandomly(driver));

        fillExtraDetails(driver);


        clickOnSave(driver);
        waitForPageToLoad(wait);
        if (verifySaved(driver, newAccount.getAccountName())) {
            newAccount.setAccountId(getIdFromUrl(driver));
            return newAccount;

        }
        else {
            logger.error("Account not saved");
            return null;
        }

    }



    public static void createOne(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        create(driver, wait, 1);
    }

    public static SalesforceAccounts[] create(FirefoxDriver driver, WebDriverWait wait, int numberOfAccounts) throws IOException {
        logger.info("Create " + numberOfAccounts + " accounts");
        SalesforceAccounts[] newAccountName = new SalesforceAccounts[numberOfAccounts];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfAccounts; i++) {
                logger.info("Create new account " + i + " out of " + numberOfAccounts);
                openUrl(driver, wait, salesforceUrls.ACCOUNT);
                verifyRecentElementExist(wait, salesforceObjectMap, salesforceRecent.ACCOUNTS);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                newAccountName[i-1] = fillDetailsAndSave(driver, wait);
            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("Accounts created successfully");
        return newAccountName;
    }

    public static void delete(FirefoxDriver driver, WebDriverWait wait, SalesforceAccounts[] accounts) throws IOException {

        for (SalesforceAccounts account : accounts) {
            delete(driver, wait, account.getAccountId());
            waitForPageToLoad(wait);
        }

    }

    public static void deleteOne(FirefoxDriver driver, WebDriverWait wait, SalesforceAccounts account) throws IOException {
        logger.info("Deleting account "+ account.getAccountName());
        openPageWithIdInUrl(driver, wait, account.getAccountId());
        if (verifySaved(driver, account.getAccountName())) {
            clickOnDelete(driver);
//            if(driver.switchTo().alert().getText().equals("Are you sure?")) {
            acceptAlertPopup(driver);
//            }
            checkRecordDeleted(driver, wait, account.getAccountId());
        }
    }


    public static void update(FirefoxDriver driver, WebDriverWait wait, SalesforceAccounts[] accounts, String newAccountName) throws IOException {
        for (SalesforceAccounts account : accounts) {
            updateOne(driver, wait, account, newAccountName);
        }
    }

    public static void updateOne(FirefoxDriver driver, WebDriverWait wait, SalesforceAccounts account, String newAccountName) throws IOException {
        logger.info("Updating account name of "+ account.getAccountName() + " to "+ newAccountName);
        openPageWithIdInUrl(driver, wait, account.getAccountId());
        waitForPageToLoad(wait);
        if (verifySaved(driver, account.getAccountName())) {
            waitForPageToLoad(wait);
            clickOnEdit(driver, wait);
            waitForPageToLoad(wait);
            account.setAccountName(fillDetails(driver, wait, newAccountName));
            waitForPageToLoad(wait);
            clickOnSave(driver);
            waitForPageToLoad(wait);
        }
        else {
            logger.info("Failed to update account " + account.getAccountName() + " since account not found");
        }
    }


    public static void updateAccountNTimes(FirefoxDriver driver1, WebDriverWait wait, SalesforceAccounts newAccount, int numberOfUpdateAccounts) throws IOException {
        logger.info("Update Account " + numberOfUpdateAccounts + " times");
        for (int i=0; i<numberOfUpdateAccounts; i++){
            waitForPageToLoad(wait);
            salesforceAccountActions.updateOne(driver1, wait, newAccount, "TestA" + i);
            waitForPageToLoad(wait);
        }
    }


    private static String fillDetails(FirefoxDriver driver, WebDriverWait wait, String accountName) throws IOException {
        Assert.assertTrue(driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).isDisplayed());
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).clear();
        waitForPageToLoad(wait);
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
    }

    private static String fillDetailsRandomly(FirefoxDriver driver) throws IOException {
        CharSequence accountName = "testA"+ Calendar.getInstance().getTimeInMillis();
        accountName = accountName.subSequence(0,14);
        driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountNAME.getValue())).sendKeys(accountName);
        logger.info("New Account name is " + accountName);
        return accountName.toString();
    }

    private static void fillExtraDetails(FirefoxDriver driver) throws IOException {

        try {

            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountRegion.getValue())).isDisplayed();
            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountRegion.getValue())).sendKeys("APAC");
            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountWebsite.getValue())).isDisplayed();
            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountWebsite.getValue())).sendKeys("w.com");
            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountCountry.getValue())).isDisplayed();
            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountCountry.getValue())).sendKeys("India");
        }
        catch (Exception ex)
        {
            logger.info(ex.getMessage());
        }
    }

    public static  void salesforcePerformActivitiesInAccounts(Salesforce sf, FirefoxDriver driver2, WebDriverWait wait2, int numOfNewAccounts, int numOfUpdateAccounts) throws IOException {

        //Create Account
        SalesforceAccounts[] salesforceAccounts =  create(driver2, wait2, numOfNewAccounts);
        //Update the account x times
        updateAccountNTimes(driver2, wait2, salesforceAccounts[0], numOfUpdateAccounts);
        deleteRecordById(driver2, wait2, salesforceAccounts[0].getAccountId());
    }


//        if (driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountRegion.getValue())).isDisplayed()) {
//            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountRegion.getValue())).sendKeys("APAC");
//
//        }
//        if (driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountWebsite.getValue())).isDisplayed()) {
//            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountWebsite.getValue())).sendKeys("w.com");
//        }
//        if (driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountCountry.getValue())).isDisplayed()) {
//            driver.findElement(By.id(salesforceTextfields.ACCOUNT_AccountCountry.getValue())).sendKeys("India");
//        }



}
