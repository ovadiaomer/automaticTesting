/*
package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.generic.jsonMaps;
import applango.common.enums.generic.requestType;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.RestApi.restAPI;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.beans.Salesforce;
import applango.common.services.clientManager.genericClientManagerActions;
import com.applango.beans.Customer;
import com.applango.rest.client.CustomerManagerClient;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static applango.common.services.Salesforce.genericSalesforceWebsiteActions.*;

*/
/*public class SanityTests extends SeleniumTestBase{
    WebDriver driver;
    public static final String HOME_SITE = "home.site.string";

    private static final String USER_NAME_UI_OBJECT = "launchWebsiteAndlogin.username.textfield.id";
    private static final String PASSWORD_UI_OBJECT = "launchWebsiteAndlogin.password.textfield.id";

    private static final String MAIN_SCREEN_CSS = "main.css";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    @Test
    public void testWebActionsWithRestAPI() throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        Map configPropertiesMapper = objectMapper.getConfigProperties();
        String websiteAddress = appObjectMapper.get(HOME_SITE).toString();
        String userNameField = appObjectMapper.get(USER_NAME_UI_OBJECT).toString();
        String passwordField = appObjectMapper.get(PASSWORD_UI_OBJECT).toString();
        String mainScreenCss = appObjectMapper.get(MAIN_SCREEN_CSS).toString();
        String password = configPropertiesMapper.get(PASSWORD).toString();
        String username = configPropertiesMapper.get(USER_NAME).toString();
        FirefoxDriver driver1 =  new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);

//        driver.manage().deleteAllCookies();

        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");

        try {
            List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
            restAPI.executeRequest(requestType.GET.getValue(), "http://www.google.com", null);
            restAPI.executeGetRequest("http://www.google.com");
            launchingWebsite(driver1, websiteAddress);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);
            enterCredentials(driver1, username, password);
            genericApplangoWebsiteActions.clickOnSubmitCredentials(driver1);
            checkThatPageLoaded(mainScreenCss, wait);
        }
        catch (Exception e) {
            System.err.println("----------Exception is: " + e.getMessage());
            logger.error("----------Exception is: " + e.getMessage());

        }
        finally {

        }
    }
    @Test
    public void testMongoDb() throws Exception {
        String json_externalObjectActivity = "{customerId : 'robc1.customer1', " +

                "extObjectType : 'Opportunity', " +
                "extUserId : '19', " +
                "extTimestamp : ISODate(2014-01-09T16:26:43.824Z), " +
                "extOperation : 'READ'}";
        MongoClient mongoClient = new MongoClient("204.232.247.134", 15101);
        DB db = mongoClient.getDB("qa-it");
        boolean auth = db.authenticate("applango-qa@applango.com", "!SaaSDud3!".toCharArray());


        String json_Omer = "{FirstName : 'Omer', FamilyName : 'Ovadia'," +
                "Age : 31, Sex : 'Male', Color : 'Blue'}";
    }



        @Test
        public void testSyncLogins() throws ParserConfigurationException, SAXException, IOException {
            logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
            Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
            FirefoxDriver driver1 = new FirefoxDriver();
            WebDriverWait wait = new WebDriverWait(driver1, 15);
            try  {


                genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver1, wait);
                genericSalesforceWebsiteActions.logout(driver1, wait);


                for (int i = 0; i < 399; i++) {
                    logger.info("Log-in No. " + i);
                    waitForLoginPageToLoad(wait);
                    enterCredentials(driver1, sf.getUsername(), sf.getPassword());
                    genericSalesforceWebsiteActions.clickOnSubmitCredentials(driver1);
                    waitForPageToLoad(wait);
                    genericSalesforceWebsiteActions.logout(driver1,wait);
                }

            }
            catch (Exception ex) {
                logger.error(ex.getMessage());
            }

            finally {
                driver1.kill();
            }

        }




    */
/*private void executeSyncUsers(Customer customer, SFUserManagerClient userManagerClient) throws Throwable {
        userManagerClient.setSfIntegrationServiceURL("http://localhost:8090/sfintegration");
        SyncProcessProgress syncResults = userManagerClient.syncUsers(customer.getCustomerId());
        SFUsageStatsManagerClient sf = new SFUsageStatsManagerClient();
        try {


            while (!(userManagerClient.getSyncProcessProgress(syncResults.getProcessId()).getProcessStatus().toString().equals("DONE")) &
                    !(userManagerClient.getSyncProcessProgress(syncResults.getProcessId()).getProcessStatus().toString().equals("DONE_WITH_ERROR"))) {
                Thread.sleep(3000);
                System.out.println("Process  " + userManagerClient.getSyncProcessProgress(syncResults.getProcessId()) + " is " + userManagerClient.getSyncProcessProgress(syncResults.getProcessId()).getProcessStatus());
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }


        System.out.println("Sync finished, Process:  " + syncResults.getProcessId() + " "  + userManagerClient.getSyncProcessProgress(syncResults.getProcessId()).getProcessStatus().toString());
    }*//*


//    private FirefoxDriver authenticateNewCustomer(FirefoxDriver driver1, Customer customer, SalesforceCredentials sfCredentials) throws IOException {
//        WebDriverWait wait;
//        logger.info("Authenticating  " + customer.getCustomerId());
//        driver1 = new FirefoxDriver();
//
//        wait = new WebDriverWait(driver1, 15);
//        logger.info("Open launchWebsiteAndlogin page - " + sf.getClientLogin() + " and Enter clientId");
//        driver1.get(sf.getClientLogin());
//        driver1.findElement(By.xpath("/html/body/form/input")).sendKeys(customer.getCustomerId());
////            driver1.findElement(By.xpath("/html/body/form/input")).sendKeys("testCust728");
//        driver1.findElement(By.xpath("/html/body/form/input[3]")).click();
//        assertTrue(driver1.findElement(By.xpath("/html/body/a")).isDisplayed());
//        driver1.findElement(By.xpath("/html/body/a")).click();
//        wait.until(ExpectedConditions.titleContains("salesforce.com"));
//
//        logger.info("Enter credentials in salesforce");
//        enterCredentials(driver1, sf.getUsername(), sf.getPassword());
//        genericSalesforceWebsiteActions.clickOnSubmitCredentials(driver1);
//        wait.until(ExpectedConditions.titleContains("Applango Authentication Completed"));
//        logger.info("Authentication Completed!");
//        return driver1;
//    }
//
//    private SalesforceCredentials addSalesforceCredentialsToNewCustomer(CustomerManagerClient client, Customer customer1) {
//
//
//        SalesforceCredentials sfCredentials = new SalesforceCredentials();
//        sfCredentials.setClientId(sf.getAccessToken());
//        sfCredentials.setClientSecret(sf.getClientSecret());
//        sfCredentials.setLoginURL(sf.getLoginUrl());
//        sfCredentials.setCustomerId(customer1.getCustomerId());
//        sfCredentials.setPassword(sf.getPassword());
//        sfCredentials.setSecurityToken(sf.getSecurityToken());
//        sfCredentials.setUsername(sf.getUsername());
//
//        logger.info("Adding credentials to customer " + customer1.getCustomerId());
//        client.addSalesforceCredentials(customer1.getCustomerId(), sfCredentials);
//        return sfCredentials;
//
//    }

    private Customer createNewRandomCustomer(CustomerManagerClient client, Customer customer) {
        String newCustomerId;
        newCustomerId = genericClientManagerActions.getRandomCustomer(client);
        customer.setCustomerId(newCustomerId);
        customer.setCustomerName(newCustomerId);
        //java -jar tools.jar -cmgr -c
        //Note, in case user already exist connection refused error will return (Port should be QA1 = 80, QA2 = 8080)

        logger.info("Creating a new client " + customer.getCustomerId());
        customer = client.createCustomer(customer);
        return customer;
    }

    public static void main (String... args) throws Exception {
    }


    @After
    public void tearDown() {
        logger.info("--------------------------- TearDown  " + Thread.currentThread().getStackTrace()[1].getClassName() + "---------------------------");

        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void setUp() {
        logger.info("++++++++++++++++ Setup  " + Thread.currentThread().getStackTrace()[1].getClassName() + "\"++++++++++++++++");

    }


*//*

}
*/
