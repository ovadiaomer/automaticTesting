package applango.common.services.Applango;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.*;
import applango.common.enums.generic.applications;
import applango.common.enums.generic.jsonMaps;
import applango.common.enums.generic.months;
import applango.common.enums.salesforce.salesforceLicenses;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Applango;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class genericApplangoWebsiteActions  extends SeleniumTestBase{
    private static final String LOG_IN_XPATH_UI_OBJECT = "login.button.xpath";
    private static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.xpath";


    private static final String RECENT_XPATH= "recent.xpath";
    private static final String APPLANGO_ENVIRONMENT = "applangoEnvironment";
    private static Map configPropertiesMapper;

    public static void openDashboardAndLogin(Applango applango, FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        openDashboardAndLogin(applango, driver1, wait, false, false);
    }
    public static void openDashboardAndLogin(Applango applango, FirefoxDriver driver1, WebDriverWait wait, boolean isOAuthTest) throws IOException {
        openDashboardAndLogin(applango, driver1, wait, isOAuthTest, false);
    }




    public static void openDashboardAndLogin(Applango applango, FirefoxDriver driver1, WebDriverWait wait, boolean isOAuthTest, boolean isFirstLogin) throws IOException {
        openDashboard(applango, driver1, wait);
        if (isOAuthTest) {
            genericApplangoWebsiteActions.enterCredentials(driver1, applango.getUsername(), applango.getPassword());

        }
        else {
            genericApplangoWebsiteActions.enterCredentials(driver1, applango.getUsername(), applango.getPassword());
        }


            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait, isFirstLogin);

        waitUntilWaitForServerDissappears(wait);

    }

    public static void selectApplication(FirefoxDriver driver1, WebDriverWait wait, applications app) throws IOException {
        logger.info("Selecting application " + app);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.APPLICATION_DROP_DOWN.getValue())));
        driver1.findElement(By.id(applangoButtons.APPLICATION_DROP_DOWN.getValue())).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(app.getValue())));
        driver1.findElement(By.id(app.getValue())).click();
    }

    public static void openDashboard(Applango applango, FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        launchingWebsite(driver1, applango.getUrl());
        waitForDashboardLoginPageToLoad(wait);
    }


    public static Applango getApplangoConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        //Getting the value of applango dashboard parameter from config.properties file
        String applangoEnvironment = configPropertiesMapper.get(APPLANGO_ENVIRONMENT).toString();
        //Setting values applango-configuration.xml in applango object
        Applango applango = readFromConfigurationFile.getApplangoConfigurationFileByEnvironmentId(applangoEnvironment);
        return applango;
    }

    public static void enterCredentials(FirefoxDriver driver, String username, String password) throws IOException {

        logger.info("Entering credentials and pressing on login (username= " + username + ", password= " + password +")");
        driver.findElement(By.id(applangoTextfields.MAIN_LoginUsername.getValue().toString())).clear();
        driver.findElement(By.id(applangoTextfields.MAIN_LoginUsername.getValue().toString())).sendKeys(username);
        driver.findElement(By.id(applangoTextfields.MAIN_LoginPassword.getValue().toString())).clear();
        driver.findElement(By.id(applangoTextfields.MAIN_LoginPassword.getValue())).sendKeys(password);
    }

    public static void waitForDashboardLoginPageToLoad(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.LOGIN_SUBMIT.getValue().toString())));
    }
    public static void waitForUsersTableToLoad(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoObject.USERTABLE_ID.getValue().toString())));
    }

    public static void clickOnLoginButtonAndWaitForUserListToLoad(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        clickOnLoginButtonAndWaitForUserListToLoad(driver, wait, false);
    }


    public static void clickOnLoginButtonAndWaitForUserListToLoad(FirefoxDriver driver, WebDriverWait wait, boolean isFirstTime) throws IOException {

        clickOnLogin(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HEADER.getValue().toString())));
//        driver.navigate().refresh();
        if (!isFirstTime) {
//            selectApplication(driver, wait, applications.SALESFORCE);
            waitForUserListToLoad(wait);

        }
    }
    public static void clickOnLoginButtonAndWaitForErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {

        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_INCORRECT_PASSWORD_INCORRECT.getValue());
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_ERROR_EMPTY_USERNAME.getValue());
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_ERROR_EMPTY_PASSWORD.getValue());
    }

    public static void clickOnLogin(FirefoxDriver driver) throws IOException {
        SeleniumTestBase.logger.info("Clicking on login button (by id)");
        driver.findElement(By.id(applangoButtons.LOGIN_SUBMIT.getValue())).click();
    }


    private static void waitForCredentialsErrorMessage(FirefoxDriver driver, WebDriverWait wait, String errorMessage) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())));
        Assert.assertTrue(driver.findElement(By.id(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())).getText().equals(errorMessage));
    }



    private static void waitForUserListToLoad(WebDriverWait wait) throws IOException {
        waitUntilWaitForServerDissappears(wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HEADER.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.USERTABLE_ID.getValue().toString())));
        waitUntilWaitForServerDissappears(wait);

    }

    public static void waitUntilWaitForServerDissappears(WebDriverWait wait) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated((By.id("waitbox"))));
    }

    private static void waitForHistogramToLoad(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HISTOGRAM.getValue()))).isDisplayed();
    }




    public static void clickOnSubmitCredentials(FirefoxDriver driver) throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        String loginSubmit = appObjectMapper.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.xpath(loginSubmit)).click();
    }

    public static void filterByDate(FirefoxDriver driver1, WebDriverWait wait, String fromYear, months fromMonth, String untilYear, months toMonth) throws IOException {
        logger.info("Filter dates " + fromMonth + "/" + fromYear + " - " + toMonth + "/" + untilYear);
        driver1.findElementById(applangoDropdowns.FROM_YEAR.getValue().toString()).sendKeys(fromYear);
        driver1.findElementById(applangoDropdowns.FROM_MONTH.getValue().toString()).sendKeys(fromMonth.getValue().toString());
        driver1.findElementById(applangoDropdowns.UNTIL_YEAR.getValue().toString()).sendKeys(untilYear);
        driver1.findElementById(applangoDropdowns.UNTIL_MONTH.getValue().toString()).sendKeys(toMonth.getValue().toString());
        clickOnDateSearchButton(driver1, wait);
    }

    private static void clickOnDateSearchButton(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElementById(applangoButtons.DATE_SEARCH.getValue().toString()).click();
        waitForUserListToLoad(wait);
    }

    public static void selectUserFromList(FirefoxDriver driver1, WebDriverWait wait , String firstName, String lastName)  throws IOException {
        logger.info("Select user from list: " + firstName + " " + lastName );
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoTextfields.SearchLastName.getValue())));
        enterValueInSearchLastName(driver1, lastName);
        waitForUserListToLoad(wait);
        if (checkIfUserExistInList(driver1, firstName + " " + lastName)) {
            if (getNumberOfUsersInList(driver1) == 1 ) {
                clickOnRecordInTable(driver1, wait);
                waitForHistogramToLoad(wait);
            }
            else if (getNumberOfUsersInList(driver1) > 1) {
                clickOnRecordInTable(driver1, wait, firstName + " " + lastName);

            }

        }


    }

    private static void clickOnRecordInTable(FirefoxDriver driver1, WebDriverWait wait, String name) throws IOException {

        int userRowNumber = searchOnWhichRowInTableTheUserAppear(driver1, name);
        if (userRowNumber != 0) {
            clickOnRecordInTable(driver1, wait, userRowNumber);
        }
        else { //default -click on first record (0)
            clickOnRecordInTable(driver1, wait);
        }
    }

    private static int searchOnWhichRowInTableTheUserAppear(FirefoxDriver driver1, String name) throws IOException {
        //Return the number of row in user table, by cutting user until finding the user on index 0
        int lineCounter = 1;
        int lineBreak = driver1.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().indexOf("\n");
        int indexOfName = driver1.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().indexOf(name);
        String listOfUsers = driver1.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText();
        while (indexOfName != 0) {
            listOfUsers = listOfUsers.substring(lineBreak + 1, listOfUsers.length());
            lineCounter ++;
            lineBreak = listOfUsers.indexOf("\n");
            indexOfName = listOfUsers.indexOf(name);
        }
        return lineCounter;
    }

    public static void enterValueInSearchLastName(FirefoxDriver driver1, String lastName) throws IOException {
        driver1.findElement(By.id(applangoTextfields.SearchLastName.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.SearchLastName.getValue().toString())).sendKeys(lastName);
    }

    private static void clickOnRecordInTable(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).get(0).click();
        waitForHistogramToLoad(wait);
    }

    private static void clickOnRecordInTable(FirefoxDriver driver1, WebDriverWait wait, int recordNumber) throws IOException {
//        driver1.findElements(By.cssSelector("#usertabler tr")).get(recordNumber).click();
        driver1.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).get(recordNumber).click();
        waitForHistogramToLoad(wait);
    }

    public static int getNumberOfUsersInList(FirefoxDriver driver) throws IOException {
        //The number of users will calculate by size of user table / 33 (33 is the size of each column in table)
//        return driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getSize().getHeight() / 33;
        return driver.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).size();
    }

    public static boolean checkIfUserExistInList(FirefoxDriver driver, String name) throws IOException {
        return driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().contains(name);

    }

    public static void openUserAccount(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("Open user account");
        openUserMenu(driver1);
        clickOnAccount(driver1);
        waitForUserAccountPage(wait);

    }

    public static void logout(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("Logout");
        openUserMenu(driver1);
        clickOnLogout(driver1);
        driver1.navigate().refresh();

    }

    private static void clickOnAccount(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.ACCOUNT.getValue().toString())).click();
    }

    private static void clickOnLogout(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.LOGOUT.getValue().toString())).click();
    }

    private static void openUserMenu(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoDropdowns.USER_MENU.getValue().toString())).click();
    }

    private static void waitForUserAccountPage(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.ACCOUNT_TITLE.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.CHANGE_PASSWORD.getValue().toString())));
    }

    public static void clickOnChangePassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.CHANGE_PASSWORD.getValue().toString())).click();
        waitForForgotPasswordDialog(wait);


    }

    private static void waitForForgotPasswordDialog(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())));
    }


    public static void fillChangePasswordAndSubmit(FirefoxDriver driver1, String currentPassword, String newPassword, String confirmNewPassword) throws IOException {
        fillChangePasswordAndSubmit(driver1, currentPassword, newPassword, confirmNewPassword, false);
    }

    public static void fillChangePasswordAndSubmit(FirefoxDriver driver1, String currentPassword, String newPassword, String confirmNewPassword, boolean isResetPassword) throws IOException {
        driver1.findElement(By.id(applangoTextfields.NEW_PASSWORD.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.NEW_PASSWORD.getValue().toString())).sendKeys(newPassword);
        if (!isResetPassword) {
            driver1.findElement(By.id(applangoTextfields.CURRENT_PASSWORD.getValue().toString())).clear();
            driver1.findElement(By.id(applangoTextfields.CURRENT_PASSWORD.getValue().toString())).sendKeys(currentPassword);
            driver1.findElement(By.id(applangoTextfields.CONFIRM_PASSWORD.getValue().toString())).clear();
            driver1.findElement(By.id(applangoTextfields.CONFIRM_PASSWORD.getValue().toString())).sendKeys(confirmNewPassword);


        }
        else {
            driver1.findElement(By.id(applangoTextfields.CONFIRM_PASSWORD_RESET.getValue().toString())).clear();
            driver1.findElement(By.id(applangoTextfields.CONFIRM_PASSWORD_RESET.getValue().toString())).sendKeys(confirmNewPassword);
            driver1.findElement(By.id(applangoButtons.LOGIN_SUBMIT.getValue().toString())).click();

        }


        if (!isResetPassword) {
            clickOnSubmitChangePassword(driver1);

        }
    }

    private static void clickOnSubmitChangePassword(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.id(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())).click();
    }

    public static void checkChangePasswordMessage(SearchContext driver1, applangoMessages changePasswordMessage) throws IOException {
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.CHANGE_PASSWORD_MESSAGE.getValue().toString())).getText().contains(changePasswordMessage.getValue().toString()));
    }

    public static String getName(FirefoxDriver driver1) throws IOException {
//        int appRank =  0;
        return driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(0).getText();

//        return appRank;
    }
    public static int getAppRank(FirefoxDriver driver1) throws IOException {
        int appRank =  0;
        appRank = Integer.parseInt(driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(1).getText());

        return appRank;
    }

    public static int getActivity(FirefoxDriver driver1) throws IOException {
        int activity = 0;
//        String userRecord = getAppRankAndActivity(driver1);
//        int spaceSeperator = userRecord.indexOf(" ");
        activity = Integer.parseInt(driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(2).getText());

        return activity;
    }

    public static String getGroup(FirefoxDriver driver1) throws IOException {
        return driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(3).getText();

    }

    public static void clickOnApplicationSettings(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        driver1.findElement(By.xpath("/html/body/span/span/div[2]/div/span/div/div/img")).click();
//        driver1.findElement(By.cssSelector(applangoButtons.AUTHENTICATION_SETTINGS.getValue().toString())).click();
        waitForAuthenticationScreen(wait1);
    }

    private static void waitForAuthenticationScreen(WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())));
    }

    public static void verifyNoAuthenticationSet(FirefoxDriver driver1) throws IOException {
        Assert.assertTrue(driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).isEnabled());
    }

    public static void enterAuthentication(FirefoxDriver driver1, String accessToken, String clientSecret) throws IOException {
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).sendKeys(accessToken);
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).sendKeys(clientSecret);
    }

    public static void clickOnAuthenticationSubmit(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("click On Authentication Submit Button");
        driver1.findElement(By.xpath("/html/body/span/span/div[2]/div/div/div/button")).click();
//        driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).click();
        waitForAuthenticateLink(wait);
    }
    public static void clickOnAuthenticationLink(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("click On Authentication link ");
        driver1.findElement(By.id(applangoObject.AUTHENTICATION_CLICK_HERE_LINK.getValue().toString())).click();
        waitForAuthenticateVerify(wait);
    }

    private static void waitForAuthenticateVerify(WebDriverWait wait) throws IOException {
        logger.info("wait for verify button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.AUTHENTICATION_VERIFY.getValue().toString())));
    }

    private static void waitForAuthenticateLink(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_CLICK_HERE_LINK.getValue().toString())));
    }

    public static void waitForSuccessfulAuthenticatedMessage(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESS.getValue().toString())));
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESS.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_SUCCESSFUL.getValue().toString()));
    }

    public static void clickOnVerifyAuthentication(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.id(applangoButtons.AUTHENTICATION_VERIFY.getValue().toString())).click();
    }

    public static void waitForSuccessfulAccountAuthenticatedMessage(WebDriverWait wait1) throws IOException {

        logger.info("Wait for message: " + applangoMessages.AUTHENTICATION_SUCCESSFUL_IN_APPLICATION.getValue().toString());
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD.getValue().toString())));

//        Assert.assertTrue(driver1.findElement(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_SUCCESSFUL_IN_APPLICATION.getValue().toString()));
    }

    public static void clickOnRecoverPassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordRequestSent(wait);
    }

    private static void waitForRecoverPasswordRequestSent(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString()))).getText().contains(applangoMessages.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString());
    }

    public static void clickOnForgotPassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.FORGOT_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordButton(wait);
    }

    private static void waitForRecoverPasswordButton(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())));
    }

    public static void enterUsernameInForgotPasswordTextfield(FirefoxDriver driver1, String username) throws IOException {
        driver1.findElement(By.id(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())).sendKeys(username);
    }

    public static void waitForFailureAuthenticatedMessage(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_FAILURE.getValue().toString())));
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_FAILURE.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_FAILURE.getValue().toString()));

    }

    public static void checkEnterNewPasswordScreenLoaded(FirefoxDriver driver2, WebDriverWait wait2) throws IOException {
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoTextfields.NEW_PASSWORD.getValue().toString())));
        Assert.assertTrue(driver2.findElement(By.id(applangoObject.CHANGE_PASSWORD_MESSAGE.getValue().toString())).getText().contains(applangoMessages.RESET_PASSWORD_DEFAULT.getValue().toString()));
    }


    public static void verifyPasswordSuccessfulyChangedMessageAppear(FirefoxDriver driver2) throws IOException {
        Assert.assertTrue(driver2.findElement(By.id(applangoObject.RESET_PASSWORD_SUCCESSFULLY.getValue())).getText().equals(applangoMessages.RESET_PASSWORD_SUCCESSFULLY.getValue()));
        driver2.findElement(By.xpath(applangoButtons.SUCCESSFUL_RESET_PASSWORD_BUTTON.getValue())).click();

    }

    public static void filterLicenseType(FirefoxDriver driver1, WebDriverWait wait1, salesforceLicenses salesforceLicenseType) throws IOException {
        driver1.findElementById(applangoDropdowns.FILTER.getValue().toString()).sendKeys(salesforceLicenseType.getValue().toString());
        clickOnDateSearchButton(driver1, wait1);
    }

    public static void unselectRememberUsernameCheckbox(FirefoxDriver driver1) throws IOException {
        if (driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).isSelected()) {
            //un-select checkbox
            driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).click();
        }
    }

    public static void selectRememberUsernameCheckbox(FirefoxDriver driver1) throws IOException {
        if (!(driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).isSelected())) {
            //Select checkbox
            driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).click();
        }
    }

    public static void clickOnUpdateAlert(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Click on Update Alert  ");
        driver1.findElement(By.id(applangoButtons.UPDATE_ALERT.getValue())).click();
        waitUntilWaitForServerDissappears(wait1);
    }

    public static void setBoxNoLoginAlertThreshold(FirefoxDriver driver1, String alertThreshold) {
        logger.info("Set Alert Threshold : " + alertThreshold);
        driver1.findElement(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/input")).clear();
        driver1.findElement(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/input")).sendKeys(alertThreshold);

    }

    public static void clickSaveBoxNoLogin(FirefoxDriver driver1, WebDriverWait wait1) {
        logger.info("Save Alert Threshold ");

        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/span[1]")));
        driver1.findElement(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/span[1]")).click();
        genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait1);
    }

    public static boolean checkAlertTrigger(FirefoxDriver driver1) throws IOException {
        logger.info("Check alert trigger");
        return driver1.findElement(By.id(applangoObject.ACTIVE_ALERT.getValue())).getAttribute("class").contains("activealert");
    }
}
