# account.feature

Feature: Account Management
  As a Magento user
  I want to manage my account
  So that I can shop securely on the platform

  Background:
    Given I navigate to the Magento homepage

  @positive @Login
  Scenario: Successful user registration and login
    When I click on the Create Account Link
    And I fill the registration form with valid details
    And I submit the registration form
    Then I should see my account dashboard
    When I logout from my account
    And I click on the Sign In Link
    And I login with the created credentials
    Then I should be logged in successfully

  @negative
  Scenario: Prevent duplicate registration
    When I click on the Create Account Link
    And I attempt to register with an existing email
    Then I should see an error message "There is already an account"

  @positive
  Scenario: Successful login with valid credentials
    Given I have a registered account
    When I click on the Sign In Link
    And I login with the created credentials
    Then I should be logged in successfully

  @negative
  Scenario: Prevent login with invalid credentials
    Given I have a registered account
    When I click on the Sign In Link
    And I enter email "invalid@example.com"
    And I enter password "wrongpassword"
    And I click the login button
    Then I should see an error message "The account sign-in was incorrect"

  @security
  Scenario: Validate password strength requirements
    When I click on the Create Account Link
    And I enter first name "Test"
    And I enter last name "User"
    And I enter email "test@example.com"
    And I enter password "weak"
    And I enter confirm password "weak"
    And I submit the registration form
    Then I should see an error message "Minimum length of this field must be equal or greater than 8 characters"