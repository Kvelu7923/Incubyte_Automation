package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.*;
import base.BaseTest;
import utils.RandomGenerator;
import reporter.ExtentReportManager;

public class AccountSteps extends BaseTest {
    private HomePage homePage;
    private CreateAccountPage createAccountPage;
    private MyAccountPage myAccountPage;
    private LoginPage loginPage;
    private String email;
    private String password = "Test@1234";

    @Given("I navigate to the home page")
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage("https://magento.softwaretestingboard.com/");
    }

    @When("I click on {string} link")
    public void clickOnLink(String linkText) {
        if (linkText.equals("Create an Account")) {
            createAccountPage = homePage.clickCreateAccountLink();
        } else if (linkText.equals("Sign In")) {
            loginPage = homePage.clickSignInLink();
        }
    }

    @When("I fill in the account creation form with valid details")
    public void fillAccountCreationForm() {
        email = RandomGenerator.generateRandomEmail();
        createAccountPage.enterFirstName("Test");
        createAccountPage.enterLastName("User");
        createAccountPage.enterEmail(email);
        createAccountPage.enterPassword(password);
        createAccountPage.enterConfirmPassword(password);
    }

    @When("I submit the account creation form")
    public void submitAccountCreationForm() {
        myAccountPage = createAccountPage.clickCreateAccountButton();
    }

    @Then("I should see my account dashboard")
    public void verifyAccountDashboard() {
        myAccountPage.verifyPageTitle();
        ExtentReportManager.getInstance().createTest("Verify Account Dashboard")
                .pass("Successfully created account and logged in");
    }

    @When("I logout from the application")
    public void logoutFromApplication() {
        homePage = myAccountPage.clickLogout();
    }

    @When("I login with the newly created credentials")
    public void loginWithNewCredentials() {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        myAccountPage = loginPage.clickSignIn();
    }
}