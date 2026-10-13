package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actions.CommonActions;
import models.Data;

public class LoginPage extends CommonActions {
	public LoginPage(WebDriver driver) throws Exception {
		super(driver);
	}

	By email = By.xpath("//input[@id='email']");

	By password = By.xpath("//input[@id='password']");

	By signInButton = By.xpath("//button[normalize-space()='Sign in']");

	By loginErrorMessage = By.xpath("//p[contains(normalize-space(.), 'Login failed')]");

	By clickOnCandidateLogin = By.xpath("//a[normalize-space()='Candidate']");

	By candidateloginURL = By.xpath("//a[contains(text(),'Candidate Login')]");

	public void goToLoginPage() {
		driver.get(properties.getProperty("kompURL"));
		waitForPageLoad();
	}

	public void enterCredentials(Data input) {
		String url = properties.getProperty("kompURL");
		
		if (url.contains("sandbox.kompglobal.com")) {
			type(email, input.getAdminEmailSandbox());
			type(password, input.getAdminPasswordSandbox());
		} 
		
		else if (url.contains("app.kompglobal.com")) {
			type(email, input.getAdminEmail());
			type(password, input.getAdminPassword());
		} 
		
		else if (url.contains("preprod.kompglobal.com")) {
			type(email, input.getAdminEmailPreprod());
			type(password, input.getAdminPasswordPreprod());
		}
		
		else {

			throw new RuntimeException("Unknown Environment : " + url);
		}
		click(signInButton, 10, "Login as admin");
	}

	public void loginAsAdmin(Data input) {
		goToLoginPage();
		enterCredentials(input);
	}

	public void loginAsEmployee(String emailID, String passcode) {
		type(email, emailID);
		type(password, passcode);
		click(signInButton, 10, "Login as Candidate");
	}

	public void clickCandidateLoginLink() {
        // UPDATED ON 2026-10-08 11:22 IST: Clicks the visible Candidate link on the post-logout User Login page, then waits for the candidate login page to render.
        click(clickOnCandidateLogin, 120, "Candidate login link");
        waitForPageLoad();
    }

	public boolean isLoginErrorVisible() {
		try {
			return driver.findElement(loginErrorMessage).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
