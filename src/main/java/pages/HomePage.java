package pages;

import core.SeleniumActions;
import org.openqa.selenium.By;

public final class HomePage {
    private final SeleniumActions actions;
    private final By createAccountLink = By.linkText("Create an Account");
    private final By signInLink = By.linkText("Sign In");
    private final By welcomeMessage = By.cssSelector(".welcome-msg");

    public HomePage() {
        this.actions = new SeleniumActions();
    }

    public void navigateToHomePage() {
        actions.navigateTo("https://magento.softwaretestingboard.com/");
    }

    public CreateAccountPage clickCreateAccount() {
        actions.click(createAccountLink);
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