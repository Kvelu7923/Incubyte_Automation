package pages;

import core.SeleniumActions;
import org.openqa.selenium.By;

public final class CreateAccountPage {
    private final SeleniumActions actions;
    private final By firstName = By.id("firstname");
    private final By lastName = By.id("lastname");
    private final By email = By.id("email_address");
    private final By password = By.id("password");
    private final By confirmPassword = By.id("password-confirmation");
    private final By createAccountBtn = By.cssSelector("button[title='Create an Account']");
    private final By successMessage = By.cssSelector(".message-success");

    public CreateAccountPage() {
        this.actions = new SeleniumActions();
    }

    public void fillRegistrationForm(String fName, String lName, String email, String pwd) {
        actions.type(firstName, fName);
        actions.type(lastName, lName);
        actions.type(this.email, email);
        actions.type(password, pwd);
        actions.type(confirmPassword, pwd);
    }

    public MyAccountPage submitRegistration() {
        actions.click(createAccountBtn);
        return new MyAccountPage();
    }

    public boolean isSuccessMessageDisplayed() {
        return actions.isVisible(successMessage);
    }
}