package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actions.CommonActions;

public class Logout extends CommonActions{
    public Logout(WebDriver driver) throws Exception {
		super(driver);
	}

    By clickOnUserProfile = By.xpath("//button[@aria-haspopup='menu' and @data-slot='dropdown-menu-trigger'][2]");
    By logoutButton = By.xpath("//div[@role='menuitem' and normalize-space()='Log out']");
    By confirmLogout = By.xpath("//button[normalize-space()='Log out']");

    public void logout() throws Exception {
        click(clickOnUserProfile, 10);
        
        waitForElementToAppear(logoutButton);
        click(logoutButton, 10);

        waitForElementToAppear(confirmLogout);
        click(confirmLogout, 10);
    }
}
