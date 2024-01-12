package com.tendable;

import com.BaseClass.LibraryClass;
import com.Constants.HomePageElements;
import com.Constants.MarketingContactPageElements;
import com.Constants.RequestDemoPageElements;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AssessmentTest extends LibraryClass {

    HomePageElements elementsHP = new HomePageElements();
    RequestDemoPageElements elementsRD = new RequestDemoPageElements();
    MarketingContactPageElements elementsMC = new MarketingContactPageElements();
    @Test
    public void task() throws IOException, InterruptedException {

        //Confirm accessibility of the top-level menus: Home, Our Story, Our Solution, and Why Tendable.
        boolean headerExistance = driver.findElement(By.xpath(elementsHP.homeXpath)).isDisplayed();
        Assert.assertTrue(headerExistance);
        System.out.println("Header is displayed as expected");
        List<WebElement> menuList = driver.findElements(By.xpath(elementsHP.menuItems));
        for (int list=0; list<menuList.size(); list++){
            menuList = driver.findElements(By.xpath(elementsHP.menuItems));
            Assert.assertFalse(menuList.get(list).getText().isEmpty());
            System.out.println("Menu item is displayed as: " + menuList.get(list).getText());
            //Verify that the "Request a Demo" button is present and active on each of the aforementioned top-level menu pages.
            menuList.get(list).click();
            Assert.assertTrue(driver.findElement(By.xpath(elementsRD.requestDemo)).isEnabled());
            driver.findElement(By.xpath(elementsRD.requestDemo)).click();
            boolean requestDemoPage = driver.findElement(By.xpath(elementsRD.requestType)).isDisplayed();
            Assert.assertTrue(requestDemoPage);
            System.out.println("Request a Demo page is displayed as expected");
        }

        //Navigate to the "Contact Us" section, choose "Marketing", and complete the form—
        //excluding the "Message" field. On submission, an error should arise. Ensure your script
        //confirms the error message's appearance. If the error is displayed, mark the test as PASS. If
        //absent, it's a FAIL.

        driver.findElement(By.xpath(elementsHP.contactUs)).click();
        driver.findElement(By.xpath(elementsMC.marketingContact)).click();
        driver.findElement(By.xpath(elementsMC.marketingContactFN)).sendKeys("test-FN");
        driver.findElement(By.xpath(elementsMC.marketingContactOrg)).sendKeys("test-Org");
        driver.findElement(By.xpath(elementsMC.marketingContactPh)).sendKeys("0123456789");
        driver.findElement(By.xpath(elementsMC.marketingContactEmail)).sendKeys("test@gmail.com");
        driver.findElement(By.xpath(elementsMC.marketingContactRole)).sendKeys("Management");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMinutes(5));
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath(elementsMC.marketingContactAgree))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(elementsMC.marketingContactAgree)));
        driver.findElement(By.xpath(elementsMC.marketingContactAgree)).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath(elementsMC.marketingContactSubmit))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(elementsMC.marketingContactSubmit)));
        driver.findElement(By.xpath(elementsMC.marketingContactSubmit)).click();
        String errorMessage = driver.findElement(By.xpath(elementsMC.marketingContactError)).getText();
        Assert.assertFalse(errorMessage.isEmpty());
        System.out.println("Error message is displayed as: " + errorMessage);
    }
}
