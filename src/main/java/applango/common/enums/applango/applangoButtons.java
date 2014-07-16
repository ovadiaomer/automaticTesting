package applango.common.enums.applango;

import applango.common.enums.generic.jsonMaps;
import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

public enum applangoButtons {


    DATE_SEARCH{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.search.button.id").toString();
        }
    },
    LOGIN_SUBMIT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("login.submit.button.id").toString();
        }
    },
    LOGIN_REMEMBER_USERNAME_CHECKBOX{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("login.remember.username.id").toString();
        }
    },
    ACCOUNT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.account").toString();
        }
    },
    LOGOUT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.logout").toString();
        }
    },
    CHANGE_PASSWORD{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.new.password.button.id").toString();
        }
    },
    CHANGE_PASSWORD_SUBMIT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.submit.button.id").toString();
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
            return applangoObjectMap.get("dashboard.application.verify.button.id").toString();
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
    },
    APPLICATION_DROP_DOWN{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.application.dropdown.id").toString();
        }
    },
    UPDATE_ALERT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.account.updateAlert.id").toString();
        }
    },

    PEOPLE_PAGE_SEARCH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.searchUser.id").toString();
        }
    },

    REPORT_PAGE_SEARCH{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.updateStatsButton.id").toString();
        }
    },
    REPORT_PAGE_EXPORT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.export.id").toString();
        }
    },
    REPORT_PAGE_DOWNLOAD{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.download.id").toString();
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