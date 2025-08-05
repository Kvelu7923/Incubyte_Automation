Feature: Account Creation and Login
  As a new user
  I want to create an account and login
  So that I can access my account on the website

  Scenario: Successful account creation and login
    Given I navigate to the home page
    When I click on "Create an Account" link
    And I fill in the account creation form with valid details
    And I submit the account creation form
    Then I should see my account dashboard
    When I logout from the application
    And I login with the newly created credentials
    Then I should see my account dashboard again