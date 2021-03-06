package applango.common.enums.applango;

import applango.common.enums.generic.jsonMaps;
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
            return applangoObjectMap.get("head.css.id").toString();


        }

    },
    APPLICATION_LINK{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("applicationPage.tab.xpath").toString();


        }

    },


    WAITBOX{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("waitbox.id").toString();


        }

    },
    HOME_PAGE_BITS{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("homePageBits.id").toString();


        }

    },

    HOME_PAGE_licenseAnalytics{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("homePage.licenseAnalytics.id").toString();


        }

    },
   HOME_PAGE_groupAnalytics{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("homePage.groupAnalytics.id").toString();


        }

    },

    HOME_PAGE_minimalUsers{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("homePage.minimalUsers.id").toString();


        }

    },


    REPORT_PAGE_TAB{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.tab.xpath").toString();


        }

    },
    PEOPLE_PAGE_TAB{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.tab.xpath").toString();


        }

    },
    INCORRECT_CREDENTIALS_ERRORMESSAGE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("login.message.incorrect.id").toString();
        }
    },

    USERTABLE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.usertable.id").toString();
        }
    },
    PEOPLEPAGE_USERDETAIL{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.userdetails.id").toString();
        }
    },
    PEOPLEPAGE_GRAPHROLLUP{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.graphRolledUp.id").toString();
        }
    },
    PEOPLEPAGE_USER_APP_TABLE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.usertable.css").toString();
        }
    },
    PEOPLEPAGE_USER_CHART{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.usertchart.id").toString();
        }
    },
    PEOPLEPAGE_USER_DETAILS{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("peoplePage.userdetails.id").toString();
        }
    },

    USERTABLER{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.usertabler.id").toString();
        }
    },
    USERTABLE_ID{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.table.id").toString();
        }
    },
    HISTOGRAM{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.histogram.id").toString();
        }
    },
    ACCOUNT_TITLE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.user.account.pagetitle.id").toString();
        }
    },
    CHANGE_PASSWORD_MESSAGE{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.change.password.message.id").toString();
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
            return salesforceObjectMap.get("dashboard.application.authenticate.link.id").toString();


        }
    },
    RESET_PASSWORD_SUCCESSFULLY{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.successful.reset.password.message.id").toString();


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
            return salesforceObjectMap.get("dashboard.recover.password.request.sent.label.id").toString();


        }
    },


    AUTHENTICATION_AUTHENTICATED_FAILURE{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.application.authenticated.failure.message.id").toString();


        }
    },
    AUTHENTICATION_AUTHENTICATED_SUCCESS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return salesforceObjectMap.get("dashboard.application.authenticated.success.message.id").toString();


        }
    },

    ACTIVE_ALERT{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("dashboard.activealert.id").toString();


        }
    },
    REPORT_DATA_HOLDER{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.dataholder.id").toString();


        }
    },
    REPORT_DATA_TABLE{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.dataholderinner.id").toString();


        }
    },
    REPORT_DATA_CHART{
        public String path() throws IOException {

            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.APPLANGO);
            return applangoObjectMap.get("reportPage.dataholderchart.id").toString();


        }
    };





    public abstract String path() throws IOException;



    applangoObject() {
    }

    public String getValue() throws IOException {
        return path();
    }
}