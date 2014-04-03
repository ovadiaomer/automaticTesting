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
    },
    ACCOUNT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.account").toString();
        }
    },
    CHANGE_PASSWORD{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.xpath").toString();
        }
    },
    CHANGE_PASSWORD_SUBMIT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.submit.xpath").toString();
        }
    },
    AUTHENTICATION_SETTINGS{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.application.settings.button.xpath").toString();
        }
    },
    AUTHENTICATION_SUBMIT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.application.settings.submit.xpath").toString();
        }
    },
    AUTHENTICATION_VERIFY{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.application.verify.button.xpath").toString();
        }
    },
    FORGOT_PASSWORD_BUTTON{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.forgot.password.button.id").toString();
        }
    },
    RECOVER_PASSWORD_BUTTON{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.recover.password.button.id").toString();
        }
    },
    SUCCESSFUL_RESET_PASSWORD_BUTTON{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.successful.reset.password.message.close.xpath").toString();
        }
    }






    ;

    public abstract String path() throws IOException;

    applangoButtons() {
    }

    public String getValue() throws IOException {
        return path();
    }
}