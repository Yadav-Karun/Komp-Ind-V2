package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static WebDriver createDriver(String browserName) {

        WebDriver driver;

        switch (browserName.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getChromeOptions());
                break;

            case "brave":
                WebDriverManager.chromedriver().setup();

                ChromeOptions braveOptions = getChromeOptions();

                braveOptions.setBinary(
                        "C:\\Users\\Karun Yadav\\AppData\\Local\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"
                );

                driver = new ChromeDriver(braveOptions);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported Browser: " + browserName
                );
        }

        return driver;
    }

    private static ChromeOptions getChromeOptions() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-features=RendererCodeIntegrity");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--remote-debugging-pipe");
        options.addArguments("--user-data-dir=" + createTemporaryProfile());

        if (Boolean.parseBoolean(ConfigManager.get("Headless", "true"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        }

        // CHANGED:
        // Runs Chrome/Brave at 80% display scaling.
        // WHY:
        // Both Chrome and Brave are Chromium-based browsers,
        // so the same scaling option works for both.
        // options.addArguments("--force-device-scale-factor=0.8");
        // options.addArguments("--high-dpi-support=0.8");

        return options;
    }

    private static String createTemporaryProfile() {

        try {
            Path profileDirectory = Files.createTempDirectory("komp-browser-profile-");
            return profileDirectory.toAbsolutePath().toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create a temporary browser profile", exception);
        }
    }

}
