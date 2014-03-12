package applango.common.services.Generic;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 10/03/14
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class genericWebsiteActions {


    public static void openUrl(FirefoxDriver driver, WebDriverWait wait, String url, boolean isHttps) throws IOException {
        URL domain = new URL(driver.getCurrentUrl().toString());
//        String accountUrl = domain.getHost().toString() + "/" + url.getValue();
        String http = "http://";
        if (isHttps) {
            http = "https://";
        }
        driver.navigate().to(http + url);
//        waitForPageToLoad(wait);



    }
}
