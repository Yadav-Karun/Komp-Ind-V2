package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actions.CommonActions;

public class ForgotPassword extends CommonActions{

    public ForgotPassword(WebDriver driver) throws Exception {
        super(driver);
    }    

    By forgotPassword = By.xpath("//a[normalize-space()='Forgot password?']");
    By enterEmail = By.xpath("//input[@id='email']");  
    By sendOTP = By.xpath("//button[normalize-space()='Send reset code']");

    By enterOTP = By.xpath("//input[@id='otp']");
    // UPDATED ON 2026-10-03 12:44 IST: Uses normalized text so harmless whitespace around the OTP confirmation does not break the wait.
    By verifiedCode = By.xpath("//span[normalize-space()='Code verified']");
    By enterNewPassword = By.xpath("//input[@id='newPassword']");
    By enterConfirmNewPassword = By.xpath("//input[@id='confirmPassword']");
    By resetPassword = By.xpath("//button[normalize-space()='Reset password']");

    By goToSignIn = By.xpath("//a[normalize-space()='Go to sign in']");

    // UPDATED ON 2026-10-03 12:44 IST: Sends only the reset code so Yopmail can retrieve the OTP before reset completion.
    public void sendResetCode(String candidateEmail) {
        click(forgotPassword, 10, "Forgot password");
        type(enterEmail, candidateEmail);
        click(sendOTP, 10, "Send OTP");
    }

    // ADDED ON 2026-10-03 12:44 IST: Completes the candidate-side reset after Yopmail has supplied the OTP.
    public void resetPassword(String otp, String newPassword) {
        type(enterOTP, otp);
        waitForElementToAppear(verifiedCode, 10);
        type(enterNewPassword, newPassword);
        type(enterConfirmNewPassword, newPassword);
        click(resetPassword, 10, "Reset password");
        waitForElementToAppear(goToSignIn, 10);
        click(goToSignIn, 10, "Go to sign in");
    }
}
