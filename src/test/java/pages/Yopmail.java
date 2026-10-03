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
    // UPDATED ON 2026-10-03 12:44 IST: Waits for the exact OTP email subject shown in the Yopmail inbox.
    private final By passwordResetMailLocator = By.xpath("//*[contains(text(), 'Your OTP Code')]");
    private final By acceptOfferButton = By.xpath("//a[normalize-space()='Accept Offer']");
    private final By congratulationsMessage = By.xpath("//*[text()='Congratulations']");
    private final By emailBody = By.tagName("body");
    // ADDED ON 2026-10-03 12:44 IST: Reads the OTP from the mail body after switching into Yopmail's mail iframe.
    private final By passwordResetOtpValue = By.xpath(
            "//p[contains(text(),'Your one-time code')]/following-sibling::p[1]"
    );
    private final By accessPortal = By.xpath("//a[normalize-space()='Access Portal']");

    public Yopmail(WebDriver driver) throws Exception {
        super(driver);
    }

    public void goToYopmailEmployee(String employeeEmail) {
        driver.get("https://yopmail.com");
        type(enterGeneratedEmail, employeeEmail);
        click(loginYopmail, 10, "Open Candidate mail: " + employeeEmail);
    }

    // ADDED ON 2026-10-03 12:44 IST: Returns to the existing Yopmail window without creating another inbox session.
    public void switchToYopmailWindow() {
        switchToWindowContainingUrl("yopmail.com");
        driver.switchTo().defaultContent();
    }

    // ADDED ON 2026-10-03 12:44 IST: Returns to the KOMP candidate window after reading an email from Yopmail.
    public void switchToCandidateWindow() {
        switchToWindowContainingUrl("/candidate/");
        driver.switchTo().defaultContent();
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

    // UPDATED ON 2026-10-03 12:44 IST: Waits for the OTP email, opens its mail iframe, and returns the displayed OTP value.
    public String getPasswordResetOtp() throws Exception {
        switchToYopmailWindow();
        Retry.refreshInboxUntilMailFound(
                driver,
                wait,
                refreshInboxButton,
                inboxFrame,
                passwordResetMailLocator,
                "Password Reset"
        );

        waitForFrameAndSwitch(driver.findElement(inboxFrame));
        // UPDATED ON 2026-10-03 12:44 IST: Uses the shared click helper so opening the OTP email automatically creates the report screenshot.
        click(passwordResetMailLocator, 10, "OTP email received");

        driver.switchTo().defaultContent();
        waitForFrameAndSwitch(driver.findElement(mailFrame));
        String otp = getElement(passwordResetOtpValue).getText().trim();
        driver.switchTo().defaultContent();

        return otp;
    }
    
}
