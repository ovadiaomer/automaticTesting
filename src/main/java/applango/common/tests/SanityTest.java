package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.*;
import applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.RestApi.restAPI;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.beans.Salesforce;
import applango.common.services.beans.SalesforceAccounts;
import applango.common.services.beans.SalesforceSobjects;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
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

public class SanityTest extends SeleniumTestBase{
    WebDriver driver;
    public static final String HOME_SITE = "home.site.string";

    private static final String USER_NAME_UI_OBJECT = "login.username.textfield.id";
    private static final String PASSWORD_UI_OBJECT = "login.password.textfield.id";

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
        driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);

        driver.manage().deleteAllCookies();

        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");

        try {

            restAPI.executeRequest(requestType.GET.getValue(), "http://www.google.com", null);
            restAPI.executeGetRequest("http://www.google.com");
            launchingWebsite(driver, websiteAddress);
            genericApplangoWebsiteActions.clickOnLoginButton(driver, wait);
            enterCredentials(driver, userNameField, username, passwordField, password);
            genericApplangoWebsiteActions.clickOnSubmitCredentials(driver);
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

        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        String json_Omer = "{FirstName : 'Omer', FamilyName : 'Ovadia'," +
                "Age : 31, Sex : 'Male', Color : 'Blue'}";
        DBObject dbObjectOmer = (DBObject) JSON.parse(json_Omer);
        String json_Becky = "{FirstName : 'Becky', FamilyName : 'Ovadia'," +
                "Age : 27, Sex : 'Female', Color : 'Red'}";
        DBObject dbObjectBecky = (DBObject) JSON.parse(json_Becky);
        String json_Avigail = "{FirstName : 'Avigail', FamilyName : 'Ovadia'," +
                "Age : 3, Sex : 'Female', Color : 'Pink'}";
        DBObject dbObjectAvigail = (DBObject) JSON.parse(json_Avigail);
        String json_Dor = "{FirstName : 'Dor', FamilyName : 'Amit'," +
                "Age : 29, Sex : 'Male', Color : 'Black'}";
        DBObject dbObjectDor = (DBObject) JSON.parse(json_Dor);



        String json_new = "{'database' : 'mkyongDB1','table' : 'hosting'," +
                "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}";
        DBObject dbObjectNew = (DBObject) JSON.parse(json_new);


//        //Set the document to query for
        String dbRecordToQuery = "{$or: [ {Age : {$gt: 30}}, {Sex : { $ne: 'Male' }} ] }";
        DBObject dbObjectRecordToQuery = (DBObject) JSON.parse(dbRecordToQuery);

//        String dbRecordQuery = "{database : 'mkyongDB1',table : 'Cars'}";
//        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(dbRecordQuery);


        List<DBObject> listOfDbObjects = new ArrayList<DBObject>();
        String collection = "OmerTest";

        DB db = mongoDB.connectToServer();
        db.getCollection(collection).drop();
        mongoDB.insertToDB(db, collection, dbObjectOmer);
        mongoDB.insertToDB(db, collection, dbObjectBecky);
        mongoDB.insertToDB(db, collection, dbObjectAvigail);
        mongoDB.insertToDB(db, collection, dbObjectDor);
//        dbObjectRecordQuery = (DBObject) JSON.parse("{database : 'Omero',table : 'Cars'}");
//        mongoDB.insertToDB(db, collection, dbObjectRecordQuery);
        listOfDbObjects = mongoDB.readFromDB(db, collection, dbObjectRecordToQuery, null);
        mongoDB.updateDB(db, collection, dbObjectRecordToQuery, dbObjectNew);
        mongoDB.deleteFromDB(db, collection, dbObjectRecordToQuery);


    }
    @Test
    public void testSalesforce() throws IOException, ParserConfigurationException, SAXException {
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);


        logger.info("Open website " + sf.getUrl().toString() + " and login");
        launchingWebsite(driver, sf.getUrl().toString());
        clickOnLoginButton(driver, wait);
        enterCredentials(driver, salesforceTextfields.MAIN_LoginUsername.getValue(), sf.getUsername(), salesforceTextfields.MAIN_LoginPassword.getValue(), sf.getPassword());
        clickOnSubmitCredentials(driver, wait);

        SalesforceSobjects[] sObject = createNewSobject(driver, wait, 1);
        SalesforceSobjects sObjectToUpdate = new SalesforceSobjects();
        sObjectToUpdate.setsObjectName("Omer1201");
        sObjectToUpdate.setSalesforceSObjectMovement(salesforceSObjectMovement.UPDATE);
        sObjectToUpdate.setUser(getUserLabel(driver));
        updateSObject(driver, wait, sObject[0], sObjectToUpdate);
        deleteRecordById(driver, wait, sObjectToUpdate.getsObjectId());

        openSetup(driver, wait);
        openTab(driver, salesforceTabs.SOBJECTS_DATA, wait);


        SalesforceAccounts[] newAccounts = createNewAccounts(driver, wait, 2);
        updateAccounts(driver, wait, newAccounts, "Omer2711-");
        deleteAccounts(driver, wait, newAccounts);

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
