package applango.common;

import applango.common.enums.applango.applangoButtons;
import applango.common.enums.database.dbTables;
import applango.common.enums.generic.applications;
import applango.common.enums.salesforce.salesforceRanks;
import applango.common.services.Applango.applangoToolsCommand;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.Salesforce.*;
import applango.common.services.beans.Applango;
import applango.common.services.beans.Salesforce;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static applango.common.services.Applango.genericApplangoWebsiteActions.*;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;

public class salesforceIntegrationTest extends SeleniumTestBase {
    public static final Logger logger = LoggerFactory.getLogger(SeleniumTestBase.class);
    @Test
//    @Category(IntegrationTestsCategory.class)
    public void testSyncingSalesforceActivities() throws Throwable {

        RemoteWebDriver driver1 = null;
        RemoteWebDriver driver2 = null;
        Applango applango = getApplangoConfigurationXML();
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        logger.info("Set CustomerAppRankWeightSet false (in order make sure weightSet is default)");
        final String connectionCustomerAppRankWeightSet = dbTables.customerAppRankWeightSet.getValue().toString();
        DBCollection coll = db.getCollection(connectionCustomerAppRankWeightSet);
        String appRankWeightSet = "testset2";
        mongoDB.updateCustomerAppRankWeightSet(coll, appRankWeightSet, false);
        final String connectionRolledUpUserAppRankInfo = dbTables.rolledUpUserAppRankInfo.getValue().toString();
        DBCollection collRoll = db.getCollection(connectionRolledUpUserAppRankInfo);

        int numOfNewContact = 1;
        int numOfUpdateContact = 1;
        int numOfNewAccount = 1;
        int numOfUpdateAccount = 1;
        int numOfNewLeads = 1;
        int numOfUpdateLeads = 1;
        int numOfNewOpportunities = 1;
        int numOfUpdateOpportunities = 1;
        int numOfDelete = numOfNewAccount + numOfNewContact + numOfNewLeads + numOfNewOpportunities;
        int login = 1;
        int totalActivities = numOfNewContact + numOfUpdateContact + numOfNewAccount + numOfUpdateAccount + numOfNewLeads + numOfUpdateLeads + numOfNewOpportunities + numOfUpdateOpportunities + login + numOfDelete;
        int contactAppRankTotal = (numOfNewContact * salesforceRanks.CREATE.getValue() + numOfUpdateContact * salesforceRanks.UPDATE.getValue()) * salesforceRanks.CONTACT.getValue() + +salesforceRanks.DELETE.getValue() * salesforceRanks.CONTACT.getValue();
        int accountAppRankTotal = (numOfNewAccount * salesforceRanks.CREATE.getValue() + numOfUpdateAccount * salesforceRanks.UPDATE.getValue()) * salesforceRanks.ACCOUNT.getValue() + salesforceRanks.DELETE.getValue() * salesforceRanks.ACCOUNT.getValue();
        int leadAppRankTotal = (numOfNewLeads * salesforceRanks.CREATE.getValue() + numOfUpdateLeads * salesforceRanks.UPDATE.getValue()) * salesforceRanks.LEAD.getValue() + salesforceRanks.DELETE.getValue() * salesforceRanks.LEAD.getValue();
        int opportunityAppRankTotal = (numOfNewOpportunities * salesforceRanks.CREATE.getValue() + numOfUpdateOpportunities * salesforceRanks.UPDATE.getValue()) * salesforceRanks.OPPORTUNITY.getValue() + salesforceRanks.DELETE.getValue() * salesforceRanks.OPPORTUNITY.getValue();
        int appRankChange = contactAppRankTotal + accountAppRankTotal + leadAppRankTotal + opportunityAppRankTotal + login;


        try {
            driver1 = getRemoteWebDriver(DesiredCapabilities.chrome());
            driver2 = getRemoteWebDriver(DesiredCapabilities.chrome());
            WebDriverWait wait1 = new WebDriverWait(driver1, getTimeout());
            WebDriverWait wait2 = new WebDriverWait(driver2, getTimeout());
            //TODO:Set tooljar url in config.properties


            logger.info("Sync metrics and roll up");
            applangoToolsCommand.syncSFActivitiesLoginsAndRollup();
            logger.info("Login to Applango");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.selectApplication(driver1, wait1, applications.SALESFORCE);

            logger.info("Open Salesforce and perform activities");
            genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver2, wait2);
            salesforceOpportunitiesActions.salesforcePerformActivitiesInOpportunities(sf, driver2, wait2, numOfNewOpportunities, numOfUpdateOpportunities);
            salesforceLeadActions.salesforcePerformActivitiesInLeads(sf, driver2, wait2, numOfNewLeads, numOfUpdateLeads);
            salesforceContactActions.salesforcePerformActivitiesInContacts(sf, driver2, wait2, numOfNewContact, numOfUpdateContact);
            salesforceAccountActions.salesforcePerformActivitiesInAccounts(sf, driver2, wait2, numOfNewAccount, numOfUpdateAccount);

            logger.info("Get appRank and Activities before sync");
            filterByDate(driver1, wait1, thisYear, thisMonth, thisYear, thisMonth);
            selectUserFromList(driver1, wait1, "Omer", "OvadiaAuto");

            int appRankBeforeActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityBeforeActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
            logger.info("AppRank before: " + appRankBeforeActivitiesInSF + " Activity before: " + activityBeforeActivitiesInSF);

            logger.info("Sync metrics again ");
            applangoToolsCommand.syncSFActivitiesLoginsAndRollup();

            logger.info("Compare appRank and activities");
//            filterByDate(driver1, wait1, thisYear, thisMonth, thisYear, thisMonth);
            logger.info("1");
            driver1.findElement(By.id(applangoButtons.DATE_SEARCH.getValue().toString())).click();
            logger.info("2");
            waitUntilWaitForServerDissappears(wait1);
//            genericApplangoWebsiteActions.clickOnDateSearchButton(driver1, wait1);

            int appRankAfterActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityAfterActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
//            String userName = genericApplangoWebsiteActions.getName(driver1);
            int expectedAppRankAfterActivitiesInSF = appRankBeforeActivitiesInSF + appRankChange;
            int expectedActivitiesAfterActivitiesInSF = activityBeforeActivitiesInSF + totalActivities;

            logger.info("\nBefore performing \nAppRank: " + appRankBeforeActivitiesInSF + " Activity: " + activityBeforeActivitiesInSF + "\n" +
                    "After performing  \nAppRank: " + appRankAfterActivitiesInSF + " Activity: " + activityAfterActivitiesInSF + "\n" +
                    "Should be \nAppRank: " + expectedAppRankAfterActivitiesInSF + " Activity: " + expectedActivitiesAfterActivitiesInSF);
            assertTrue(appRankAfterActivitiesInSF == expectedAppRankAfterActivitiesInSF);
            assertTrue(activityAfterActivitiesInSF == expectedActivitiesAfterActivitiesInSF);

            logger.info("Set CustomerAppRankWeightSet true (weight is four times higher than default)");
            DBObject rollupRecordAfterRollupActivitiesBeforeSettingNewWeight = mongoDB.getRollupValue(collRoll, sf.getUsername());
            mongoDB.updateCustomerAppRankWeightSet(coll, appRankWeightSet, true);
            applangoToolsCommand.syncSFActivitiesLoginsAndRollup();
            DBObject rollupRecordAfterRollupActivitiesAfterSettingNewWeight = mongoDB.getRollupValue(collRoll, sf.getUsername());
//            filterByDate(driver1, wait1, thisYear, thisMonth, thisYear, thisMonth);
            genericApplangoWebsiteActions.clickOnDateSearchButton(driver1, wait1);
            int appRankBefore = mongoDB.parseAppRankValueFromRollupRecord(rollupRecordAfterRollupActivitiesBeforeSettingNewWeight);
            int appRankAfter = mongoDB.parseAppRankValueFromRollupRecord(rollupRecordAfterRollupActivitiesAfterSettingNewWeight);
            int totalLogins = Integer.parseInt(rollupRecordAfterRollupActivitiesAfterSettingNewWeight.get("numLogins").toString());
            int expectedAppRankAfterChangingWeightSet = (appRankBefore - totalLogins) * 4 + totalLogins;
            logger.info("\nBefore updating appRank weight set the AppRank is: " + appRankBefore + "\n" +
                    "After  updating appRank weight set the AppRank is: " + appRankAfter + "\n" +
                    "Should be: " + expectedAppRankAfterChangingWeightSet);
            assertTrue(appRankAfter == expectedAppRankAfterChangingWeightSet);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Test failed " + ex.getMessage());
        } finally {
            mongoDB.updateCustomerAppRankWeightSet(coll, appRankWeightSet, false);
            driver1.close();
            driver2.close();
        }
    }

}