package applango.common;

//import org.apache.commons.logging.impl.Log4JLogger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import java.util.logging.Logger;

public class SeleniumTestBase {

    public static final Logger logger = LogManager.getLogger(SeleniumTestBase.class.getName());


    @BeforeClass
    public static void setUpBase() {
        logger.info("+++++++++++++++++++++ SetUp Base +++++++++++++++++++++");

    }

    protected static void enterCredentials(WebDriver driver, String userNameField, String username, String passwordField, String password) {
        logger.info("Entering credentials and pressing on login (username= " + username + ", password= " + password +")");
        driver.findElement(By.id(userNameField)).sendKeys(username);
        driver.findElement(By.id(passwordField)).sendKeys(password);
    }

    protected static void launchingWebsite(WebDriver driver, String websiteAdress) {
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
