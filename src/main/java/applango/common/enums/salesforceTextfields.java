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
public enum salesforceTextfields {
    ACCOUNT_AccountNameInTitle{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.title.accountName.xpath").toString();


        }
    },
   ACCOUNT_AccountNAME{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.textfield.accountName.id").toString();


        }
    },
    ACCOUNT_AccountStatus{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.record.status.xpath").toString();


        }
    }

    ;

    public abstract String path() throws IOException;



    salesforceTextfields() {
    }

    public String getValue() throws IOException {
        return path();
    }
}