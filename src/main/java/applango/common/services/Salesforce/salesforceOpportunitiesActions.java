package applango.common.services.Salesforce;

import applango.common.enums.salesforceButtons;
import applango.common.enums.salesforceTextfields;
import applango.common.enums.salesforceUrls;
import applango.common.services.beans.SalesforceOpportunities;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Calendar;

public class salesforceOpportunitiesActions extends genericSalesforceWebsiteActions {

    public static SalesforceOpportunities[] create(FirefoxDriver driver, WebDriverWait wait, int numberOfOpportunities) throws IOException {
        SalesforceOpportunities[] newOpportunity =  new SalesforceOpportunities[numberOfOpportunities];
        SalesforceOpportunities newOpportunityWithDefaultData;

//        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfOpportunities; i++) {
                logger.info("Create new opportunity " + i + " out of " + numberOfOpportunities);

                newOpportunityWithDefaultData = new SalesforceOpportunities();
                openUrl(driver, wait, salesforceUrls.OPPORTUNITY);
                waitForPageToLoad(wait);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                //Pressing on save uses as Continue also
                clickOnButton(driver, salesforceButtons.SAVE);
                waitForPageToLoad(wait);
                newOpportunity[i-1] = fillDetailsAndSave(driver, wait, newOpportunityWithDefaultData);


            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("Opportunity created successfully");
        return newOpportunity;
    }

    private static SalesforceOpportunities fillDetailsAndSave(FirefoxDriver driver, WebDriverWait wait, SalesforceOpportunities sfOpportunity) throws IOException {
        logger.info("Fill new opportunity details and return object SalesforceOpportunity");
        waitForPageToLoad(wait);
//        SalesforceOpportunities newOpportunity = new SalesforceOpportunities();
        waitForPageToLoad(wait);
        sfOpportunity = fillDetailsRandomly(driver, wait, sfOpportunity);
        clickOnButton(driver, salesforceButtons.SAVE);
        waitForPageToLoad(wait);
        //Save for choose price book
        clickOnButton(driver, salesforceButtons.SAVE);
        waitForPageToLoad(wait);
        //Click on cancel in Product Selection
        clickOnButton(driver, salesforceButtons.CANCEL);
        waitForPageToLoad(wait);
        sfOpportunity.setOpportunityId(getIdFromUrl(driver));
        return sfOpportunity;
    }

    private static SalesforceOpportunities fillDetailsRandomly(FirefoxDriver driver, WebDriverWait wait, SalesforceOpportunities sfOpportunity) throws IOException {
        CharSequence opportunityName = "testOpp"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).sendKeys(opportunityName);
        sfOpportunity.setOpportunityName(opportunityName.toString());
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityAccountName.getValue())).sendKeys(sfOpportunity.getAccountName());
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityStage.getValue())).sendKeys(sfOpportunity.getStage());
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityType.getValue())).sendKeys(sfOpportunity.getType());
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityClosedDate.getValue())).sendKeys(sfOpportunity.getClosedDate());
        logger.info("New opportunityName name is " + opportunityName);
        return sfOpportunity;
    }

    public static void update(FirefoxDriver driver, WebDriverWait wait, SalesforceOpportunities opportunity, String newOpportunityName) throws IOException {
        logger.info("Updating opportunity name of "+ opportunity.getOpportunityName() + " to "+ newOpportunityName);
        waitForPageToLoad(wait);
        waitForPageToLoad(wait);
        openPageWithIdInUrl(driver, wait, opportunity.getOpportunityId());
        waitForPageToLoad(wait);
        clickOnEdit(driver, wait);
        waitForPageToLoad(wait);
        opportunity.setOpportunityName(fillDetails(driver, newOpportunityName));
        waitForPageToLoad(wait);
        clickOnSave(driver);
        waitForPageToLoad(wait);

    }
    public static void updateOpportunityNTimes(FirefoxDriver driver1, WebDriverWait wait, SalesforceOpportunities opportunity, int numberOfUpdateOpportunities) throws IOException {
        for (int i=0; i<numberOfUpdateOpportunities; i++){
            waitForPageToLoad(wait);
            salesforceOpportunitiesActions.update(driver1, wait, opportunity, "TestOpp" + i);
            waitForPageToLoad(wait);
        }
    }
    public static void delete(FirefoxDriver driver, WebDriverWait wait, SalesforceOpportunities[] opportunity) throws IOException {

        for (SalesforceOpportunities salesforceOpportunities : opportunity) {
            delete(driver, wait, salesforceOpportunities.getOpportunityId());
        }

    }

    public static String fillDetails(FirefoxDriver driver, String newOpportunityName) throws IOException {
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).sendKeys(newOpportunityName);
        return newOpportunityName;
    }



}
