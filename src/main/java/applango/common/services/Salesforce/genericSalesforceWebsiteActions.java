package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import applango.common.enums.*;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Salesforce;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bodyCell")));

    }

    public static void createNewAccount(WebDriver driver, WebDriverWait wait) throws IOException {
        String newAccountName;
        logger.info("Create new account");
        Map salesforceObjectMap = getMap();
        try {
            openTab(driver, salesforceTabs.ACCOUNT);
            verifyRecentElementExist(wait, salesforceObjectMap, salesforceRecent.ACCOUNTS);

            clickOnButton(driver, salesforceButtons.NEW);
            newAccountName = fillNewAccountDetailsAndSave(driver);
            verifyAccountSaved(driver, newAccountName);

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getMessage());
        }
    }

    private static boolean verifyAccountSaved(WebDriver driver, String newAccountName) {
        boolean accountExist = true;

        //TODO implement
        return accountExist;
    }

    private static String fillNewAccountDetailsAndSave(WebDriver driver) throws IOException {
        logger.info("Fill new account details");
        String newAccount = fillNewAccountDetails(driver);
        clickOnButton(driver, salesforceButtons.SAVE);

        return newAccount;

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
