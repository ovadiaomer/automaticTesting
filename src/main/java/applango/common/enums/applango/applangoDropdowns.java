package applango.common.enums.applango;

import applango.common.enums.generic.jsonMaps;
import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

public enum applangoDropdowns {


    FROM_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.from.year.dropdown.id").toString();
        }

    },
    UNTIL_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.until.year.dropdown.id").toString();
        }

    },
    FROM_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.from.month.dropdown.id").toString();
        }

    },
    UNTIL_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.until.month.dropdown.id").toString();
        }

    },
    FILTER{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.filter.dropdown.id").toString();
        }

    },
    USER_MENU{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.dropdown").toString();
        }

    },
    REPORT_FROM_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.fromYear.id").toString();
        }

    },
    REPORT_UNTIL_YEAR{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.toYear.id").toString();
        }

    },
    REPORT_FROM_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.fromMonth.id").toString();
        }

    },
    REPORT_UNTIL_MONTH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.toMonth.id").toString();
        }

    },
    REPORT_APP_NAME{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.appnames.id").toString();
        }

    },
    REPORT_NAME{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.reportnames.id").toString();
        }

    }




    ;

    public abstract String path() throws IOException;

    applangoDropdowns() {
    }

    public String getValue() throws IOException {
        return path();
    }
}