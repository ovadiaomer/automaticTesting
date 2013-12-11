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
    RECORD_STATUS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("record.status.xpath").toString();


        }
    },
    ACCOUNT_AccountStatus{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("record.status.xpath").toString();


        }
    },

    SOBJECT_USER{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.User.id").toString();


        }
    },
    SOBJECT_MOVEMENT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.Movement.id").toString();


        }
    },
    SOBJECT_NAME{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.name.id").toString();


        }
    },
    SOBJECT_SObjectNameInTitle{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.title.sObjects.xpath").toString();


        }
    },
    MAIN_LoginUsername{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.username.textfield.id").toString();


        }
    },
    MAIN_LoginPassword{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.password.textfield.id").toString();


        }
    },
    CustomOBJECT_Label{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.label.textfield.id").toString();


        }
    },
    CustomOBJECT_PluralLabel{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.plural.label.textfield.id").toString();


        }
    },
    CustomOBJECT_ObjectName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.object.name.textfield.id").toString();


        }
    },
    CustomOBJECT_RecordName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.record.name.textfield.id").toString();


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