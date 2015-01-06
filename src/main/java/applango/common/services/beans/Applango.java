package applango.common.services.beans;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 17/11/13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class Applango {
    String url;
    String username;
    String primaryEmail;

    String primaryEmailPassword;
    String password;
    String userForoAuth;
    String passwordForoAuth;

    public String getToolsJarServer() {
        return toolsJarServer;
    }

    public void setToolsJarServer(String toolsJarServer) {
        this.toolsJarServer = toolsJarServer;
    }

    String toolsJarServer;

    public String getPrimaryEmailPassword() {
        return primaryEmailPassword;
    }

    public void setPrimaryEmailPassword(String primaryEmailPassword) {
        this.primaryEmailPassword = primaryEmailPassword;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getCustomerForoAuth() {
        return customerForoAuth;
    }

    public void setCustomerForoAuth(String customerForoAuth) {
        this.customerForoAuth = customerForoAuth;
    }

    String customerForoAuth;

    public String getUserForoAuth() {
        return userForoAuth;
    }

    public void setUserForoAuth(String userForoAuth) {
        this.userForoAuth = userForoAuth;
    }

    public String getPasswordForoAuth() {
        return passwordForoAuth;
    }

    public void setPasswordForoAuth(String passwordForoAuth) {
        this.passwordForoAuth = passwordForoAuth;
    }

    String environment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

}
