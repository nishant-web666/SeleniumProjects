package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginScript {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\MyProjects\\LoginPage\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://app.repcard.com/admin/login");
    }

    @Test
    public void LoginTest(){
        WebElement email = driver.findElement(By.xpath("//input[@name=\"email\"]"));
        WebElement password = driver.findElement(By.xpath("//input[@name=\"password\"]"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type=\"submit\"]"));

        // Email Positive scenario
        email.clear();
        email.sendKeys("nishant@yopmail.com");
        password.sendKeys("Qwerty");
        loginBtn.click();
        //After Clickng Login button expected URL should match the script
        String expectedUrl = "https://app.repcard.com/admin/home";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL after login is not as expected!", expectedUrl, actualUrl);
    }

    @After
    public void endTest(){
        driver.quit();
    }
}
