package pages;

import java.time.Duration;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import actions.CommonActions;

public class VerifyCandidate extends CommonActions {

    // ADDED ON 2026-10-05 12:18 IST: Receives the active browser driver and creates the existing admin-login page object for candidate verification.
    public VerifyCandidate(WebDriver driver) throws Exception {
        super(driver);
    }

    By clickEOR = By.xpath("//a[@href='/employees']");
    By clickEmployee = By.xpath("//span[text()='Employees']");

    By searchEmployee = By.xpath("//input[@placeholder='Search by name, code, or email']");
    // ADDED ON 2026-10-07 12:58 IST: Identifies the employee-list rows so searching begins only after the EOR table is rendered.
    By employeeTableRows = By.xpath("//table//tbody/tr");
    // ADDED ON 2026-10-07 13:29 IST: Identifies the table footer displayed only after the Employees results have loaded.
    By employeeTableSummary = By.xpath(
        "//*[starts-with(normalize-space(.), 'Showing ') and contains(normalize-space(.), ' employees')]"
    );

    // ADDED ON 2026-10-07 13:05 IST: Identifies the View option displayed by the matching employee row's Actions menu.
    By viewButton = By.xpath("//*[normalize-space()='View']");
    By verifyCandidate = By.xpath("//*[text()='Verify']");

    public void searchEmployee(String generatedEmail) {
        click(clickEmployee, 120, "Employees menu");
        waitForElementToAppear(clickEOR, 120);
        click(clickEOR, 120, "EOR tab");

        // UPDATED ON 2026-10-07 13:29 IST: Waits for both EOR table rows and its loaded-results footer before a search is entered.
        waitForPageLoad();
        waitForElementToAppear(employeeTableRows, 120);
        waitForElementToAppear(employeeTableSummary, 120);
        waitForElementToAppear(searchEmployee, 120);
        type(searchEmployee, generatedEmail);

        // UPDATED ON 2026-10-07 13:29 IST: Continues only when filtering has completed and exactly one visible row matches the searched email.
        waitForSingleFilteredEmployee(generatedEmail);
        actionButton(generatedEmail);

        // UPDATED ON 2026-10-07 13:05 IST: Waits for Verify on the employee detail view after the row's View option has opened it.
        waitForElementToAppear(verifyCandidate, 120);
        click(verifyCandidate, 120, "Verify candidate");

        // ADDED ON 2026-10-07 12:58 IST: Confirms the verification redirect returns to the Employees page and that the document has completed loading.
        waitForUrl("/employees");
        waitForPageLoad();
    }

    public void actionButton(String generatedEmail) {
        By actionButton = By.xpath(employeeRowXpath(generatedEmail) + "//button[@title='Actions']");

        // UPDATED ON 2026-10-07 13:05 IST: Opens Actions for the exact email row, waits for its View option, and opens the employee detail view.
        waitForElementToAppear(actionButton, 120);
        click(actionButton, 120, "Candidate actions");
        waitForElementToAppear(viewButton, 120);
        click(viewButton, 120, "View candidate");

        // ADDED ON 2026-10-07 13:05 IST: Allows the employee detail view opened by View to finish rendering before Verify is requested.
        waitForPageLoad();
    }

    // ADDED ON 2026-10-07 12:58 IST: Builds one case-insensitive row locator for both the search-result wait and its Actions button.
    private By employeeRow(String generatedEmail) {
        return By.xpath(employeeRowXpath(generatedEmail));
    }

    // ADDED ON 2026-10-07 13:29 IST: Prevents Actions from opening while the email filter is still replacing the initial EOR table rows.
    private void waitForSingleFilteredEmployee(String generatedEmail) {
        new WebDriverWait(driver, Duration.ofSeconds(120)).until(webDriver ->
            webDriver.findElements(employeeTableRows).size() == 1
                    && !webDriver.findElements(employeeRow(generatedEmail)).isEmpty()
        );
    }

    // ADDED ON 2026-10-07 12:58 IST: Keeps the email comparison locale-independent while locating the exact employee row.
    private String employeeRowXpath(String generatedEmail) {
        return "//tr[.//*[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                + generatedEmail.toLowerCase(Locale.ROOT) + "']]";
    }
}
