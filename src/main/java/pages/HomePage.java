package pages;

import core.DriverManager;
import core.SeleniumActions;
import org.openqa.selenium.By;
import utils.PopupHandler;

public final class HomePage {
    private final SeleniumActions actions;
    private PopupHandler popupHandler;

    private final By createAccountLink = By.linkText("Create an Account");
    private final By signInLink = By.xpath("(//a[contains(text(),'Sign In')])[1]");
    private final By welcomeMessage = By.cssSelector(".welcome-msg");
    private final By adpresent = By.cssSelector("div[role='dialog'] button[aria-label='Close ad']");

    public HomePage() {
        this.actions = new SeleniumActions();
    }


    public void navigateToHomePage() {
        actions.navigateTo("https://magento.softwaretestingboard.com/");

    }

    public CreateAccountPage clickCreateAccount() {
        actions.click(createAccountLink);
        System.out.println("Create account link Clicked");
        PopupHandler popupHandler = new PopupHandler(DriverManager.getDriver());
        popupHandler.tryToClosePopupWithRandomClicks();
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