package applango.common.enums.gmail;

import applango.common.enums.jsonMaps;
import applango.common.services.Mappers.objectMapper;

import java.io.IOException;
import java.util.Map;

public enum gmailButtons {



    SIGN_IN{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return applangoObjectMap.get("gmail.signin.button.id").toString();
        }
    },

    INBOX{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return applangoObjectMap.get("gmail.Inbox.button.xpath").toString();
        }
    },

    FIRST_MAIL_SUBJECT{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return applangoObjectMap.get("gmail.firstMail.subject.xpath").toString();
        }
    },
    FIRST_MAIL_SENDER{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return applangoObjectMap.get("gmail.firstMail.sender.xpath").toString();
        }
    },
    RESET_PASSWORD_LINK{
        public String path() throws IOException {
            Map applangoObjectMap = objectMapper.getObjectMap(jsonMaps.GMAIL);
            return applangoObjectMap.get("gmail.mail.forgot.password.link.xpath").toString();
        }
    }














    ;

    public abstract String path() throws IOException;

    gmailButtons() {
    }

    public String getValue() throws IOException {
        return path();
    }
}