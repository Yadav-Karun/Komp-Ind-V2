package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actions.CommonActions;
import utils.PasswordExtractor;
import utils.Retry;

public class Yopmail extends CommonActions {

    private final By enterGeneratedEmail = By.id("login");
    private final By loginYopmail = By.xpath("//i[@class='material-icons-outlined f36']");
    private final By mailFrame = By.id("ifmail");
    private final By inboxFrame = By.id("ifinbox");
    private final By refreshInboxButton = By.id("refresh");
    private final By loiMailLocator = By.xpath("//div[contains(normalize-space(.), 'Letter of intent')]");
    private final By candidateMailLocator = By.xpath("//div[contains(normalize-space(.), 'Candidate Login')]");
    private final By acceptOfferButton = By.xpath("//a[normalize-space()='Accept Offer']");
    private final By congratulationsMessage = By.xpath("//*[text()='Congratulations']");
    private final By emailBody = By.tagName("body");
    private final By accessPortal = By.xpath("//a[normalize-space()='Access Portal']");

    public Yopmail(WebDriver driver) throws Exception {
        super(driver);
    }

    public void goToYopmailEmployee(String employeeEmail) {
        driver.get("https://yopmail.com");
        type(enterGeneratedEmail, employeeEmail);
        click(loginYopmail, 10, "Open Candidate mail: " + employeeEmail);
    }

    public void acceptEmployeeLoi() throws Exception {
        Retry.refreshInboxUntilMailFound(driver, wait, refreshInboxButton, inboxFrame, loiMailLocator, "Letter of Intent");
        waitForFrameAndSwitch(driver.findElement(inboxFrame));
        clickWithRetry(loiMailLocator);

        driver.switchTo().defaultContent();
        waitForFrameAndSwitch(driver.findElement(mailFrame));
        String yopmailWindow = getCurrentWindow();
        waitForElementToAppear(acceptOfferButton);
        click(acceptOfferButton, 10, "Accept LOI");

        switchToNewWindow(yopmailWindow);
        waitForElementToAppear(congratulationsMessage);
        closeCurrentWindow();
        switchToWindow(yopmailWindow);
        driver.switchTo().defaultContent();
    }

    public String getEmployeeLoginPassword() throws Exception {
        Retry.refreshInboxUntilMailFound(driver, wait, refreshInboxButton, inboxFrame, candidateMailLocator, "Candidate Login");
        waitForFrameAndSwitch(driver.findElement(inboxFrame));
        clickWithRetry(candidateMailLocator);

        driver.switchTo().defaultContent();
        waitForFrameAndSwitch(driver.findElement(mailFrame));
        String mailBodyText = getElement(emailBody).getText();
        //driver.switchTo().defaultContent();

        String employeePassword = PasswordExtractor.extractEmployeePassword(mailBodyText);

        Set<String> existingWindows = driver.getWindowHandles();

        click(accessPortal, 10, "Go to Candidate portal, " + employeePassword);

        switchToNewWindow(existingWindows);
        driver.switchTo().defaultContent();
        waitForUrl("/candidate/login");

        return employeePassword;
    }
}
