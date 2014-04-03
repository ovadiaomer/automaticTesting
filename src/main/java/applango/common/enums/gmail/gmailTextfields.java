package applango.common.enums.gmail;

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
public enum gmailTextfields {


    PASSWORD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return salesforceObjectMap.get("gmail.password.textfield.id").toString();


        }
    }





    ;

    public abstract String path() throws IOException;



    gmailTextfields() {
    }

    public String getValue() throws IOException {
        return path();
    }
}