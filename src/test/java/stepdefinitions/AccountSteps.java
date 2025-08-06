package stepdefinitions;

import com.aventstack.extentreports.Status;
import core.Reporter;
import io.cucumber.java.en.*;
import pages.*;
import utils.DataGenerator;
import static core.Reporter.logStep;

public class AccountSteps {
    private HomePage homePage;
    private CreateAccountPage createAccountPage;
    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private String generatedEmail;
    private final String TEST_PASSWORD = "Test@1234";

    // ================== Common Steps ================== //

    @Given("I navigate to the Magento homepage")
    public void navigateToHomepage() {
        homePage = new HomePage();
        homePage.navigateToHomePage();
        logStep("Navigated to Magento homepage", Status.PASS);
    }

    @When("I click on the Create Account Link")
    public void clickCreateAccountLink() {
        createAccountPage = homePage.clickCreateAccount();
        logStep("Clicked Create Account link", Status.PASS);
    }

    @When("I click on the Sign In Link")
    public void clickSignInLink() {
        loginPage = homePage.clickSignIn();
        logStep("Clicked Sign In link", Status.PASS);
    }

    // ================== Registration Flow ================== //

    @Given("I have a registered account")
    public void createRegisteredAccount() {
        // Create new account
        clickCreateAccountLink();
        fillRegistrationForm();
        submitRegistrationForm();

        // Logout to return to homepage
        homePage = myAccountPage.logout();
        logStep("Created registered account and logged out", Status.PASS);
    }

    @When("I fill the registration form with valid details")
    public void fillRegistrationForm() {
        generatedEmail = DataGenerator.randomEmail();
        createAccountPage.enterFirstName(DataGenerator.randomFirstName());
        createAccountPage.enterLastName(DataGenerator.randomLastName());
        createAccountPage.enterEmail(generatedEmail);
        createAccountPage.enterPassword(TEST_PASSWORD);
        createAccountPage.enterConfirmPassword(TEST_PASSWORD);
        logStep("Filled registration form with email: " + generatedEmail, Status.PASS);
    }

    @When("I enter first name {string}")
    public void enterFirstName(String firstName) {
        createAccountPage.enterFirstName(firstName);
        logStep("Entered first name: " + firstName, Status.PASS);
    }

    @When("I enter last name {string}")
    public void enterLastName(String lastName) {
        createAccountPage.enterLastName(lastName);
        logStep("Entered last name: " + lastName, Status.PASS);
    }

    @When("I enter email {string}")
    public void enterEmail(String email) {
        createAccountPage.enterEmail(email);
        logStep("Entered email: " + email, Status.PASS);
    }

    @When("I enter password {string}")
    public void enterPassword(String password) {
        createAccountPage.enterPassword(password);
        logStep("Entered password", Status.PASS);
    }

    @When("I enter confirm password {string}")
    public void enterConfirmPassword(String password) {
        createAccountPage.enterConfirmPassword(password);
        logStep("Entered confirm password", Status.PASS);
    }

 @When("I submit the registration form")
public void submitRegistrationForm() {
    myAccountPage = createAccountPage.submitRegistration();

    // Initialize loginPage if registration fails
    if (myAccountPage == null) {
        loginPage = new LoginPage();
    }

    logStep("Submitted registration form", Status.PASS);
}

    @Then("I should see my account dashboard")
    public void verifyAccountDashboard() {
        String pageTitle = myAccountPage.getPageTitle();
        if (pageTitle.contains("My Account")) {
            logStep("Account dashboard verified. Title: " + pageTitle, Status.PASS);
        } else {
            logStep("Dashboard verification failed. Actual title: " + pageTitle, Status.FAIL);
            throw new AssertionError("Dashboard not displayed");
        }
    }

    // ================== Login Flow ================== //

    @When("I logout from my account")
    public void logoutFromAccount() {
        homePage = myAccountPage.logout();
        logStep("Logged out successfully", Status.PASS);
    }

    @When("I login with the created credentials")
    public void loginWithCreatedCredentials() {
        loginPage.enterEmail(generatedEmail);
        loginPage.enterPassword(TEST_PASSWORD);
        loginPage.clickLoginButton();
        myAccountPage = new MyAccountPage();
        logStep("Logged in with created credentials", Status.PASS);
    }

    @When("I click the login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
        myAccountPage = new MyAccountPage();
        logStep("Clicked login button", Status.PASS);
    }

    @Then("I should be logged in successfully")
    public void verifySuccessfulLogin() {
        if (myAccountPage.isLoggedIn()) {
            logStep("Login verification passed - User is logged in", Status.PASS);
        } else {
            logStep("Login verification failed", Status.FAIL);
            throw new AssertionError("User not logged in");
        }
    }

    // ================== Negative Testing ================== //

    @When("I attempt to register with an existing email")
    public void registerWithExistingEmail() {
        createAccountPage.fillRegistrationForm(
                "Existing",
                "User",
                "eugenio.upton@yahoo.com", // Pre-existing test email
                TEST_PASSWORD
        );
        createAccountPage.submitRegistration();
        loginPage = new LoginPage();
        logStep("Attempted registration with existing email", Status.INFO);
    }

  @Then("I should see an error message {string}")
public void verifyErrorMessage(String expectedMessage) {
    // Initialize loginPage if null
    if (loginPage == null) {
        loginPage = new LoginPage();
    }

    String actualMessage = loginPage.getErrorMessage();
    if (actualMessage.contains(expectedMessage)) {
        logStep("Error message verified: " + actualMessage, Status.PASS);
    } else {
        logStep("Expected error: '" + expectedMessage + "' but got: '" + actualMessage + "'", Status.FAIL);
        throw new AssertionError("Error message mismatch");
    }
}
}