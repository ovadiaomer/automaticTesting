package applango.common;

//import org.apache.commons.logging.impl.Log4JLogger;

import applango.common.services.beans.Applango;
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

import static applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions.getApplangoConfigurationXML;

//import java.util.logging.Logger;

public class SeleniumTestBase {

    public static final Logger logger = LoggerFactory.getLogger(SeleniumTestBase.class);


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


}
