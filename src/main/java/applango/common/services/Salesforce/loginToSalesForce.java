package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 17/11/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class loginToSalesForce extends SeleniumTestBase {
    WebDriver driver;


    @Before
    public void setUp() {
        logger.info("++++++++++++++++ Setup  " + Thread.currentThread().getStackTrace()[1].getClassName() + "\"++++++++++++++++");

    }

    @After
    public void tearDown() {
        logger.info("--------------------------- TearDown  " + Thread.currentThread().getStackTrace()[1].getClassName() + "---------------------------");

        if (driver != null) {
            driver.quit();
        }
    }


}
