package applango.common.services.Applango;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.*;
import applango.common.enums.generic.applications;
import applango.common.enums.generic.jsonMaps;
import applango.common.enums.generic.months;
import applango.common.enums.salesforce.salesforceLicenses;
import applango.common.services.DB.mongo.mongoDB;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Applango;
import com.mongodb.DBCollection;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static applango.common.services.DB.mongo.mongoDB.countFailureLogins;
import static applango.common.services.DB.mongo.mongoDB.countSuccessfulLogins;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class genericApplangoWebsiteActions  extends SeleniumTestBase{
    private static final String LOG_IN_XPATH_UI_OBJECT = "login.button.xpath";
    private static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.xpath";


    private static final String RECENT_XPATH= "recent.xpath";
    private static final String APPLANGO_ENVIRONMENT = "applangoEnvironment";
    private static Map configPropertiesMapper;

    public static void openDashboardAndLogin(Applango applango, WebDriver driver1, WebDriverWait wait) throws IOException {
        openDashboardAndLogin(applango, driver1, wait, false, false);
        openApplicationPage(driver1, wait);
    }
    public static void openDashboardAndLogin(Applango applango, WebDriver driver1, WebDriverWait wait, boolean isOAuthTest) throws IOException {
        openDashboardAndLogin(applango, driver1, wait, isOAuthTest, false);
    }




    public static void openDashboardAndLogin(Applango applango, WebDriver driver1, WebDriverWait wait, boolean isOAuthTest, boolean isFirstLogin) throws IOException {
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

    public static void selectApplication(WebDriver driver1, WebDriverWait wait, applications app) throws IOException {
        if (!isApplicationAlreadySelected(driver1, app)) {
            logger.info("Selecting application " + app);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.APPLICATION_DROP_DOWN.getValue())));
            driver1.findElement(By.id(applangoButtons.APPLICATION_DROP_DOWN.getValue())).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(app.getValue())));
            driver1.findElement(By.id(app.getValue())).click();
            waitUntilWaitForServerDissappears(wait);
        }
    }

    private static boolean isApplicationAlreadySelected(WebDriver driver1, applications app) {
        return app.getValue().toLowerCase().contains(driver1.findElement(By.id("appTitle")).getText().toLowerCase());
    }

    public static void openDashboard(Applango applango, WebDriver driver1, WebDriverWait wait) throws IOException {
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

    public static void enterCredentials(WebDriver driver, String username, String password) throws IOException {

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

    public static void clickOnLoginButtonAndWaitForUserListToLoad(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnLoginButtonAndWaitForUserListToLoad(driver, wait, false);
    }


    public static void clickOnLoginButtonAndWaitForUserListToLoad(WebDriver driver, WebDriverWait wait, boolean isFirstTime) throws IOException {

        clickOnLogin(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HEADER.getValue().toString())));
        if (!isFirstTime) {
//            waitForUserListToLoad(wait);

        }
    }
    public static void clickOnLoginButtonAndWaitForErrorMessage(WebDriver driver, WebDriverWait wait) throws IOException {

        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_INCORRECT_PASSWORD_INCORRECT.getValue());
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_ERROR_EMPTY_USERNAME.getValue());
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForCredentialsErrorMessage(driver, wait, applangoMessages.ENTER_CREDENTIALS_ERROR_EMPTY_PASSWORD.getValue());
    }

    public static void clickOnLogin(WebDriver driver) throws IOException {
        SeleniumTestBase.logger.info("Clicking on login button (by id)");
        driver.findElement(By.id(applangoButtons.LOGIN_SUBMIT.getValue())).click();

        System.currentTimeMillis();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);


    }


    private static void waitForCredentialsErrorMessage(WebDriver driver, WebDriverWait wait, String errorMessage) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())));
        Assert.assertTrue(driver.findElement(By.id(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())).getText().equals(errorMessage));
    }



    private static void waitForUserListToLoad(WebDriverWait wait) throws IOException {
        waitUntilWaitForServerDissappears(wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HEADER.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.USERTABLE_ID.getValue().toString())));
        waitUntilWaitForServerDissappears(wait);

    }

    public static void waitUntilWaitForServerDissappears(WebDriverWait wait) throws IOException {
        logger.info("Wait until waitbox disappear");
        long start = getStartTimeInMillis();
        wait.until(ExpectedConditions.invisibilityOfElementLocated((By.id(applangoObject.WAITBOX.getValue()))));
        getFinishTimeAndCompareToStartTime(start);
    }

    private static void getFinishTimeAndCompareToStartTime(long start) {
        long finish = System.currentTimeMillis();
        long totalTime = finish - start;
        totalTime /= 1000;
        System.out.println("Total Time for page load :  "+totalTime + " seconds");
    }

    private static long getStartTimeInMillis() {
        return System.currentTimeMillis();
    }


    public static void waitForUserDetailsInPeoplePage(WebDriverWait wait) throws IOException {
        logger.info("wait For User Details In PeoplePage");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.id(applangoObject.PEOPLEPAGE_USERDETAIL.getValue()))));
    }

    private static void waitForHistogramToLoad(WebDriverWait wait) throws IOException {
        logger.info("wait For Histogram To Load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.HISTOGRAM.getValue()))).isDisplayed();
    }




    public static void clickOnSubmitCredentials(WebDriver driver) throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        String loginSubmit = appObjectMapper.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.xpath(loginSubmit)).click();
    }

    public static void filterByDate(WebDriver driver1, WebDriverWait wait, String fromYear, months fromMonth, String untilYear, months toMonth) throws IOException {
        logger.info("Filter dates " + fromMonth + "/" + fromYear + " - " + toMonth + "/" + untilYear);
        driver1.findElement(By.id(applangoDropdowns.FROM_YEAR.getValue().toString())).sendKeys(fromYear);
        driver1.findElement(By.id(applangoDropdowns.FROM_MONTH.getValue().toString())).sendKeys(fromMonth.getValue().toString());
        driver1.findElement(By.id(applangoDropdowns.UNTIL_YEAR.getValue().toString())).sendKeys(untilYear);
        driver1.findElement(By.id(applangoDropdowns.UNTIL_MONTH.getValue().toString())).sendKeys(toMonth.getValue().toString());
        clickOnDateSearchButton(driver1, wait);
    }

    private static void clickOnDateSearchButton(WebDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.DATE_SEARCH.getValue().toString())).click();
        waitForUserListToLoad(wait);
    }

    public static void selectUserFromList(WebDriver driver1, WebDriverWait wait , String firstName, String lastName)  throws IOException {
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

    private static void clickOnRecordInTable(WebDriver driver1, WebDriverWait wait, String name) throws IOException {

        int userRowNumber = searchOnWhichRowInTableTheUserAppear(driver1, name);
        if (userRowNumber != 0) {
            clickOnRecordInTable(driver1, wait, 1);
        }
        else { //default -click on first record (0)
            clickOnRecordInTable(driver1, wait);
        }
    }

    private static int searchOnWhichRowInTableTheUserAppear(WebDriver driver1, String name) throws IOException {
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

    public static void enterValueInSearchLastName(WebDriver driver1, String lastName) throws IOException {
        driver1.findElement(By.id(applangoTextfields.SearchLastName.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.SearchLastName.getValue().toString())).sendKeys(lastName);
    }

    private static void clickOnRecordInTable(WebDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).get(0).click();
        waitForHistogramToLoad(wait);
    }

    private static void clickOnRecordInTable(WebDriver driver1, WebDriverWait wait, int recordNumber) throws IOException {
//        driver1.findElements(By.cssSelector("#usertabler tr")).get(recordNumber).click();
        driver1.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).get(recordNumber).click();
        waitForHistogramToLoad(wait);
    }

    public static int getNumberOfUsersInList(WebDriver driver) throws IOException {
        //The number of users will calculate by size of user table / 33 (33 is the size of each column in table)
//        return driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getSize().getHeight() / 33;
        return driver.findElements(By.cssSelector(applangoObject.USERTABLER.getValue())).size();
    }

    public static boolean checkIfUserExistInList(WebDriver driver, String name) throws IOException {
        return driver.findElement(By.id(applangoObject.USERTABLE.getValue().toString())).getText().contains(name);

    }

    public static void openUserAccount(WebDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("Open user account");
        openUserMenu(driver1);
        clickOnAccount(driver1);
        waitForUserAccountPage(wait);

    }

    public static void logout(WebDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("Logout");
        openUserMenu(driver1);
        clickOnLogout(driver1);
        driver1.navigate().refresh();

    }

    private static void clickOnAccount(WebDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.ACCOUNT.getValue().toString())).click();
    }

    private static void clickOnLogout(WebDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.LOGOUT.getValue().toString())).click();
    }

    private static void openUserMenu(WebDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoDropdowns.USER_MENU.getValue().toString())).click();
    }

    private static void waitForUserAccountPage(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.ACCOUNT_TITLE.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.CHANGE_PASSWORD.getValue().toString())));
    }

    public static void clickOnChangePassword(WebDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.CHANGE_PASSWORD.getValue().toString())).click();
        waitForForgotPasswordDialog(wait);


    }

    private static void waitForForgotPasswordDialog(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())));
    }


    public static void fillChangePasswordAndSubmit(WebDriver driver1, String currentPassword, String newPassword, String confirmNewPassword) throws IOException {
        fillChangePasswordAndSubmit(driver1, currentPassword, newPassword, confirmNewPassword, false);
    }

    public static void fillChangePasswordAndSubmit(WebDriver driver1, String currentPassword, String newPassword, String confirmNewPassword, boolean isResetPassword) throws IOException {
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

    private static void clickOnSubmitChangePassword(WebDriver driver1) throws IOException {
        driver1.findElement(By.id(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())).click();
    }

    public static void checkChangePasswordMessage(SearchContext driver1, applangoMessages changePasswordMessage) throws IOException {
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.CHANGE_PASSWORD_MESSAGE.getValue().toString())).getText().contains(changePasswordMessage.getValue().toString()));
    }

    public static String getName(WebDriver driver1) throws IOException {
//        int appRank =  0;
        return driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(0).getText();

//        return appRank;
    }
    public static int getAppRank(WebDriver driver1) throws IOException {
        int appRank =  0;
        appRank = Integer.parseInt(driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(1).getText());

        return appRank;
    }

    public static int getActivity(WebDriver driver1) throws IOException {
        int activity = 0;
//        String userRecord = getAppRankAndActivity(driver1);
//        int spaceSeperator = userRecord.indexOf(" ");
        activity = Integer.parseInt(driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(2).getText());

        return activity;
    }

    public static String getGroup(WebDriver driver1) throws IOException {
        return driver1.findElement(By.cssSelector(applangoObject.USERTABLER.getValue())).findElements(By.cssSelector("td")).get(3).getText();

    }

    public static void clickOnApplicationSettings(WebDriver driver1, WebDriverWait wait1) throws IOException {
        driver1.findElement(By.xpath("/html/body/span/span/div[2]/div/span/div/div/img")).click();
//        driver1.findElement(By.cssSelector(applangoButtons.AUTHENTICATION_SETTINGS.getValue().toString())).click();
        waitForAuthenticationScreen(wait1);
    }

    private static void waitForAuthenticationScreen(WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())));
    }

    public static void verifyNoAuthenticationSet(WebDriver driver1) throws IOException {
        Assert.assertTrue(driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).isEnabled());
    }

    public static void enterAuthentication(WebDriver driver1, String accessToken, String clientSecret) throws IOException {
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).sendKeys(accessToken);
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).clear();
        driver1.findElement(By.id(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).sendKeys(clientSecret);
    }

    public static void clickOnAuthenticationSubmit(WebDriver driver1, WebDriverWait wait) throws IOException {
        logger.info("click On Authentication Submit Button");
        driver1.findElement(By.xpath("/html/body/span/span/div[2]/div/div/div/button")).click();
//        driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).click();
        waitForAuthenticateLink(wait);
    }
    public static void clickOnAuthenticationLink(WebDriver driver1, WebDriverWait wait) throws IOException {
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

    public static void waitForSuccessfulAuthenticatedMessage(WebDriver driver1, WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESS.getValue().toString())));
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESS.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_SUCCESSFUL.getValue().toString()));
    }

    public static void clickOnVerifyAuthentication(WebDriver driver1) throws IOException {
        driver1.findElement(By.id(applangoButtons.AUTHENTICATION_VERIFY.getValue().toString())).click();
    }

    public static void waitForSuccessfulAccountAuthenticatedMessage(WebDriverWait wait1) throws IOException {

        logger.info("Wait for message: " + applangoMessages.AUTHENTICATION_SUCCESSFUL_IN_APPLICATION.getValue().toString());
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD.getValue().toString())));
    }

    public static void clickOnRecoverPassword(WebDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordRequestSent(wait);
    }

    private static void waitForRecoverPasswordRequestSent(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString()))).getText().contains(applangoMessages.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString());
    }

    public static void clickOnForgotPassword(WebDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.id(applangoButtons.FORGOT_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordButton(wait);
    }

    private static void waitForRecoverPasswordButton(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())));
    }

    public static void enterUsernameInForgotPasswordTextfield(WebDriver driver1, String username) throws IOException {
        driver1.findElement(By.id(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())).sendKeys(username);
    }

    public static void waitForFailureAuthenticatedMessage(WebDriver driver1, WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_FAILURE.getValue().toString())));
        Assert.assertTrue(driver1.findElement(By.id(applangoObject.AUTHENTICATION_AUTHENTICATED_FAILURE.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_FAILURE.getValue().toString()));

    }

    public static void checkEnterNewPasswordScreenLoaded(WebDriver driver2, WebDriverWait wait2) throws IOException {
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id(applangoTextfields.NEW_PASSWORD.getValue().toString())));
        Assert.assertTrue(driver2.findElement(By.id(applangoObject.CHANGE_PASSWORD_MESSAGE.getValue().toString())).getText().contains(applangoMessages.RESET_PASSWORD_DEFAULT.getValue().toString()));
    }


    public static void verifyPasswordSuccessfulyChangedMessageAppear(WebDriver driver2) throws IOException {
        Assert.assertTrue(driver2.findElement(By.id(applangoObject.RESET_PASSWORD_SUCCESSFULLY.getValue())).getText().equals(applangoMessages.RESET_PASSWORD_SUCCESSFULLY.getValue()));
        driver2.findElement(By.xpath(applangoButtons.SUCCESSFUL_RESET_PASSWORD_BUTTON.getValue())).click();

    }

    public static void filterLicenseType(WebDriver driver1, WebDriverWait wait1, salesforceLicenses salesforceLicenseType) throws IOException {
        driver1.findElement(By.id(applangoDropdowns.FILTER.getValue().toString())).sendKeys(salesforceLicenseType.getValue().toString());
        clickOnDateSearchButton(driver1, wait1);
    }

    public static void unselectRememberUsernameCheckbox(WebDriver driver1) throws IOException {
        if (driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).isSelected()) {
            //un-select checkbox
            driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).click();
        }
    }

    public static void selectRememberUsernameCheckbox(WebDriver driver1) throws IOException {
        if (!(driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).isSelected())) {
            //Select checkbox
            driver1.findElement(By.id(applangoButtons.LOGIN_REMEMBER_USERNAME_CHECKBOX.getValue())).click();
        }
    }

    public static void clickOnUpdateAlert(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Click on Update Alert  ");
        driver1.findElement(By.id(applangoButtons.UPDATE_ALERT.getValue())).click();
        waitUntilWaitForServerDissappears(wait1);
    }

    public static void setBoxNoLoginAlertThreshold(WebDriver driver1, String alertThreshold) {
        logger.info("Set Alert Threshold : " + alertThreshold);
        driver1.findElement(By.cssSelector("#alertsdiv > div:nth-child(2) > input")).clear();
        driver1.findElement(By.cssSelector("#alertsdiv > div:nth-child(2) > input")).sendKeys(alertThreshold);
    }

    public static void clickSaveBoxNoLogin(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Save Alert Threshold ");

        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/span[1]")));
        driver1.findElement(By.xpath("//*[@id=\"alertsdiv\"]/div[2]/span[1]")).click();
        genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait1);
    }

    public static boolean checkAlertTrigger(WebDriver driver1) throws IOException {
        logger.info("Check alert trigger");
        return driver1.findElement(By.id(applangoObject.ACTIVE_ALERT.getValue())).getAttribute("class").contains("activealert");
    }


    public static void searchPeople(WebDriver driver1, WebDriverWait wait1, String firstName, String lastName, String email, months fromMonth, String fromYear, months toMonth, String toYear) throws IOException {
        logger.info("Search for:" + firstName + " "+ lastName + " email: " + email);
        driver1.findElement(By.id(applangoTextfields.PEOPLE_PAGE_FIRSTNAME.getValue())).sendKeys(firstName);
        driver1.findElement(By.id(applangoTextfields.PEOPLE_PAGE_LASTNAME.getValue())).sendKeys(lastName);
        driver1.findElement(By.id(applangoTextfields.PEOPLE_PAGE_EMAIL.getValue())).sendKeys(email);
        clickOnPeoplePageSearchButton(driver1, wait1);


    }

    private static void clickOnPeoplePageSearchButton(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("click On People Page Search Button ");
        driver1.findElement(By.id(applangoButtons.PEOPLE_PAGE_SEARCH.getValue())).click();
        waitUntilWaitForServerDissappears(wait1);
    }

    public static void clickOnUserInPeopleTable(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("click On User In People Table ");
        clickOnUserInPeopleTable(driver1, wait1, "1");

    }

    public static void clickOnUserInPeopleTable(WebDriver driver1, WebDriverWait wait1, String rowNumber) throws IOException {
        logger.info("click on user app in people page");
        driver1.findElement(By.cssSelector("#alluserstabler > tbody > tr:nth-child(" +rowNumber + ")")).click();
        waitUntilWaitForServerDissappears(wait1);
        waitForGraphRollUp(wait1);

    }

    public static void clickOnAppInPeopleTable(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Click on first app in table");
        if (checkUserApplicationTableIsNotEmpty(driver1)) {
            driver1.findElement(By.cssSelector(applangoObject.PEOPLEPAGE_USER_APP_TABLE.getValue())).click();
            waitUntilWaitForServerDissappears(wait1);
            checkUserAppData(wait1);
        }
    }

    private static void checkUserAppData(WebDriverWait wait1) throws IOException {
        logger.info("Check user app in people page");
        wait1.until(ExpectedConditions.visibilityOfElementLocated((By.id("appData")))); //wait for activity chart to load
        wait1.until(ExpectedConditions.visibilityOfElementLocated((By.id(applangoObject.PEOPLEPAGE_USER_CHART.getValue())))); //wait for activity chart to load
        wait1.until(ExpectedConditions.visibilityOfElementLocated((By.id(applangoObject.PEOPLEPAGE_USER_DETAILS.getValue())))); //wait for activity chart to load
    }

    private static boolean checkUserApplicationTableIsNotEmpty(WebDriver driver1) throws IOException {
        if (!driver1.findElement(By.cssSelector(applangoObject.PEOPLEPAGE_USER_APP_TABLE.getValue())).getText().isEmpty()) {
            return true;
        }
        else
            return false;
    }

    private static void waitForGraphRollUp(WebDriverWait wait1) throws IOException {
        logger.info("Wait until graphRolledUp appear");
        wait1.until(ExpectedConditions.visibilityOfElementLocated((By.id(applangoObject.PEOPLEPAGE_GRAPHROLLUP.getValue()))));
    }

    public static void clickOnUserInPeoplePageUserTabler(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Clicking on: " + driver1.findElement(By.xpath("//*[@id=\"usertabler\"]/tbody/tr[1]")).getText());
        clickOnUserInPeoplePageUserTabler(driver1, wait1, "1");
    }

    public static void clickOnUserInPeoplePageUserTabler(WebDriver driver1, WebDriverWait wait1, String rowNumber) throws IOException {
        driver1.findElement(By.xpath("//*[@id=\"usertabler\"]/tbody/tr[1]")).click();
        waitUntilWaitForServerDissappears(wait1);
        waitForHistogramToLoad(wait1);
    }

    public static void checkSuccessfulAndFailureLoginsCounts(Applango applango, DBCollection applangoUserLoginHistoryCollection, int failureLogins, int successfulLogins) {
        logger.info("Failure login actual: " + countFailureLogins(applango, applangoUserLoginHistoryCollection) + " Expected login: " + failureLogins);
        assertTrue(countFailureLogins(applango, applangoUserLoginHistoryCollection) == failureLogins);
        logger.info("Successful login actual: " + countSuccessfulLogins(applango, applangoUserLoginHistoryCollection) + " Expected login: " + successfulLogins);
        assertTrue(countSuccessfulLogins(applango, applangoUserLoginHistoryCollection) == successfulLogins);
    }
    public static void openPeoplePage(WebDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Open people page");
        driver1.findElement(By.xpath(applangoObject.PEOPLE_PAGE_TAB.getValue())).click();
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.PEOPLE_PAGE_SEARCH.getValue())));
    }

    public static void openReportPage(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Open Report page");
        driver1.findElement(By.xpath(applangoObject.REPORT_PAGE_TAB.getValue())).click();
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.REPORT_PAGE_SEARCH.getValue())));
    }

    public static void clickOnReportSearch(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("Click On report search butoon");
        driver1.findElement(By.id(applangoButtons.REPORT_PAGE_SEARCH.getValue())).click();
        genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait1);
        waitForReportDataHolder(wait1);
        waitForReportDataChart(wait1);
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.REPORT_PAGE_EXPORT.getValue())));
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.REPORT_PAGE_DOWNLOAD.getValue())));
    }

    private static void waitForReportDataChart(WebDriverWait wait1) throws IOException {
        logger.info("wait For Report Data Chart");
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.id(applangoObject.REPORT_DATA_CHART.getValue()))));
    }

    private static void waitForReportDataTable(WebDriverWait wait1) throws IOException {
        logger.info("wait For Report Data Table");
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.id(applangoObject.REPORT_DATA_TABLE.getValue()))));
    }

    private static void waitForReportDataHolder(WebDriverWait wait1) throws IOException {
        logger.info("wait For Report Data Holder");
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.id(applangoObject.REPORT_DATA_HOLDER.getValue()))));
    }

    public static void filterReportsDates(FirefoxDriver driver1, months fromMonth, String fromYear, months toMonth, String toYear) throws IOException {
        logger.info("Filter Report From: " + fromMonth + "/" + fromYear + " till: " + toMonth + "/" + toYear);
        driver1.findElement(By.id(applangoDropdowns.REPORT_FROM_MONTH.getValue())).sendKeys(fromMonth.toString());
        driver1.findElement(By.id(applangoDropdowns.REPORT_FROM_YEAR.getValue())).sendKeys(fromYear);
        driver1.findElement(By.id(applangoDropdowns.REPORT_UNTIL_MONTH.getValue())).sendKeys(toMonth.toString());
        driver1.findElement(By.id(applangoDropdowns.REPORT_UNTIL_YEAR.getValue())).sendKeys(toYear);
    }

    public static void selectReportApplication(FirefoxDriver driver1, applications app) throws IOException {
        logger.info("Select application " + app.getValue());
        driver1.findElement(By.id(applangoDropdowns.REPORT_APP_NAME.getValue())).sendKeys(app.getValue());
    }

    public static void selectReport(FirefoxDriver driver1, applangoReports reportName) throws IOException {
        logger.info("Select Report " + reportName.getValue());
        driver1.findElement(By.id(applangoDropdowns.REPORT_NAME.getValue())).sendKeys(reportName.getValue());
    }

    public static void clickOnReportExportCSV(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("click On Report Export CSV");
        driver1.findElement(By.id(applangoButtons.REPORT_PAGE_EXPORT.getValue())).click();
        System.out.println("--- " + driver1.switchTo().activeElement().getText());
        List<String> browserTabs = new ArrayList<String>(driver1.getWindowHandles());
        assertTrue(driver1.switchTo().window(browserTabs.get(1)).getTitle().equals("Applango Report"));
    }
    public static void clickOnReportDownload(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        logger.info("click On Report Download CSV");
        driver1.findElement(By.id(applangoButtons.REPORT_PAGE_DOWNLOAD.getValue())).click();
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"buttons\"]/output/a")));
    }

    public static String getLicenseCostInfo(FirefoxDriver driver) throws IOException {
        return driver.findElement(By.cssSelector(applangoTextfields.MAIN_LICENSE_COST.getValue())).getText();
    }

    public static void validateLicenseCostDataInApplicationPageBeforeUpdate(String licenseCost) {
        logger.info("Check the license Cost when 'FDC_SUB' = 30 ");
        assertTrue(licenseCost.contains("Total licenses"));
        assertTrue(licenseCost.contains("101,770"));
//        assertTrue(licenseCost.contains("2634"));
//        assertTrue(licenseCost.contains("$39510"));
//        assertTrue(licenseCost.contains("1"));
//        assertTrue(licenseCost.contains("$75"));
    }

    public static void validateLicenseCostDataInApplicationPageAfterUpdate(String licenseCost) {
        logger.info("Check the license Cost when 'FDC_SUB' = 10 ");
        assertTrue(licenseCost.contains("Total licenses"));
        assertTrue(licenseCost.contains("101,730"));
//        assertTrue(licenseCost.contains("2634"));
//        assertTrue(licenseCost.contains("$26340"));
//        assertTrue(licenseCost.contains("1"));
//        assertTrue(licenseCost.contains("$75"));
    }

    public static void checkLicenseCostInApplicationPageAfterUpdate(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("check License Cost In Application Page After Update");
        openApplicationPage(driver, wait);
        String licenseCostInfoAfterUpdate = genericApplangoWebsiteActions.getLicenseCostInfo(driver);
        validateLicenseCostDataInApplicationPageAfterUpdate(licenseCostInfoAfterUpdate);
    }

    private static void openApplicationPage(WebDriver driver, WebDriverWait wait) throws IOException {
        clickOnApplicationLink(driver);
        genericApplangoWebsiteActions.waitUntilWaitForServerDissappears(wait);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(applangoButtons.APPLICATION_DROP_DOWN.getValue())));
    }

    private static void clickOnApplicationLink(WebDriver driver) throws IOException {
        driver.findElement(By.xpath(applangoObject.APPLICATION_LINK.getValue())).click();
    }


    public static void updateLicenseCostInDBAndReloadApplicationData(String licenseType, FirefoxDriver driver, WebDriverWait wait, DBCollection coll) throws IOException {
        mongoDB.updateLicensePrice(coll, licenseType, 10);
        //reload page
        openApplicationPage(driver, wait);
        genericApplangoWebsiteActions.selectApplication(driver, wait, applications.BOX);
        genericApplangoWebsiteActions.selectApplication(driver, wait, applications.SALESFORCE);
    }

    public static void checkLicenseCostInApplicationPageBeforeUpdate(String licenseType, FirefoxDriver driver, WebDriverWait wait, DBCollection coll) throws IOException {
        logger.info("check License Cost In Application Page Before Update");
        openApplicationPage(driver, wait);
        genericApplangoWebsiteActions.selectApplication(driver, wait, applications.SALESFORCE);
        String licenseCostInfoBeforeUpdate = genericApplangoWebsiteActions.getLicenseCostInfo(driver);

        int price  = mongoDB.getPriceByLicenseType(coll, licenseType);
        if (!(price==30)) {
            mongoDB.updateLicensePrice(coll, licenseType, 30);
        }
        validateLicenseCostDataInApplicationPageBeforeUpdate(licenseCostInfoBeforeUpdate); //licenseCost = 30
    }

    private static String getLicenseCostInAccountPage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
//        genericApplangoWebsiteActions.openUserAccount(driver, wait);
//            Click on SF in account page
        driver.findElement(By.xpath("//*[@id=\"applist\"]/div[3]")).click();
        waitUntilWaitForServerDissappears(wait);
        return driver.findElement(By.id("licenses")).getText();
    }

    private static void validateLicenseCostInAccountsPageAfterUpdate(String licenseCostsInAccounts) {
        assertTrue(licenseCostsInAccounts.contains("FDC_SUB:  $10 each for 2 users = $20"));
    }

    public static void checkLicenseCostInAccountPageAfterUpdate(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("check License Cost In Account Page After Update");
        openUserAccount(driver, wait);
        String licenseCostsInAccountsAfterUpdate = getLicenseCostInAccountPage(driver, wait);
        validateLicenseCostInAccountsPageAfterUpdate(licenseCostsInAccountsAfterUpdate);
    }

    public static void checkLicenseCostInAccountPageBeforeUpdate(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("check License Cost In Account Page Before Update");
        openUserAccount(driver, wait);
        String licenseCostsInAccountsBeforeUpdate = getLicenseCostInAccountPage(driver, wait);
        validateLicenseCostInAccountsPageBeforeUpdate(licenseCostsInAccountsBeforeUpdate);
    }


    private static void validateLicenseCostInAccountsPageBeforeUpdate(String licenseCostsInAccounts) {
        assertTrue(licenseCostsInAccounts.contains("FDC_SUB:  $30 each for 2 users = $60"));
    }


    public static void reloadDashboard(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        driver1.navigate().refresh();
        waitUntilWaitForServerDissappears(wait1);
    }
}
