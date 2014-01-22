package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.jsonMaps;
import applango.common.enums.requestType;
import applango.common.enums.salesforceTabs;
import applango.common.enums.salesforceTextfields;
import applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.RestApi.restAPI;
import applango.common.services.Salesforce.*;
import applango.common.services.beans.*;
import com.applango.beans.Customer;
import com.applango.rest.client.CustomerManagerClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static applango.common.services.Salesforce.genericSalesforceWebsiteActions.*;

//import com.mongodb.DB;
//import com.mongodb.DBObject;
//import com.mongodb.util.JSON;

public class SanityTest extends SeleniumTestBase{
    WebDriver driver;
    public static final String HOME_SITE = "home.site.string";

    private static final String USER_NAME_UI_OBJECT = "login.username.textfield.id";
    private static final String PASSWORD_UI_OBJECT = "login.password.textfield.id";

    private static final String MAIN_SCREEN_CSS = "main.css";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";


    @Test
    public void testCustomerCreation() throws Exception {

        CustomerManagerClient client = new CustomerManagerClient();
        client.setManagerServicesURL("http://localhost:8090/managerservices");
        Customer customer = new Customer();
        customer.setCustomerId("omer1TestAuto");
        customer.setCustomerName("omer1TestAuto");

        Customer customer2 = client.createCustomer(customer);
        System.out.println("This is customer: " + customer2);



    }

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
//            urlParameters.add(new BasicNameValuePair("customerId", "omer0801"));
//            restAPI.executePostRequest("http://localhost:8090/managerservices/customer-manager/cm-rest/customer", urlParameters);
            restAPI.executeRequest(requestType.GET.getValue(), "http://www.google.com", null);
            restAPI.executeGetRequest("http://www.google.com");
            launchingWebsite(driver1, websiteAddress);
            genericApplangoWebsiteActions.clickOnLoginButton(driver1, wait);
            enterCredentials(driver1, userNameField, username, passwordField, password);
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
//
//        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
//        String json_Omer = "{FirstName : 'Omer', FamilyName : 'Ovadia'," +
//                "Age : 31, Sex : 'Male', Color : 'Blue'}";
//        DBObject dbObjectOmer = (DBObject) JSON.parse(json_Omer);
//        String json_Becky = "{FirstName : 'Becky', FamilyName : 'Ovadia'," +
//                "Age : 27, Sex : 'Female', Color : 'Red'}";
//        DBObject dbObjectBecky = (DBObject) JSON.parse(json_Becky);
//        String json_Avigail = "{FirstName : 'Avigail', FamilyName : 'Ovadia'," +
//                "Age : 3, Sex : 'Female', Color : 'Pink'}";
//        DBObject dbObjectAvigail = (DBObject) JSON.parse(json_Avigail);
//        String json_Dor = "{FirstName : 'Dor', FamilyName : 'Amit'," +
//                "Age : 29, Sex : 'Male', Color : 'Black'}";
//        DBObject dbObjectDor = (DBObject) JSON.parse(json_Dor);
//
//
//
//        String json_new = "{'database' : 'mkyongDB1','table' : 'hosting'," +
//                "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}";
//        DBObject dbObjectNew = (DBObject) JSON.parse(json_new);
//
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
    public void testSalesforce() throws Exception {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);

        logger.info("Open website " + sf.getUrl().toString() + " and login");
        launchingWebsite(driver1, sf.getUrl().toString());
        //Login Button not appear in sandbox because enter credentials appears
        if (!sf.getEnvironment().contains("sandbox"))   {
            clickOnLoginButton(driver1, wait);

        }
        enterCredentials(driver1, salesforceTextfields.MAIN_LoginUsername.getValue(), sf.getUsername(), salesforceTextfields.MAIN_LoginPassword.getValue(), sf.getPassword());
        //Since SF open screen after login is different, wait fail
        if (!sf.getEnvironment().contains("sandbox"))   {
            clickOnSubmitCredentials(driver1, wait);


        }
        else {
            clickOnSubmitCredentialsForTesting(driver1, wait);
        }


//        SalesforceCustomObject[] customObject;
//        customObject = salesforceCustomObjectsActions.createNewCustomObject(driver, wait, 4);

//        customObject[0].setObjectId("01Ib0000000EeWu");
//        customObject[0].setTrigger(salesforceCustomObjectsActions.getTriggers(driver, wait, customObject[0]));
//        SalesforceCustomObject newCustomObject = new SalesforceCustomObject(customObject[0].getObjectName() + "-updated");
//        salesforceCustomObjectsActions.updateCustomObject(driver, wait, customObject[0], newCustomObject);
////        salesforceCustomObjectsActions.deleteCustomObject(driver, wait, customObject[0], salesforceButtons.CUSTOM_OBJECT_DELETE);
//



        for (int i=0; i <2; i++){
            SalesforceAccounts[] newAccounts = salesforceAccountActions.create(driver1, wait, 1);
            salesforceAccountActions.update(driver1, wait, newAccounts, "Test-");
            salesforceAccountActions.delete(driver1, wait, newAccounts);
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


        openTab((FirefoxDriver) driver, salesforceTabs.SOBJECTS_DATA, wait);




    }
    @Test
    public void testPerformActivitiesNetformx() throws Exception {
        FirefoxDriver driver1 = new FirefoxDriver();
        try {

            logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
            Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();

            WebDriverWait wait = new WebDriverWait(driver1, 15);

            logger.info("Open website " + sf.getUrl().toString() + " and login");
            launchingWebsite(driver1, sf.getUrl().toString());

            enterCredentials(driver1, salesforceTextfields.MAIN_LoginUsername.getValue(), sf.getUsername(), salesforceTextfields.MAIN_LoginPassword.getValue(), sf.getPassword());
            //Since SF open screen after login is different, wait fail
            clickOnSubmitCredentialsForTesting(driver1, wait);

            navigateToUrl(driver1, wait, "001Q000000oFFc2");

            //Create Lead
            SalesforceLeads[] salesforceLead =  salesforceLeadActions.create(driver1, wait, 1);
            //Update Lead
            for (int i=0; i<20; i++){
                waitForPageToLoad(wait);
                salesforceLeadActions.update(driver1, wait, salesforceLead[0], "TestL" + i);
                waitForPageToLoad(wait);
            }
            //Delete Lead
            genericSalesforceWebsiteActions.delete(driver1, wait, salesforceLead[0].getId());

            //Create Contact
            SalesforceContacts[] salesforceContact =  salesforceContactActions.create(driver1, wait, 1);
            //Update the Contact x times
            for (int i=0; i<20; i++){
                waitForPageToLoad(wait);
                salesforceContactActions.update(driver1, wait, salesforceContact[0], "TestC" + i);
                waitForPageToLoad(wait);
            }
            //Delete Contact
            genericSalesforceWebsiteActions.delete(driver1, wait, salesforceContact[0].getContactId());


            //Create Account
            SalesforceAccounts[] newAccounts = salesforceAccountActions.create(driver1, wait, 1);
            //Update Account x times
            for (int i=0; i<20; i++){
                waitForPageToLoad(wait);
                salesforceAccountActions.updateOne(driver1, wait, newAccounts[0], "TestA" + i);
                waitForPageToLoad(wait);
            }
            //Delete Account
            salesforceAccountActions.deleteOne(driver1, wait, newAccounts[0]);


            //Create Opportunity
            SalesforceOpportunities[] salesforceOpportunity = salesforceOpportunitiesActions.create(driver1, wait, 1);
            //Update Opportunity
            for (int i=0; i<20; i++){
                waitForPageToLoad(wait);
                salesforceOpportunitiesActions.update(driver1, wait, salesforceOpportunity[0], "TestOpp" + i);
                waitForPageToLoad(wait);
            }
            //Delete Opportunity
            genericSalesforceWebsiteActions.delete(driver1, wait, salesforceOpportunity[0].getOpportunityId());









        }
        catch (Exception e) {
            logger.error(e.getMessage());

        }
        finally {
            driver1.kill();
        }
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
