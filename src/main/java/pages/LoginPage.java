package pages;

import core.SeleniumActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public final class LoginPage {
    private final SeleniumActions actions;
    private final By emailField = By.id("email");
    private final By passwordField = By.id("pass");
    private final By signInBtn = By.id("send2");
    private final By errorMessage = By.xpath("//div[@for='password']");
    private final By ads = By.cssSelector("google_vignette");
    private final By adCloseBtn = By.cssSelector("div[role='dialog'] button[aria-label='Close ad']"); // ✅ added at top

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

    public String getErrorMessage() throws InterruptedException {
        Thread.sleep(4000);
        return actions.getText(errorMessage);
    }
    public void clickLoginButton() {
        actions.click(signInBtn);
    }
}