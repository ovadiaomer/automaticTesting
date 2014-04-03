package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.applangoMessages;
import applango.common.enums.database.dbTables;
import applango.common.enums.gmail.gmailButtons;
import applango.common.enums.months;
import applango.common.enums.salesforce.salesforceRanks;
import applango.common.services.Applango.applangoToolsCommand;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.Gmail.genericGmailWebsiteActions;
import applango.common.services.Salesforce.*;
import applango.common.services.beans.Applango;
import applango.common.services.beans.Database;
import applango.common.services.beans.Gmail;
import applango.common.services.beans.Salesforce;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static applango.common.services.Applango.genericApplangoWebsiteActions.*;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;

//import com.applango.beans.Oauth2Credentials;

public class dashboardTests extends SeleniumTestBase{

    @Test
    public void testDashboardLogin() throws Exception {
        Applango applango = getApplangoConfigurationXML();
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        FirefoxDriver driver1 = new FirefoxDriver();
//        driver1.manage().deleteAllCookies();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        String validUsername = applango.getUsername();
        String validPassword = applango.getPassword();
        String invalidUsername = "notRealUsername";
        String invalidPassword = "notRealPassword";
        String empty = "";
        try {
            launchingWebsite(driver1, applango.getUrl());
            waitForDashboardLoginPageToLoad(wait);
            //Enter invalid credentials and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, invalidUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            //Enter invalid password and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            //Enter empty credentials and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, empty, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(driver1, wait);
            //Enter empty password and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(driver1, wait);
            //Enter Valid Credentials and check page loads
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername.toLowerCase(), validPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);


        }
        catch (Exception ex) {
            fail("");
            logger.error(ex.getMessage());
        }

        finally {
            driver1.kill();
        }
    }

    @Test
    public void testEnteringValidOAuthenticationCredentials() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        Database dbProperties = getDatabaseConfigurationXML();
        final String connection = dbTables.OAuth2Credentials.getValue().toString();

        try {

            DB db = connectToDB(dbProperties);
            DBCollection coll = db.getCollection(connection);
            DBObject dbObjectRecordQuery = mongoDB.removeRecordsFromDB(applango, coll);

            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1, true);
            genericApplangoWebsiteActions.clickOnApplicationSettings(driver1, wait1);
            genericApplangoWebsiteActions.verifyNoAuthenticationSet(driver1);
            genericApplangoWebsiteActions.enterAuthentication(driver1, sf.getAccessToken(), sf.getClientSecret());
            genericApplangoWebsiteActions.clickOnAuthenticationSubmit(driver1, wait1);
            genericApplangoWebsiteActions.clickOnAuthenticationLink(driver1, wait1);

            String mainWinID = controlAndSwitchWindow(driver1);

            genericSalesforceWebsiteActions.waitForLoginPageToLoad(wait1);
            genericSalesforceWebsiteActions.enterCredentials(driver1, sf.getUsername(), sf.getPassword());
            genericSalesforceWebsiteActions.clickOnSubmitCredentials(driver1);
            genericApplangoWebsiteActions.waitForSuccessfulAuthenticatedMessage(driver1, wait1);
            driver1.close(); //Closes new window that opened (sf)

            driver1.switchTo().window(mainWinID);   //Switch back to applango
            clickOnVerifyAuthentication(driver1);
            genericApplangoWebsiteActions.waitForSuccessfulAccountAuthenticatedMessage(wait1);

            mongoDB.checkRecordCreatedInDB(applango, connection, coll, dbObjectRecordQuery);
        }

        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        }
        finally {
            driver1.kill();
        }
    }

    @Test
    public void testSyncingActivities() throws Throwable {
        //Before running this test make sure that you have a tunnel (applangoqa1.cloudapp.net) from putty not shortcut, http://localhost:8090/managerservices/customer-manager/cm-rest/hello
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        FirefoxDriver driver2 = new FirefoxDriver();
        WebDriverWait wait2 = new WebDriverWait(driver2, 15);
        int numOfNewContact = 1;
        int numOfUpdateContact = 1;
        int numOfNewAccount = 1;
        int numOfUpdateAccount = 1;
        int numOfNewLeads = 1;
        int numOfUpdateLeads = 1;
        int numOfNewOpportunities = 1;
        int numOfUpdateOpportunities = 1;
        int login = 1;
        int totalActivities = numOfNewContact + numOfUpdateContact + numOfNewAccount + numOfUpdateAccount + numOfNewLeads + numOfUpdateLeads + numOfNewOpportunities + numOfUpdateOpportunities;
        int contactAppRankTotal = (numOfNewContact*salesforceRanks.CREATE.getValue() + numOfUpdateContact*salesforceRanks.UPDATE.getValue())*salesforceRanks.CONTACT.getValue();
        int accountAppRankTotal = (numOfNewAccount*salesforceRanks.CREATE.getValue() + numOfUpdateAccount*salesforceRanks.UPDATE.getValue())*salesforceRanks.ACCOUNT.getValue();
        int leadAppRankTotal = (numOfNewLeads*salesforceRanks.CREATE.getValue() + numOfUpdateLeads*salesforceRanks.UPDATE.getValue())*salesforceRanks.LEAD.getValue();
        int opportunityAppRankTotal = (numOfNewOpportunities*salesforceRanks.CREATE.getValue() + numOfUpdateOpportunities*salesforceRanks.UPDATE.getValue())*salesforceRanks.OPPORTUNITY.getValue();
        int appRankChange =  contactAppRankTotal + accountAppRankTotal + leadAppRankTotal + opportunityAppRankTotal;
        try {



            logger.info("Sync metrics ");
//            applangoToolsCommand.runSyncLogins();
            applangoToolsCommand.runSyncActivities();
            logger.info("Check a user appRank, activity");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            filterByDate(driver1, wait1, "2014", months.APRIL, "2014", months.APRIL);
            selectUserFromList(driver1, wait1, "Omer", "Ovadia");

            int appRankBeforeActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityBeforeActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
            logger.info("AppRank: " + appRankBeforeActivitiesInSF + " Activity: " + activityBeforeActivitiesInSF);

            logger.info("Open Salesforce and perform activities");
            genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver2, wait2);
            salesforceOpportunitiesActions.salesforcePerformActivitiesInOpportunities(sf, driver2, wait2, numOfNewOpportunities, numOfUpdateOpportunities);
            salesforceContactActions.salesforcePerformActivitiesInContacts(sf, driver2, wait2, numOfNewContact, numOfUpdateContact);
            salesforceAccountActions.salesforcePerformActivitiesInAccounts(sf, driver2, wait2, numOfNewAccount, numOfUpdateAccount);
            salesforceLeadActions.salesforcePerformActivitiesInLeads(sf, driver2, wait2, numOfNewLeads, numOfUpdateLeads);

            logger.info("Sync metrics again ");
//            applangoToolsCommand.runSyncLogins();
            applangoToolsCommand.runSyncActivities();

            logger.info("Compare appRank and activities");

            filterByDate(driver1, wait1, "2014", months.APRIL, "2014", months.APRIL);
            selectUserFromList(driver1, wait1, "Omer", "Ovadia");

            int appRankAfterActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityAfterActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
            int expectedAppRankAfterActivitiesInSF = appRankBeforeActivitiesInSF + appRankChange;
            int expectedActivitiesAfterActivitiesInSF = activityBeforeActivitiesInSF + totalActivities;

            logger.info("After performing  \nAppRank: " + appRankAfterActivitiesInSF + " Activity: " + activityAfterActivitiesInSF+"\n" +
                    "Should be \nAppRank: " + expectedAppRankAfterActivitiesInSF + " Activity: " + expectedActivitiesAfterActivitiesInSF);
            assertTrue(appRankAfterActivitiesInSF == expectedAppRankAfterActivitiesInSF);
            assertTrue(activityAfterActivitiesInSF == expectedActivitiesAfterActivitiesInSF);


        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Test failed " + ex.getMessage());
        }
        finally {
            driver1.kill();
            driver2.kill();
        }
    }

    @Test
    public void testDashboardFilterDate() throws ParserConfigurationException, SAXException, IOException {
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            filterByDate(driver1, wait1, "2013", months.SEPTEMBER, "2014", months.JANUARY);
            genericApplangoWebsiteActions.waitForUsersTableToLoad(wait1);

        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        }
        finally {
            driver1.kill();
        }
    }

    @Test
    public void testChangePassword() throws ParserConfigurationException, SAXException, IOException {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        Applango applango = getApplangoConfigurationXML();
        String currentPassword = applango.getPassword();
        String tooShortNewPassword = "Omer19";
        String onlyLettersPassword = "OmerOvadia";
        String onlyNumbersPassword = "123456789";
        String validNewPassword = applango.getPassword();
        String emptyPassword = "";

        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait);
            genericApplangoWebsiteActions.openUserAccount(driver1, wait);
            genericApplangoWebsiteActions.clickOnChangePassword(driver1, wait);

            logger.info(" Entering password shorter than 8 digits");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, tooShortNewPassword, tooShortNewPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_TOO_SHORT);

            logger.info(" Entering not matching password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, validNewPassword, validNewPassword + "1");
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_NOT_MATCH);

            logger.info("Entering password that has letters only");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, onlyLettersPassword, onlyLettersPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            logger.info("Entering password that has numbers only");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, onlyNumbersPassword, onlyNumbersPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            logger.info("Entering empty new password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, emptyPassword, emptyPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_EMPTY);

            logger.info("Entering empty current password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, emptyPassword, validNewPassword, validNewPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_EMPTY_CURRENT_PASSWORD);

            logger.info("Entering valid new password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, currentPassword, validNewPassword, validNewPassword);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver1.kill();
        }
    }


    @Test
    public void testResetPassword() throws ParserConfigurationException, SAXException, IOException {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Gmail gmail = genericGmailWebsiteActions.getGmailConfigurationXML();
        FirefoxDriver driver = new FirefoxDriver();
        FirefoxDriver driver1 = new FirefoxDriver();
        FirefoxDriver driver2= new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        WebDriverWait wait2 = new WebDriverWait(driver2, 15);
        Applango applango = getApplangoConfigurationXML();
        String validUsername = applango.getUsername();
        String validPassword = applango.getPassword();
        long sleepTime = 26000;

        try {
            //Gmail Get Inbox
            logger.info("Open Gamil and get current inbox amount");
            loginToGmail(gmail, driver, wait);
            String amountOfMailBeforeResetPassword =  genericGmailWebsiteActions.getInboxLabel(driver);
            driver.kill();
            //Applango Recover Password
            logger.info("Recover Password via dashboard login page ");
            genericApplangoWebsiteActions.openDashboard(applango, driver1, wait1);
            genericApplangoWebsiteActions.clickOnForgotPassword(driver1, wait1);
            genericApplangoWebsiteActions.enterUsernameInForgotPasswordTextfield(driver1, validUsername);
            genericApplangoWebsiteActions.clickOnRecoverPassword(driver1, wait1);


            //Gmail click on link and set new password
            logger.info("Wait few seconds and click on Inbox label in order to refresh inbox and compare no. of mails in order to make sure mail received");
            loginToGmail(gmail, driver2, wait2);
            genericGmailWebsiteActions.checkNewMailRecieved(driver2, sleepTime, amountOfMailBeforeResetPassword);

            logger.info("Check that the mail is from applango with correct subject)");
            genericGmailWebsiteActions.checkMailSubjectIsResetPassword(driver2);
            genericGmailWebsiteActions.checkMailSenderIsApplango(driver2);

            logger.info("Enter into mail and click on link");
            genericGmailWebsiteActions.clickOnFirstMail(driver2, wait2);

            genericGmailWebsiteActions.waitForMailToLoad(wait2);
            driver2.findElement(By.xpath(gmailButtons.RESET_PASSWORD_LINK.getValue())).click();
//            genericGmailWebsiteActions.clickOnChangePasswordLink(driver2);

            logger.info("Set new Password in Change Password screen");
            String gmailWinID = controlAndSwitchWindow(driver2);
            genericApplangoWebsiteActions.checkEnterNewPasswordScreenLoaded(driver2, wait2);
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver2, validPassword, validPassword, validPassword, true);

            //TODO add click on set password
            logger.info("Verify pop up message and click on 'Yes'");
            controlAndSwitchWindow(driver2);
            genericApplangoWebsiteActions.verifyPasswordSuccessfulyChangedMessageAppear(driver2);

            //Applango login with new password
            logger.info("Login with new password");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());

            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver1.kill();
            driver2.kill();
            driver.kill();
        }
    }

    private void loginToGmail(Gmail gmail, FirefoxDriver driver2, WebDriverWait wait2) throws IOException {
        SeleniumTestBase.launchingWebsite(driver2, gmail.getUrl());
        genericGmailWebsiteActions.enterPassword(driver2, gmail.getPassword());
        genericGmailWebsiteActions.clickOnSignIn(driver2, wait2);
    }


    private void checkNoRecordCreatedInDB(Applango applango, String connection, DBCollection coll, DBObject dbObjectRecordQuery) {
        logger.info("Check that record created in DB table : " + connection);
        Assert.assertTrue(coll.getCount(dbObjectRecordQuery) == 0);
//        coll.find(dbObjectRecordQuery).next().get("customerId").toString().contains(applango.getUserForoAuth());
    }

//    public void testSyncingWithoutAuthenticationCredentials() throws Throwable {
//        Applango applango = getApplangoConfigurationXML();
//        FirefoxDriver driver1 = new FirefoxDriver();
//        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
//        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
//        Database dbProperties = getDatabaseConfigurationXML();
//        final String connection = dbTables.OAuth2Credentials.getValue().toString();
//
//        try {
//
//            DB db = connectToDB(dbProperties);
//            DBCollection coll = db.getCollection(connection);
//            DBObject dbObjectRecordQuery = mongoDB.removeRecordsFromDB(applango, coll);
//
//            String invalidClientKey = sf.getAccessToken() + "_JUNK";
//            String validClientKey = sf.getAccessToken();
//            String invalidClientSecret = sf.getClientSecret() + "_JUNK";
//            String validClientSecret = sf.getClientSecret();
//            String clientKey_prodBeta = "3MVG99qusVZJwhsn_xr.KeDe5wD_cIWJ_.Ih2ZXMxUMqWKgycZ_U8dc50kEb_hLjzfEnBeoKWMmjWsaeR97.4";
//            String clientSecret_prodBeta = "2634862500859149299";
//
//            String empty = null;
//            //Perform activities in salesforce
////            applangoToolsCommand.runSyncActivities("ultra5");
//
//            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1, true);
//            genericApplangoWebsiteActions.clickOnApplicationSettings(driver1, wait1);
//            genericApplangoWebsiteActions.verifyNoAuthenticationSet(driver1);
//            genericApplangoWebsiteActions.enterAuthentication(driver1, validClientKey, clientSecret_prodBeta);
//            genericApplangoWebsiteActions.clickOnAuthenticationSubmit(driver1, wait1);
//            genericApplangoWebsiteActions.clickOnAuthenticationLink(driver1, wait1);
//
//            String mainWinID = controlAndSwitchWindow(driver1);
//
//            genericSalesforceWebsiteActions.waitForLoginPageToLoad(wait1);
//            genericSalesforceWebsiteActions.enterCredentials(driver1, sf.getUsername(), sf.getPassword());
//            genericSalesforceWebsiteActions.clickOnSubmitCredentials(driver1);
//            genericApplangoWebsiteActions.waitForFailureAuthenticatedMessage(driver1, wait1);
//            driver1.close(); //Closes new window that opened (sf)
//
//            driver1.switchTo().window(mainWinID);   //Switch back to applango
//            clickOnVerifyAuthentication(driver1);
////            genericApplangoWebsiteActions.waitForSuccessfulAccountAuthenticatedMessage(wait1);
//
//            mongoDB.checkRecordCreatedInDB(applango, connection, coll, dbObjectRecordQuery);
//
//        }
//
//        catch (Exception ex) {
//            logger.error(ex.getMessage());
//            fail("Failed due to : " + ex.getMessage());
//        }
//        finally {
//            driver1.kill();
//        }
//    }

    @Test
    public void testRunner() throws Throwable {
        testChangePassword();
        testDashboardLogin();
        testEnteringValidOAuthenticationCredentials();
        testSyncingActivities();

    }


}

