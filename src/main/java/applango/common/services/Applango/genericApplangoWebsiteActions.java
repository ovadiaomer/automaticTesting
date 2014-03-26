package applango.common.services.Applango;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.*;
import applango.common.enums.jsonMaps;
import applango.common.enums.months;
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

        if (!isFirstLogin) {
            genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);
        }
    }

    public static void openDashboard(Applango applango, FirefoxDriver driver1, WebDriverWait wait) {
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

    public static void waitForDashboardLoginPageToLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("Loginbutton")));
    }

    public static void clickOnLoginButtonAndWaitForUserListToLoad(FirefoxDriver driver, WebDriverWait wait) throws IOException {

        clickOnLogin(driver);
        waitForUserListToLoad(wait);
    }
    public static void clickOnLoginButtonAndWaitForErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {

        clickOnLogin(driver);
        waitForIncorrectCredentialsErrorMessage(driver, wait);
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterUsernameErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForPleaseEnterUsernameErrorMessage(driver, wait);
    }

    public static void clickOnLoginButtonAndWaitForPleaseEnterPasswordErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        clickOnLogin(driver);
        waitForPleaseEnterPasswordErrorMessage(driver, wait);
    }

    public static void clickOnLogin(FirefoxDriver driver) throws IOException {
        SeleniumTestBase.logger.info("Clicking on login button (by id)");
        driver.findElement(By.id(applangoButtons.LOGIN_SUBMIT.getValue())).click();
    }


    private static void waitForPleaseEnterUsernameErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        String errorMessage = "Please enter a user name";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())));
        Assert.assertTrue(driver.findElement(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())).getText().equals(errorMessage));
    }

    private static void waitForPleaseEnterPasswordErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        String errorMessage = "Please enter your password";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())));
        Assert.assertTrue(driver.findElement(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())).getText().equals(errorMessage));
    }



    private static void waitForIncorrectCredentialsErrorMessage(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        String errorMessage = "Your login attempt has failed. The username or password may be incorrect. Please contact the administrator at your company for help.";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())));
        Assert.assertTrue(driver.findElement(By.xpath(applangoObject.INCORRECT_CREDENTIALS_ERRORMESSAGE.getValue().toString())).getText().equals(errorMessage));
    }


    private static void waitForUserListToLoad(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.HEADER.getValue().toString())));
    }
    private static void waitForHistogramToLoad(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.HISTOGRAM.getValue().toString())));
    }




    public static void clickOnSubmitCredentials(FirefoxDriver driver) throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        String loginSubmit = appObjectMapper.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.xpath(loginSubmit)).click();
    }

    public static void filterByDate(FirefoxDriver driver1, WebDriverWait wait, String fromYear, months fromMonth, String untilYear, months toMonth) throws IOException {
        driver1.findElementByXPath(applangoDropdowns.FROM_YEAR.getValue().toString()).sendKeys(fromYear);
        driver1.findElementByXPath(applangoDropdowns.FROM_MONTH.getValue().toString()).sendKeys(fromMonth.getValue().toString());
        driver1.findElementByXPath(applangoDropdowns.UNTIL_YEAR.getValue().toString()).sendKeys(untilYear);
        driver1.findElementByXPath(applangoDropdowns.UNTIL_MONTH.getValue().toString()).sendKeys(toMonth.getValue().toString());
        clickOnDateSearchButton(driver1, wait);
    }

    private static void clickOnDateSearchButton(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElementByXPath(applangoButtons.DATE_SEARCH.getValue().toString()).click();
        waitForUserListToLoad(wait);
    }

    public static void selectUserFromList(FirefoxDriver driver1, WebDriverWait wait , String firstName, String lastName)  throws IOException {

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
        int lineBreak = driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getText().indexOf("\n");
        int indexOfName = driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getText().indexOf(name);
        String listOfUsers = driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getText();
        while (indexOfName != 0) {
            listOfUsers = listOfUsers.substring(lineBreak + 1, listOfUsers.length());
            lineCounter ++;
            lineBreak = listOfUsers.indexOf("\n");
            indexOfName = listOfUsers.indexOf(name);
        }
        return lineCounter;
    }

    private static void enterValueInSearchLastName(FirefoxDriver driver1, String lastName) throws IOException {
        driver1.findElement(By.xpath(applangoTextfields.SearchLastName.getValue().toString())).clear();
        driver1.findElement(By.xpath(applangoTextfields.SearchLastName.getValue().toString())).sendKeys(lastName);
    }

    private static void clickOnRecordInTable(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString() + "//tr/td")).click();
        waitForHistogramToLoad(wait);
    }

    private static void clickOnRecordInTable(FirefoxDriver driver1, WebDriverWait wait, int recordNumber) throws IOException {
        driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString() + "//tr["+recordNumber+"]/td")).click();
        waitForHistogramToLoad(wait);
    }

    public static int getNumberOfUsersInList(FirefoxDriver driver) throws IOException {
        //The number of users will calculate by size of user table / 33 (33 is the size of each column in table)
        return driver.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getSize().getHeight() / 33;
    }

    public static boolean checkIfUserExistInList(FirefoxDriver driver, String name) throws IOException {
        return driver.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getText().contains(name);

    }

    public static void openUserAccount(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.xpath(applangoDropdowns.USER_MENU.getValue().toString())).click();
        driver1.findElement(By.xpath(applangoButtons.ACCOUNT.getValue().toString())).click();
        waitForUserAccountPage(wait);

    }

    private static void waitForUserAccountPage(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.ACCOUNT_TITLE.getValue().toString())));
    }

    public static void clickOnChangePassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.CHANGE_PASSWORD.getValue().toString())).click();
        waitForForgotPasswordDialog(wait);


    }

    private static void waitForForgotPasswordDialog(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())));
    }

    public static void fillChangePasswordAndSubmit(FirefoxDriver driver1, WebDriverWait wait,String currentPassword, String newPassword, String confirmNewPassword) throws IOException {
        driver1.findElement(By.xpath(applangoTextfields.CURRENT_PASSWORD.getValue().toString())).clear();
        driver1.findElement(By.xpath(applangoTextfields.NEW_PASSWORD.getValue().toString())).clear();
        driver1.findElement(By.xpath(applangoTextfields.CONFIRM_PASSWORD.getValue().toString())).clear();

        driver1.findElement(By.xpath(applangoTextfields.CURRENT_PASSWORD.getValue().toString())).sendKeys(currentPassword);
        driver1.findElement(By.xpath(applangoTextfields.NEW_PASSWORD.getValue().toString())).sendKeys(newPassword);
        driver1.findElement(By.xpath(applangoTextfields.CONFIRM_PASSWORD.getValue().toString())).sendKeys(confirmNewPassword);
        clickOnSubmitChangePassword(driver1);
    }

    private static void clickOnSubmitChangePassword(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.CHANGE_PASSWORD_SUBMIT.getValue().toString())).click();
    }

    public static void checkChangePasswordMessage(SearchContext driver1, applangoMessages changePasswordMessage) throws IOException {
        Assert.assertTrue(driver1.findElement(By.xpath(applangoObject.CHANGE_PASSWORD_MESSAGE.getValue().toString())).getText().contains(changePasswordMessage.getValue().toString()));
    }

    public static int getAppRank(FirefoxDriver driver1) throws IOException {
        int appRank =  0;

        String userRecord = getAppRankAndActivity(driver1);
        int spaceSeperator = userRecord.indexOf(" ");
        appRank = Integer.parseInt(userRecord.substring(0, spaceSeperator));

        return appRank;
    }

    public static int getActivity(FirefoxDriver driver1) throws IOException {
        int activity = 0;
        String userRecord = getAppRankAndActivity(driver1);
        int spaceSeperator = userRecord.indexOf(" ");
        activity = Integer.parseInt(userRecord.substring(spaceSeperator+1));

        return activity;
    }

    private static String getAppRankAndActivity(FirefoxDriver driver1) throws IOException {
        String userRecord =  driver1.findElement(By.xpath(applangoObject.USERTABLE.getValue().toString())).getText();
        //Trim first name , after this step should remain <lastName> <appRank> <activity>
        int firstSpace =  userRecord.indexOf(" ");
        userRecord = userRecord.substring(firstSpace+1);
        //Trim last name, after this step should remain <appRank> <activity>
        int secondSpace =  userRecord.indexOf(" ");
        userRecord = userRecord.substring(secondSpace+1);
        return userRecord;
    }


    public static void clickOnApplicationSettings(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SETTINGS.getValue().toString())).click();
        waitForAuthenticationScreen(wait1);
    }

    private static void waitForAuthenticationScreen(WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())));
    }

    public static void verifyNoAuthenticationSet(FirefoxDriver driver1) throws IOException {
        Assert.assertTrue(driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).isDisplayed());
        Assert.assertTrue(driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).isEnabled());
    }

    public static void enterAuthentication(FirefoxDriver driver1, String accessToken, String clientSecret) throws IOException {
        driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).clear();
        driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_KEY.getValue().toString())).sendKeys(accessToken);
        driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).clear();
        driver1.findElement(By.xpath(applangoTextfields.APPLICATION_CLIENT_SECRET.getValue().toString())).sendKeys(clientSecret);
    }

    public static void clickOnAuthenticationSubmit(FirefoxDriver driver1, WebDriverWait wait) throws IOException {

        driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_SUBMIT.getValue().toString())).click();
        waitForAuthenticateLink(wait);
    }
    public static void clickOnAuthenticationLink(FirefoxDriver driver1, WebDriverWait wait) throws IOException {

        driver1.findElement(By.xpath(applangoObject.AUTHENTICATION_CLICK_HERE_LINK.getValue().toString())).click();
        waitForAuthenticateVerify(wait);
    }

    private static void waitForAuthenticateVerify(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.AUTHENTICATION_VERIFY.getValue().toString())));
    }

    private static void waitForAuthenticateLink(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.AUTHENTICATION_CLICK_HERE_LINK.getValue().toString())));
    }

    public static void waitForSuccessfulAuthenticatedMessage(FirefoxDriver driver1, WebDriverWait wait1) throws IOException {
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY.getValue().toString())));
        Assert.assertTrue(driver1.findElement(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_SUCCESSFUL.getValue().toString()));
    }

    public static void clickOnVerifyAuthentication(FirefoxDriver driver1) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.AUTHENTICATION_VERIFY.getValue().toString())).click();
    }

    public static void waitForSuccessfulAccountAuthenticatedMessage(WebDriverWait wait1) throws IOException {

        logger.info("Wait for message: " + applangoMessages.AUTHENTICATION_SUCCESSFUL_IN_APPLICATION.getValue().toString());
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD.getValue().toString())));

//        Assert.assertTrue(driver1.findElement(By.xpath(applangoObject.AUTHENTICATION_AUTHENTICATED_SUCCESSFULLY_IN_DASHBOARD.getValue().toString())).getText().contains(applangoMessages.AUTHENTICATION_SUCCESSFUL_IN_APPLICATION.getValue().toString()));
    }

    public static void clickOnRecoverPassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordRequestSent(wait);
    }

    private static void waitForRecoverPasswordRequestSent(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoObject.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString()))).getText().contains(applangoMessages.RECOVER_PASSWORD_REQUEST_SENT.getValue().toString());
    }

    public static void clickOnForgotPassword(FirefoxDriver driver1, WebDriverWait wait) throws IOException {
        driver1.findElement(By.xpath(applangoButtons.FORGOT_PASSWORD_BUTTON.getValue().toString())).click();
        waitForRecoverPasswordButton(wait);
    }

    private static void waitForRecoverPasswordButton(WebDriverWait wait) throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoButtons.RECOVER_PASSWORD_BUTTON.getValue().toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())));
    }

    public static void enterUsernameInForgotPasswordTextfield(FirefoxDriver driver1, String username) throws IOException {
        driver1.findElement(By.xpath(applangoTextfields.FORGOT_PASSWORD_USERNAME.getValue().toString())).sendKeys(username);
    }
}
