package applango.common.enums.salesforce;

import applango.common.enums.generic.jsonMaps;
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
    LEAD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("lead.tab.xpath").toString();


        }
    },
    CONTACT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("contact.tab.xpath").toString();


        }
    },
    OPPORTUNITIES{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.tab.xpath").toString();


        }
    },
    CAMPAIGN{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("campaign.tab.xpath").toString();


        }
    },
    SOBJECTS_DATA{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.tab.xpath").toString();


        }
    }

    ;

    public abstract String path() throws IOException;



    salesforceTabs() {
    }

    public String getValue() throws IOException {
        return path();
    }
}