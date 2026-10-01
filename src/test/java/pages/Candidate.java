package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import actions.CommonActions;

public class Candidate extends CommonActions{
        public Candidate(WebDriver driver) throws Exception {
        super(driver);
    }

        By clickCandidate = By.xpath(
        "//span[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'candidates')]"
    );

    By searchCandidate = By.xpath(
        "//input[@placeholder='Filter by name or email']"
    );

    public void searchCandidate(String generatedEmail) {
        click(clickCandidate, 120);
        click(searchCandidate, 120);
        type(searchCandidate, generatedEmail);
    }
}
