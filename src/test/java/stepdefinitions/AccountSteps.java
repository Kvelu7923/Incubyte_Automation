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

    // ================== Account Creation ================== //

    @Given("I navigate to the Magento homepage")
    public void navigateToHomepage() {
        homePage = new HomePage();
        homePage.navigateToHomePage();
        logStep("Navigated to Magento homepage", Status.PASS);
    }

    @When("I click on the Create Account link")
    public void clickCreateAccountLink() {
        createAccountPage = homePage.clickCreateAccount();
        logStep("Clicked Create Account link", Status.PASS);
    }

    @When("I fill the registration form with valid details")
    public void fillRegistrationForm() {
        generatedEmail = DataGenerator.randomEmail();
        createAccountPage.fillRegistrationForm(
                "Test",
                "User",
                generatedEmail,
                TEST_PASSWORD
        );
        logStep("Filled registration form with email: " + generatedEmail, Status.PASS);
    }

    @When("I submit the registration form")
    public void submitRegistrationForm() {
        myAccountPage = createAccountPage.submitRegistration();
        logStep("Submitted registration form", Status.PASS);
    }

    @Then("I should see my account dashboard")
    public void verifyAccountDashboard() {
        String pageTitle = myAccountPage.getPageTitle();
        if (pageTitle.contains("My Account")) {
            logStep("Account created successfully. Dashboard title: " + pageTitle, Status.PASS);
        } else {
            logStep("Failed to verify dashboard. Actual title: " + pageTitle, Status.FAIL);
            throw new AssertionError("Dashboard verification failed");
        }
    }

    // ================== logStepin Flow ================== //

    @When("I logStepout from my account")
    public void logStepoutFromAccount() {
        homePage = myAccountPage.logout();
        logStep("logStepged out successfully", Status.PASS);
    }

    @When("I click on the Sign In link")
    public void clickSignInLink() {
        loginPage = homePage.clickSignIn();
        logStep("Clicked Sign In link", Status.PASS);
    }

    @When("I logStepin with the created credentials")
    public void logStepinWithCreatedCredentials() {
        loginPage.enterEmail(generatedEmail);
        loginPage.enterPassword(TEST_PASSWORD);
        myAccountPage = loginPage.clickSignIn();
        logStep("logStepged in with created credentials", Status.PASS);
    }

    @Then("I should be logStepged in successfully")
    public void verifySuccessfullogStepin() {
        if (myAccountPage.isLoggedIn()) {
            logStep("logStepin verified - User is logStepged in", Status.PASS);
        } else {
            logStep("logStepin verification failed", Status.FAIL);
            throw new AssertionError("logStepin verification failed");
        }
    }

    // ================== Negative Tests ================== //

    @When("I attempt to register with an existing email")
    public void registerWithExistingEmail() {
        createAccountPage.fillRegistrationForm(
                "Existing",
                "User",
                "existing@example.com", // Known existing email
                TEST_PASSWORD
        );
        createAccountPage.submitRegistration();
        logStep("Attempted registration with existing email", Status.INFO);
    }

    @Then("I should see an error message {string}")
    public void verifyErrorMessage(String expectedMessage) {
        String actualMessage = loginPage.getErrorMessage();
        if (actualMessage.contains(expectedMessage)) {
            logStep("Correct error message displayed: " + actualMessage, Status.PASS);
        } else {
            logStep("Expected error: '" + expectedMessage + "' but got: '" + actualMessage + "'", Status.FAIL);
            throw new AssertionError("Error message verification failed");
        }
    }
}