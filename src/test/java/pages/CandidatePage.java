package pages;

import java.time.Duration;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actions.CommonActions;

// UPDATED ON 2026-10-06 12:17 IST: Renamed from Candidate to CandidatePage so this page object follows the framework naming convention; its existing behaviour is unchanged.
public class CandidatePage extends CommonActions{
        public CandidatePage(WebDriver driver) throws Exception {
        super(driver);
    }

    // UPDATED ON 2026-10-07 11:07 IST: Resolves the closest interactive Candidates sidebar control rather than its text label, supporting either button- or link-based navigation.
    By clickCandidate = By.xpath(
        "(//span[normalize-space()='Candidates']/ancestor::*[self::button or self::a or @role='button'][1])[1]"
    );
    // ADDED ON 2026-10-07 15:08 IST: Confirms that the Candidates listing, not merely a URL containing the word candidates, has rendered after the sidebar click.
    By candidatePageHeading = By.xpath("//h1[normalize-space()='Candidates']");
    // ADDED ON 2026-10-07 15:10 IST: Identifies rendered Candidate-table rows so the isolated navigation run waits for table data, not just the page heading.
    By candidateTableRows = By.xpath("//table//tbody/tr");
    // ADDED ON 2026-10-07 15:10 IST: Identifies the populated Candidates results footer, confirming the table has finished loading.
    By candidateTableSummary = By.xpath(
        "//*[starts-with(normalize-space(.), 'Showing ') and contains(normalize-space(.), ' candidates')]"
    );

    // UPDATED ON 2026-10-07 15:53 IST: Re-enabled the exact Candidates-page filter for the isolated email-search step; it avoids the global header search.
    By searchCandidate = By.xpath(
        "//input[@placeholder='Filter by name or email']"
    );

    // COMMENTED ON 2026-10-07 15:03 IST: This unused status locator remains paused while the Candidate selection uses its row-scoped status check.
    /*
    By isVerified = By.xpath("//span[contains(text(), 'Verified')]");
    */

    // UPDATED ON 2026-10-07 17:27 IST: Re-enabled only the button that opens the Send Offer Letter dialog after Candidate selection.
    By clickSendOfferLetter = By.xpath("//*[contains(normalize-space(text()), 'Send Offer Letter')]");

    // UPDATED ON 2026-10-08 10:45 IST: Re-enabled the Offer Letter dialog controls for template selection and final sending.
    By offerLetterDropdown = By.xpath("//button[contains(normalize-space(.), 'Choose a template')]");

    By selectOfferLetterTemplate = By.xpath("//span[contains(normalize-space(.), 'DBZ - Offer Letter')]");

    // UPDATED ON 2026-10-07 12:02 IST: Targets the exact Send button in the active Offer Letter dialog; the previous XPath was incomplete and could not reliably address the dialog footer.
    By clickSend = By.xpath(
        "//div[@role='dialog']//div[@data-slot='dialog-footer']//button[normalize-space()='Send']"
    );

    By clickDone = By.xpath("//button[text()='Done']");

    By acceptOfferLetter = By.xpath("//button[contains(text(), 'Accept Offer')]");

    // By candidateloginURL = By.xpath("//a[contains(text(),'Candidate Login')]");

    public void searchCandidate(String generatedEmail) {
        // UPDATED ON 2026-10-07 15:53 IST: Navigates to Candidates, then applies only the exact-email search for this staged run.
        navigateToCandidatesPage();

        waitForElementToAppear(searchCandidate, 120);
        type(searchCandidate, generatedEmail);

        // ADDED ON 2026-10-07 15:53 IST: Prevents later steps until filtering leaves exactly one Candidate-table row matching the generated email.
        waitForSingleFilteredCandidate(generatedEmail);

        // UPDATED ON 2026-10-07 16:09 IST: Selects the one remaining filtered Candidate row only after the exact-email table check has completed.
        selectCandidate(generatedEmail);
    }

    // UPDATED ON 2026-10-07 15:08 IST: Always clicks Candidates and records that click, then confirms the Candidates listing has rendered for the isolated observation run.
    private void navigateToCandidatesPage() {
        TimeoutException lastTimeout = null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            click(clickCandidate, 120, "Candidates menu");

            try {
                new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.urlContains("/candidates"));
                waitForElementToAppear(candidatePageHeading, 120);

                // ADDED ON 2026-10-07 15:10 IST: Waits for the Candidates page, its rows, and its populated footer to finish rendering before this isolated run ends.
                waitForPageLoad();
                waitForElementToAppear(candidateTableRows, 120);
                waitForElementToAppear(candidateTableSummary, 120);
                return;
            } catch (TimeoutException timeoutException) {
                lastTimeout = timeoutException;
            }
        }

        throw new RuntimeException("Candidates sidebar navigation did not activate the /candidates route.", lastTimeout);
    }

    // ADDED ON 2026-10-07 15:53 IST: Builds a locale-independent locator for the exact row expected after the Candidate-table filter finishes.
    private By candidateRow(String generatedEmail) {
        String normalizedEmail = generatedEmail.trim().toLowerCase(Locale.ROOT);
        return By.xpath(
            "//tr[.//*[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                + normalizedEmail
                + "']]"
        );
    }

    // ADDED ON 2026-10-07 15:53 IST: Waits for one filtered row and verifies that the remaining row is the generated candidate.
    private void waitForSingleFilteredCandidate(String generatedEmail) {
        new WebDriverWait(driver, Duration.ofSeconds(120)).until(webDriver ->
            webDriver.findElements(candidateTableRows).size() == 1
                    && !webDriver.findElements(candidateRow(generatedEmail)).isEmpty()
        );
    }

    // ADDED ON 2026-10-08 10:49 IST: Locates the generated candidate row only after its Offer Letter status has updated to PENDING.
    private By offerLetterPendingStatus(String generatedEmail) {
        String normalizedEmail = generatedEmail.trim().toLowerCase(Locale.ROOT);
        return By.xpath(
            "//tr[.//*[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                + normalizedEmail
                + "']][.//*[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='pending']]"
        );
    }

    // UPDATED ON 2026-10-08 10:51 IST: Verifies the PENDING status with a 3-second wait, then up to 10 Ctrl+Shift+R hard refreshes if the status has not updated.
    private void verifyOfferLetterPending(String generatedEmail) {
        hardRefreshUntilElementVisible(offerLetterPendingStatus(generatedEmail), 3, 10);
    }

    public void selectCandidate(String generatedEmail) {
        // UPDATED ON 2026-10-07 16:09 IST: Checks that the single filtered Candidate is Verified before selecting its multi-select checkbox.
        String normalizedEmail = generatedEmail.trim().toLowerCase(Locale.ROOT);
        String candidateRow = "//tr[.//*[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                + normalizedEmail
                + "']]";
        By profileStatusVerified = By.xpath(
            candidateRow
            + "//*[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='verified']"
        );
        By multiSelectCheckbox = By.xpath(
            candidateRow + "//button[@aria-label='Select row']"
        );

        // ADDED ON 2026-10-07 11:12 IST: Prevents selection until this candidate's Profile Status is displayed as Verified.
        waitForElementToAppear(profileStatusVerified, 120);
        waitForElementToAppear(multiSelectCheckbox, 120);

        // ADDED ON 2026-10-07 11:10 IST: Uses a physical pointer action because this checkbox did not respond to the standard WebElement click.
        WebElement candidateCheckbox = getClickableElement(multiSelectCheckbox, 120);
        scrollIntoView(candidateCheckbox);
        action.moveToElement(candidateCheckbox).click().perform();
    }
    public void sendOfferLetter(String generatedEmail) {
        // UPDATED ON 2026-10-08 10:45 IST: Opens the Send Offer Letter dialog after selection, then selects the configured template and sends it.
        WebElement offerLetterButton = getClickableElement(clickSendOfferLetter, 120);
        scrollIntoView(offerLetterButton);
        click(clickSendOfferLetter, 120, "Send Offer Letter");

        // ADDED ON 2026-10-08 10:45 IST: Waits for the dialog template control before choosing the required offer-letter template.
        WebElement dropdown = getClickableElement(offerLetterDropdown, 120);
        scrollIntoView(dropdown);
        action.moveToElement(dropdown).click().perform();

        WebElement offerLetterTemplate = getClickableElement(selectOfferLetterTemplate, 120);
        scrollIntoView(offerLetterTemplate);
        action.moveToElement(offerLetterTemplate).click().perform();

        // ADDED ON 2026-10-08 10:45 IST: Uses an explicit visibility wait instead of a fixed delay before the dialog's final Send click.
        waitForElementToAppear(clickSend, 120);
        click(clickSend, 120, "Send");

        // ADDED ON 2026-10-08 10:49 IST: Waits for the generated candidate's Offer Letter status to update to PENDING after sending.
        verifyOfferLetterPending(generatedEmail);
    }

    public void acceptOfferLetter() {

    }

}
