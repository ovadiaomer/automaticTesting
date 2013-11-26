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
public enum salesforceButtons {
    NEW{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("new.button.xpath").toString();


        }
    },
 DELETE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("delete.button.xpath").toString();


        }
    },
    SAVE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.button.save.xpath").toString();


        }
    }

    ;

    public abstract String path() throws IOException;



    salesforceButtons() {
    }

    public String getValue() throws IOException {
        return path();
    }
}