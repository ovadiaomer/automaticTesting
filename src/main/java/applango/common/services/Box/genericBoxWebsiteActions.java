package applango.common.services.Box;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;

import static applango.common.SeleniumTestBase.logger;
import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 30/06/14
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class genericBoxWebsiteActions {
    public static String createNewFolder(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        Calendar cal = Calendar.getInstance();
        String folderName = "testFolder" + cal.getTime();
        logger.info("Create a new folder: " + folderName);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#create_item_button > ul > li:nth-child(2) > span")));
        driver.findElement(By.cssSelector("#create_item_button > ul > li:nth-child(2) > span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new_item_new_folder_link")));
        driver.findElement(By.id("new_item_new_folder_link")).click();
        sleep(2000);
        driver.switchTo().activeElement().findElement(By.id("new_folder_popup_name")).clear();
        driver.switchTo().activeElement().findElement(By.id("new_folder_popup_name")).sendKeys(folderName);
        driver.findElement(By.id("popup_button_okay")).click();
        logger.info("Wait for create_item_button");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("create_item_button")));
        return  folderName;
    }

    public static void deleteFolder(WebDriver driver, WebDriverWait wait, String newFolder) throws InterruptedException {
        logger.info("Delete new folder: "+ newFolder);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(newFolder)));
        driver.findElement(By.partialLinkText(newFolder)).click();


        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("folder_options_button")));
        driver.findElement(By.id("folder_options_button")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("item_menu_delete")));
        driver.findElement(By.id("item_menu_delete")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"popup-container\"]/div/div[2]/div/button[1]/span")));
        driver.switchTo().activeElement().findElement(By.xpath("//*[@id=\"popup-container\"]/div/div[2]/div/button[1]/span")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mod-header")));
        sleep(2000);
    }

    public static void loginToBox(WebDriver driver, WebDriverWait wait) {
        logger.info("Login to Box ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("continue")));
        //Enter credentials
        driver.findElement(By.id("login")).sendKeys("omer.ovadia@applango.com");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("omer1982");
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mod-header")));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("create_item_button")));
    }
}
