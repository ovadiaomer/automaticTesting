package applango.common;

//import org.apache.commons.logging.impl.Log4JLogger;

import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Database;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import java.util.logging.Logger;

public class SeleniumTestBase {



    public static final Logger logger = LoggerFactory.getLogger(SeleniumTestBase.class);

    private static Map configPropertiesMapper;

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

    public static void launchingWebsite(FirefoxDriver driver, String websiteAdress) {
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


    protected static String controlAndSwitchWindow(FirefoxDriver driver1) {
        Set<String> windowId = driver1.getWindowHandles();    // get  window id of current window
        Iterator<String> it = windowId.iterator();

        String mainWinID = it.next();
        String  newAdwinID = it.next();

        driver1.switchTo().window(newAdwinID);
        return mainWinID;
    }
}
