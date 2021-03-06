package applango.common.services.Salesforce;

import applango.common.enums.salesforce.salesforceButtons;
import applango.common.enums.salesforce.salesforceTextfields;
import applango.common.enums.salesforce.salesforceUrls;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceLeads;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 21/01/14
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public class salesforceLeadActions   extends genericSalesforceWebsiteActions {

    public static SalesforceLeads[] create(WebDriver driver, WebDriverWait wait, int numberOfLeads) throws IOException {
        SalesforceLeads[] newLead =  new SalesforceLeads[numberOfLeads];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfLeads; i++) {
                logger.info("Create new lead " + i + " out of " + numberOfLeads);
                openUrl(driver, wait, salesforceUrls.LEADS);
                waitForPageToLoad(wait);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                newLead[i-1] = fillDetailsAndSave(driver, wait);


            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("Lead created successfully");
        return newLead;
    }

    public static SalesforceLeads[] create(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        SalesforceLeads[] newLead =  create(driver, wait, 1);
        return newLead;
    }


    private static SalesforceLeads fillDetailsAndSave(WebDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new lead details and return object SalesforceContact with LeadName and LeadId");
        waitForPageToLoad(wait);
        SalesforceLeads newLead = new SalesforceLeads();
        waitForPageToLoad(wait);
        newLead.setLastName(fillDetailsRandomly(driver, wait));
        clickOnButton(driver, salesforceButtons.SAVE);
        waitForPageToLoad(wait);
        newLead.setId(getIdFromUrl(driver));
        return newLead;
    }

    public static String fillDetails(WebDriver driver, String newLeadName) throws IOException {
        Assert.assertTrue(driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).isDisplayed());
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).sendKeys(newLeadName);
        return newLeadName;
    }

    private static String fillDetailsRandomly(WebDriver driver, WebDriverWait wait) throws IOException {
        Assert.assertTrue(driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).isDisplayed());
        CharSequence leadName = "testLead"+ Calendar.getInstance().getTimeInMillis();
        try {


            driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).sendKeys(leadName);
            driver.findElement(By.id(salesforceTextfields.LEAD_LeadCompany.getValue())).sendKeys("applango");
            driver.findElement(By.id(salesforceTextfields.LEAD_LeadRegion.getValue())).sendKeys("APAC");
            driver.findElement(By.id(salesforceTextfields.LEAD_LeadSource.getValue())).sendKeys("Cold Call");
            driver.findElement(By.id(salesforceTextfields.LEAD_LeadCountry.getValue())).sendKeys("Fiji");
        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
        }

        logger.info("New lead name is " + leadName);
        return leadName.toString();
    }

    public static void delete(FirefoxDriver driver, WebDriverWait wait, SalesforceLeads[] leads) throws IOException {

        for (SalesforceLeads lead : leads) {
            delete(driver, wait, lead.getId());
        }

    }

    public static void update(WebDriver driver, WebDriverWait wait, SalesforceLeads lead, String newLeadName) throws IOException {
        logger.info("Updating lead name of "+ lead.getLastName() + " to "+ newLeadName);
        waitForPageToLoad(wait);
        waitForPageToLoad(wait);
        openPageWithIdInUrl(driver, wait, lead.getId());
        waitForPageToLoad(wait);
        clickOnEdit(driver, wait);
        waitForPageToLoad(wait);
        lead.setLastName(fillDetails(driver, newLeadName));
        waitForPageToLoad(wait);
        clickOnSave(driver);
        waitForPageToLoad(wait);

    }

    public static void updateLeadNTimes(WebDriver driver1, WebDriverWait wait, SalesforceLeads lead, int numberOfUpdateLeads) throws IOException {
        for (int i=0; i<numberOfUpdateLeads; i++){
            waitForPageToLoad(wait);
            salesforceLeadActions.update(driver1, wait, lead, "TestL" + i);
            waitForPageToLoad(wait);
        }
    }

    public static  void salesforcePerformActivitiesInLeads(Salesforce sf, WebDriver driver2, WebDriverWait wait2, int numOfNewLeads, int numOfUpdateLeads) throws IOException {

        //Create Lead
        SalesforceLeads[] salesforceLeads =  create(driver2, wait2, numOfNewLeads);
        //Update the Lead x times
        updateLeadNTimes(driver2, wait2, salesforceLeads[0], numOfUpdateLeads);
        deleteRecordById(driver2, wait2, salesforceLeads[0].getId());
    }
}
