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
    By verifiedCode = By.xpath("//span[text()=' Code verified']");
    By enterNewPassword = By.xpath("//input[@id='newPassword']");
    By enterConfirmNewPassword = By.xpath("//input[@id='confirmPassword']");
    By resetPassword = By.xpath("//button[normalize-space()='Reset password']");

    By goToSignIn = By.xpath("//a[normalize-space()='Go to sign in']");

    public void forgotPassword(String candidateEmail,String otp,String newPassword){
        click(forgotPassword, 10, "Forgot password");
        type(enterEmail, candidateEmail);
        click(sendOTP, 10, "Send OTP");
    }
}
