package applango.common.services.Applango;

import applango.common.SeleniumTestBase;
import applango.common.services.Salesforce.genericSalesforceWebsiteActions;
import applango.common.services.beans.Salesforce;
import com.applango.beans.SyncProcessProgress;
import com.applango.rest.client.SFUsageStatsManagerClient;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 13/03/14
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class applangoToolsCommand   extends SeleniumTestBase {


    public static void runSyncActivities() throws Throwable {
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        String customerId = sf.getClientId();
        runSyncActivities(customerId);
    }
//    public static void runSyncActivities() throws Throwable {
//        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
//        int maximumWait = 20000;
//        int napTime = 10;
//        String customerId = sf.getClientId();
//        String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
//        try {
//
//            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
//            sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
//            SyncProcessProgress spp = sfUsageStatsManagerClient.populateUsage(customerId);
//            sfUsageStatsManagerClient.syncLoginInfo(customerId, "salesforce");
//            System.out.println("processId: " + spp.getProcessId());
//
//            int processTime = 0;
//            while (spp.getFinished() == null && processTime < maximumWait) {
//                Thread.sleep(napTime);
//                processTime += napTime;
//                spp = sfUsageStatsManagerClient.getSyncProcessProgress(spp.getProcessId());
//            }
//
//        }
//        catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }
//    }
    public static void runSyncActivities(String customerId) throws Throwable {
//        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        int maximumWait = 20000;
        int napTime = 10;
//        String customerId = sf.getClientId();
        String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
        try {

            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
            sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
            SyncProcessProgress spp = sfUsageStatsManagerClient.populateUsage(customerId);
            sfUsageStatsManagerClient.syncLoginInfo(customerId, "salesforce");
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
    public static void runSyncLogins() throws Throwable {
        Salesforce sf = genericSalesforceWebsiteActions.getSalesforceConfigurationXML();
        int maximumWait = 20000;
        int napTime = 10;
        String customerId = sf.getClientId();
        String sfIntegrationServiceURL =  "http://localhost:8090/sfintegration";
        try {


            SFUsageStatsManagerClient sfUsageStatsManagerClient = new  SFUsageStatsManagerClient();
            sfUsageStatsManagerClient.setSfIntegrationServiceURL(sfIntegrationServiceURL);
//            SyncProcessProgress spp = sfUsageStatsManagerClient.populateUsage(customerId);
            SyncProcessProgress spp = sfUsageStatsManagerClient.syncLoginInfo(customerId, "salesforce");
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
}
