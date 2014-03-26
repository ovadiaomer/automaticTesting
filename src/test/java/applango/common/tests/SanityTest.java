package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.jsonMaps;
import applango.common.enums.requestType;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.RestApi.restAPI;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.Salesforce.salesforceAccountActions;
import applango.common.services.Salesforce.salesforceContactActions;
import applango.common.services.Salesforce.salesforceLeadActions;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceAccounts;
import applango.common.services.beans.SalesforceContacts;
import applango.common.services.beans.SalesforceLeads;
import applango.common.services.clientManager.genericClientManagerActions;
import com.applango.beans.Customer;
import com.applango.beans.SalesforceCredentials;
import com.applango.beans.SyncProcessProgress;
import com.applango.rest.client.CustomerManagerClient;
import com.applango.rest.client.SFUsageStatsManagerClient;
import com.applango.rest.client.SFUserManagerClient;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.http.message.BasicNameValuePair;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static applango.common.services.Salesforce.genericSalesforceWebsiteActions.*;
import static applango.common.services.Salesforce.salesforceContactActions.updateContactNTimes;
import static applango.common.services.Salesforce.salesforceLeadActions.updateLeadNTimes;
import static org.junit.Assert.assertTrue;

public class SanityTest extends SeleniumTestBase{
    WebDriver driver;
    public static final String HOME_SITE = "home.site.string";

    private static final String USER_NAME_UI_OBJECT = "launchWebsiteAndlogin.username.textfield.id";
    private static final String PASSWORD_UI_OBJECT = "launchWebsiteAndlogin.password.textfield.id";

    private static final String MAIN_SCREEN_CSS = "main.css";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    Salesforce sf;
    CustomerManagerClient customerManagerClient;
    //TODO fix writing to log

    MongoCollection mongoCollection;
    Jongo jongo;
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



//        mongoCollection = jongo.getCollection("Customer");
//        mongoCollection.ensureIndex("{userId:1,customerId:1,appName:1,endDate:1}", "{unique:true}");
//        Customer customer = new Customer();
//        customer.setCustomerId("qaAutomatic");
//        customer.setCustomerName("qaAutomatic");
//        customer.setId("asdfsdfsdf");
//        mongoCollection.save(customer);



//        String json_Omer = "{FirstName : 'Omer', FamilyName : 'Ovadia'," +
//                "Age : 31, Sex : 'Male', Color : 'Blue'}";

        String json_externalObjectActivity = "{customerId : 'robc1.customer1', " +

                "extObjectType : 'Opportunity', " +
                "extUserId : '19', " +
                "extTimestamp : ISODate(2014-01-09T16:26:43.824Z), " +
                "extOperation : 'READ'}";
        MongoClient mongoClient = new MongoClient("204.232.247.134", 15101);
        DB db = mongoClient.getDB("qa-it");
        boolean auth = db.authenticate("applango-qa@applango.com", "!SaaSDud3!".toCharArray());
//        com.mongodb.DBCollection table = db.getCollection("externalObjectActivity");


//        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        String json_Omer = "{FirstName : 'Omer', FamilyName : 'Ovadia'," +
                "Age : 31, Sex : 'Male', Color : 'Blue'}";
//        mongoCollection dbObjectOmer = () JSON.parse(json_Omer);
//        String json_Becky = "{FirstName : 'Becky', FamilyName : 'Ovadia'," +
//                "Age : 27, Sex : 'Female', Color : 'Red'}";
//        com.mongodb.DBObject json_externalObjectActivity = (com.mongodb.DBObject) JSON.parse(json_Becky);
//        String json_Avigail = "{FirstName : 'Avigail', FamilyName : 'Ovadia'," +
//                "Age : 3, Sex : 'Female', Color : 'Pink'}";
//        com.mongodb.DBObject dbObjectAvigail = (com.mongodb.DBObject) JSON.parse(json_Avigail);
//        String json_Dor = "{FirstName : 'Dor', FamilyName : 'Amit'," +
//                "Age : 29, Sex : 'Male', Color : 'Black'}";
//        com.mongodb.DBObject dbObjectDor = (com.mongodb.DBObject) JSON.parse(json_Dor);



//        String json_new = "{'database' : 'mkyongDB1','table' : 'hosting'," +
//                "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}";
//        com.mongodb.DBObject dbObjectNew = (com.mongodb.DBObject) JSON.parse(json_new);

//
////        //Set the document to query for
//        String dbRecordToQuery = "{$or: [ {Age : {$gt: 30}}, {Sex : { $ne: 'Male' }} ] }";
//        DBObject dbObjectRecordToQuery = (DBObject) JSON.parse(dbRecordToQuery);
//
////        String dbRecordQuery = "{database : 'mkyongDB1',table : 'Cars'}";
////        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(dbRecordQuery);
//
//
//        List<DBObject> listOfDbObjects = new ArrayList<DBObject>();
//        String collection = "user";
//
//        DB db = mongoDB.connectToServer();
////        db.getCollection(collection).drop();
//        mongoDB.insertToDB(db, collection, dbObjectOmer);
//        mongoDB.insertToDB(db, collection, dbObjectBecky);
//        mongoDB.insertToDB(db, collection, dbObjectAvigail);
//        mongoDB.insertToDB(db, collection, dbObjectDor);
////        dbObjectRecordQuery = (DBObject) JSON.parse("{database : 'Omero',table : 'Cars'}");
////        mongoDB.insertToDB(db, collection, dbObjectRecordQuery);
//        listOfDbObjects = mongoDB.readFromDB(db, collection, dbObjectRecordToQuery, null);
//        mongoDB.updateDB(db, collection, dbObjectRecordToQuery, dbObjectNew);
//        mongoDB.deleteFromDB(db, collection, dbObjectRecordToQuery);
//
//
    }
    @Test
    public void testPerformActivitiesInSalesforce() throws Exception {



        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        genericSalesforceWebsiteActions.launchWebsiteAndlogin(sf, driver1, wait);
        try {
            //Create Contact
            int numberOfNewContacts = 1;
            int numberOfUpdateContacts = 2;
            int numberOfNewLeads = 1;
            int numberOfUpdateLeads = 2;
            int numberOfNewOpportunities = 1 ;
            int numberOfUpdateOpportunities = 34;
            int numberOfNewAccounts =  1;
            int numberOfUpdateAccounts = 55;


            //Create Contacts
            SalesforceContacts[] salesforceContact =  salesforceContactActions.create(driver1, wait, numberOfNewContacts);
            //Update the Contact x times
            updateContactNTimes(driver1, wait, salesforceContact[0], numberOfUpdateContacts);
            //Delete Contact
//            salesforceContactActions.delete(driver1, wait, salesforceContact);


            //Create Accounts
            SalesforceAccounts[] newAccounts = salesforceAccountActions.create(driver1, wait, numberOfNewAccounts);
            //Update Accounts
            salesforceAccountActions.updateAccountNTimes(driver1, wait, newAccounts[0], numberOfUpdateAccounts);
//                salesforceAccountActions.delete(driver1, wait, newAccounts);


//            Create leads
            SalesforceLeads[] salesforceLead = salesforceLeadActions.create(driver1, wait, numberOfNewLeads);
            //Update Lead
            updateLeadNTimes(driver1, wait, salesforceLead[0], numberOfUpdateLeads);


        }
        catch (Exception e) {
            logger.error(e.getMessage());

        }
        finally {
            driver1.kill();
        }

//        //Create SObject in salesforce - sObject (random objectName)
//        SalesforceSobjects[] sObject = salesforceSobjectsActions.createNewSobject(driver1, wait, 1);
//        //Create SObject sObjectToUpdate (not in salesforce) "Omer1201"
//        SalesforceSobjects sObjectToUpdate = new SalesforceSobjects();
//        sObjectToUpdate.setsObjectName("Omer1201");
//        sObjectToUpdate.setSalesforceSObjectMovement(salesforceSObjectMovement.UPDATE);
//        sObjectToUpdate.setUser(getUserLabel(driver1));
//        //Update sObject to sObjectToUpdate
//        salesforceSobjectsActions.updateSObject(driver1, wait, sObject[0], sObjectToUpdate);
//        //Delete updated sObjectToUpdate from salesforce
//        deleteRecordById(driver1, wait, sObjectToUpdate.getsObjectId());






    }


    //    @Test
//    public void testPerformActivitiesNetformx() throws Exception {
//        int numberOfNewAccounts = 1;
//        int numberOfUpdateAccounts = 18;
//        int numberOfNewContacts = 1;
//        int numberOfUpdateContacts = 12;
//        int numberOfNewLeads = 1;
//        int numberOfUpdateLeads = 12;
//        int numberOfNewOpportunities = 1;
//        int numberOfUpdateOpportunities = 11;
//        FirefoxDriver driver1 = new FirefoxDriver();
//        try {
//
//            logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
//            Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
//
//            WebDriverWait wait = new WebDriverWait(driver1, 15);
//
//            logger.info("Open website " + sf.getUrl().toString() + " and launchWebsiteAndlogin");
//            launchWebsiteAndlogin(sf, driver1, wait);
////            launchingWebsite(driver1, sf.getUrl().toString());
////
////            enterCredentials(driver1, sf.getUsername(), sf.getPassword());
//            //Since SF open screen after launchWebsiteAndlogin is different, wait fail
////            clickOnSubmitCredentialsForTesting(driver1);
//            navigateToUrl(driver1, wait, salesforceUrls.ACCOUNT.getValue());
//
//
//            //Create Contact
//            SalesforceContacts[] salesforceContact =  salesforceContactActions.create(driver1, wait, numberOfNewContacts);
//            //Update the Contact x times
//            updateContactNTimes(driver1, wait, salesforceContact[0], numberOfUpdateContacts);
//            //Delete Contact
//            salesforceContactActions.delete(driver1, wait, salesforceContact);
//
//
//
//            //Create Account
//            SalesforceAccounts[] newAccounts = salesforceAccountActions.create(driver1, wait, numberOfNewAccounts);
//            //Update Account x times
//            salesforceAccountActions.updateAccountNTimes(driver1, wait, newAccounts[0], numberOfUpdateAccounts);
//            //Delete Account
//            salesforceAccountActions.delete(driver1, wait, newAccounts);
//
//            //Create Lead
//            SalesforceLeads[] salesforceLead = salesforceLeadActions.create(driver1, wait, numberOfNewLeads);
//            //Update Lead
//            updateLeadNTimes(driver1, wait, salesforceLead[0], numberOfUpdateLeads);
//            //Delete Leads
//            salesforceLeadActions.delete(driver1, wait, salesforceLead);
//
//
//            //Create Opportunity
//            SalesforceOpportunities[] salesforceOpportunity = salesforceOpportunitiesActions.create(driver1, wait, numberOfNewOpportunities);
//            //Update Opportunity
//            updateOpportunityNTimes(driver1, wait, salesforceOpportunity[0], numberOfUpdateOpportunities);
//            //Delete Opportunity
//            salesforceOpportunitiesActions.delete(driver1, wait, salesforceOpportunity);
//
//
//        }
//        catch (Exception e) {
//            logger.error(e.getMessage());
//
//        }
//        finally {
//            driver1.kill();
//        }
//    }
    @Test
    public void testValidCustomerCreation() throws ParserConfigurationException, SAXException, IOException, Throwable {
        FirefoxDriver driver1 = null;
//        WebDriverWait wait = null;
        CustomerManagerClient client = new CustomerManagerClient();
        Customer customer = new Customer();
//        String newCustomerId;
        client.setManagerServicesURL("http://localhost:8090/managerservices");
        sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        SalesforceCredentials sfCredentials;
        SFUserManagerClient userManagerClient = new SFUserManagerClient();
        SFUsageStatsManagerClient userManagerstat = new SFUsageStatsManagerClient();
        SFUsageStatsManagerClient usageStatsManagerClient = new SFUsageStatsManagerClient();
        try {
            //If connection refused run applangoQa1 ssh tunnel from putty (not shortcut) TODO: Fix tunnel
            customer = createNewRandomCustomer(client, customer);
//
            sfCredentials = addSalesforceCredentialsToNewCustomer(client, customer);
            driver1 = authenticateNewCustomer(driver1, customer, sfCredentials);
//            executeSyncUsers(customer, userManagerClient);
            //TODO check users in DB
//            userManagerstat.
            SFUsageStatsManagerClient sFUsageStatsManagerClient = new SFUsageStatsManagerClient();
            sFUsageStatsManagerClient.syncLoginInfo(customer.getCustomerId(), null);
            //Todo install triggers

            //Todo get activities



        }
        catch (Exception ex)  {
            if (ex.getMessage().contains("500")) {
                logger.error("Failed to create customer since user exist- \n" + ex.getMessage());

            }

            else {
                logger.error(ex.getMessage());
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        finally {

                client.deleteCustomer(customer.getCustomerId());
                driver1.kill();
            }

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




    private void executeSyncUsers(Customer customer, SFUserManagerClient userManagerClient) throws Throwable {
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
    }

    private FirefoxDriver authenticateNewCustomer(FirefoxDriver driver1, Customer customer, SalesforceCredentials sfCredentials) throws IOException {
        WebDriverWait wait;
        logger.info("Authenticating  " + customer.getCustomerId());
        driver1 = new FirefoxDriver();

        wait = new WebDriverWait(driver1, 15);
        logger.info("Open launchWebsiteAndlogin page - " + sf.getClientLogin() + " and Enter clientId");
        driver1.get(sf.getClientLogin());
        driver1.findElement(By.xpath("/html/body/form/input")).sendKeys(customer.getCustomerId());
//            driver1.findElement(By.xpath("/html/body/form/input")).sendKeys("testCust728");
        driver1.findElement(By.xpath("/html/body/form/input[3]")).click();
        assertTrue(driver1.findElement(By.xpath("/html/body/a")).isDisplayed());
        driver1.findElement(By.xpath("/html/body/a")).click();
        wait.until(ExpectedConditions.titleContains("salesforce.com"));

        logger.info("Enter credentials in salesforce");
        enterCredentials(driver1, sf.getUsername(), sf.getPassword());
        genericSalesforceWebsiteActions.clickOnSubmitCredentials(driver1);
        wait.until(ExpectedConditions.titleContains("Applango Authentication Completed"));
        logger.info("Authentication Completed!");
        return driver1;
    }

    private SalesforceCredentials addSalesforceCredentialsToNewCustomer(CustomerManagerClient client, Customer customer1) {


        SalesforceCredentials sfCredentials = new SalesforceCredentials();
        sfCredentials.setClientId(sf.getAccessToken());
        sfCredentials.setClientSecret(sf.getClientSecret());
        sfCredentials.setLoginURL(sf.getLoginUrl());
        sfCredentials.setCustomerId(customer1.getCustomerId());
        sfCredentials.setPassword(sf.getPassword());
        sfCredentials.setSecurityToken(sf.getSecurityToken());
        sfCredentials.setUsername(sf.getUsername());

        logger.info("Adding credentials to customer " + customer1.getCustomerId());
        client.addSalesforceCredentials(customer1.getCustomerId(), sfCredentials);
        return sfCredentials;

    }

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



}
