package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.requestType;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.objectMapper;
import applango.common.services.restAPI;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SanityTest extends SeleniumTestBase{
    WebDriver driver;
    Map<String, String> env;
    public static final String HOME_SITE = "home.site.string";
    public static final String LOG_IN_XPATH_UI_OBJECT = "login.button.xpath";
    public static final String USER_NAME_UI_OBJECT = "login.username.textfield.id";
    public static final String PASSWORD_UI_OBJECT = "login.password.textfield.id";
    public static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.xpath";
    public static final String MAIN_SCREEN_CSS = "main.css";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";

    @Test
    public void testLoginWihExistingUser() throws IOException {
        Map appObjectMapper = objectMapper.getAppObjectMap();
        Map configPropertiesMapper = objectMapper.getConfigProperties();
        String websiteAdress = appObjectMapper.get(HOME_SITE).toString();
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
            launchingWebsite(websiteAdress);
            clickOnLoginButton(wait);
            enterCredentials(userNameField, username, passwordField, password);
            clickOnSubmitCredentials();
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
    public void testInsertingToMongoDb() throws Exception {

        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        String json = "{'database' : 'mkyongDB','table' : 'hosting'," +
                "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";
        DBObject dbObject = (DBObject) JSON.parse(json);
        String json_new = "{'database' : 'mkyongDB1','table' : 'hosting'," +
                "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}";
        DBObject dbObjectNew = (DBObject) JSON.parse(json_new);

        String dbRecord = "{database1 : 'omerDB',table : 'Cars'}";
        DBObject dbObjectRecord = (DBObject) JSON.parse(dbRecord);

        List<DBObject> listOfDbObjects = new ArrayList<DBObject>();
        String collection = "OmerTest";

        DB db = mongoDB.connectToServer();
        mongoDB.insertToDB(db, collection, dbObjectRecord);
        listOfDbObjects = mongoDB.readFromDB(db, collection, dbObjectRecord, null);
        mongoDB.updateDB(db, collection, dbObjectRecord, dbObjectNew);
        mongoDB.deleteFromDB(db, collection, dbObjectRecord);

//        mongoDB.connectAndInsertToDB(collection, dbObject);
//        cursor =  mongoDB.connectAndReadFromDB(collection, dbObject, null);
//        mongoDB.connectAndUpdateDB(collection, dbObject, dbObjectNew);
//        mongoDB.connectAndDeleteFromDB(collection, dbObjectNew);


    }

    private void checkThatPageLoaded(String mainScreenCss, WebDriverWait wait) {
        logger.info("Checking page loaded ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(mainScreenCss)));
    }

    private void launchingWebsite(String websiteAdress) {
        logger.info("Launching " + websiteAdress);
        driver.navigate().to(websiteAdress);
    }

    private void clickOnSubmitCredentials() throws IOException {
        Map appObjectMapper = objectMapper.getAppObjectMap();
        String loginSubmit = appObjectMapper.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.xpath(loginSubmit)).click();
    }

    private void clickOnLoginButton(WebDriverWait wait) throws IOException {
        Map appObjectMapper = objectMapper.getAppObjectMap();
        String loginButton = appObjectMapper.get(LOG_IN_XPATH_UI_OBJECT).toString();
        logger.info("Clicking on login button (by xpath)" + loginButton);
        driver.findElement(By.xpath(loginButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("userName")));
    }

    private void enterCredentials(String userNameField, String username, String passwordField, String password) {
        logger.info("Entering credentials and pressing on login (username= " + username + ", password= " + password +")");
        driver.findElement(By.id(userNameField)).sendKeys(username);
        driver.findElement(By.id(passwordField)).sendKeys(password);
    }

    public static void main (String... args) throws Exception {
////        Map<String, String> env = new HashMap<String, String>();
////        env.put(HOME_SITE, "dst.applango.com");
////        StringWriter sw = new StringWriter();
////        mapper.writeValue(sw, env);
////        String s = sw.toString();
//
//        Map m = objectMapper.getConfigProperties();
////        Properties properties = new Properties();
//
////        SanityTest.class.getClassLoader().getResource("data/config.properties");
//
////        properties.load(new FileInputStream("C:\\app-project\\AutomaticTesting\\src\\main\\java\\applango\\common\\tests\\config1.properties"));
//
//
//        System.out.println(m.get("password").toString());

//        System.out.print(sw.toString());
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




    //    }
////        }
//// do nothing
////        } catch (IOException e) {
////            }
////                loggingWriter.close();
////            if (null != loggingWriter) {
////        try {
////        selenium.stop();
//    public static void tearDownBase() {
//    @After
//
//    }
////        System.out.println("here");
//////        selenium.st();
////        selenium = (LoggingSelenium) new LoggingDefaultSelenium(myProcessor);
////        myProcessor.setExcludedCommands(new String[] {});
////        LoggingCommandProcessor myProcessor = new LoggingCommandProcessor(new HttpCommandProcessor("localhost", 4444, "*firefox",OPENQA_URL), htmlFormatter);
////        htmlFormatter.setAutomaticScreenshotPath(screenshotsResultsPath);
////        htmlFormatter.setScreenShotBaseUri(this.SCREENSHOT_PATH + "/"); // has to be "/" as this is a URI
////        LoggingResultsFormatter htmlFormatter = new HtmlResultFormatter(loggingWriter,this.RESULT_FILE_ENCODING);
////
////        loggingWriter = LoggingUtils.createWriter(resultHtmlFileName, this.RESULT_FILE_ENCODING, true);
////
////        System.err.println("resultHtmlFileName=" + resultHtmlFileName);
////        final String resultHtmlFileName = resultsPath + File.separator + "sampleResultSuccess.html";
////
////        }
////            new File(screenshotsResultsPath).mkdirs();
////        if (!new File(screenshotsResultsPath).exists()) {
//    public void setUpBase() {

    @Test
    public void  testLoginToSystem() {

    }




}
