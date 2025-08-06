package pages;

import core.SeleniumActions;
import org.openqa.selenium.By;

public final class MyAccountPage {
    private final SeleniumActions actions;
    private final By pageTitle = By.cssSelector(".base");
    private final By contactInfo = By.cssSelector(".box-information");
    private final By logoutLink = By.linkText("Sign Out");
    private final By welcome = By.xpath("(//span[@class='customer-name'])[1]");


    public MyAccountPage() {
        this.actions = new SeleniumActions();
    }

    public String getPageTitle() {
        return actions.getText(pageTitle);
    }

    public String getContactInfo() {
        return actions.getText(contactInfo);
    }

    public boolean isLoggedIn() {
        return actions.isVisible(welcome);
    }
    public HomePage logout() {
        actions.click(welcome);
        actions.click(logoutLink);
        return new HomePage();
    }
}