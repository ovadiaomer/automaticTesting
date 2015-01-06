package applango.common;

//import org.apache.commons.logging.impl.Log4JLogger;

import applango.common.enums.generic.months;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Applango;
import applango.common.services.beans.Database;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static applango.common.services.Applango.genericApplangoWebsiteActions.getApplangoConfigurationXML;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;

public class SeleniumTestBase {
    public static Database dbProperties;
    public static DB db;
    public static int timeOut = getTimeout();
    public static String thisYear = "2015";
    public static months thisMonth = months.JANUARY;
    public static Applango applango;
    public static final Logger logger = LoggerFactory.getLogger(SeleniumTestBase.class);

    private static Map configPropertiesMapper;


    public static RemoteWebDriver getRemoteWebDriver(DesiredCapabilities capabilities) throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
        return new RemoteWebDriver(new URL("http://selenium-server.cloudapp.net:4444/wd/hub"), capabilities);
    }

    public static RemoteWebDriver getRemoteWebDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = getDesiredCapabilities();
        return getRemoteWebDriver(capabilities);
    }

    public static DesiredCapabilities getDesiredCapabilities() {
        return DesiredCapabilities.chrome();
    }


    @Before
    public void setup() throws IOException, ParserConfigurationException, SAXException {
        dbProperties = getDatabaseConfigurationXML();
        db = connectToDB(dbProperties);
        applango = getApplangoConfigurationXML();

    }

    public static RemoteWebDriver getRemoteDriver() throws MalformedURLException {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.LINUX);
        capabilities.setVersion("3.6");
        RemoteWebDriver driver1 = new RemoteWebDriver(new URL("http://applangoqa4.cloudapp.net:4444/wd/hub"), capabilities);

        return driver1;
    }

    public static FirefoxDriver getFirefoxDriver() {
        return new FirefoxDriver();
    }


    public static int getTimeout() {
        try {

            configPropertiesMapper = objectMapper.getConfigProperties();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            fail("Failed due to : " + ex.getMessage());
        }
        return Integer.parseInt(configPropertiesMapper.get("timeout").toString());

    }

    public static Database getDatabaseConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        //Getting the value of database parameter from config.properties file
        String dbEnvironment = configPropertiesMapper.get("database").toString();
        //Setting values applango-configuration.xml in applango object
        Database db = readFromConfigurationFile.getDatabaseConfigurationFileByDbName(dbEnvironment);
        return db;
    }

    public static DB connectToDB(Database dbProperties) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(dbProperties.getUrl(), dbProperties.getPort());
        DB db = mongoClient.getDB(dbProperties.getDbName());
        boolean auth = db.authenticate(dbProperties.getUsername(), dbProperties.getPassword().toCharArray());
        mongoClient.setWriteConcern(WriteConcern.JOURNALED);
        return db;
    }


    @BeforeClass
    public static void setUpBase() throws ParserConfigurationException, SAXException, IOException {
        logger.info("+++++++++++++++++++++ SetUp Base +++++++++++++++++++++");

    }

    public static void launchingWebsite(WebDriver driver, String websiteAdress) {
        logger.info("Launching " + websiteAdress);
        driver.navigate().to(websiteAdress);

    }

    protected static void checkThatPageLoaded(String mainScreenCss, WebDriverWait wait) {
        logger.info("Checking page loaded ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(mainScreenCss)));
    }

    @AfterClass
    public static void tearDownBase() {
        logger.info("--------------------------- TearDown Base ---------------------------");

    }


    protected static String controlAndSwitchWindow(WebDriver driver1) {
        Set<String> windowId = driver1.getWindowHandles();    // get  window id of current window
        Iterator<String> it = windowId.iterator();

        String mainWinID = it.next();
        String  newAdwinID = it.next();

        driver1.switchTo().window(newAdwinID);
        return mainWinID;
    }
}
