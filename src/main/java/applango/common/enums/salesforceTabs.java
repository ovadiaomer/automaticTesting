package applango.common.enums;

import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceTabs {
    ACCOUNT{
        public String path() throws IOException {

//            String ACCOUNTS_TAB_XPATH= "accounts.tab.xpath";
            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.tab.xpath").toString();


        }
    },
    SETUP{
        public String path() throws IOException {

//            String ACCOUNTS_TAB_XPATH= "accounts.tab.xpath";
            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("setup.tab.xpath").toString();


        }
    };

    //    ACCOUNTS("Recent Accounts"),
//    CONTACTS("Recent Contacts");
//
//    private final String value;
    public abstract String path() throws IOException;



//    private salesforceTabs(String path) {
//        this.path = path;
//    }

    salesforceTabs() {
        //To change body of created methods use File | Settings | File Templates.
    }

    public String getValue() throws IOException {
        return path();
    }
}