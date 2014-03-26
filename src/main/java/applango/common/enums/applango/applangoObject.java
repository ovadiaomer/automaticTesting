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
public enum applangoObject {


    HEADER{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("head.css.xpath").toString();


        }

    },
    INCORRECT_CREDENTIALS_ERRORMESSAGE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("login.message.incorrect.xpath").toString();
        }
    },

    USERTABLE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.usertable.xpath").toString();
        }
    },
    HISTOGRAM{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.histogram.xpath").toString();
        }
    },
    ACCOUNT_TITLE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.account.pagetitle.xpath").toString();
        }
    },
    CHANGE_PASSWORD_MESSAGE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.message.xpath").toString();
        }
    },
    AUTHENTICATION_HEADER{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.application.settings.header").toString();
        }
    },
    AUTHENTICATION_CLICK_HERE_LINK{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.application.authenticate.link.xpath").toString();


        }
    },
    AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.application.authenticated.message.xpath").toString();


        }
    },
    AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.application.authenticated").toString();


        }
    },
    RECOVER_PASSWORD_REQUEST_SENT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.recover.password.request.sent.label.xpath").toString();


        }
    }





    ;




    public abstract String path() throws IOException;



    applangoObject() {
    }

    public String getValue() throws IOException {
        return path();
    }
}