Feature: Account Management
  Scenario: Successful user registration and login
    Given I navigate to the Magento homepage
    When I click on the Create Account link
    And I fill the registration form with valid details
    And I submit the registration form
    Then I should see my account dashboard
    When I logout from my account
    And I click on the Sign In link
    And I login with the created credentials
    Then I should be logged in successfully

  Scenario: Prevent duplicate registration
    Given I navigate to the Magento homepage
    When I click on the Create Account link
    And I attempt to register with an existing email
    Then I should see an error message "There is already an account"