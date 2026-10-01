package core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import listeners.ClickScreenshotListener;

@Listeners(ClickScreenshotListener.class)
public class TestBase {

    protected static ThreadLocal<WebDriver> threadLocalDriver =
            new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setupBrowser() {

        String browserName =
                ConfigManager.get("Browser", "chrome");

        WebDriver driver =
                DriverFactory.createDriver(browserName);

        threadLocalDriver.set(driver);
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    //Dont uncomment this code, It will remain commented because of debugging purpose. If you uncomment this code, it will close the browser after each test method execution.
    // @AfterMethod(alwaysRun = true)
    // public void tearDown() {

    //     WebDriver driver = threadLocalDriver.get();

    //     if (driver != null) {
    //         driver.quit();
    //         threadLocalDriver.remove();
    //     }
    // }
}
