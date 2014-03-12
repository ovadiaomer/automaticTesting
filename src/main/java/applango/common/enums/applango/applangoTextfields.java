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


    ;

    public abstract String path() throws IOException;



    applangoTextfields() {
    }

    public String getValue() throws IOException {
        return path();
    }
}