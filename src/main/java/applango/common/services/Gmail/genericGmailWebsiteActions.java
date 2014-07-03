package applango.common.services.Gmail;

import applango.common.SeleniumTestBase;
import applango.common.enums.gmail.gmailButtons;
import applango.common.enums.gmail.gmailTextfields;
import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Gmail;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 26/03/14
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class genericGmailWebsiteActions {

    private static Map configPropertiesMapper;

    public static Gmail getGmailConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        //Getting the value of applango dashboard parameter from config.properties file
//        String applangoEnvironment = configPropertiesMapper.get(APPLANGO_ENVIRONMENT).toString();
        //Setting values applango-configuration.xml in applango object
        Gmail gmail = readFromConfigurationFile.getGmailConfigurationFile();
        return gmail;
    }

    public static void enterPassword(FirefoxDriver driver2, String password) throws IOException {
        driver2.findElement(By.id(gmailTextfields.PASSWORD.getValue().toString())).clear();
        driver2.findElement(By.id(gmailTextfields.PASSWORD.getValue().toString())).sendKeys(password);
    }
    public static void clickOnSignIn(FirefoxDriver driver2, WebDriverWait wait2) throws IOException {
        driver2.findElement(By.id(gmailButtons.SIGN_IN.getValue().toString())).click();
        waitForMailBoxToLoad(wait2);
    }
    private static void waitForMailBoxToLoad(WebDriverWait wait2) throws IOException {
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(gmailButtons.INBOX.getValue())));
    }


    public static void clickOnInbox(FirefoxDriver driver2) throws IOException {
        driver2.findElement(By.xpath(gmailButtons.INBOX.getValue())).click();
    }

    public static String getInboxLabel(FirefoxDriver driver2) throws IOException {
        return driver2.findElement(By.xpath(gmailButtons.INBOX.getValue())).getText();
    }

    public static int getAmountFromInboxLabel(String amountOfMailBeforeResetPassword) {
        return Integer.parseInt(amountOfMailBeforeResetPassword.substring(7, amountOfMailBeforeResetPassword.length()-1));
    }

    public static String getFirstMailSubject(FirefoxDriver driver2) throws IOException {
        return driver2.findElement(By.xpath(gmailButtons.FIRST_MAIL_SUBJECT.getValue())).getText();
    }

    public static String getFirstMailSender(FirefoxDriver driver2) throws IOException {
        return driver2.findElement(By.xpath(gmailButtons.FIRST_MAIL_SENDER.getValue())).getText();
    }

    public static void checkMailSubjectIsResetPassword(FirefoxDriver driver2) throws IOException {
        Assert.assertTrue(getFirstMailSubject(driver2).equals("Reset password"));
    }

    public static void checkMailSenderIsApplango(FirefoxDriver driver2) throws IOException {
        Assert.assertTrue(getFirstMailSender(driver2).equals("applango.mailer"));
    }

    public static void clickOnFirstMail(FirefoxDriver driver2, WebDriverWait wait2) throws IOException, InterruptedException {
        driver2.findElement(By.xpath(gmailButtons.FIRST_MAIL_SUBJECT.getValue())).click();
//        sleep(3000);
//        waitForMailToLoad(wait2);
//        driver2.findElement(By.xpath(gmailButtons.RESET_PASSWORD_LINK.getValue())).click();
    }

    public static void waitForMailToLoad(WebDriverWait wait2) throws IOException {
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(gmailTextfields.SENDER_LABEL.getValue())));

    }

    public static void clickOnChangePasswordLink(FirefoxDriver driver2) throws IOException {
        driver2.findElement(By.xpath(gmailButtons.RESET_PASSWORD_LINK.getValue())).click();
    }

    public static void checkNewMailRecieved(FirefoxDriver driver2, long sleepTime, String amountOfMailBeforeResetPassword) throws InterruptedException, IOException {
        sleep(sleepTime);
        clickOnInbox(driver2);
        String amountOfMailAfterResetPassword =  getInboxLabel(driver2);
        int amountBefore = getAmountFromInboxLabel(amountOfMailBeforeResetPassword);
        int amountAfter = getAmountFromInboxLabel(amountOfMailAfterResetPassword);
        System.out.println("Amount of mail is " + amountBefore + "\nAnd after " + amountAfter);
        assertTrue(amountBefore+1 == amountAfter);
    }

    public static void loginToGmail(Gmail gmail, FirefoxDriver driver2, WebDriverWait wait2) throws IOException {
        SeleniumTestBase.launchingWebsite(driver2, gmail.getUrl());
        enterPassword(driver2, gmail.getPassword());
        clickOnSignIn(driver2, wait2);
    }

    public static void checkResetPasswordToken(FirefoxDriver driver2, String token) throws IOException {
        assertTrue(driver2.findElement(By.xpath(gmailButtons.RESET_PASSWORD_LINK.getValue())).getText().contains(token));
    }

}
