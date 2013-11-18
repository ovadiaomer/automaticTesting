package applango.common.services.Salesforce;

import applango.common.SeleniumTestBase;
import applango.common.enums.jsonMaps;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Salesforce;
import applango.common.services.Mappers.objectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 17/11/13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class genericSalesforceWebsiteActions {
    private static final String LOG_IN_UI_OBJECT = "login.button.id";
    private static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.id";
    private static final String SALESFORCE_ENVIRONMENT = "salesfoceEnvironment";
    private static Map configPropertiesMapper;

    public static Salesforce getSalesforceConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        String salesfoceEnvironment = configPropertiesMapper.get(SALESFORCE_ENVIRONMENT).toString();
        Salesforce salesforce = readFromConfigurationFile.getSalesfoceConfigurationFileByenvironmentId(salesfoceEnvironment);
        return salesforce;
    }

    public static void clickOnLoginButton(WebDriver driver, WebDriverWait wait) throws IOException {

        Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
        String loginButton = salesforceObjectMap.get(LOG_IN_UI_OBJECT).toString();
        SeleniumTestBase.logger.info("Clicking on login button (by xpath)" + loginButton);
        driver.findElement(By.id(loginButton)).click();
    }

    public static void clickOnSubmitCredentials(WebDriver driver, WebDriverWait wait) throws IOException {
        Map salesforceObjectMap = objectMapper.getObjectMap(jsonMaps.SALESFORCE);
        String loginSubmit = salesforceObjectMap.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.id(loginSubmit)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bodyCell")));

    }
}
