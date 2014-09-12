Feature: Fill information
  This feature allows the user to input his personal information to the app.

  Scenario: Inputting information
    Given I press the "Input your info" button
    Then I press "Male"
    Then I select "25–35" from "ageSelectionSpinner"
