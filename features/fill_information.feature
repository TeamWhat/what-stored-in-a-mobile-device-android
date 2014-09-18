Feature: Fill information
  This feature allows the user to input his personal information to the app.

  Scenario: Inputting information
    Given I press the "Input your info" button
    Then I press "Male"
    Then I select "25–35" from "ageSelectionSpinner"
    
  Scenario: Inputting gender
    Given I press the menu key
    Then I press "Settings" button
    Then I select "Male" from "settingsUserGenderListPreference"
    Then I should see "Male"
    Then I should not see "Female"
    
  Scenario: Inputting age
    Given I press the menu key
    Then I press "Settings" button
    Then I select "Over 35" from "settingsUserAgeListPreference"
    Then I should see "Over 35"
    Then I should not see "Under 18"
    
  Scenario: Inputting frequency
    Given I press the menu key
    Then I press "Settings" button
    Then I toggle checkbox number 0
    Then I select "Weekly" from "settingsDataSendingFrequencyListPreference"
    Then I should see "Weekly"
    Then I should not see "Monthly"
    Then I should not see "Daily"
    
