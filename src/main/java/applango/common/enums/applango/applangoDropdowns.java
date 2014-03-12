package applango.common.enums.applango;

import applango.common.enums.jsonMaps;
import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

public enum applangoDropdowns {


    FROM_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.from.year.dropdown.xpath").toString();
        }

    },
    UNTIL_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.until.year.dropdown.xpath").toString();
        }

    },
    FROM_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.from.month.dropdown.xpath").toString();
        }

    },
    UNTIL_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.until.month.dropdown.xpath").toString();
        }

    };

    public abstract String path() throws IOException;

    applangoDropdowns() {
    }

    public String getValue() throws IOException {
        return path();
    }
}