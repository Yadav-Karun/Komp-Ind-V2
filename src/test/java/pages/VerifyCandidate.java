package pages;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import actions.CommonActions;

public class VerifyCandidate extends CommonActions {

    // ADDED ON 2026-10-05 12:18 IST: Receives the active browser driver and creates the existing admin-login page object for candidate verification.
    public VerifyCandidate(WebDriver driver) throws Exception {
        super(driver);
    }

    By clickEOR = By.xpath("//a[@href='/employees']");
    By clickEmployee = By.xpath("//span[text()='Employees']");

    By searchEmployee = By.xpath("//input[@placeholder='Search by name, code, or email']");
    By viewButton = By.xpath("//*[contains(text(),'View')]");

    By verifyCandidate = By.xpath("//*[text()='Verify']");

    public void searchCandidate(String generatedEmail) {
        click(clickEmployee, 120, "Employees menu");
        waitForElementToAppear(clickEOR, 120);
        click(clickEOR, 120, "EOR tab");

        type(searchEmployee, generatedEmail);
        actionButton(generatedEmail);
        click(verifyCandidate, 120, "Verify candidate");
    }

    public void actionButton(String generatedEmail) {
        By actionButton = By.xpath(
            "//tr[.//*[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" 
            + generatedEmail.toLowerCase(Locale.ROOT) + "']]//button[@title='Actions']"
        );

        // UPDATED ON 2026-10-05 16:32 IST: Normalizes the email consistently and waits for Actions to be visible and clickable before opening it.
        waitForElementToAppear(actionButton, 120);
        click(actionButton, 120, "Candidate actions");

        // ADDED ON 2026-10-05 16:32 IST: Waits for the View option to be visible and clickable after the Actions menu opens.
        waitForElementToAppear(viewButton, 120);
        click(viewButton, 120, "View candidate");
    }
}
