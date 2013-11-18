package applango.common.services.ApplangoWebsite;

import applango.common.SeleniumTestBase;
import applango.common.enums.jsonMaps;
import applango.common.services.Mappers.objectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Map;

public class genericApplangoWebsiteActions {
    private static final String LOG_IN_XPATH_UI_OBJECT = "login.button.xpath";
    private static final String LOGIN_SUBMIT_UI_OBJECT = "login.submit.button.xpath";

    public static void clickOnLoginButton(WebDriver driver, WebDriverWait wait) throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        String loginButton = appObjectMapper.get(LOG_IN_XPATH_UI_OBJECT).toString();
        SeleniumTestBase.logger.info("Clicking on login button (by xpath)" + loginButton);
        driver.findElement(By.xpath(loginButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("userName")));
    }

    public static void clickOnSubmitCredentials(WebDriver driver) throws IOException {
        Map appObjectMapper = objectMapper.getObjectMap(jsonMaps.APPLANGO);
        String loginSubmit = appObjectMapper.get(LOGIN_SUBMIT_UI_OBJECT).toString();
        driver.findElement(By.xpath(loginSubmit)).click();
    }
}
