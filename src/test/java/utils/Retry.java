package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Retry {

    private static final int MAX_ATTEMPTS = 60;
    private static final long RETRY_INTERVAL_MILLIS = 2_000L;

    private Retry() {
    }

    public static void refreshInboxUntilMailFound(
            WebDriver driver,
            WebDriverWait wait,
            By refreshButton,
            By inboxFrame,
            By mailLocator,
            String mailDescription) throws InterruptedException {

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(refreshButton)).click();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(inboxFrame));

            if (!driver.findElements(mailLocator).isEmpty()) {
                driver.switchTo().defaultContent();
                return;
            }

            driver.switchTo().defaultContent();
            if (attempt < MAX_ATTEMPTS) {
                Thread.sleep(RETRY_INTERVAL_MILLIS);
            }
        }

        throw new IllegalStateException(mailDescription + " email was not received after "
                + MAX_ATTEMPTS + " attempts (120 seconds).");
    }

    public static void selectDropdownOptionUntilFound(WebDriver driver, WebDriverWait wait, By dropdown, String value, String description) throws InterruptedException {

        By option = By.xpath("//div[@role='option' and @data-value='" + value + "']");

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

                Actions action = new Actions(driver);

                action.keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .perform();

                for (char character : value.toCharArray()) {
                    action.sendKeys(String.valueOf(character)).perform();
                }

                WebDriverWait optionWait = new WebDriverWait(
                        driver,
                        java.time.Duration.ofSeconds(2)
                );

                optionWait.until(
                        ExpectedConditions.elementToBeClickable(option)
                ).click();

                return;

            } catch (Exception e) {
                System.out.println(
                        "Attempt " + attempt + " failed for " +
                        description + ": " + e.getMessage()
                );

                try {
                    new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                } catch (Exception ignored) {
                }

                if (attempt < MAX_ATTEMPTS) {
                    Thread.sleep(RETRY_INTERVAL_MILLIS);
                }
            }
        }

        throw new IllegalStateException(
                description + " '" + value +
                "' was not found after " +
                MAX_ATTEMPTS +
                " attempts (120 seconds)."
        );
    }
}
