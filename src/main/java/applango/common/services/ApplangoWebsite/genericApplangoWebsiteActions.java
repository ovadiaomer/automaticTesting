package applango.common.services.ApplangoWebsite;

import applango.common.SeleniumTestBase;
import applango.common.enums.applango.applangoButtons;
import applango.common.enums.applango.applangoDropdowns;
import applango.common.enums.applango.applangoObject;
import applango.common.enums.applango.applangoTextfields;
import applango.common.enums.jsonMaps;
import applango.common.enums.months;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Applango;
import org.junit.Assert;
import org.openqa.selenium.By;
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
        launchingWebsite(driver1, applango.getUrl());
        waitForDashboardLoginPageToLoad(wait);
        genericApplangoWebsiteActions.enterCredentials(driver1, applango.getUsername(), applango.getPassword());
        genericApplangoWebsiteActions.clickOnLoginButtonAndWaitForUserListToLoad(driver1, wait);
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



//    public static void clickOnLoginButtonAndWaitForUserListToLoad(FirefoxDriver driver, WebDriverWait wait) throws IOException {
//        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
//        String loginButton = appObjectMapper.get(LOG_IN_XPATH_UI_OBJECT).toString();
//        SeleniumTestBase.logger.info("Clicking on login button (by xpath)" + loginButton);
//        driver.findElement(By.xpath(loginButton)).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("userName")));
//    }

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

}
