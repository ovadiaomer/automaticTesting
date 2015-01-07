package applango.common;

import applango.common.enums.generic.applications;
import applango.common.services.Applango.applangoToolsCommand;
import applango.common.services.Applango.genericApplangoWebsiteActions;
import applango.common.services.Box.genericBoxWebsiteActions;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class boxIntegrationTest  extends SeleniumTestBase {
    @Test
    @Ignore
    public void testSyncBoxActivities() throws Throwable {
        RemoteWebDriver driver1 = getRemoteWebDriver(DesiredCapabilities.chrome());
        RemoteWebDriver driver0 = getRemoteWebDriver(DesiredCapabilities.chrome());

        WebDriverWait wait = new WebDriverWait(driver0, getTimeout());
        WebDriverWait wait1 = new WebDriverWait(driver1, getTimeout());
//        Applango applango = getApplangoConfigurationXML();

        try {
            logger.info("Sync metrics and roll up");
            String customerId = "automationCustomer";
            applangoToolsCommand.syncBoxActivitiesAndRollup(customerId);

            logger.info("Login to Applango");
            genericApplangoWebsiteActions.openDashboardAndLogin(applango, driver1, wait1);
            genericApplangoWebsiteActions.selectApplication(driver1, wait1, applications.BOX);
            genericApplangoWebsiteActions.filterByDate(driver1, wait1, thisYear, thisMonth, thisYear, thisMonth);

            logger.info("Perform actions in Box");
            launchingWebsite(driver0, "https://app.box.com/login/");
            genericBoxWebsiteActions.loginToBox(driver0, wait);
            String newFolder =  genericBoxWebsiteActions.createNewFolder(driver0, wait);
            genericBoxWebsiteActions.deleteFolder(driver0, wait, newFolder);

            logger.info("Sync box metrics and get appRank, activities");
            int appRank = genericApplangoWebsiteActions.getAppRank(driver1);
            int activites = genericApplangoWebsiteActions.getActivity(driver1);
            applangoToolsCommand.syncBoxActivitiesAndRollup(customerId);
//            reloadDashboard(driver1, wait1);
            genericApplangoWebsiteActions.filterByDate(driver1, wait1, thisYear, thisMonth, thisYear, thisMonth);
            genericApplangoWebsiteActions.selectUserFromList(driver1, wait1, "Omer1", "OvadiaAutoBox");
            int appRankAfter = genericApplangoWebsiteActions.getAppRank(driver1);
            int activitesAfter = genericApplangoWebsiteActions.getActivity(driver1);
            int expectedActivities =  activites + 3;
            int expectedAppRank =  appRank + 4;
            logger.info("" +
                    "Before performing  \nAppRank: " +  appRank         + " Activity: " + activites+"\n" +
                    "After              \nAppRank: " +  appRankAfter    + " Activity: " + activitesAfter+"\n"+
                    "Expected           \nAppRank: " +    expectedAppRank + " Activity: " + expectedActivities);
            assertTrue(activitesAfter == expectedActivities);
            assertTrue(appRankAfter == expectedAppRank);
        }

        finally {
            driver0.close();
            driver1.close();

        }

    }
}
