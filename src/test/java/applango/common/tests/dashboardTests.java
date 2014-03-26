package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.applangoMessages;
import applango.common.enums.database.dbTables;
import applango.common.enums.months;
import applango.common.services.Applango.applangoToolsCommand;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.Gmail.genericGmailWebsiteActions;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.Salesforce.salesforceContactActions;
import applango.common.services.beans.*;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import static applango.common.services.Applango.genericApplangoWebsiteActions.*;
import static applango.common.services.Salesforce.salesforceContactActions.updateContactNTimes;
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
            DBObject dbObjectRecordQuery = removeRecordsFromDB(applango, coll);

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

            checkRecordCreatedInDB(applango, connection, coll, dbObjectRecordQuery);
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
    public void testSyncingWithoutAuthenticationCredentials() throws Throwable {
        Applango applango = getApplangoConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait1 = new WebDriverWait(driver1, 15);
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        Database dbProperties = getDatabaseConfigurationXML();
        final String connection = dbTables.OAuth2Credentials.getValue().toString();

        try {

            DB db = connectToDB(dbProperties);
            DBCollection coll = db.getCollection(connection);
            DBObject dbObjectRecordQuery = removeRecordsFromDB(applango, coll);

            String invalidClientKey = sf.getAccessToken() + "_JUNK";
            String validClientKey = sf.getAccessToken();
            String invalidClientSecret = sf.getClientSecret() + "_JUNK";
            String validClientSecret = sf.getClientSecret();
            String empty = null;
            //Perform activities in salesforce
//            applangoToolsCommand.runSyncActivities("ultra5");

            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1, true);
            genericApplangoWebsiteActions.clickOnApplicationSettings(driver1, wait1);
            genericApplangoWebsiteActions.verifyNoAuthenticationSet(driver1);
            genericApplangoWebsiteActions.enterAuthentication(driver1, validClientKey, validClientSecret);
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

            checkRecordCreatedInDB(applango, connection, coll, dbObjectRecordQuery);

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
        int numOfUpdateContact = 2;
        int login = 1;
        int totalActivities = numOfNewContact + numOfUpdateContact + login;
        int appRankChange = (numOfNewContact*3 + numOfUpdateContact*2)*1 + login;
        try {

            logger.info("Sync metrics ");
            applangoToolsCommand.runSyncActivities();
            applangoToolsCommand.runSyncLogins();
            logger.info("Check a user appRank, activity");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            filterByDate(driver1, wait1, "2013", months.FEBRUARY, "2014", months.MARCH);
            selectUserFromList(driver1, wait1, "Omer", "Ovadia");

//            Actions action = new Actions(driver1);
//            String xpath = "/html/body/div[3]/div/div[3]/div[4]/div[2]/div[2]/svg/g/circle[14]";
//            String cssS = "html.ng-scope body.ng-scope div#pagecontent div#mainrow.ng-scope div#usersanddetails.ng-scope div#userdetails.showUserDetails div#userdetailstop div#userdetailschartscontainer svg.linechart g circle";
//            WebElement elem = driver1.findElement(By.cssSelector(cssS));
//            action.moveToElement(elem);
//            action.perform();

            int appRank = genericApplangoWebsiteActions.getAppRank(driver1);
            int activity = genericApplangoWebsiteActions.getActivity(driver1);
            logger.info("AppRank: " + appRank + " Activity: " + activity);

            performActivitiesInSalesforce(sf, driver2, wait2, numOfNewContact, numOfUpdateContact);

            logger.info("Sync metrics again ");
            applangoToolsCommand.runSyncActivities();
            applangoToolsCommand.runSyncLogins();

            filterByDate(driver1, wait1, "2013", months.FEBRUARY, "2014", months.MARCH);
            selectUserFromList(driver1, wait1, "Omer", "Ovadia");
            int appRank2 = genericApplangoWebsiteActions.getAppRank(driver1);
            int activity2 = genericApplangoWebsiteActions.getActivity(driver1);
            logger.info("After performing activities \nAppRank: " + appRank2 + " Activity: " + activity2);
            appRank += appRankChange;
            activity += totalActivities;
            logger.info("After performing  \nAppRank: " + appRank2 + " Activity: " + activity2+"\n" +
                    "Should be \nAppRank: " + appRank + " Activity: " + activity);


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

    private void performActivitiesInSalesforce(Salesforce sf, FirefoxDriver driver2, WebDriverWait wait2, int numOfNewContact, int numOfUpdateContact) throws IOException {
        logger.info("Open Salesforce");
        genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver2, wait2);
        //Create Contacts
        SalesforceContacts[] salesforceContact =  salesforceContactActions.create(driver2, wait2, numOfNewContact);
        //Update the Contact x times
        updateContactNTimes(driver2, wait2, salesforceContact[0], numOfUpdateContact);
    }


    private String controlAndSwitchWindow(FirefoxDriver driver1) {
        Set<String> windowId = driver1.getWindowHandles();    // get  window id of current window
        Iterator<String> it = windowId.iterator();

        String mainWinID = it.next();
        String  newAdwinID = it.next();

        driver1.switchTo().window(newAdwinID);
        return mainWinID;
    }

    private DBObject removeRecordsFromDB(Applango applango, DBCollection coll) {
        logger.info("Remove all records with customerId : " + applango.getCustomerForoAuth() );
        String json_Omer = "{'customerId' : '" + applango.getCustomerForoAuth() +"'}";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(json_Omer);
        coll.remove(dbObjectRecordQuery, WriteConcern.ACKNOWLEDGED);
        return dbObjectRecordQuery;
    }

    private void checkRecordCreatedInDB(Applango applango, String connection, DBCollection coll, DBObject dbObjectRecordQuery) {
        logger.info("Check that record created in DB table : " + connection);
        Assert.assertTrue(coll.getCount(dbObjectRecordQuery) == 1);
//        coll.find(dbObjectRecordQuery).next().get("customerId").toString().contains(applango.getUserForoAuth());
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
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, tooShortNewPassword, tooShortNewPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_TOO_SHORT);

            logger.info(" Entering not matching password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, validNewPassword, validNewPassword + "1");
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_NOT_MATCH);

            logger.info("Entering password that has letters only");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, onlyLettersPassword, onlyLettersPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            logger.info("Entering password that has numbers only");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, onlyNumbersPassword, onlyNumbersPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS);

            logger.info("Entering empty new password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, emptyPassword, emptyPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_EMPTY);

            logger.info("Entering empty current password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, emptyPassword, validNewPassword, validNewPassword);
            genericApplangoWebsiteActions.checkChangePasswordMessage(driver1, applangoMessages.CHANGE_PASSWORD_EMPTY_CURRENT_PASSWORD);

            logger.info("Entering valid new password");
            genericApplangoWebsiteActions.fillChangePasswordAndSubmit(driver1, wait, currentPassword, validNewPassword, validNewPassword);
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
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        Applango applango = getApplangoConfigurationXML();
        String validUsername = applango.getUsername();

        try {
            genericApplangoWebsiteActions.openDashboard(applango, driver1, wait);
            genericApplangoWebsiteActions.clickOnForgotPassword(driver1, wait);
            genericApplangoWebsiteActions.enterUsernameInForgotPasswordTextfield(driver1, validUsername);
            genericApplangoWebsiteActions.clickOnRecoverPassword(driver1, wait);


        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        } finally {
            driver1.kill();
        }
    }
}

