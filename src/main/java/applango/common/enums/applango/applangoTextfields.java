package applango.common.enums.applango;

import applango.common.enums.jsonMaps;
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
public enum applangoTextfields {

    MAIN_LoginUsername{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("login.username.textfield.id").toString();


        }
    },
    MAIN_LoginPassword{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("login.password.textfield.id").toString();


        }
    },
    SearchLastName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.search.lastName.textfield.xpath").toString();


        }
    },
    CURRENT_PASSWORD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.change.password.current.password.xpath").toString();


        }
    },
    NEW_PASSWORD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.change.password.new.password.id").toString();


        }
    },
    CONFIRM_PASSWORD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.change.password.confirm.password.id").toString();


        }
    },
    APPLICATION_CLIENT_KEY{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.client.key.textfield.xpath").toString();


        }
    },
    APPLICATION_CLIENT_SECRET{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.client.secret.textfield.xpath").toString();


        }
    },
    FORGOT_PASSWORD_USERNAME{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.forgot.password.textfield.id").toString();


        }
    }





    ;

    public abstract String path() throws IOException;



    applangoTextfields() {
    }

    public String getValue() throws IOException {
        return path();
    }
}