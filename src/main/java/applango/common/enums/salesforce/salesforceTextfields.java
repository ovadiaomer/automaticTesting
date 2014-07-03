package applango.common.enums.salesforce;

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
public enum salesforceTextfields {
    ACCOUNT_AccountNameInTitle{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.title.accountName.xpath").toString();


        }
    },
    ACCOUNT_AccountNAME{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.textfield.accountName.id").toString();


        }
    },
    ACCOUNT_AccountWebsite{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.website.textfield.id").toString();


        }
    },
    ACCOUNT_AccountRegion{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.region.dropdown.id").toString();


        }
    },
    ACCOUNT_AccountCountry{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("accounts.country.dropdown.id").toString();


        }
    },
    CONTACT_ContactLastName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("contacts.lastName.textfield.id").toString();


        }
    },
    CONTACT_ContactAccountName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("contacts.accountName.textfield.id").toString();


        }
    },
    CONTACT_ContactCountry{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("contacts.country.dropdown.id").toString();


        }
    },
    LEAD_LeadLastName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.lastname.id").toString();


        }
    },
    LEAD_LeadCompany{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.company.id").toString();


        }
    },
    LEAD_LeadSource{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.leadSource.id").toString();


        }
    },
    LEAD_LeadRegion{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.region.dropdown.id").toString();


        }
    },
    LEAD_LeadCountry{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("leads.country.dropdown.id").toString();


        }
    },
    OPPORTUNITY_OpportunityName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.name.textfield.id").toString();


        }
    },
    OPPORTUNITY_OpportunityAccountName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.accountName.textfield.id").toString();


        }
    },
    OPPORTUNITY_OpportunityClosedDate{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.closedDate.textfield.id").toString();


        }
    },
    OPPORTUNITY_OpportunityType{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.type.dropdown.id").toString();


        }
    },
    OPPORTUNITY_OpportunityStage{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.stage.dropdown.id").toString();


        }
    },
    OPPORTUNITY_OpportunityProbability{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("opportunities.probability.textfield.id").toString();


        }
    },

    RECORD_STATUS{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("record.status.xpath").toString();


        }
    },
    ACCOUNT_AccountStatus{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("record.status.xpath").toString();


        }
    },

    SOBJECT_USER{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.User.id").toString();


        }
    },
    SOBJECT_MOVEMENT{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.Movement.id").toString();


        }
    },
    SOBJECT_NAME{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.textfield.name.id").toString();


        }
    },
    SOBJECT_SObjectNameInTitle{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("sObjects.title.sObjects.xpath").toString();


        }
    },
    MAIN_LoginUsername{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.username.textfield.id").toString();


        }
    },
    MAIN_LoginPassword{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("login.password.textfield.id").toString();


        }
    },
    CustomOBJECT_Label{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.label.textfield.id").toString();


        }
    },
    CustomOBJECT_PluralLabel{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.plural.label.textfield.id").toString();


        }
    },
    CustomOBJECT_ObjectName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.object.name.textfield.id").toString();


        }
    },
    CustomOBJECT_RecordName{
        public String path() throws IOException {

            Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
            return salesforceObjectMap.get("custom.objects.record.name.textfield.id").toString();


        }
    }

    ;

    public abstract String path() throws IOException;



    salesforceTextfields() {
    }

    public String getValue() throws IOException {
        return path();
    }
}