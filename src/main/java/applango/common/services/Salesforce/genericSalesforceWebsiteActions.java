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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 17/11/13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class genericSalesforceWebsiteActions extends SeleniumTestBase{
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

        SeleniumTestBase.logger.info("Clicking on login button (by xpath)");
        driver.findElement(By.id(salesforceButtons.LOGIN_BUTTON.getValue())).click();
//        waitForPageToLoad(wait);
    }

    public static void clickOnSubmitCredentials(WebDriver driver, WebDriverWait wait) throws IOException {
        driver.findElement(By.id(salesforceButtons.LOGIN_SUBMIT.getValue())).click();
        waitForPageToLoad(wait);

    }

    protected static void waitForPageToLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bodyCell")));
    }

    public static String getUserLabel(WebDriver driver) {
        return driver.findElement(By.id("userNavLabel")).getText();
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


    protected static void clickOnDelete(WebDriver driver) throws IOException {
        clickOnButton(driver, salesforceButtons.DELETE);
    }
    protected static void clickOnSave(WebDriver driver) throws IOException {
        clickOnButton(driver, salesforceButtons.SAVE);
    }
    protected static void clickOnEdit(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnButton(driver, salesforceButtons.EDIT);
        waitForPageToLoad(wait);
    }

    public static void openPageWithIdInUrl(WebDriver driver, WebDriverWait wait, String id) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String url = domain.getHost().toString() + "/" + id;
        driver.navigate().to("https://" + url);
        waitForPageToLoad(wait);
    }

    public static void openSetup(WebDriver driver, WebDriverWait wait) throws IOException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        String accountUrl = domain.getHost().toString() + "/" + salesforceTabs.SETUP.getValue();
        driver.navigate().to("https://" + accountUrl);
        waitForPageToLoad(wait);
    }

    protected static void checkRecordDeleted(WebDriver driver, WebDriverWait wait, String recordId) throws IOException {
        logger.info("Verify record deleted");
        openPageWithIdInUrl(driver, wait, recordId);
        Assert.assertTrue(driver.findElement(By.xpath(salesforceTextfields.RECORD_STATUS.getValue())).getText().contains("Record deleted"));
    }

    protected static void acceptAlertPopup(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    private static void dismissAlertPopup(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }


    protected static String getIdFromUrl(WebDriver driver) throws MalformedURLException {
        URL domain = new URL(driver.getCurrentUrl().toString());
        return domain.getPath().substring(1);
    }


    protected static void clickOnButton(WebDriver driver, salesforceButtons sfButton) throws IOException {
        logger.info("Click on button " + sfButton);
        driver.findElement(By.xpath(sfButton.getValue())).click();
    }


    public static void openTab(WebDriver driver, salesforceTabs sfTab, WebDriverWait wait) throws IOException {
        logger.info("Open tab " + sfTab);
        driver.findElement(By.xpath(sfTab.getValue())).click();
        waitForPageToLoad(wait);
    }

    protected static void verifyRecentElementExist(WebDriverWait wait, Map salesforceObjectMap, salesforceRecent sfRecent) {
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

    protected static Map getMap() throws IOException {
        return objectMapper.getObjectMap(jsonMaps.SALESFORCE);
    }


}
