package applango.common.services.Salesforce;

import applango.common.enums.salesforce.salesforceButtons;
import applango.common.enums.salesforce.salesforceTextfields;
import applango.common.enums.salesforce.salesforceUrls;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceOpportunities;
import org.junit.Assert;
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
//                clickOnButton(driver, salesforceButtons.SAVE);
//                waitForPageToLoad(wait);
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
        waitForPageToLoad(wait);
        sfOpportunity = fillDetailsRandomly(driver, wait, sfOpportunity);
        clickOnSave(driver);
        waitForPageToLoad(wait);
        waitForPageToLoad(wait);
        sfOpportunity.setOpportunityId(getIdFromUrl(driver));
        return sfOpportunity;
    }

    private static SalesforceOpportunities fillDetailsRandomly(FirefoxDriver driver, WebDriverWait wait, SalesforceOpportunities sfOpportunity) throws IOException {
        CharSequence opportunityName = "testOpp"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).sendKeys(opportunityName);
        sfOpportunity.setOpportunityName(opportunityName.toString());
        try {
            driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityAccountName.getValue())).sendKeys(sfOpportunity.getAccountName());
            driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityStage.getValue())).sendKeys(sfOpportunity.getStage());
            driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityType.getValue())).sendKeys(sfOpportunity.getType());
            driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityClosedDate.getValue())).sendKeys(sfOpportunity.getClosedDate());

        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
        }
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
        Assert.assertTrue(driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).isDisplayed());
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.OPPORTUNITY_OpportunityName.getValue())).sendKeys(newOpportunityName);
        return newOpportunityName;
    }


    public static  void salesforcePerformActivitiesInOpportunities(Salesforce sf, FirefoxDriver driver2, WebDriverWait wait2, int numOfNewOpportunities, int numOfUpdateOpportunities) throws IOException {

        //Create Opportunity
        SalesforceOpportunities[] salesforceOpportunities =  create(driver2, wait2, numOfNewOpportunities);
        //Update the Opportunity x times
        updateOpportunityNTimes(driver2, wait2, salesforceOpportunities[0], numOfUpdateOpportunities);
    }
}
