package applango.common.enums;

import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 03/12/13
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public enum salesforceUrls {

    SETUP{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("setup.tab.url").toString();


        }
    },
    CONTACT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("contacts.tab.url").toString();


        }
    },
    ACCOUNT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("account.tab.url").toString();


        }
    },
    LEADS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.tab.url").toString();


        }
    },
    OPPORTUNITY{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.tab.url").toString();


        }
    },
    CUSTOM_OBJECTS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.url").toString();


        }
    };
    public abstract String path() throws IOException;

    salesforceUrls() {
    }

    public String getValue() throws IOException {
        return path();
    }
}

