package com.tendable;

import com.Constants.Elements;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class AssessmentTest {

    Elements elements = new Elements();
    @Test
    public void task() throws IOException, InterruptedException {
        WebDriver driver = null;
        Properties prop;

        FileInputStream file=new FileInputStream(".\\src\\test\\resources\\Config Properties\\config.properties");
        prop =new Properties();
        prop.load(file);

        if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver=new ChromeDriver(options);

        }
        assert driver != null;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofDays(30));
        driver.navigate().to(prop.getProperty("url"));

        //Confirm accessibility of the top-level menus: Home, Our Story, Our Solution, and Why Tendable.
        boolean headerExistance = driver.findElement(By.xpath(elements.homeXpath)).isDisplayed();
        Assert.assertTrue(headerExistance);
        System.out.println("Header is displayed as expected");
        List<WebElement> menuList = driver.findElements(By.xpath(elements.menuItems));
        for (int list=0; list<menuList.size(); list++){
            menuList = driver.findElements(By.xpath(elements.menuItems));
            Assert.assertFalse(menuList.get(list).getText().isEmpty());
            System.out.println("Menu item is displayed as: " + menuList.get(list).getText());
            //Verify that the "Request a Demo" button is present and active on each of the aforementioned top-level menu pages.
            menuList.get(list).click();
            Assert.assertTrue(driver.findElement(By.xpath(elements.requestDemo)).isEnabled());
            driver.findElement(By.xpath(elements.requestDemo)).click();
            boolean requestDemoPage = driver.findElement(By.xpath(elements.requestType)).isDisplayed();
            Assert.assertTrue(requestDemoPage);
            System.out.println("Request a Demo page is displayed as expected");
        }

        //Navigate to the "Contact Us" section, choose "Marketing", and complete the form—
        //excluding the "Message" field. On submission, an error should arise. Ensure your script
        //confirms the error message's appearance. If the error is displayed, mark the test as PASS. If
        //absent, it's a FAIL.

        driver.findElement(By.xpath(elements.contactUs)).click();
        driver.findElement(By.xpath(elements.marketingContact)).click();
        driver.findElement(By.xpath(elements.marketingContactFN)).sendKeys("test-FN");
        driver.findElement(By.xpath(elements.marketingContactOrg)).sendKeys("test-Org");
        driver.findElement(By.xpath(elements.marketingContactPh)).sendKeys("0123456789");
        driver.findElement(By.xpath(elements.marketingContactEmail)).sendKeys("test@gmail.com");
        driver.findElement(By.xpath(elements.marketingContactRole)).sendKeys("Management");
        Thread.sleep(5000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(elements.marketingContactAgree)));
        driver.findElement(By.xpath(elements.marketingContactAgree)).click();
        Thread.sleep(5000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(elements.marketingContactSubmit)));
        driver.findElement(By.xpath(elements.marketingContactSubmit)).click();
        String errorMessage = driver.findElement(By.xpath(elements.marketingContactError)).getText();
        Assert.assertFalse(errorMessage.isEmpty());
        System.out.println("Error message is displayed as: " + errorMessage);
    }
}
