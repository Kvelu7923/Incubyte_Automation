package pages;

import core.DriverManager;
import core.SeleniumActions;
import org.openqa.selenium.By;
import utils.PopupHandler;

public final class HomePage {
    private final SeleniumActions actions;
    private PopupHandler popupHandler;

    private final By createAccountLink = By.linkText("Create an Account");
    private final By signInLink = By.linkText("Sign In");
    private final By welcomeMessage = By.cssSelector(".welcome-msg");
    private final By adpresent = By.cssSelector("div[role='dialog'] button[aria-label='Close ad']");

    public HomePage() {
        this.actions = new SeleniumActions();
    }


    public void navigateToHomePage() {
        actions.navigateTo("https://magento.softwaretestingboard.com/");
 // close if ad appears immediately

    }

    public CreateAccountPage clickCreateAccount() {
//        actions.handleAdIfPresent(adpresent, 15);
        actions.click(createAccountLink);
        PopupHandler popupHandler = new PopupHandler(DriverManager.getDriver());
        popupHandler.closeAdPopupIfPresent();
        return new CreateAccountPage();
    }

    public LoginPage clickSignIn() {
        actions.click(signInLink);
        return new LoginPage();
    }

    public String getWelcomeMessage() {
        return actions.getText(welcomeMessage);
    }
}