package pages;

import core.SeleniumActions;
import org.openqa.selenium.By;

public final class LoginPage {
    private final SeleniumActions actions;
    private final By emailField = By.id("email");
    private final By passwordField = By.id("pass");
    private final By signInBtn = By.id("send2");
    private final By errorMessage = By.cssSelector(".message-error");

    public LoginPage() {
        this.actions = new SeleniumActions();
    }

    public void enterEmail(String email) {
        actions.type(emailField, email);
    }

    public void enterPassword(String password) {
        actions.type(passwordField, password);
    }

    public MyAccountPage clickSignIn() {
        actions.click(signInBtn);
        return new MyAccountPage();
    }

    public boolean isErrorMessageDisplayed() {
        return actions.isVisible(errorMessage);
    }

    public String getErrorMessage() {
        return actions.getText(errorMessage);
    }
}