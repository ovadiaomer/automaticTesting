package applango.common.services.Applango;

import applango.common.SeleniumTestBase;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.beans.Salesforce;
import com.applango.beans.SyncProcessProgress;
import com.applango.beans.UsageStatsAggregation;
import com.applango.rest.client.BoxIntegrationManagerClient;
import com.applango.rest.client.SFUsageStatsManagerClient;
import com.applango.services.UsageRollupManager;
import org.springframework.beans.factory.annotation.Autowired;

public class applangoToolsCommand   extends SeleniumTestBase {

    @Autowired
    UsageRollupManager usageRollupManager;

    public static void runRollUp() throws Throwable {
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        String customerId = sf.getClientId();

        rollUp(customerId, "America/Denver", "salesforce", UsageStatsAggregation.DAILY.toString());
    }

    public static void runRollUp(String appName) throws Throwable {
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        String customerId = sf.getClientId();
        rollUp(customerId, appName, "Israel", "DAILY");
    }

    private static void rollUp(String customerId, String zone, String appName, String period) throws Throwable {
        System.out.println("Rollup");
        int maximumWait = 20000;
        int napTime = 10;
        try {
                String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
                SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
                sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
                SyncProcessProgress spp =  sfUsageStatsManagerClient.rollupAppRankInfo(customerId, zone, appName, "DAILY", null);
//
            System.out.println("processId: " + spp.getProcessId());
            int processTime = 0;
            while (spp.getFinished() == null && processTime < maximumWait) {
                Thread.sleep(napTime);
                processTime += napTime;
                spp = sfUsageStatsManagerClient.getRollupProcessProgress(spp.getProcessId());
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
    public static void runSyncActivities() throws Throwable {

        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        String customerId = sf.getClientId();
        runSyncSFActivities(customerId);
    }
    public static void runSyncSFActivities(String customerId) throws Throwable {
        System.out.println("Sync Activities");
        int maximumWait = 20000;
        int napTime = 10;
        try {
            String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
            sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
            SyncProcessProgress spp = sfUsageStatsManagerClient.populateUsage(customerId);
//            sfUsageStatsManagerClient.syncLoginInfo(customerId, "salesforce");
            waitForProcessFinished(maximumWait, napTime, sfUsageStatsManagerClient, spp);

        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private static void waitForProcessFinished(int maximumWait, int napTime, SFUsageStatsManagerClient sfUsageStatsManagerClient, SyncProcessProgress spp) throws InterruptedException {
        System.out.println("processId: " + spp.getProcessId());

        int processTime = 0;
        while (spp.getFinished() == null && processTime < maximumWait) {
            Thread.sleep(napTime);
            processTime += napTime;
            spp = sfUsageStatsManagerClient.getSyncProcessProgress(spp.getProcessId());
        }
    }

    public static void runSyncLogins() throws Throwable {
        System.out.println("Sync Logins");
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        int maximumWait = 20000;
        int napTime = 10;
        String customerId = sf.getClientId();
        String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
        try {

            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
            sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
            SyncProcessProgress spp = sfUsageStatsManagerClient.syncLoginInfo(customerId, null);
            System.out.println("processId: " + spp.getProcessId());

            int processTime = 0;
            while (spp.getFinished() == null && processTime < maximumWait) {
                Thread.sleep(napTime);
                processTime += napTime;
                spp = sfUsageStatsManagerClient.getSyncProcessProgress(spp.getProcessId());
            }

        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public static void syncBoxActivitiesAndRollup(String customerId) throws InterruptedException {
        logger.info("Sync box activities and rollup");
        int maximumWait = 20000;
        int napTime = 10;

        BoxIntegrationManagerClient boxIntegrationMgr = new BoxIntegrationManagerClient();
        String boxIntegrationServiceURL  = "http://localhost:8090/boxintegration";
        boxIntegrationMgr.setBoxIntegrationServiceURL(boxIntegrationServiceURL);
        SyncProcessProgress spp = boxIntegrationMgr.populateUsage(customerId);
        System.out.println("processId: " + spp.getProcessId());

        int processTime = 0;
        while (spp.getFinished() == null && processTime < maximumWait) {
            Thread.sleep(napTime);
            processTime += napTime;
            spp = boxIntegrationMgr.getSyncProcessProgress(spp.getProcessId());
        }

        spp = boxIntegrationMgr.rollupAppRankInfo(customerId, "America/Denver", "box", "DAILY", null);
        System.out.println("processId: " + spp.getProcessId());

        processTime = 0;
        while (spp.getFinished() == null && processTime < maximumWait) {
            Thread.sleep(napTime);
            processTime += napTime;
            spp = boxIntegrationMgr.getSyncProcessProgress(spp.getProcessId());
        }

    }
}
