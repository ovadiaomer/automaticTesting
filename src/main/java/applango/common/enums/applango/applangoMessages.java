package applango.common.enums.applango;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum applangoMessages {


        CHANGE_PASSWORD_INVALID_TOO_SHORT("Please pick a password of at least 8 characters and no more than 64"),
        CHANGE_PASSWORD_NOT_MATCH("The passwords do not match. Please try again."),
        CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS("Passwords must have at least one lowercase letter, one uppercase letter and a number"),
        CHANGE_PASSWORD_EMPTY("Please enter the new password you would like to have and try again"),
        CHANGE_PASSWORD_EMPTY_CURRENT_PASSWORD("Please enter your current password and try again."),
        AUTHENTICATION_SUCCESSFUL("Congratulations! You have successfully authenticated your Applango account with your SaaS application."),
        AUTHENTICATION_SUCCESSFUL_IN_APPLICATION("Your account is authenticated"),
        RECOVER_PASSWORD_REQUEST_SENT("Request sent. Check your email for a recovery link")


        ;

        private final String value;

        private applangoMessages(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }