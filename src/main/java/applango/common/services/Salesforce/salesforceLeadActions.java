package applango.common.services.Salesforce;

import applango.common.enums.salesforceButtons;
import applango.common.enums.salesforceTextfields;
import applango.common.enums.salesforceUrls;
import applango.common.services.beans.SalesforceLeads;
import org.openqa.selenium.By;
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

    public static SalesforceLeads[] create(FirefoxDriver driver, WebDriverWait wait, int numberOfLeads) throws IOException {
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


    private static SalesforceLeads fillDetailsAndSave(FirefoxDriver driver, WebDriverWait wait) throws IOException {
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

    public static String fillDetails(FirefoxDriver driver, String newLeadName) throws IOException {
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).sendKeys(newLeadName);
        return newLeadName;
    }

    private static String fillDetailsRandomly(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        CharSequence leadName = "testLead"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadLastName.getValue())).sendKeys(leadName);
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadCompany.getValue())).sendKeys("applango");
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadRegion.getValue())).sendKeys("APAC");
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadSource.getValue())).sendKeys("Cold Call");
        driver.findElement(By.id(salesforceTextfields.LEAD_LeadCountry.getValue())).sendKeys("Fiji");

        logger.info("New lead name is " + leadName);
        return leadName.toString();
    }

    public static void delete(FirefoxDriver driver, WebDriverWait wait, SalesforceLeads[] leads) throws IOException {

        for (SalesforceLeads lead : leads) {
            delete(driver, wait, lead.getId());
        }

    }

    public static void update(FirefoxDriver driver, WebDriverWait wait, SalesforceLeads lead, String newLeadName) throws IOException {
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

    public static void updateLeadNTimes(FirefoxDriver driver1, WebDriverWait wait, SalesforceLeads lead, int numberOfUpdateLeads) throws IOException {
        for (int i=0; i<numberOfUpdateLeads; i++){
            waitForPageToLoad(wait);
            salesforceLeadActions.update(driver1, wait, lead, "TestL" + i);
            waitForPageToLoad(wait);
        }
    }

}
