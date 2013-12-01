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

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.tab.xpath").toString();


        }
    },
    SOBJECTS_DATA{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.tab.xpath").toString();


        }
    },
    SETUP{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("setup.tab.url").toString();


        }
    };

    public abstract String path() throws IOException;



    salesforceTabs() {
    }

    public String getValue() throws IOException {
        return path();
    }
}