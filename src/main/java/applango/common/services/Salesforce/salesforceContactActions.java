package applango.common.services.Salesforce;

import applango.common.enums.salesforceButtons;
import applango.common.enums.salesforceTextfields;
import applango.common.enums.salesforceUrls;
import applango.common.services.beans.SalesforceContacts;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class salesforceContactActions  extends genericSalesforceWebsiteActions {

    public static SalesforceContacts[] create(FirefoxDriver driver, WebDriverWait wait, int numberOfContacts) throws IOException {
        SalesforceContacts[] newContact =  new SalesforceContacts[numberOfContacts];
        Map salesforceObjectMap = getMap();
        try {
            for (int i=1; i<=numberOfContacts; i++) {
                logger.info("Create new contact " + i + " out of " + numberOfContacts);
                openUrl(driver, wait, salesforceUrls.CONTACT);
                waitForPageToLoad(wait);
                clickOnButton(driver, salesforceButtons.NEW);
                waitForPageToLoad(wait);
                newContact[i-1] = fillDetailsAndSave(driver, wait);
            }

        }
        catch (Exception ex) {
            logger.error("Error- " + ex.getCause());
        }
        logger.info("Contact created successfully");
        return newContact;
    }

    public static void update(FirefoxDriver driver, WebDriverWait wait, SalesforceContacts contact, String newContactName) throws IOException {
        logger.info("Updating contact name of "+ contact.getAccountName() + " to "+ newContactName);
        waitForPageToLoad(wait);
        openPageWithIdInUrl(driver, wait, contact.getContactId());
        waitForPageToLoad(wait);
        clickOnEdit(driver, wait);
        waitForPageToLoad(wait);
        contact.setContactName(fillDetails(driver, newContactName));
        waitForPageToLoad(wait);
        clickOnSave(driver);
        waitForPageToLoad(wait);
    }

    public static void updateContactNTimes(FirefoxDriver driver1, WebDriverWait wait, SalesforceContacts contact, int numberOfUpdateContacts) throws IOException {
        for (int i=0; i<numberOfUpdateContacts; i++){
            waitForPageToLoad(wait);
            salesforceContactActions.update(driver1, wait, contact, "TestC" + i);
            waitForPageToLoad(wait);
        }
    }

    public static String fillDetails(FirefoxDriver driver, String newContactName) throws IOException {
        driver.findElement(By.id(salesforceTextfields.CONTACT_ContactLastName.getValue())).clear();
        driver.findElement(By.id(salesforceTextfields.CONTACT_ContactLastName.getValue())).sendKeys(newContactName);
        return newContactName;
    }


    private static SalesforceContacts fillDetailsAndSave(FirefoxDriver driver, WebDriverWait wait) throws IOException {
        logger.info("Fill new contact details and return object SalesforceContact with contactName and contactId");
        waitForPageToLoad(wait);
        SalesforceContacts newContact = new SalesforceContacts();
        waitForPageToLoad(wait);
        newContact.setContactName(fillDetailsRandomly(driver));

        clickOnSave(driver);
        waitForPageToLoad(wait);
        newContact.setContactId(getIdFromUrl(driver));
        return newContact;

    }

    public static void delete(FirefoxDriver driver, WebDriverWait wait, SalesforceContacts[] contact) throws IOException {

        for (SalesforceContacts salesforceContacts : contact) {
            delete(driver, wait, salesforceContacts.getContactId());
        }

    }

    private static String fillDetailsRandomly(FirefoxDriver driver) throws IOException {
        CharSequence contactName = "testContact"+ Calendar.getInstance().getTimeInMillis();
        driver.findElement(By.id(salesforceTextfields.CONTACT_ContactLastName.getValue())).sendKeys(contactName);
        driver.findElement(By.id(salesforceTextfields.CONTACT_ContactAccountName.getValue())).sendKeys("danielt");
        driver.findElement(By.id(salesforceTextfields.CONTACT_ContactCountry.getValue())).sendKeys("Israel");

        logger.info("New Contact name is " + contactName);
        return contactName.toString();
    }
}


