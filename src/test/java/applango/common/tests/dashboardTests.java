package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.*;
import applango.common.enums.database.dbTables;
import applango.common.enums.generic.applications;
import applango.common.enums.generic.months;
import applango.common.enums.salesforce.salesforceLicenses;
import applango.common.enums.salesforce.salesforceRanks;
import applango.common.services.Applango.applangoToolsCommand;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.Box.genericBoxWebsiteActions;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.Gmail.genericGmailWebsiteActions;
import applango.common.services.Salesforce.*;
import applango.common.services.beans.Applango;
import applango.common.services.beans.Database;
import applango.common.services.beans.Gmail;
import applango.common.services.beans.Salesforce;
import com.applango.services.UsageRollupManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static applango.common.enums.salesforce.salesforceLicenses.SALESFORCE;
import static applango.common.services.Applango.genericApplangoWebsiteActions.*;
import static applango.common.services.DB.mongo.mongoDB.countFailureLogins;
import static applango.common.services.DB.mongo.mongoDB.countSuccessfulLogins;
import static applango.common.services.Gmail.genericGmailWebsiteActions.loginToGmail;
import static com.thoughtworks.selenium.SeleneseTestBase.*;

//import com.applango.beans.Oauth2Credentials;

public class dashboardTests extends SeleniumTestBase{
    Database dbProperties;
    DB db;
    private int timeOutInSeconds() {
        return 155;
    }
    Applango applango = getApplangoConfigurationXML();

    @Autowired
    UsageRollupManager usageRollupManager;

    public dashboardTests() throws ParserConfigurationException, SAXException, IOException {

    }

    @Before
    public void setup() throws IOException, ParserConfigurationException, SAXException {
        dbProperties = getDatabaseConfigurationXML();
        db = connectToDB(dbProperties);
    }

    @Test
    public void testEnteringValidOAuthenticationCredentials() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        //Make sure customer and user for applango created
        //Set application  java -jar tools.jar -caimgr -dc automationCustomer,salesforce

        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 55);
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        final String connection = dbTables.OAuth2Credentials.getValue().toString();

        try {

            DBCollection coll = db.getCollection(connection);
            DBObject dbObjectRecordQuery = mongoDB.removeRecordsFromDB(applango, coll);

            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1, true, true);
            genericApplangoWebsiteActions.clickOnApplicationSettings(driver1, wait1);
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
    public void testDashboardLogin() throws Exception {
        Applango applango = getApplangoConfigurationXML();
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        FirefoxDriver driver1 = getFirefoxDriver();

        WebDriverWait wait = new WebDriverWait(driver1, timeOutInSeconds());
        String validUsername = applango.getUsername();
        String validPassword = applango.getPassword();
        String invalidUsername = "notRealUsername";
        String invalidPassword = "notRealPassword";
        String empty = "";
        try {
            DBCollection applangoUserLoginHistoryCollection = db.getCollection(dbTables.applangoUserLoginHistory.getValue().toString());
            int failureLogins = countFailureLogins(applango, applangoUserLoginHistoryCollection);
            int successfulLogins = countSuccessfulLogins(applango, applangoUserLoginHistoryCollection);

            launchingWebsite(driver1, applango.getUrl());
            waitForDashboardLoginPageToLoad(wait);

            logger.info("Enter invalid credentials and check login failed error message appears");
            genericApplangoWebsiteActions.enterCredentials(driver1, invalidUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            logger.info("Check no change in amount of successful, failure login");
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);

            logger.info("Enter invalid password and check login failed error message appears");
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            logger.info("Check no change in amount of successful logins, failure login added");
            failureLogins++;
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);

            logger.info("Enter empty credentials and check login failed error message appears");
            genericApplangoWebsiteActions.enterCredentials(driver1, empty, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(driver1, wait);
            logger.info("Check no change in amount of successful, failure login");
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);


            logger.info("empty password and check login failed error message appears");
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(driver1, wait);
            logger.info("Check no change in amount of successful logins, failure login added");
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);


            logger.info("Valid password and check login successes (Remember username checked)");
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername.toLowerCase(), validPassword);
            selectRememberUsernameCheckbox(driver1);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);
            logger.info("Check no change in amount of failure logins, successfil login added");
            successfulLogins++;
            genericApplangoWebsiteActions.logout(driver1, wait);
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);

            logger.info("Valid password and check login successes (Remember username un-checked)");
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername.toLowerCase(), validPassword);
            unselectRememberUsernameCheckbox(driver1);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);
            logger.info("Check no change in amount of failure logins, successfil login added");
            successfulLogins++;
            genericApplangoWebsiteActions.logout(driver1, wait);
            checkSuccessfulAndFailureLoginsCounts(applango, applangoUserLoginHistoryCollection, failureLogins, successfulLogins);
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
    public void testExcludeUsers() throws ParserConfigurationException, SAXException, IOException {

        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver = getFirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds());
        final String connection = dbTables.excludedUser.getValue().toString();
        DBCollection coll = db.getCollection(connection);

        try {
            BasicDBObject documentBuilder = new BasicDBObject("appName", "salesforce").
                    append("customerId", "automationCustomer").
                    append("externalId", "005b0000000w0ytAAA");
            //userId : zzzzchatteruser2888@applangoqa.com
            String lastName = "excludedUser";

            logger.info("Check that user is in [excludedUser] table");
            if(coll.count(documentBuilder) != 0) {
                genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver, wait);
                genericApplangoWebsiteActions.enterValueInSearchLastName(driver, lastName);
                logger.info("Check that user not exist in usertable");
                assertFalse(driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().contains(lastName));
            }
        }
        finally {
            driver.kill();
        }
    }

    @Test
    public void testGroupByLicenseType() throws ParserConfigurationException, SAXException, IOException {
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver = getFirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds());
        final String connection = dbTables.groupInfo.getValue().toString();
        DBCollection coll = db.getCollection(connection);
        String licenseType =  salesforceLicenses.FORCE.getValue().toString();
        try {
//            SFUserManagerClient sfUserMgr = new SFUserManagerClient();
//            SyncProcessProgress spp = sfUserMgr.syncGroupInfo("automationCustomer");
            BasicDBObject documentBuilder = new BasicDBObject("appName", "salesforce").
                    append("customerId", "automationCustomer").
                    append("externalId", "00520000003ClOvAAK");
            String lastName = "Hilsta";
            logger.info("Check that user is in [groupInfo] table with group 'Chatter Free'");
            if (coll.find(documentBuilder).next().get("groups").toString().toLowerCase().contains(licenseType.toLowerCase())) {
                genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver, wait);
                driver.manage().window().maximize();
                logger.info("Check that user exist in usertable when filtering by " + licenseType);
                genericApplangoWebsiteActions.filterLicenseType(driver, wait, salesforceLicenses.FORCE);
                genericApplangoWebsiteActions.enterValueInSearchLastName(driver, lastName);
                assertTrue(driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().contains(lastName));
                //check Groups field

                logger.info("Check that user not exist in usertable when filtering by " + salesforceLicenses.SYSTEM_ADMINISTRATOR.getValue());

                genericApplangoWebsiteActions.filterLicenseType(driver, wait, salesforceLicenses.SYSTEM_ADMINISTRATOR);
                genericApplangoWebsiteActions.enterValueInSearchLastName(driver, lastName);
                assertFalse(driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().contains(lastName));
            }
        }
        finally {
            driver.kill();
        }
    }

    @Test
    public void testSyncingSalesforceActivities() throws Throwable {
        //Before running this test make sure that you have a tunnel (applangoqa3.cloudapp.net) from putty not shortcut, http://localhost:8090/managerservices/customer-manager/cm-rest/hello

//        ApplangoCmdTool.main(new String[]{"-rollupmgr", "-r", "-cid", "automationCustomer"
//                , "-an", "salesforce", "-ag", "DAILY"});

        applangoToolsCommand.syncSFActivitiesLoginsAndRollup();

        Applango applango = getApplangoConfigurationXML();

        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, timeOutInSeconds());
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        FirefoxDriver driver2 = getFirefoxDriver();
        WebDriverWait wait2 = new WebDriverWait(driver2, timeOutInSeconds());
        int numOfNewContact = 1;
        int numOfUpdateContact = 1;
        int numOfNewAccount = 1;
        int numOfUpdateAccount = 1;
        int numOfNewLeads = 1;
        int numOfUpdateLeads = 1;
        int numOfNewOpportunities = 1;
        int numOfUpdateOpportunities = 1;
        int login = 1;
        int totalActivities = numOfNewContact + numOfUpdateContact + numOfNewAccount + numOfUpdateAccount + numOfNewLeads + numOfUpdateLeads + numOfNewOpportunities + numOfUpdateOpportunities + login;
        int contactAppRankTotal = (numOfNewContact*salesforceRanks.CREATE.getValue() + numOfUpdateContact*salesforceRanks.UPDATE.getValue())*salesforceRanks.CONTACT.getValue();
        int accountAppRankTotal = (numOfNewAccount*salesforceRanks.CREATE.getValue() + numOfUpdateAccount*salesforceRanks.UPDATE.getValue())*salesforceRanks.ACCOUNT.getValue();
        int leadAppRankTotal = (numOfNewLeads*salesforceRanks.CREATE.getValue() + numOfUpdateLeads*salesforceRanks.UPDATE.getValue())*salesforceRanks.LEAD.getValue();
        int opportunityAppRankTotal = (numOfNewOpportunities*salesforceRanks.CREATE.getValue() + numOfUpdateOpportunities*salesforceRanks.UPDATE.getValue())*salesforceRanks.OPPORTUNITY.getValue();
        int appRankChange =  contactAppRankTotal + accountAppRankTotal + leadAppRankTotal + opportunityAppRankTotal + login;
        try {
            logger.info("Sync metrics and roll up");
            applangoToolsCommand.syncSFActivitiesLoginsAndRollup();

            logger.info("Login to Applango");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.selectApplication(driver1, wait1, applications.SALESFORCE);


            logger.info("Open Salesforce and perform activities");
            genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver2, wait2);
            salesforceOpportunitiesActions.salesforcePerformActivitiesInOpportunities(sf, driver2, wait2, numOfNewOpportunities, numOfUpdateOpportunities);
            salesforceContactActions.salesforcePerformActivitiesInContacts(sf, driver2, wait2, numOfNewContact, numOfUpdateContact);
            salesforceAccountActions.salesforcePerformActivitiesInAccounts(sf, driver2, wait2, numOfNewAccount, numOfUpdateAccount);
            salesforceLeadActions.salesforcePerformActivitiesInLeads(sf, driver2, wait2, numOfNewLeads, numOfUpdateLeads);

            logger.info("Get appRank and Activities before sync");
            filterByDate(driver1, wait1, "2014", months.JULY, "2014", months.JULY);
            selectUserFromList(driver1, wait1, "Omer", "OvadiaAuto");

            int appRankBeforeActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityBeforeActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
            logger.info("AppRank before: " + appRankBeforeActivitiesInSF + " Activity before: " + activityBeforeActivitiesInSF);

            logger.info("Sync metrics again ");
            applangoToolsCommand.syncSFActivitiesLoginsAndRollup();

            logger.info("Compare appRank and activities");

            filterByDate(driver1, wait1, "2014", months.JULY, "2014", months.JULY);
//            selectUserFromList(driver1, wait1, "Omer", "OvadiaAuto");

            int appRankAfterActivitiesInSF = genericApplangoWebsiteActions.getAppRank(driver1);
            int activityAfterActivitiesInSF = genericApplangoWebsiteActions.getActivity(driver1);
            String userName = genericApplangoWebsiteActions.getName(driver1);
            int expectedAppRankAfterActivitiesInSF = appRankBeforeActivitiesInSF + appRankChange;
            int expectedActivitiesAfterActivitiesInSF = activityBeforeActivitiesInSF + totalActivities;

            logger.info("\nBefore performing \nAppRank: " + appRankBeforeActivitiesInSF + " Activity: " + activityBeforeActivitiesInSF +"\n" +
                    "After performing  \nAppRank: " + appRankAfterActivitiesInSF + " Activity: " + activityAfterActivitiesInSF+"\n" +
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
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        wait1.withTimeout(50, TimeUnit.SECONDS);
        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.filterLicenseType(driver1, wait1, SALESFORCE);
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
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, timeOutInSeconds());
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
//            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_SUCCESSFULLY);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver1.kill();
        }
    }

    @Test
    public void testResetPasswordEmailRecieved() throws ParserConfigurationException, SAXException, IOException {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Gmail gmail = genericGmailWebsiteActions.getGmailConfigurationXML();
        FirefoxDriver driver = getFirefoxDriver();
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        Applango applango = getApplangoConfigurationXML();
        String validUsername = applango.getUsername();
        long sleepTime = 13000;
        final String connection = dbTables.emailToken.getValue().toString();

        try {
            //Remove emailTokens
            logger.info("Connect to db table " + connection);
            DBCollection coll = db.getCollection(connection);
            mongoDB.removeRecordsFromDB(applango, coll);

            //Gmail Get Inbox
            logger.info("Open Gamil and get current inbox amount");
            loginToGmail(gmail, driver, wait);
            String amountOfMailBeforeResetPassword =  genericGmailWebsiteActions.getInboxLabel(driver);
            //Applango Recover Password
            logger.info("Recover Password via dashboard login page ");
            genericApplangoWebsiteActions.openDashboard(applango, driver1, wait1);
            genericApplangoWebsiteActions.clickOnForgotPassword(driver1, wait1);
            genericApplangoWebsiteActions.enterUsernameInForgotPasswordTextfield(driver1, validUsername);
            genericApplangoWebsiteActions.clickOnRecoverPassword(driver1, wait1);

            logger.info("Get new token from DB");
            String token = mongoDB.getSpecialToken(applango, coll);
            token = mongoDB.encodeToken(token);
            logger.info("Token is " + token);

            //Gmail click on link and set new password
            logger.info("Wait few seconds and click on Inbox label in order to refresh inbox and compare no. of mails in order to make sure mail received");
            genericGmailWebsiteActions.checkNewMailRecieved(driver, sleepTime, amountOfMailBeforeResetPassword);

        }
        catch (Exception ex) {
            logger.error(ex.getMessage());

            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver.kill();
            driver1.kill();
        }
    }


    @Test
    public void testResettingPasswordFromEmail() throws ParserConfigurationException, SAXException, IOException {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Gmail gmail = genericGmailWebsiteActions.getGmailConfigurationXML();
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        Applango applango = getApplangoConfigurationXML();
        String validPassword = applango.getPassword();
        String invalidPasswordNoDigits = "NoDigitsPwd";
        String invalidPasswordNoChars = "123456789";
        String invalidPasswordTooShort = "12345a";
        long sleepTime = 13000;
//        Database dbProperties = getDatabaseConfigurationXML();
        final String connection = dbTables.emailToken.getValue().toString();
        try {
            logger.info("Get new token from DB");
//            DB db = connectToDB(dbProperties);
            DBCollection coll = db.getCollection(connection);
            String token = mongoDB.getSpecialToken(applango, coll);
            token = mongoDB.encodeToken(token);
            logger.info("Token is " + token);

            logger.info("Try again to open mail and locate link..");
            loginToGmail(gmail, driver1, wait1);

            logger.info("Check that the mail is from applango with correct subject)");
            genericGmailWebsiteActions.checkMailSubjectIsResetPassword(driver1);
            genericGmailWebsiteActions.checkMailSenderIsApplango(driver1);
            genericGmailWebsiteActions.clickOnFirstMail(driver1, wait1);


            genericGmailWebsiteActions.waitForMailToLoad(wait1);
            genericGmailWebsiteActions.checkResetPasswordToken(driver1, token);
            genericGmailWebsiteActions.clickOnChangePasswordLink(driver1);

            logger.info("Set new Password in Change Password screen");
            String gmailWinID = controlAndSwitchWindow(driver1);
            genericApplangoWebsiteActions.checkEnterNewPasswordScreenLoaded(driver1, wait1);

            logger.info("Negative test - set password with invalid passwords");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, null, validPassword, validPassword + "A", true);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_NOT_MATCH);

            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, null, invalidPasswordNoChars, invalidPasswordNoChars, true);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, null, invalidPasswordNoDigits, invalidPasswordNoDigits, true);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, null, invalidPasswordTooShort, invalidPasswordTooShort, true);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_TOO_SHORT);

            logger.info("Set valid Password in Change Password screen");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, null, validPassword, validPassword, true);

            logger.info("Verify pop up message and click on 'Yes'");
            controlAndSwitchWindow(driver1);
            genericApplangoWebsiteActions.verifyPasswordSuccessfulyChangedMessageAppear(driver1);

            //Applango login with new password
            logger.info("Login with new password");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver1.kill();
        }
    }

    @Test
    public void testRunner() throws Throwable {
        testChangePassword();
        testDashboardLogin();
        testEnteringValidOAuthenticationCredentials();
        testSyncingSalesforceActivities();

    }


    @Test
    public void testAlertTrigger() throws Throwable {
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, timeOutInSeconds());

        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.openUserAccount(driver1 , wait1);
            String thresholdLow = "0";
            String thresholdHigh = "2000";

            genericApplangoWebsiteActions.setBoxNoLoginAlertThreshold(driver1, thresholdLow);
            clickSaveBoxNoLogin(driver1, wait1);
            genericApplangoWebsiteActions.clickOnUpdateAlert(driver1, wait1);
            driver1.navigate().refresh();
            genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait1);
            assertTrue(checkAlertTrigger(driver1));

            genericApplangoWebsiteActions.setBoxNoLoginAlertThreshold(driver1, thresholdHigh);
            clickSaveBoxNoLogin(driver1, wait1);
            genericApplangoWebsiteActions.clickOnUpdateAlert(driver1, wait1);
            driver1.navigate().refresh();
            genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait1);
            assertFalse(checkAlertTrigger(driver1));
        }
        finally {
            driver1.kill();
        }
    }
    private FirefoxDriver getFirefoxDriver() {
        return new FirefoxDriver();
    }


    @Test
    public void testSyncBoxActivities() throws Throwable {

        FirefoxDriver driver = new FirefoxDriver(DesiredCapabilities.firefox());
        FirefoxDriver driver1 = new FirefoxDriver(DesiredCapabilities.firefox());
//        driver = new SafariDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, timeOutInSeconds());
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        logger.info("Sync metrics and roll up");


        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {

            String customerId = "automationCustomer";
            applangoToolsCommand.syncBoxActivitiesAndRollup(customerId);

            logger.info("Login to Applango");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.selectApplication(driver1, wait1, applications.BOX);
            genericApplangoWebsiteActions.filterByDate(driver1, wait1, "2014", months.JULY, "2014", months.JULY);

            logger.info("Perform actions in Box");
            launchingWebsite(driver, "https://app.box.com/login/");
            genericBoxWebsiteActions.loginToBox(driver, wait);
            String newFolder =  genericBoxWebsiteActions.createNewFolder(driver, wait);
            genericBoxWebsiteActions.deleteFolder(driver, wait, newFolder);

            logger.info("Sync box metrics and get appRank, activities");
            int appRank = genericApplangoWebsiteActions.getAppRank(driver1);
            int activites = genericApplangoWebsiteActions.getActivity(driver1);
            applangoToolsCommand.syncBoxActivitiesAndRollup(customerId);
            driver.navigate().refresh();
            genericApplangoWebsiteActions.filterByDate(driver1, wait1, "2014", months.JULY, "2014", months.JULY);
            genericApplangoWebsiteActions.selectUserFromList(driver1, wait1, "Omer1", "OvadiaAutoBox");
            int appRankAfter = genericApplangoWebsiteActions.getAppRank(driver1);
            int activitesAfter = genericApplangoWebsiteActions.getActivity(driver1);
            int expectedActivities =  activites + 3;
            int expectedAppRank =  appRank + 4;
            logger.info("" +
                    "Before performing  \nAppRank: " +  appRank         + " Activity: " + activites+"\n" +
                    "After              \nAppRank: " +  appRankAfter    + " Activity: " + activitesAfter+"\n"+
                    "Expected           \nAppRank" +    expectedAppRank + " Activity: " + expectedActivities);
            assertTrue(activitesAfter == expectedActivities);
            assertTrue(appRankAfter == expectedAppRank);
        }

        finally {
            driver.kill();
            driver1.kill();

        }

    }
    @Test
    public void testPeoplePage()  throws Throwable {
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, timeOutInSeconds());
        String firstName = "omer";
        String lastName = "ovadia";
        String email = null;
        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);

            genericApplangoWebsiteActions.openPeoplePage(driver1, wait1);
            genericApplangoWebsiteActions.searchPeople(driver1, wait1, firstName, lastName, email, months.JULY, "2014", months.JULY, "2014");
            genericApplangoWebsiteActions.clickOnUserInPeopleTable(driver1, wait1);

//            genericApplangoWebsiteActions.clickOnUserInPeoplePageUserTabler(driver1, wait1);

        }
        finally {
            driver1.kill();
        }

    }

    @Test
    public void testReportPage() throws IOException {
        FirefoxDriver driver1 = getFirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, timeOutInSeconds());


        try {
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.openReportPage(driver1, wait1);

            selectReport(driver1, applangoReports.TotalObjectActivityForCompany);
            selectReportApplication(driver1, applications.SALESFORCE);

            filterReportsDates(driver1, months.JANUARY, "2013", months.JULY, "2014");
            clickOnReportSearch(driver1, wait1);

            genericApplangoWebsiteActions.clickOnReportDownload(driver1, wait1);
            genericApplangoWebsiteActions.clickOnReportExportCSV(driver1, wait1);


        }
        finally {
            driver1.kill();
        }
    }

}

