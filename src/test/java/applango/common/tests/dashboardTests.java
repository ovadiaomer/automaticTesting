package applango.common.tests;

import applango.common.SeleniumTestBase;
import applango.common.enums.months;
import applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions;
import applango.common.services.beans.Applango;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions.getApplangoConfigurationXML;
import static applango.common.services.ApplangoWebsite.genericApplangoWebsiteActions.waitForDashboardLoginPageToLoad;

public class dashboardTests extends SeleniumTestBase{

    @Test
    public void testDasboardLogin() throws Exception {
        Applango applango = getApplangoConfigurationXML();
        logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        String validUsername = applango.getUsername();
        String validPassword = applango.getPassword();
        String invalidUsername = "notRealUsername";
        String invalidPassword = "notRealPassword";
        String empty = "";
        try {
            launchingWebsite(driver1, applango.getUrl());
            waitForDashboardLoginPageToLoad(wait);
            //Enter invalid credentials and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, invalidUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            //Enter invalid password and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, invalidPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForErrorMessage(driver1, wait);
            //Enter empty credentials and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, empty, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(driver1, wait);
            //Enter empty password and check login failed error message appears
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, empty);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(driver1, wait);
            //Enter Valid Credentials and check page loads
            genericApplangoWebsiteActions.enterCredentials(driver1, validUsername, validPassword);
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);


        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        finally {
            driver1.kill();
        }
    }


    @Test
    public void testFilterByDateAndSelectingUser() throws ParserConfigurationException, SAXException, IOException {

        FirefoxDriver driver1 = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver1, 15);
        try {

            Applango applango = getApplangoConfigurationXML();
            logger.info("********************************************* Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "*********************************************");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait);

            genericApplangoWebsiteActions.filterByDate(driver1, wait, "2013", months.AUGUST, null, months.FEBRUARY);
            genericApplangoWebsiteActions.selectUserFromList(driver1, wait, "Omer", "Ovadia");




//            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
//            sfUsageStatsManagerClient.setSfIntegrationServiceURL("https://applangoqa1.cloudapp.net:8090/sfintegration");
////            sfUsageStatsManagerClient.s
//
//
////            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
////            sfUsageStatsManagerClient.sync("http://applangoqa1.cloudapp.net:8090/sfintegration");
//            SyncProcessProgress spp = sfUsageStatsManagerClient.populateUsage("omerTest4");
        }
        catch (Exception ex) {
           logger.error(ex.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            driver1.kill();
        }

    }


}
