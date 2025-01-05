Feature: Validate Error Message functionality

Background: To launch the browser for validating the scenarios
Given User launched desired browser and navigate to ecommerce login page and prepares the incorrect credentials

@ErrorMessage
Scenario: Validate error message when provided incorrect username and password
Given User is on ecommerce login page and login with incorrect username and password
Then User validate error message on login page



