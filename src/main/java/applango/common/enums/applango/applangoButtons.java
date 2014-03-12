package applango.common.enums.applango;

import applango.common.enums.jsonMaps;
import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

public enum applangoButtons {


    DATE_SEARCH{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.search.button.xpath").toString();
        }
    },
    LOGIN_SUBMIT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("login.submit.button.id").toString();
        }
    };

    public abstract String path() throws IOException;

    applangoButtons() {
    }

    public String getValue() throws IOException {
        return path();
    }
}