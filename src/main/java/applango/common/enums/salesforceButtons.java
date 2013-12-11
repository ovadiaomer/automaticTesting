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
    EDIT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("edit.button.xpath").toString();


        }
    },
    SAVE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.button.save.xpath").toString();


        }
    },
    LOGIN_BUTTON{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.button.id").toString();


        }
    },
    LOGIN_SUBMIT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.submit.button.id").toString();


        }
    },
    CUSTOM_OBJECT_NEW{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.new.button.xpath").toString();


        }
    },
    CUSTOM_OBJECT_EDIT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.edit.button.xpath").toString();


        }
    },
    CUSTOM_OBJECT_SAVE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.save.xpath").toString();


        }
    },
    CUSTOM_OBJECT_DELETE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.delete.button.xpath").toString();


        }
    },
    CUSTOM_OBJECT_UNDELETE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.undelete.button.xpath").toString();


        }
    },
    CUSTOM_OBJECT_POPUP_DELETE_CHECKBOX{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.popup.delete.checkbox.id").toString();
        }
    },
    CUSTOM_OBJECT_POPUP_DELETE_BUTTON{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.popup.delete.button.xpath").toString();
        }
    },
    CUSTOM_OBJECT_ALLOW_REPORTS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.optional.features.allow.report.checkbox.id").toString();


        }
    },


    CUSTOM_OBJECT_ALLOW_ACTIVITIES{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.optional.features.allow.activities.checkbox.id").toString();


        }
    },
    CUSTOM_OBJECT_TRACK_FIELD_HISTORY{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.optional.features.allow.track.field.history.checkbox.id").toString();


        }
    },
    CUSTOM_OBJECT_DEPLOY{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.deployment.status.deployed.checkbox.id").toString();


        }
    };

    public abstract String path() throws IOException;



    salesforceButtons() {
    }

    public String getValue() throws IOException {
        return path();
    }
}