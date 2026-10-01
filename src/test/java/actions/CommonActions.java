package actions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import reporting.ClickReportManager;
import reporting.ScreenshotManager;

public class CommonActions {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions action;
    protected Properties properties;

    public CommonActions(WebDriver driver) throws Exception {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.action = new Actions(driver);
        this.properties = new Properties();
        loadProperties();
        PageFactory.initElements(driver, this);
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void scrollIntoViewAndClick(By locator, int seconds, String elementName) {
        handleToastIfVisible();
        WebElement element = getClickableElement(locator, seconds);
        scrollIntoView(element);
        click(locator, seconds);
    }

    public void scrollIntoViewAndClick(By locator) {
        WebElement element = getElement(locator);
        scrollIntoView(element);
        element.click();
    }

    public WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement getClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement getClickableElement(By locator, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void isClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void isClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void click(By locator, int seconds, String elementName) {

    int maxAttempts = 3;

    try {

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {
                handleToastIfVisible();

                WebElement element = getClickableElement(locator, seconds);
                element.click();

                return;

            } catch (StaleElementReferenceException |
                     ElementNotInteractableException e) {

                if (attempt == maxAttempts) {
                    throw e;
                }
            }
        }

    } 
        finally {

            try {
                String screenshotPath =
                        ScreenshotManager.capture(driver, elementName);

                ClickReportManager.logClick(
                        elementName,
                        screenshotPath
                );

            } catch (Exception e) {

                System.out.println(
                    "Failed to capture click screenshot for: "
                    + elementName
                    + " | "
                    + e.getMessage()
                );
            }
        }
    }

    public void click(By locator, int seconds) {
    int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                handleToastIfVisible();
                WebElement element = getClickableElement(locator, seconds);
                element.click();
                return;
            } 
            
            catch (StaleElementReferenceException | ElementNotInteractableException e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
            }
        }
    }

    public void clickWithJS(By locator) {
        for (int i = 0; i < 3; i++) {
            try {
                handleToastIfVisible();
                WebElement element = getClickableElement(locator);
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (StaleElementReferenceException e) {
                if (i == 2) {
                    throw new RuntimeException("Element remained stale after 3 attempts: " + locator, e);
                }
            }
        }
    }

    public void clickWithJS(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void clickWithRetry(By locator) {
        for (int i = 0; i < 3; i++) {
            try {
                handleToastIfVisible();
                WebElement element = getClickableElement(locator);
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                if (i == 2) {
                    throw new RuntimeException("Unable to click element after 3 attempts: " + locator, e);
                }
            }
        }
    }

    public void clickWithRetry(By locator, int seconds) {
        for (int i = 0; i < 3; i++) {
            try {
                handleToastIfVisible();
                WebElement element = getClickableElement(locator, seconds);
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                if (i == 2) {
                    throw new RuntimeException("Unable to click element after 3 attempts: " + locator, e);
                }
            }
        }
    }

    public void type(By locator, String text) {

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {
                handleToastIfVisible();

                WebElement element = getClickableElement(locator);

                // Scroll element to the center of the viewport
                scrollIntoView(element);

                // Wait until the element is clickable after scrolling
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(element));

                // Click the field
                element.click();

                // Clear existing value
                element.sendKeys(Keys.CONTROL, "a");
                element.sendKeys(Keys.BACK_SPACE);

                // Enter new value
                element.sendKeys(text);

                return;

            } 
            catch (StaleElementReferenceException | InvalidElementStateException e) {
                if (attempt == 3) {
                    throw new RuntimeException( "Unable to type into element after 3 attempts: " + locator,e);
                }
            }
        }
    }

    public void selectOption(By dropdownLocator, String optionText) {

        for (int i = 0; i < 3; i++) {

            try {
                handleToastIfVisible();

                WebElement dropdown = getClickableElement(dropdownLocator);
                dropdown.click();

                By optionLocator = By.xpath(
                    "//*[@role='option' and normalize-space(.)='" + optionText + "']"
                );

                WebElement option = getClickableElement(optionLocator);
                option.click();

                return;

            } catch (StaleElementReferenceException |
                    ElementNotInteractableException |
                    TimeoutException e) {

                if (i == 2) {
                    throw new RuntimeException(
                        "Unable to select option '" + optionText +
                        "' after 3 attempts: " + dropdownLocator,
                        e
                    );
                }
            }
        }
    }

    public void selectOption(By dropdownLocator, By optionLocator) {

        for (int i = 0; i < 3; i++) {

            try {
                handleToastIfVisible();

                WebElement dropdown = getClickableElement(dropdownLocator);
                dropdown.click();

                WebElement option = getClickableElement(optionLocator);
                option.click();

                return;

            } 
            catch (StaleElementReferenceException | ElementNotInteractableException | TimeoutException e) {
                if (i == 2) {
                    throw new RuntimeException("Unable to select option after 3 attempts. " + "Dropdown: " + dropdownLocator + ", Option: " + optionLocator, e);
                    }
            }
        }
    }

    public void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToAppear(By locator, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToAppear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementToDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForAllElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForCartProductsToLoad(By locator) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0));
    }

    public void waitForUrl(String keyword) {
        wait.until(ExpectedConditions.urlContains(keyword));
    }

    public void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void isAlertVisible() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForFrameAndSwitch(WebElement frame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public String getCurrentWindow() {
        return driver.getWindowHandle();
    }

    public void switchToNewWindow(String parentWindow) {
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                return;
            }
        }
        throw new RuntimeException("New window was not found");
    }

    public void closeCurrentWindow() {
        driver.close();
    }

    public void switchToWindow(String windowName) {
        driver.switchTo().window(windowName);
    }

    public void loadProperties() throws Exception {
        String path = System.getProperty("user.dir") + "/src/test/resources/config/GlobalData.properties";
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        }
    }

    public void customWait(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

    public void customWait(int seconds, String message) throws InterruptedException {
        showLoader(message);
        try {
            Thread.sleep(seconds * 1000L);
        } finally {
            hideLoader();
        }
    }

    public void disablePointerEvents(WebElement element) {
        js.executeScript("arguments[0].style.pointerEvents='none';", element);
    }

    public void removeDesigne(WebElement element) {
        js.executeScript("arguments[0].style.display='none';", element);
    }

    public void uploadFile(By locator, String filePath) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].removeAttribute('hidden');", element);
        element.sendKeys(filePath);
    }

    public void typeCharacterByCharacter(String text) {
        for (char character : text.toCharArray()) {
            action.sendKeys(String.valueOf(character)).pause(Duration.ofMillis(100)).perform();
         }
    }

    public void showLoader(String message) {
        js.executeScript(
                "var loader=document.createElement('div');"
                        + "loader.id='customLoader';"
                        + "loader.innerHTML=arguments[0];"
                        + "loader.style.position='fixed';"
                        + "loader.style.top='0';"
                        + "loader.style.left='0';"
                        + "loader.style.width='100%';"
                        + "loader.style.height='100%';"
                        + "loader.style.background='rgba(0,0,0,0.7)';"
                        + "loader.style.color='white';"
                        + "loader.style.fontSize='30px';"
                        + "loader.style.fontWeight='bold';"
                        + "loader.style.display='flex';"
                        + "loader.style.justifyContent='center';"
                        + "loader.style.alignItems='center';"
                        + "loader.style.zIndex='999999';"
                        + "document.body.appendChild(loader);",
                message
        );
    }

    public void hideLoader() {
        js.executeScript("var loader = document.getElementById('customLoader');" + "if(loader){loader.remove();}");
    }

    public void setDate(By locator, String date) {
        WebElement element = getClickableElement(locator);
        element.sendKeys(date);
    }

    public void waitForNotificationToDisappear(int seconds) {
        By notification = By.xpath("//section[@aria-label='Notifications alt+T']");
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
        .until(ExpectedConditions.invisibilityOfElementLocated(notification));
    }

    public void closeToastIfVisible(By closeToastButton) {
        try {
            List<WebElement> closeButtons = driver.findElements(closeToastButton);
            if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
                closeButtons.get(0).click();
            }
        } catch (Exception ignored) {
        }
    }

    public void handleToastIfVisible() {
    try {
        List<WebElement> closeButtons = driver.findElements(
                By.xpath("//button[@aria-label='Close toast' and @data-close-button='true']")
        );

        if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
            closeButtons.get(0).click();
            Thread.sleep(1000);
        }
    } catch (Exception ignored) {
    }
}

    public void switchToNewWindow(Set<String> existingWindows) {
    wait.until(driver -> driver.getWindowHandles().size() > existingWindows.size());

        for (String windowHandle : driver.getWindowHandles()) {
            if (!existingWindows.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                return;
            }
        }

        throw new RuntimeException("New window was not found");
    }
}
