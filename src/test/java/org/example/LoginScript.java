package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;


public class LoginScript {
    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //implicitlyWait() is a global wait that applies to all element-finding methods.
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30));
        driver.get("https://app.repcard.com/admin/login");
    }

    @Test
    public void LoginTest(){
        // Create FluentWait
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))       // total time to wait
                .pollingEvery(Duration.ofSeconds(2))       // check every 2 seconds
                .ignoring(NoSuchElementException.class)    // ignore these exceptions
                .withMessage("Element not found within the time frame");
        // Example 1: Using ExpectedConditions (common in industries) using fluent wait
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name=\"email\"]")));
        // Clearing email input and then sending the keys to email input
        email.clear();
        email.sendKeys("nishant@yopmail.com");
        // Explicit wait of an element in the page till the element became clickable
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement loginBtn = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\"submit\"]")));
        //Stored the value of the fields by using WebElement interface
        WebElement password = driver.findElement(By.xpath("//input[@name=\"password\"]"));
        // Use of Point class to get the position of the window
        Point point = driver.manage().window().getPosition();
        System.out.println(point.x);
        System.out.println(point.y);
        // Email Positive scenario
        password.sendKeys("Qwerty");
        loginBtn.click();
        //After Clickng Login button expected URL should match the script
        String expectedUrl = "https://app.repcard.com/admin/home";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL after login is not as expected!", expectedUrl, actualUrl);
    }

    @Test
    public void newMethods(){
        LoginTest();
        //After this click on the calendar icon
        driver.findElement(By.className("calendars-icon")).click();
        // Create Calendar Click by linkText
        driver.findElement(By.linkText("Create Calendar")).click();
        // By using XPath following XPath axes method
        //driver.findElement(By.xpath("//ul[contains(@class, 'caltabslisting')]//following::li[1]")).click();
        driver.findElement(By.xpath("(//ul[contains(@class,'caltabslisting')]/li)[1]")).click();
        driver.findElement(By.id("calendar_name")).sendKeys("My new calendar");
        driver.findElement(By.id("calendar_desc")).sendKeys("This is the demo calendar description for testing purpose.");
        // Dynamic elements using XPath by attributes and the index of the element
        WebElement nextBtn1 = driver.findElement(By.xpath("//a[contains(text(),'Next')][1]"));
        nextBtn1.click();
        //driver.findElement(By.xpath("//li[contains(@data-utc,'Africa/Algiers')]"));
    }

    @Test
    public void actionClass(){
        Actions action = new Actions(driver);
        WebElement email = driver.findElement(By.xpath("//input[@name=\"email\"]"));
        action.moveToElement(email).click().sendKeys(email,"nishant@yopmail.com").doubleClick(email).contextClick(email).build().perform();
        driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("fwbjhbfhdb");
    }

    @Test
    public void handlingAlerts(){
        WebElement email = driver.findElement(By.xpath("//input[@name=\"email\"]"));
        // Clearing email input and then sending the keys to email input
        email.clear();
        email.sendKeys("nishant@yopmail.com");
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
        //Stored the value of the fields by using WebElement interface
        WebElement password = driver.findElement(By.xpath("//input[@name=\"password\"]"));
        password.sendKeys("Qwerty");
        loginBtn.click();
        driver.navigate().to("https://app.repcard.com/admin/appointments?tab=appointment");
        driver.findElement(By.id("export-appointments")).click();

    }

    @Test
    public void windowHandle() {
        boolean isTryBlockExecuted = false;
        try {
            driver.navigate().to("https://www.naukri.com/mnjuser/homepage");

            String mainWindow = driver.getWindowHandle();

            Set<String> allWindows = driver.getWindowHandles();
            for (String handle : allWindows) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
            }

            driver.switchTo().window(mainWindow);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement searchDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'nI-gNb-sb__main')]")
            ));
            searchDiv.click();

            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//input[contains(@class,'suggestor-input')])[1]")
            ));
            searchInput.sendKeys("Manual Tester");

            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[contains(@class,'nI-gNb-sb__icon-wrapper')])[1]")
            ));
            searchBtn.click();

            isTryBlockExecuted = true;
            System.out.println("✅ TRY block executed successfully.");

        } catch (NoSuchElementException e) {
            System.out.println("⚠️ CATCH block: Element not found → " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("⏳ CATCH block: Element took too long to load.");
        } catch (Exception e) {
            System.out.println("❌ CATCH block: Unexpected error → " + e.getMessage());
        } finally {
            if (isTryBlockExecuted) {
                System.out.println("✅ The TRY block ran successfully without any exception.");
                //Getting all the cookies from manage class
                Set <Cookie> cookies = driver.manage().getCookies();
                //System.out.println(cookies);
                //Getting a cookie by providing a specific name
                Cookie myCookie = driver.manage().getCookieNamed("bm_sv");
                //Deleting cookies methods
                driver.manage().deleteAllCookies();
                //driver.manage().deleteCookie(myCookie);
                Cookie deletedCookie = driver.manage().getCookieNamed("bm_sv");
                System.out.println(deletedCookie);
                //Add Custom Cookies
                Cookie custom = new Cookie("myCustom","Testing");
                driver.manage().addCookie(custom);
                System.out.println(driver.manage().getCookieNamed("myCustom"));
                System.out.println(myCookie);
            } else {
                System.out.println("⚠️ The CATCH block handled an exception.");
            }
        }
    }

    @Test
    public void webTables(){
        LoginTest();
        driver.navigate().to("https://app.repcard.com/admin/employee");
        WebElement webTable = driver.findElement(By.id("employee"));
        WebElement element = driver.findElement(By.xpath("//table[@id='employee']/tbody/tr[1]/td[4]"));
        // Get CSS value
        String color = element.getCssValue("color");
        String fontSize = element.getCssValue("font-size");
        String padding = element.getCssValue("padding");
        String backgroundColor = element.getCssValue("background-color");
        //Check CSS value
        if (element.getCssValue("font-size").equals("16px")) {
            System.out.println("Font size is correct!");
        } else {
            System.out.println("Font size is incorrect!");
        }
        System.out.println("Color: " + color);
        System.out.println("Font Size: " + fontSize);
        System.out.println("Padding: " + padding);
        WebElement link = driver.findElement(By.xpath("//table[@id='employee']/tbody/tr[1]/td[5]/a"));
        link.click();
    }

    @After
    public void endTest(){
        driver.quit();
    }
}
