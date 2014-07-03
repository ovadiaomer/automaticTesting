package applango.common.enums.applango;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public enum applangoMessages {


    ENTER_CREDENTIALS_ERROR_EMPTY_USERNAME("Please enter a user name"),
    ENTER_CREDENTIALS_ERROR_EMPTY_PASSWORD("Please enter your password"),
    ENTER_CREDENTIALS_INCORRECT_PASSWORD_INCORRECT("Your login attempt has failed. The username or password may be incorrect. Please contact the administrator at your company for help."),
    CHANGE_PASSWORD_INVALID_TOO_SHORT("Please pick a password of at least 8 characters"),
    CHANGE_PASSWORD_NOT_MATCH("The passwords do not match. Please try again."),
    CHANGE_PASSWORD_INVALID_LETTERS_AND_NUMBERS("Passwords must have at least one letter and one number"),
    CHANGE_PASSWORD_EMPTY("Please enter the new password you would like to have and try again"),
    CHANGE_PASSWORD_EMPTY_CURRENT_PASSWORD("Please enter your current password and try again."),
    CHANGE_PASSWORD_SUCCESSFULLY("Password successfully changed."),
    RESET_PASSWORD_DEFAULT("Please enter the new password"),
    RESET_PASSWORD_SUCCESSFULLY("Password successfully changed. Go to login page"),
    AUTHENTICATION_SUCCESSFUL("Congratulations! You have successfully authenticated with your Applango account."),
    AUTHENTICATION_SUCCESSFUL_IN_APPLICATION("Your account is authenticated"),
    AUTHENTICATION_FAILURE("There was an error, or you have denied authenticating your Applango account with your SaaS application."),
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